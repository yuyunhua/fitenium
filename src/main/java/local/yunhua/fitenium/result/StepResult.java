package local.yunhua.fitenium.result;

/**
 * Created by yuyunhua on 2015-11-05.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import local.yunhua.fitenium.pojo.TestStep;
import local.yunhua.fitenium.util.MapUtil;
import org.openqa.selenium.SearchContext;

import java.util.Map;

/**
 * Created by yuyunhua on 2015-11-03.
 */
public class StepResult extends AbstractResult {
    private TestStep step;

    @JsonIgnore
    private SearchContext target;
    private String resultValue;
    private Exception exception;
    private ResultCode code;
    private String resultDesc;
    private Map<String, Object> targetDesc = MapUtil.getMapFromObject(target);

    private String screenShotFile;

    public String getScreenShotFile() {
        return screenShotFile;
    }

    public void setScreenShotFile(String screenShotFile) {
        this.screenShotFile = screenShotFile;
    }

    public StepResult() {
        step = null;
        target = null;
        code = ResultCode.OK;
        exception = null;
        resultValue = null;
        resultDesc = "OK";
        targetDesc = MapUtil.getMapFromObject(target);
    }

    public StepResult(TestStep step, ResultCode resultCode, SearchContext context, String result, Exception e, String resultDesc) {
        setStep(step);
        setCode(resultCode);
        setTarget(context);
        setException(e);
        setResultValue(result);
        setResultDesc(resultDesc);;
    }

    public Map<String, Object> getTargetDesc() {
        return targetDesc;
    }

    public TestStep getStep() {
        return step;
    }

    public void setStep(TestStep step) {
        this.step = null == step ? null : step.copy();
    }

    public SearchContext getTarget() {
        return target;
    }

    public void setTarget(SearchContext target) {
        this.target = target;
        targetDesc = MapUtil.getMapFromObject(target);
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code == null ? ResultCode.OK : code;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }



}
