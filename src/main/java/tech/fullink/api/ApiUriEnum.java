package tech.fullink.api;

/**
 * URI映射
 * @author crow
 */
public enum ApiUriEnum {
    /***/
    LXF("fullink.lxf.personal", "/lxf"),
    LXF_ENTERPRISE("fullink.lxf.enterprise", "/enterprise"),
    ;
    private final String method;
    private final String uri;

    ApiUriEnum(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    public static String getUri(String method) {
        for (ApiUriEnum e : ApiUriEnum.values()) {
            if (e.getMethod().equals(method)) {
                return e.getUri();
            }
        }
        throw new RuntimeException(String.format("method:%s is not found", method));
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }
}
