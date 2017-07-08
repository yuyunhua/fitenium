package local.yunhua.fitenium.api.actions;

import local.yunhua.fitenium.pojo.TestStep;
import local.yunhua.fitenium.result.ResultCode;
import local.yunhua.fitenium.result.StepResult;
import local.yunhua.fitenium.util.ResultSaver;
import local.yunhua.fitenium.util.StringUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.util.Date;

/**
 * Created by yuyunhua on 2015-11-05.
 */
public class OperationContext {


    /**
     * 根据测试步骤中的动作，调用相关的动作执行方法，执行操作
     * 并在测试步骤操作完成后，截图
     *
     * @param driver      WebDriver对象，浏览器
     * @param lastElement 上个操作步骤的操作对象
     * @param step        操作步骤，包括对象，动作，参数
     * @return 操作步骤结果
     */
    public static StepResult execute(WebDriver driver, WebElement lastElement, TestStep step) {

        String className = OperationContext.class.getPackage().getName() + "." + StringUtil.convert(step.getAction().toString());

        StepResult result = new StepResult();
        result.setStartTime(new Date());
        IExecute operation;
        try {
            Class<?> demo = Class.forName(className);
            operation = (IExecute) demo.newInstance();

            /**
             * 执行操作步骤
             */
            result = operation.execute(driver, lastElement, step);

            /**
             * 截图
             */
            if (driver instanceof RemoteWebDriver) {
                File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                result.setScreenShotFile(ResultSaver.saveImg(screenShotFile));
            }


        } catch (Exception e) {
            e.printStackTrace();
            result.setStep(step);
            result.setException(e);
            result.setCode(ResultCode.EXCEPTION);
            if (e instanceof ClassNotFoundException) {
                result.setResultDesc("不受支持的操作" + step.getAction());
            }
        }

        result.setEndTime(new Date());
        return result;
    }
}
