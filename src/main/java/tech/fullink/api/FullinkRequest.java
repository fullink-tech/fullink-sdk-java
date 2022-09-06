package tech.fullink.api;

/**
 * @author crow
 */
public interface FullinkRequest<T extends FullinkResponse> {
    String getApiMethodName();
    /**
     * api版本号
     * @return
     */
    String getApiVersion();

    Class<T> getResponseClass();

    void setTimestamp(Long timestamp);

    void setSign(String sign);

    String getSign();
}
