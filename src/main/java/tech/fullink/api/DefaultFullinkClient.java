package tech.fullink.api;

/**
 * @author crow
 */
public class DefaultFullinkClient extends AbstractFullinkClient{
    private String customerCode;
    private String key;
    private Encryptor encryptor;
    private Decryptor decryptor;

    public DefaultFullinkClient(String serverUrl, String customerCode, String key) {
        super(serverUrl, "utf-8");
        this.customerCode = customerCode;
        this.key = key;
        this.encryptor = new DefaultEncryptor(key);
        this.decryptor = new DefaultDecryptor(key);
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Encryptor getEncryptor() {
        return this.encryptor;
    }

    @Override
    public Decryptor getDecryptor() {
        return this.decryptor;
    }

}
