package local.yunhua.fitenium.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyunhua on 2015-11-05.
 */
public class CaseResult {

    private static Logger logger = LoggerFactory.getLogger(CaseResult.class);

    private List<IterationResult> iterations;

    public List<IterationResult> getIterations() {
        return iterations;
    }

    public void addIteration(IterationResult iteration) {
        if (null == iterations) {
            iterations = new ArrayList<>();
        }
        iterations.add(iteration);
        logger.debug(iteration.toString());
    }

    public CaseResult() {
        iterations = new ArrayList<>();
    }

    @Override
    public String toString() {
        return iterations.toString();
    }
}
