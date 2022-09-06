package tech.fullink.api;

/**
 * @author crow
 */
public interface FullinkClient {

    void setLogger(Boolean enabled);

    <T extends FullinkResponse> T execute(FullinkRequest<T> request);
}
