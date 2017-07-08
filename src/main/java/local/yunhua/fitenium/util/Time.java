package local.yunhua.fitenium.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuyunhua on 2015-12-29.
 */
public class Time {

    public String getTime() {
        Date date = new Date();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
