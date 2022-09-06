package tech.fullink.api;

/**
 * @author crow
 */
public class FullinkApiException extends Exception {
    private String errCode;
    private String errMsg;

    public FullinkApiException() {
    }

    public FullinkApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public FullinkApiException(ApiErrorEnum messageEnum, Throwable cause) {
        super(messageEnum.getErrMsg(), cause);
    }

    public FullinkApiException(String message) {
        super(message);
    }

    public FullinkApiException(ApiErrorEnum messageEnum) {
        super(messageEnum.getErrMsg());
    }

    public FullinkApiException(Throwable cause) {
        super(cause);
    }

    public FullinkApiException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
