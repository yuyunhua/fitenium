package local.yunhua.fitenium.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * getJsonFromObject
     *
     * @param obj Object
     * @return String
     * @throws Exception e
     */
    public static String getJsonFromObject(final Object obj) throws Exception {


        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        return mapper.writeValueAsString(obj);

    }

    /**
     * getListFromJson
     *
     * @param json String
     * @return List
     * @throws Exception e
     */
    @SuppressWarnings("unchecked")
    public static List<Object> getListFromJson(final String json) {
        try {
            return mapper.readValue(json, ArrayList.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * getMapFromJson
     *
     * @param json String
     * @return Map
     * @throws Exception e
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromJson(final String json) {
        try {
            return mapper.readValue(json, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
