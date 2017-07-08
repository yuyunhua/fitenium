package local.yunhua.fitenium.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyunhua on 2015-11-05.
 */
public class IterationResult extends AbstractResult{

    private List<StepResult> stepResultList;


    public IterationResult() {
        stepResultList = new ArrayList<>();
    }

    public List<StepResult> getStepResultList() {
        return stepResultList;
    }

    public void addStepResult(StepResult stepResult) {
        if (null == stepResultList) {
            stepResultList = new ArrayList<>();
        }
        stepResultList.add(stepResult);

    }

}
