package local.yunhua.fitenium.api.actions;

import local.yunhua.fitenium.api.finder.FinderContext;
import local.yunhua.fitenium.pojo.TestStep;
import local.yunhua.fitenium.result.ResultCode;
import local.yunhua.fitenium.result.StepResult;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by yuyunhua on 2016-01-04.
 */
public class HighLight implements IExecute {

    public StepResult execute(WebDriver driver, WebElement lastElement, TestStep step) {

        Exception exception = null;
        ResultCode code = null;
        try {

            lastElement = FinderContext.getElement(driver, lastElement, step.getByType(), step.getTarget());


            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("element = arguments[0];" +
                    "original_style = element.getAttribute('style');" +
                    "element.setAttribute('style', original_style + \";" +
                    "background: yellow; border: 2px solid red;\");" +
                    "setTimeout(function(){element.setAttribute('style', original_style);}, 1000);", lastElement);


        } catch (Exception e) {
            code = ResultCode.EXCEPTION;
            exception = e;
        }


        return new StepResult(step, code, lastElement, null, exception, null);
    }

}