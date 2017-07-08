package local.yunhua.fitenium.api.actions;

import local.yunhua.fitenium.pojo.TestStep;
import local.yunhua.fitenium.result.ResultCode;
import local.yunhua.fitenium.result.StepResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yuyunhua on 2015-11-05.
 */
public class Open implements IExecute {

    private static final Logger log = LoggerFactory.getLogger(Open.class);

    public StepResult execute(WebDriver driver, WebElement lastElement, TestStep step) {

        Exception exception = null;
        ResultCode code = null;

        try {
            new URL(step.getValue());
            driver.get(step.getValue());
        } catch (MalformedURLException e) {
            code = ResultCode.EXCEPTION;
            exception = e;
        } catch (Exception e) {
            code = ResultCode.EXCEPTION;
            exception = e;
        }


        return new StepResult(step, code, driver, null, exception, null);
    }
}
