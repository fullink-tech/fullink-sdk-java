package tech.fullink.api.util;

import tech.fullink.api.ApiErrorEnum;
import tech.fullink.api.FullinkApiException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * @author crow
 */
public class DesEncrypt {
    public static final String DES_ALG = "DES";
    public static final String DES_CBC_PCK_ALG = "DES/CBC/PKCS5Padding";

    private DesEncrypt() {}

    public static String encrypt(String content, String desKey, String charset) throws FullinkApiException {
        try {
            Cipher cipher = Cipher.getInstance(DES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(desKey.getBytes());
            cipher.init(1, new SecretKeySpec(desKey.getBytes(), DES_ALG), iv);
            byte[] encryptBytes = cipher.doFinal(content.getBytes(charset));
            return byteToHexString(encryptBytes);
        } catch (UnsupportedEncodingException e) {
            throw new FullinkApiException(String.format(ApiErrorEnum.ENCRYPT_DES_CHARSET_ERROR.getErrMsg(), charset), e);
        } catch (Exception e) {
            throw new FullinkApiException(String.format(ApiErrorEnum.ENCRYPT_DES_ERROR.getErrMsg(), content, charset), e);
        }
    }

    public static String decrypt(String content, String desKey, String charset) throws FullinkApiException {
        try {
            Cipher cipher = Cipher.getInstance(DES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(desKey.getBytes());
            cipher.init(2, new SecretKeySpec(desKey.getBytes(), DES_ALG), iv);
            byte[] decryptBytes = cipher.doFinal(hexStringToBytes(content));
            return new String(decryptBytes, charset);
        } catch (UnsupportedEncodingException e) {
            throw new FullinkApiException(String.format(ApiErrorEnum.DECRYPT_DES_CHARSET_ERROR.getErrMsg(), charset), e);
        } catch (Exception e) {
            throw new FullinkApiException(String.format(ApiErrorEnum.DECRYPT_DES_ERROR.getErrMsg(), content, charset), e);
        }
    }

    public static String byteToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length);

        for (byte aByte : bytes) {
            String sTemp = Integer.toHexString(255 & aByte);
            if (sTemp.length() < 2) {
                sb.append(0);
            }

            sb.append(sTemp.toUpperCase());
        }

        return sb.toString();
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (hexString != null && !"".equals(hexString)) {
            hexString = hexString.toUpperCase();
            int length = hexString.length() / 2;
            char[] hexChars = hexString.toCharArray();
            byte[] d = new byte[length];
            for(int i = 0; i < length; ++i) {
                int pos = i * 2;
                d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            }
            return d;
        } else {
            return null;
        }
    }

    private static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }
}
