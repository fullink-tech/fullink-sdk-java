package tech.fullink.api;

/**
 * @author crow
 */
public class DefaultDecryptor implements Decryptor {
    private String encryptKey;

    public DefaultDecryptor(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    @Override
    public String decrypt(String encryptContent, String charset) {
        try {
            return LxfEncryptor.decryptContent(encryptContent, encryptKey, charset);
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
