package local.yunhua.fitenium.api.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {

    private static final Logger log = LoggerFactory.getLogger(Driver.class);
    public static WebDriver driver = getInstance("MY_FIREFOX");


    public static synchronized WebDriver getInstance() {
        /* 默认firefox */
        return getInstance("MY_FIREFOX");
    }

    public static synchronized WebDriver getInstance(String type) {
        Browser browser;
        try {
            browser = Browser.valueOf(type.toUpperCase());
        } catch (Exception e) {
            browser = Browser.UNKNOWN;
        }
        return getInstance(browser);
    }

    public static synchronized WebDriver getInstance(Browser browser) {

        try {
            driver.getWindowHandle();
            return driver;
        } catch (Exception e) {
        }

        switch (browser) {
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case MY_FIREFOX:
                ProfilesIni allProfiles = new ProfilesIni();
                FirefoxProfile profile = allProfiles.getProfile("default");
                driver = new FirefoxDriver(profile);
                break;
            case HTML_UNIT:
//                driver = new HtmlUnitDriver();
                break;
            default:
                log.error("不受支持的浏览器，使用HTMLUNIT代替");
//                driver = new HtmlUnitDriver();

        }


        driver.manage().window().maximize();
        return driver;
    }


    private Driver() {
    }
}
