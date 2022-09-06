package tech.fullink.api.request;

import tech.fullink.api.FullinkApiException;
import tech.fullink.api.FullinkRequest;
import tech.fullink.api.response.PersonalLxfResponse;
import tech.fullink.api.util.DesEncrypt;

/**
 * @author crow
 */
public class PersonalLxfRequest implements FullinkRequest<PersonalLxfResponse> {
    private String apiVersion = "1.0";

    private String customerId;

    private String customerProdId;

    private String customerRequestId;

    private Long timestamp;

    private String sign;

    private String name;

    private String mobile;

    private String idCardNo;

    @Override
    public String getApiMethodName() {
        return "fullink.lxf.personal";
    }

    @Override
    public String getApiVersion() {
        return this.apiVersion;
    }

    @Override
    public Class<PersonalLxfResponse> getResponseClass() {
        return PersonalLxfResponse.class;
    }

    @Override
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getSign() {
        return this.sign;
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
                "&name=" + this.name +
                "&mobile=" + this.mobile +
                "&idCardNo=" + this.idCardNo +
                "&timestamp=" + this.timestamp;
        try {
            this.sign = DesEncrypt.encrypt(sourceContent, sign, "utf-8");
        } catch (FullinkApiException e) {
            throw new RuntimeException(e);
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }
}
