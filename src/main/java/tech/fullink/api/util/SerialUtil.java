package tech.fullink.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * @author crow
 */
public class SerialUtil {

    private SerialUtil () {}
    private static final SimplePropertyPreFilter SIMPLE_PROPERTY_PRE_FILTER = new SimplePropertyPreFilter();

    static {
        // 排除字段名为bizData的字段
        SIMPLE_PROPERTY_PRE_FILTER.getExcludes().add("bizModel");
    }

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, SIMPLE_PROPERTY_PRE_FILTER);
    }
}
