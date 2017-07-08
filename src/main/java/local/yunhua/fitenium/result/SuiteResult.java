package local.yunhua.fitenium.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyunhua on 2015-11-05.
 */
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler","operations","roles","menus"})
public class SuiteResult {


    private List<CaseResult> caseResults;

    public List<CaseResult> getCaseResults() {
        return caseResults;
    }

    public void addCaseResult(CaseResult caseResult) {
        if (null == caseResults) {
            caseResults = new ArrayList<CaseResult>();
        }
        caseResults.add(caseResult);
    }

    public SuiteResult() {
        caseResults = new ArrayList<CaseResult>();
    }

    @Override
    public String toString() {
        return caseResults.toString();
    }
}
