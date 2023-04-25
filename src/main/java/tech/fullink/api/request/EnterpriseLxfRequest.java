package tech.fullink.api.request;

import tech.fullink.api.FullinkApiException;
import tech.fullink.api.FullinkObject;
import tech.fullink.api.FullinkRequest;
import tech.fullink.api.response.EnterpriseLxfResponse;
import tech.fullink.api.util.DesEncrypt;
import tech.fullink.api.util.StringUtils;

/**
 * @author crow
 */
public class EnterpriseLxfRequest implements FullinkRequest<EnterpriseLxfResponse> {
    private String apiVersion = "1.0";

    private String customerId;

    private String customerProdId;

    private String customerRequestId;

    private Long timestamp;

    private String sign;

    private String uscc;

    private String mobile;

    private String name;

    private String idCardNo;

    private String orgName;

    @Override
    public String getApiMethodName() {
        return "fullink.lxf.enterprise";
    }

    @Override
    public String getApiVersion() {
        return this.apiVersion;
    }

    @Override
    public Class<EnterpriseLxfResponse> getResponseClass() {
        return EnterpriseLxfResponse.class;
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
        StringBuilder sb = new StringBuilder();
        sb.append("customerId=").append(this.customerId)
                .append("&customerProdId=").append(this.customerProdId)
                .append("&customerRequestId=").append(this.customerRequestId)
                .append("&ucc=").append(this.uscc)
                .append("&mobile=").append(null == this.mobile ? "" : this.mobile);
        if (!StringUtils.isEmpty(this.name)) {
            sb.append("&name=").append(this.name);
        }
        if (!StringUtils.isEmpty(this.idCardNo)) {
            sb.append("&idCardNo=").append(this.idCardNo);
        }
        if (!StringUtils.isEmpty(this.orgName)) {
            sb.append("&orgName=").append(this.orgName);
        }
        sb.append("&timestamp=").append(timestamp);
        try {
            this.sign = DesEncrypt.encrypt(sb.toString(), sign, "utf-8");
        } catch (FullinkApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSign() {
        return this.sign;
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

    public String getUscc() {
        return uscc;
    }

    public void setUscc(String uscc) {
        this.uscc = uscc;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public String getBizData() {
        return null;
    }

    @Override
    public void setBizData(String bizData) {

    }

    @Override
    public FullinkObject getBizModel() {
        return null;
    }

    @Override
    public void setBizModel(FullinkObject bizModel) {

    }
}
