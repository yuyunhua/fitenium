package local.yunhua.fitenium.util;

/**
 * Created by yuyunhua on 2015-11-18.
 */
public class StringUtil {

    /**
     *
     * @param src
     * @return
     */
    public static String convert(String src) {
        src = ("_" + src).toLowerCase();
        String[] arr = src.split("_");
        String des = "";
        for (String str : arr) {
            try {
                des += str.substring(0, 1).toUpperCase() + str.substring(1);
            } catch (StringIndexOutOfBoundsException e) {
                continue;
            }
        }
        return des;
    }

}
