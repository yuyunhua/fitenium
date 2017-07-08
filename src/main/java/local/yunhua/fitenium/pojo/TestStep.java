package local.yunhua.fitenium.pojo;

/**
 * Created by yuyunhua on 2015-11-03.
 */
public class TestStep {

    private String target;
    private Action action;
    private String value;
    private String byType = "";
    private String field;
    private String operation;

    public TestStep() {
    }

    public TestStep(String target, Action action, String value) {
        setTarget(target);
        setAction(action);
        setValue(value);
    }

    public TestStep(String target, String byType, Action action, String value) {
        setTarget(target);
        setByType(byType);
        setAction(action);
        setValue(value);
    }

    public TestStep(String target, String byType, String action, String value) {
        setTarget(target);
        setByType(byType);
        setAction(action);
        setValue(value);
    }

    public TestStep(String target, Action action) {
        setTarget(target);
        setAction(action);
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getByType() {
        return byType;
    }

    public void setByType(String byType) {
        this.byType = byType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(String operation) {
        Action action;
        try {
            action = Action.valueOf(operation.toUpperCase());
        } catch (IllegalArgumentException e) {
            action = Action.CLICK;
        } catch (NullPointerException e) {
            action = Action.CLICK;
        }
        setAction(action);
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TestStep copy() {
        TestStep newStep = new TestStep();
        newStep.setField(getField());
        newStep.setByType(getByType());
        newStep.setValue(getValue());
        newStep.setAction(getAction());
        newStep.setTarget(getTarget());
        return newStep;
    }


}
