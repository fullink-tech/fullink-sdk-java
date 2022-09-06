package tech.fullink.api;

/**
 * @author crow
 */
public interface Parser<T extends FullinkResponse> {
    T parse(String rsp) throws FullinkApiException;

    Class<T> getResponseClass() throws FullinkApiException;


    String decryptSourceData(FullinkRequest<?> request, String body, String format, Decryptor decryptor, String encryptType, String charset) throws FullinkApiException;
}
