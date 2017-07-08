package local.yunhua.fitenium.api.actions;

import local.yunhua.fitenium.api.finder.FinderContext;
import local.yunhua.fitenium.pojo.TestStep;
import local.yunhua.fitenium.result.ResultCode;
import local.yunhua.fitenium.result.StepResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by yuyunhua on 2015-11-05.
 */
public class Type implements IExecute {

    public StepResult execute(WebDriver driver, WebElement lastElement, TestStep step) {

        Exception exception = null;
        ResultCode code = null;
        try {
            lastElement = FinderContext.getInputElement(driver, lastElement, step.getByType(), step.getTarget());
            lastElement.sendKeys(step.getValue());
        } catch (Exception e) {
            code = ResultCode.EXCEPTION;
            exception = e;
        }


        return new StepResult(step, code, lastElement, null, exception, null);
    }

}
