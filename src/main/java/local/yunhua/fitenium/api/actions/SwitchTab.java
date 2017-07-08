package local.yunhua.fitenium.api.actions;

import local.yunhua.fitenium.pojo.TestStep;
import local.yunhua.fitenium.result.ResultCode;
import local.yunhua.fitenium.result.StepResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by yuyunhua on 2015-11-05.
 */
public class SwitchTab implements IExecute {

    private static final Logger log = LoggerFactory.getLogger(SwitchTab.class);

    public StepResult execute(WebDriver driver, WebElement lastElement, TestStep step) {

        Exception exception = null;
        ResultCode code = null;

        /*
        * 切换是否成功的标志
        * */
        boolean flag = false;
        try {
            String title = step.getValue().toLowerCase();
            Set<String> handles = driver.getWindowHandles();
            String currentHandle = driver.getWindowHandle();
            for (String handle : handles) {
                if (currentHandle.equals(handle)) {
                    /**
                     * 当前Tab页不切换
                     */
                    continue;
                }
                driver.switchTo().window(handle);
                if (driver.getTitle().toLowerCase().contains(title)) {
                    flag = true;
                    /**
                     * 切换成功
                     */
                    break;
                }
            }
        } catch (Exception e) {
            code = ResultCode.EXCEPTION;
            exception = e;
        }


        return new StepResult(step, code, driver, null, exception, null);
    }
}
