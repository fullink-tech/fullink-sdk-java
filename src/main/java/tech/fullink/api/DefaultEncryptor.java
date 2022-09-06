package tech.fullink.api;

/**
 * @author crow
 */
public class DefaultEncryptor implements Encryptor {
    private String encryptKey;

    public DefaultEncryptor(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    @Override
    public String encrypt(String sourceContent, String charset) {
        try {
            return LxfEncryptor.encryptContent(sourceContent, encryptKey, charset);
        } catch (FullinkApiException e) {
            throw new RuntimeException(e);
        }
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }
}
