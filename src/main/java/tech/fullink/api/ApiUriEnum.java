package tech.fullink.api;

/**
 * URI映射
 * @author crow
 */
public enum ApiUriEnum {
    /***/
    LXF("fullink.lxf.personal", "/report/encode"),
    LXF_ENTERPRISE("fullink.lxf.enterprise", "/enterprise"),

    LXF_COMMON("fullink.lxf.common", "/common")
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
