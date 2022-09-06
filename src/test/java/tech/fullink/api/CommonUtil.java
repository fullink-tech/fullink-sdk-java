package tech.fullink.api;

import java.util.UUID;

/**
 * @author crow
 */
public class CommonUtil {
    private CommonUtil() {}

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
