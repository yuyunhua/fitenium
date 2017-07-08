package local.yunhua.fitenium.api.browser;

import org.openqa.selenium.*;

public class WebElementUtil {


    public static WebElement getActiveElement(WebDriver driver) {
        return driver.switchTo().activeElement();
    }

    public static boolean isElementPresent(SearchContext context, By by) {
        try {
            context.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public static String closeAlertAndGetItsText(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            alert.accept();
            return alertText;
        } finally {
        }
    }


}
