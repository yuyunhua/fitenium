package local.yunhua.fitenium.api.checker;

import org.openqa.selenium.WebElement;

/**
 * Created by yuyunhua on 2015-11-19.
 */
public class Displayed extends Checker {

    @Override
    public boolean check(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
