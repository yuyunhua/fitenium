package local.yunhua.fitenium.runner;

import local.yunhua.fitenium.api.actions.OperationContext;
import local.yunhua.fitenium.api.browser.Driver;
import local.yunhua.fitenium.pojo.TestCase;
import local.yunhua.fitenium.pojo.TestStep;
import local.yunhua.fitenium.pojo.TestSuite;
import local.yunhua.fitenium.result.CaseResult;
import local.yunhua.fitenium.result.IterationResult;
import local.yunhua.fitenium.result.StepResult;
import local.yunhua.fitenium.result.SuiteResult;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by yuyunhua on 2015-11-19.
 */
public class DataDrivenRunner extends Runner {

    private static Logger log = LoggerFactory.getLogger(DataDrivenRunner.class);

    @Override
    public SuiteResult run(TestSuite testSuite) {
        if (null == testSuite) {
            return null;
        }

        SuiteResult suiteResult = new SuiteResult();
        for (TestCase testCase : testSuite.getTestCases()) {
            String dataSource = testCase.getDataSource();
            if (null != dataSource && null != testSuite.getDataMap() && testSuite.getDataMap().containsKey(dataSource)) {
                /* 如果有指定了数据源，且数据源不为空，按照数据列表执行测试用例 */
                List<Map<String, String>> dataIterations = testSuite.getDataMap().get(dataSource);
                suiteResult.addCaseResult(run(testCase, dataIterations));
            } else {
                /* 否则执行测试用例一次 */
                suiteResult.addCaseResult(run(testCase));
            }
        }
        return suiteResult;
    }


    /* 根据数据运行测试用例 */
    public IterationResult run(TestCase testCase, Map<String, String> data) {

        IterationResult iterationResult = new IterationResult();

        /* 遍历测试用例中的步骤，重新分配测试步骤中的值，运行测试步骤*/
        for (TestStep testStep : testCase.getTestSteps()) {
            /* 获取target及field属性 */
            String target = testStep.getTarget();
            String field = testStep.getField();

            /*
            * 如果数据源中包含以target为键的测试数据，以此数据作为测试步骤的新值
            * 如果不包含target，再检查数据源中是否包含以field为键的测试数据，如果包含,以此数据作为测试步骤的新值
            * 否则，使用默认value属性的值
            * */
            if (null != target && data.containsKey(target)) {
                testStep.setValue(data.get(target));
            } else if (null != field && data.containsKey(field)) {
                testStep.setValue(data.get(field));
            }

            iterationResult.addStepResult(run(testStep));
        }
        log.debug(iterationResult.toString());
        return iterationResult;
    }

    /* 对数据列表中的数据进行迭代运行 */
    public CaseResult run(TestCase testCase, List<Map<String, String>> dataList) {
        CaseResult caseResult = new CaseResult();
        /* 遍历测试数据列表中的数据，运行测试用例 */
        for (Map<String, String> data : dataList) {
            caseResult.addIteration(run(testCase, data));
        }
        return caseResult;
    }

    /* 运行无测试数据的用例 */
    @Override
    public CaseResult run(TestCase testCase) {
        CaseResult caseResult = new CaseResult();

        IterationResult iterationResult = new IterationResult();
        for (TestStep testStep : testCase.getTestSteps()) {
            iterationResult.addStepResult(run(testStep));
        }

        caseResult.addIteration(iterationResult);
        return caseResult;
    }


    @Override
    public StepResult run(TestStep testStep) {

        StepResult result = OperationContext.execute(Driver.getInstance(), lastElement, testStep);
        if (null == result) {
            lastElement = null;
        } else if (result.getTarget() instanceof WebElement) {
            lastElement = ((WebElement) result.getTarget());
        } else {
            lastElement = null;
        }
        return result;
    }
}
