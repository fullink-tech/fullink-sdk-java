package tech.fullink.api;

/**
 * @author crow
 */
public interface Encryptor {
    /***
     * 加密
     */
    String encrypt(String sourceContent, String charset);

    /**
     * 获取密钥
     */
    String getEncryptKey();
}
