package local.yunhua.fitenium.interpreter;

import local.yunhua.fitenium.pojo.TestSuite;

/**
 * Created by yuyunhua on 2015-11-04.
 */
public interface Interpreter {

    public TestSuite interpret(String filePath);
}
