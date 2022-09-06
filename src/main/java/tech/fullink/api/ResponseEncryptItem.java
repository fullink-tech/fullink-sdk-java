package tech.fullink.api;

/**
 * @author crow
 */
public class ResponseEncryptItem {
    private static final long serialVersionUID = 6680775791485372169L;
    private String respContent;
    private String realContent;

    public ResponseEncryptItem(String respContent, String realContent) {
        this.respContent = respContent;
        this.realContent = realContent;
    }

    public String getRespContent() {
        return this.respContent;
    }

    public String getRealContent() {
        return this.realContent;
    }
}
