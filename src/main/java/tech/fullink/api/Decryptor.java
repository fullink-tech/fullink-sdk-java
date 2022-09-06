package tech.fullink.api;

/**
 * @author crow
 */
public interface Decryptor {
    /***
     * 解密
     */
    String decrypt(String encryptContent, String charset);
    /**
     * 获取密钥
     */
    String getEncryptKey();
}
