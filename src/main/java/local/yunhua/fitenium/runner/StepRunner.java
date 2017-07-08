package local.yunhua.fitenium.runner;

import local.yunhua.fitenium.api.actions.OperationContext;
import local.yunhua.fitenium.api.browser.Driver;
import local.yunhua.fitenium.pojo.Action;
import local.yunhua.fitenium.pojo.TestCase;
import local.yunhua.fitenium.pojo.TestStep;
import local.yunhua.fitenium.pojo.TestSuite;
import local.yunhua.fitenium.result.CaseResult;
import local.yunhua.fitenium.result.ResultCode;
import local.yunhua.fitenium.result.StepResult;
import local.yunhua.fitenium.result.SuiteResult;
import org.openqa.selenium.WebElement;

/**
 * Created by yuyunhua on 2015-11-19.
 */
public class StepRunner extends Runner {

    @Override
    public SuiteResult run(TestSuite testSuite) {
        return null;
    }

    @Override
    public CaseResult run(TestCase testCase) {
        return null;
    }

    @Override
    public StepResult run(TestStep testStep) {
        return null;
    }


    private String target;
    private String operation;
    private String value;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public ResultCode run() {

        TestStep step = new TestStep();
        step.setTarget(target);
        step.setValue(value);

        /* 设置动作 */
        Action actions = null;
        try {
            actions = Action.valueOf(operation.toUpperCase());
        } catch (IllegalArgumentException e) {
        }
        step.setAction(actions);

        /* 设置动作 */
        StepResult result = OperationContext.execute(Driver.getInstance(), lastElement, step);
        lastElement = result.getTarget() instanceof WebElement ? (WebElement) result.getTarget() : null;

        return result.getCode();
    }
}
