package tech.fullink.api.util;

import tech.fullink.api.SdkLogger;

import javax.net.ssl.*;
import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author crow
 */
public class WebUtils {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";
    private static SSLContext ctx = null;
    private static HostnameVerifier verifier = null;
    private static SSLSocketFactory socketFactory = null;
    private static int keepAliveTimeout = 0;
    private static volatile boolean needCheckServerTrusted = true;

    private WebUtils() {
    }

    public static void setNeedCheckServerTrusted(boolean needCheckServerTrusted) {
        WebUtils.needCheckServerTrusted = needCheckServerTrusted;
    }

    public static void setKeepAliveTimeout(int timeout) {
        if (timeout >= 0 && timeout <= 60) {
            keepAliveTimeout = timeout;
        } else {
            throw new RuntimeException("keep-alive timeout value must be between 0 and 60.");
        }
    }

    public static String doPost(String url, String jsonContent, String charset, int connectTimeout, int readTimeout, Map<String, String> headers, Map<String, String> resHeaders) throws IOException {
        String contentType = "application/json";
        byte[] content = jsonContent.getBytes(charset);
        return doPost(url, contentType, content, connectTimeout, readTimeout, headers, resHeaders);
    }

    public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout, Map<String, String> headers, Map<String, String> resHeaders) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;

        try {
            try {
                conn = getConnection(new URL(url), METHOD_POST, ctype, headers);

                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            } catch (IOException e) {
                SdkLogger.logCommError(e, url, content);
                throw e;
            }

            try {
                out = conn.getOutputStream();
                out.write(content);
                rsp = getResponseAsString(conn);
                if (resHeaders != null) {
                    resHeaders.put("trace_id", conn.getHeaderField("trace_id"));
                }
            } catch (IOException e) {
                SdkLogger.logCommError(e, conn, content);
                throw e;
            }
        } finally {
            if (out != null) {
                out.close();
            }

            if (conn != null) {
                conn.disconnect();
            }

        }

        return rsp;
    }

    public static HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headers) throws IOException {
        return getConnection(url, method, ctype, (Proxy)null, headers);
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype, Proxy proxy, Map<String, String> headers) throws IOException {
        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            HttpsURLConnection connHttps = null;
            if (proxy != null) {
                connHttps = (HttpsURLConnection)url.openConnection(proxy);
            } else {
                connHttps = (HttpsURLConnection)url.openConnection();
            }

            if (!needCheckServerTrusted) {
                connHttps.setSSLSocketFactory(socketFactory);
                connHttps.setHostnameVerifier(verifier);
            }

            conn = connHttps;
        } else {
            conn = null;
            if (proxy != null) {
                conn = (HttpURLConnection)url.openConnection(proxy);
            } else {
                conn = (HttpURLConnection)url.openConnection();
            }
        }

        ((HttpURLConnection)conn).setRequestMethod(method);
        ((HttpURLConnection)conn).setDoInput(true);
        ((HttpURLConnection)conn).setDoOutput(true);
        ((HttpURLConnection)conn).setRequestProperty("Accept", "text/plain,text/xml,text/javascript,text/html,application/json");
        ((HttpURLConnection)conn).setRequestProperty("User-Agent", "aop-sdk-java");
        ((HttpURLConnection)conn).setRequestProperty("Content-Type", ctype);
        if (headers != null) {
            Iterator var8 = headers.entrySet().iterator();

            while(var8.hasNext()) {
                Map.Entry<String, String> header = (Map.Entry)var8.next();
                ((HttpURLConnection)conn).setRequestProperty((String)header.getKey(), (String)header.getValue());
            }
        }

        return (HttpURLConnection)conn;
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        setKeepAliveTimeout(conn);
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (StringUtils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();
            char[] chars = new char[256];

            int count;
            while((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }

            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;
        if (!StringUtils.isEmpty(ctype)) {
            String[] params = ctype.split(";");
            String[] var3 = params;
            int var4 = params.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String param = var3[var5];
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2 && !StringUtils.isEmpty(pair[1])) {
                        charset = pair[1].trim();
                    }
                    break;
                }
            }
        }

        return charset;
    }

    public static String decode(String value, String charset) {
        String result = null;
        if (!StringUtils.isEmpty(value)) {
            try {
                result = URLDecoder.decode(value, charset);
            } catch (IOException var4) {
                throw new RuntimeException(var4);
            }
        }

        return result;
    }

    public static String encode(String value, String charset) {
        String result = null;
        if (!StringUtils.isEmpty(value)) {
            try {
                result = URLEncoder.encode(value, charset);
            } catch (IOException var4) {
                throw new RuntimeException(var4);
            }
        }

        return result;
    }

    private static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> map = null;
        if (url != null && url.indexOf(13) != -1) {
            map = splitUrlQuery(url.substring(url.indexOf(63) + 1));
        }

        if (map == null) {
            map = new HashMap();
        }

        return (Map)map;
    }

    public static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap();
        String[] pairs = query.split("&");
        if (pairs != null && pairs.length > 0) {
            String[] var3 = pairs;
            int var4 = pairs.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String pair = var3[var5];
                String[] param = pair.split("=", 2);
                if (param != null && param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }

        return result;
    }

    private static void setKeepAliveTimeout(HttpURLConnection connection) {
        if (keepAliveTimeout != 0) {
            try {
                Field delegateHttpsUrlConnectionField = Class.forName("sun.net.www.protocol.https.HttpsURLConnectionImpl").getDeclaredField("delegate");
                delegateHttpsUrlConnectionField.setAccessible(true);
                Object delegateHttpsUrlConnection = delegateHttpsUrlConnectionField.get(connection);
                Field httpClientField = Class.forName("sun.net.www.protocol.http.HttpURLConnection").getDeclaredField("http");
                httpClientField.setAccessible(true);
                Object httpClient = httpClientField.get(delegateHttpsUrlConnection);
                Field keepAliveTimeoutField = Class.forName("sun.net.www.http.HttpClient").getDeclaredField("keepAliveTimeout");
                keepAliveTimeoutField.setAccessible(true);
                keepAliveTimeoutField.setInt(httpClient, keepAliveTimeout);
            } catch (Throwable var6) {
            }

        }
    }

    static {
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            ctx.getClientSessionContext().setSessionTimeout(15);
            ctx.getClientSessionContext().setSessionCacheSize(1000);
            socketFactory = ctx.getSocketFactory();
        } catch (Exception var1) {
        }

        verifier = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return false;
            }
        };
    }

    private static class DefaultTrustManager implements X509TrustManager {
        private DefaultTrustManager() {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }
}
