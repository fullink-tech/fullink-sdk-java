package tech.fullink.api;

import com.alibaba.fastjson.JSON;
import tech.fullink.api.util.StringUtils;
import tech.fullink.api.util.WebUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author crow
 */
public abstract class AbstractFullinkClient implements FullinkClient {
    private String serverUrl;

    private String charset;
    private int connectTimeout;
    private int readTimeout;
    private Map<String, String> headers;

    public AbstractFullinkClient(String serverUrl, String charset) {
        this.connectTimeout = 3000;
        this.readTimeout = 15000;
        this.serverUrl = serverUrl;
        this.charset = charset;
    }

    public AbstractFullinkClient(String serverUrl, String charset, int connectTimeout, int readTimeout) {
        this.serverUrl = serverUrl;
        this.charset = charset;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    public void setLogger(Boolean enabled) {
        SdkLogger.setNeedEnableLogger(enabled);
    }

    @Override
    public <T extends FullinkResponse> T execute(FullinkRequest<T> request) {
        T tRsp = null;
        long beginTime = System.currentTimeMillis();

        try {
            Map<String, Object> rt = this.doPost(request);
            Map<String, Long> costTimeMap = new HashMap();
            if (rt.containsKey("prepareTime")) {
                costTimeMap.put("prepareCostTime", (Long)rt.get("prepareTime") - beginTime);
                if (rt.containsKey("requestTime")) {
                    costTimeMap.put("requestCostTime", (Long)rt.get("requestTime") - (Long)rt.get("prepareTime"));
                }
            }

            String rsp = (String) rt.get("rsp");

            tRsp = JSON.parseObject(rsp, request.getResponseClass());

            if (costTimeMap.containsKey("requestCostTime")) {
                costTimeMap.put("postCostTime", System.currentTimeMillis() - (Long)rt.get("requestTime"));
            }

            if (!tRsp.isOk()) {
                SdkLogger.logErrorScene(rt, tRsp, costTimeMap);
            } else {
                SdkLogger.logBizSummary(rt, tRsp, costTimeMap);
            }
            ResponseEncryptItem encryptItem = this.decryptResponse(tRsp);

            FullinkHashMap score = JSON.parseObject(encryptItem.getRealContent(), FullinkHashMap.class);
            tRsp.setScore(score);
        } catch (FullinkApiException e) {
            throw new RuntimeException(e);
        }

        return tRsp;
    }

    private <T extends FullinkResponse> Map<String, Object> doPost(FullinkRequest<T> request) throws FullinkApiException {
        Map<String, Object> result = new HashMap();
        String requestUrl = this.serverUrl + ApiUriEnum.getUri(request.getApiMethodName());
        String jsonContent = this.jsonContentWithSign(request);

        result.put("prepareTime", System.currentTimeMillis());
        String rsp;
        Map<String, String> resHeaders = new HashMap();
        try {
            rsp = WebUtils.doPost(requestUrl, jsonContent, this.charset, this.connectTimeout, this.readTimeout, headers, resHeaders);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        result.put("requestTime", System.currentTimeMillis());
        result.put("jsonContent", jsonContent);
        result.put("rsp", rsp);
        return result;
    }

    private <T extends FullinkResponse> String jsonContentWithSign(FullinkRequest<T> request) {
        request.setSign(this.getEncryptor().getEncryptKey());
        return JSON.toJSONString(request);
    }

    private <T extends FullinkResponse> ResponseEncryptItem decryptResponse(T t) throws FullinkApiException {
        String data = t.getData();
        if (t.isOk() && StringUtils.isEmpty(data)) {
            throw new FullinkApiException(ApiErrorEnum.RESPONSE_BODY_EMPTY_ERROR);
        }
        String realData = null;
        if (null == this.getDecryptor() || !t.isOk()) {
            realData = data;
        } else {
            realData = this.getDecryptor().decrypt(data, charset);
        }
        return new ResponseEncryptItem(data, realData);
    }

    public abstract Encryptor getEncryptor();

    public abstract Decryptor getDecryptor();
}
