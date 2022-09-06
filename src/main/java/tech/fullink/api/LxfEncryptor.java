package tech.fullink.api;

import tech.fullink.api.util.DesEncrypt;

/**
 * @author crow
 */
public class LxfEncryptor {
    public LxfEncryptor() {}

    public static String encryptContent(String content, String desKey, String charset) throws FullinkApiException {
        return DesEncrypt.encrypt(content, desKey, charset);
    }

    public static String decryptContent(String content, String desKey, String charset) throws FullinkApiException {
        return DesEncrypt.decrypt(content, desKey, charset);
    }

}
