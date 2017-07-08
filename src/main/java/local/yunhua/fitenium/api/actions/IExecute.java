package local.yunhua.fitenium.api.actions;

import local.yunhua.fitenium.pojo.TestStep;
import local.yunhua.fitenium.result.StepResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by yuyunhua on 2015-11-05.
 */
public interface IExecute {
    StepResult execute(WebDriver driver, WebElement lastElement, TestStep step) throws Exception;
}
