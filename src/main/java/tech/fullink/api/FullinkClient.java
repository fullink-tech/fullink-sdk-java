package tech.fullink.api;

/**
 * @author crow
 */
public interface FullinkClient {

    void setLogger(Boolean enabled);

    <T extends FullinkResponse> T execute(FullinkRequest<T> request);

    <T extends FullinkResponse> T commonExecute(FullinkRequest<T> request);
}
