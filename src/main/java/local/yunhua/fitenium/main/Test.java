package local.yunhua.fitenium.main;

import local.yunhua.fitenium.runner.StepRunner;

/**
 * Created by yuyunhua on 2015-11-17.
 */
public class Test {


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


    public boolean run() {
        StepRunner runner = new StepRunner();

        runner.run();
        return false;
    }
}
