package tech.fullink.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author crow
 */
public class SdkLogger {
    private static final Log clog = LogFactory.getLog("sdk.comm.err");
    private static final Log blog = LogFactory.getLog("sdk.biz.err");
    private static final Log ilog = LogFactory.getLog("sdk.biz.info");
    private static String osName = System.getProperties().getProperty("os.name");
    private static String ip = null;
    private static boolean needEnableLogger = true;

    public static void setNeedEnableLogger(boolean needEnableLogger) {
        SdkLogger.needEnableLogger = needEnableLogger;
    }

    public static String getIp() {
        if (ip == null) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception var1) {
                // do nothing
            }
        }
        return ip;
    }

    public static void logCommError(Exception e, HttpURLConnection conn, byte[] content) {
        if (needEnableLogger) {
            String contentString = null;

            try {
                contentString = new String(content, "UTF-8");
                logCommError(e, conn, contentString);
            } catch (Exception var7) {
                var7.printStackTrace();
            }

        }
    }

    public static void logCommError(Exception e, String url, byte[] content) {
        if (needEnableLogger) {
            String contentString = null;

            try {
                contentString = new String(content, "UTF-8");
                logCommError(e, url, contentString);
            } catch (UnsupportedEncodingException var7) {
                var7.printStackTrace();
            }

        }
    }

    private static void logCommError(Exception e, HttpURLConnection conn, String content) {
        _logCommError(e, conn, (String)null, content);
    }

    private static void logCommError(Exception e, String url, String content) {
        _logCommError(e, null, url, content);
    }

    private static void _logCommError(Exception e, HttpURLConnection conn, String url, String content) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String sdkName = "fullink-sdk-java";
        String urlStr;
        if (conn != null) {
            urlStr = conn.getURL().toString();
        } else {
            urlStr = url;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(df.format(new Date()));
        sb.append("^_^");
        sb.append(getIp());
        sb.append("^_^");
        sb.append(osName);
        sb.append("^_^");
        sb.append(sdkName);
        sb.append("^_^");
        sb.append(urlStr);
        sb.append("^_^");
        sb.append("Body:");
        sb.append(content);
        sb.append("^_^");
        sb.append((e.getMessage() + "").replaceAll("\r\n", " "));
        clog.error(sb.toString());
    }

    public static void logBizSummary(Map<String, Object> rt, FullinkResponse tRsp, Map<String, Long> costTimeMap) {
        if (needEnableLogger) {
            Map<String, Object> resultMap = rt == null ? new HashMap<>() : rt;
            StringBuilder sb = new StringBuilder();
            sb.append("Summary");
            sb.append("^_^");
            sb.append(tRsp.getStatus());
            sb.append("^_^");
            sb.append(tRsp.getInternalErrorCode());
            sb.append("^_^");
            sb.append("Request:");
            sb.append(resultMap.get("jsonContent"));
            sb.append("^_^");
            sb.append("Response:");
            sb.append(resultMap.get("rsp"));
            sb.append("^_^");
            sb.append(costTimeMap.get("prepareCostTime"));
            sb.append("ms,");
            sb.append(costTimeMap.get("requestCostTime"));
            sb.append("ms,");
            sb.append(costTimeMap.get("postCostTime"));
            sb.append("ms");
            ilog.info(sb.toString());
        }
    }

    public static void logErrorScene(Map<String, Object> rt, FullinkResponse tRsp, Map<String, Long> costTimeMap) {
        if (needEnableLogger) {
            Map<String, Object> resultMap = rt == null ? new HashMap<>() : rt;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            StringBuilder sb = new StringBuilder();
            sb.append("ErrorScene");
            sb.append("^_^");
            sb.append(tRsp.getStatus());
            sb.append("^_^");
            sb.append(tRsp.getInternalErrorCode());
            sb.append("^_^");
            sb.append(getIp());
            sb.append("^_^");
            sb.append(osName);
            sb.append("^_^");
            sb.append(df.format(new Date()));
            sb.append("^_^");
            sb.append("Request:");
            sb.append(resultMap.get("jsonContent"));
            sb.append("^_^");
            sb.append("Response:");
            sb.append(resultMap.get("rsp"));
            sb.append("^_^");
            sb.append(costTimeMap.get("prepareCostTime"));
            sb.append("ms,");
            sb.append(costTimeMap.get("requestCostTime"));
            sb.append("ms,");
            sb.append(costTimeMap.get("postCostTime"));
            sb.append("ms");
            blog.error(sb.toString());
        }
    }

}
