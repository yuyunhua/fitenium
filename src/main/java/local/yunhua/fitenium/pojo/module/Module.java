package local.yunhua.fitenium.pojo.module;

import local.yunhua.fitenium.pojo.TestSuite;
import local.yunhua.fitenium.result.SuiteResult;

/**
 * Created by yuyunhua on 2016-01-08.
 */
public abstract class Module {

    public abstract TestSuite getTestSuite();
    public abstract SuiteResult run();

}
