package local.yunhua.fitenium.runner;

import local.yunhua.fitenium.pojo.TestCase;
import local.yunhua.fitenium.pojo.TestStep;
import local.yunhua.fitenium.pojo.TestSuite;
import local.yunhua.fitenium.result.CaseResult;
import local.yunhua.fitenium.result.StepResult;
import local.yunhua.fitenium.result.SuiteResult;
import org.openqa.selenium.WebElement;

/**
 * Created by yuyunhua on 2015-11-04.
 */
public abstract class Runner {

    public WebElement lastElement = null;

    public abstract SuiteResult run(TestSuite testSuite);

    public abstract CaseResult run(TestCase testCase);

    public abstract StepResult run(TestStep testStep);
}
