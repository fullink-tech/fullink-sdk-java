package tech.fullink.api.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import tech.fullink.api.ApiErrorEnum;
import tech.fullink.api.FullinkApiException;

/**
 * @author crow
 */
public class Md5Encrypt {
    public static final String MD5_ALG = "MD5";

    private Md5Encrypt () {}

    public static String encrypt(String content) throws FullinkApiException {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5_ALG);
            byte[] bytes = md.digest(content.getBytes(StandardCharsets.UTF_8));

            char[] hexArr = "0123456789abcdef".toCharArray();
            StringBuilder ret = new StringBuilder(bytes.length * 2);

            for (byte aByte : bytes) {
                ret.append(hexArr[aByte >> 4 & 15]);
                ret.append(hexArr[aByte & 15]);
            }
            return ret.toString();
        } catch (Exception e) {
            throw new FullinkApiException(String.format(ApiErrorEnum.ENCRYPT_MD5_ERROR.getErrMsg(), content), e);
        }
    }

}
