package tech.fullink.api.request;

import tech.fullink.api.FullinkApiException;
import tech.fullink.api.FullinkObject;
import tech.fullink.api.FullinkRequest;
import tech.fullink.api.response.CommonLxfResponse;
import tech.fullink.api.util.Md5Encrypt;

/**
 * @author crow
 */
public class CommonLxfRequest implements FullinkRequest<CommonLxfResponse> {
    private String apiVersion = "1.0";

    private String customerId;

    private String customerProdId;

    private String customerRequestId;

    private Long timestamp;

    private String bizData;

    private FullinkObject bizModel;

    private String sign;

    private String prune;

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerProdId() {
        return customerProdId;
    }

    public void setCustomerProdId(String customerProdId) {
        this.customerProdId = customerProdId;
    }

    public String getCustomerRequestId() {
        return customerRequestId;
    }

    public void setCustomerRequestId(String customerRequestId) {
        this.customerRequestId = customerRequestId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getApiMethodName() {
        return "fullink.lxf.common";
    }

    @Override
    public String getApiVersion() {
        return this.apiVersion;
    }

    @Override
    public Class<CommonLxfResponse> getResponseClass() {
        return CommonLxfResponse.class;
    }

    @Override
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public void setSign(String sign) {
        // 此处sign入参作为desKey使用
        if (null == this.timestamp) {
            this.timestamp = System.currentTimeMillis();
        }
        String sourceContent = "customerId=" + this.getCustomerId() +
            "&customerProdId=" + this.getCustomerProdId() +
            "&customerRequestId=" + this.getCustomerRequestId() +
            "&timestamp=" + this.timestamp +
            "&bizData=" + this.bizData +
            "&encryptKey=" + sign;
        try {
            this.sign = Md5Encrypt.encrypt(sourceContent);
        } catch (FullinkApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSign() {
        return this.sign;
    }

    @Override
    public String getBizData() {
        return bizData;
    }

    @Override
    public void setBizData(String bizData) {
        this.bizData = bizData;
    }

    @Override
    public FullinkObject getBizModel() {
        return this.bizModel;
    }

    @Override
    public void setBizModel(FullinkObject bizModel) {
        this.bizModel = bizModel;
    }

    public String getPrune() {
        return prune;
    }

    public void setPrune(String prune) {
        this.prune = prune;
    }
}
