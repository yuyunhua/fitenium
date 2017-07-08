package local.yunhua.fitenium.api.finder;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yuyunhua on 2015-11-13.
 */
public class FinderContext {

    private static final Logger log = LoggerFactory.getLogger(FinderContext.class);

    public static WebElement getElement(WebDriver driver, WebElement lastElement, String byType, String target) {
        return getElement(driver, lastElement, getType(byType), target);
    }

    public static WebElement getElement(WebDriver driver, WebElement lastElement, ByType byType, String target) {
        Finder finder = getFinder(byType);
        WebElement element = finder.getElement(driver, lastElement, target);
        highLight(driver, element);
        return element;
    }

    public static WebElement getInputElement(WebDriver driver, WebElement lastElement, String byType, String target) {
        return getInputElement(driver, lastElement, getType(byType), target);
    }

    public static WebElement getInputElement(WebDriver driver, WebElement lastElement, ByType byType, String target) {
        Finder finder = getFinder(byType);
        WebElement element = finder.getInputElement(driver, lastElement, target);
        highLight(driver, element);
        return element;
    }

    private static ByType getType(String byType) {
        ByType type;
        try {
            type = ByType.valueOf(byType.toUpperCase());
        } catch (Exception e) {
            type = ByType.KEY;
        }
        return type;
    }

    private static Finder getFinder(ByType byType) {
        Finder finder;
        switch (byType) {
            case KEY:
                finder = new KeyFinder();
                break;
            case XPATH:
                finder = new XpathFinder();
                break;
            default:
                finder = new KeyFinder();
        }
        return finder;
    }


    private static boolean highLight(WebDriver driver, WebElement element) {
        if (null == element) {
            return false;
        }
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("element = arguments[0];" +
                    "original_style = element.getAttribute('style');" +
                    "element.setAttribute('style', original_style + \";" +
                    "background: yellow; border: 2px solid red;\");" +
                    "setTimeout(function(){element.setAttribute('style', original_style);}, 1000);", element);
            return true;
        } catch (Exception e) {
            log.warn("高亮时异常");
        }
        return false;

    }

}
