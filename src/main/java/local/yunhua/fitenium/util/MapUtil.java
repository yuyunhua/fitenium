package local.yunhua.fitenium.util;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuyunhua on 2015-11-06.
 */
public class MapUtil {

    public static Map<String, Object> getMapFromObject(WebElement element) {

        if (null == element) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();
        try {
            map.put("tag", element.getTagName());
            map.put("id", element.getAttribute("id"));
            map.put("name", element.getAttribute("name"));
            map.put("class", element.getAttribute("class"));
            map.put("value", element.getAttribute("value"));
            map.put("type", element.getAttribute("type"));
        } catch (Exception e) {
            return null;
        }
        return map;
    }


    public static Map<String, Object> getMapFromObject(SearchContext context) {

        if (null == context) {
            return null;
        }

        if (context instanceof WebElement) {
            return getMapFromObject(((WebElement) context));
        } else if (context instanceof WebDriver) {
            return getMapFromObject(((WebDriver) context));
        }

        return null;
    }

    public static Map<String, Object> getMapFromObject(WebDriver driver) {

        if (null == driver) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();

        if (driver instanceof RemoteWebDriver) {
            RemoteWebDriver remoteWebDriver = (RemoteWebDriver) driver;
            Capabilities caps = remoteWebDriver.getCapabilities();
            if (caps != null) {
                map.put("browser name", caps.getBrowserName());
                map.put("platform", caps.getPlatform());
            }
            map.put("session id", remoteWebDriver.getSessionId());
            map.put("web driver", driver.getClass().getSimpleName());
        } else {

            map.put("driver", driver.toString());

        }
        return map;
    }
}
