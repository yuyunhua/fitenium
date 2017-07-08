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
public class Click implements IExecute {

    public StepResult execute(WebDriver driver, WebElement lastElement, TestStep step) {

        Exception exception = null;
        ResultCode code = null;
        
        try {
            lastElement = FinderContext.getElement(driver, lastElement, step.getByType(), step.getTarget());
            lastElement.click();
        } catch (NullPointerException e) {
            exception = e;
            code = ResultCode.EXCEPTION;
            e.printStackTrace();
        } catch (Exception e) {
            exception = e;
            code = ResultCode.EXCEPTION;
            e.printStackTrace();
        }

        return new StepResult(step, code, lastElement, null, exception, null);
    }
}
