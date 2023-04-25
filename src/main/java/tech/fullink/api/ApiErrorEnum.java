package tech.fullink.api;

/**
 * @author crow
 */
public enum ApiErrorEnum {
    /***/
    SYSTEM_ERROR("9031001", "系统异常"),
    ENCRYPT_DES_ERROR("9031002", "DES加密失败：content = %s; charset = %s"),
    ENCRYPT_MD5_ERROR("9031002", "MD5加密失败：content = %s; charset = utf-8"),
    ENCRYPT_DES_CHARSET_ERROR("9031003", "DES加密失败，请检查编码格式是否正确：charset = %s"),
    DECRYPT_DES_ERROR("9031004", "DES解密失败：content = %s; charset = %s"),
    DECRYPT_DES_CHARSET_ERROR("9031005", "DES解密失败：请检查编码格式是否正确：charset = %s"),
    RESPONSE_BODY_EMPTY_ERROR("9031006", "响应字符串为空"),
    ;

    private final String errCode;
    private final String errMsg;

    ApiErrorEnum(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
