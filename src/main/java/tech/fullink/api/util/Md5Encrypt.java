package tech.fullink.api.util;

import java.math.BigInteger;
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
            md.update(content.getBytes(StandardCharsets.UTF_8));
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new FullinkApiException(String.format(ApiErrorEnum.ENCRYPT_MD5_ERROR.getErrMsg(), content), e);
        }
    }

}
