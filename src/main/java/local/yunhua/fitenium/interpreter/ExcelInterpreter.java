package local.yunhua.fitenium.interpreter;

import local.yunhua.fitenium.interpreter.excel.ExcelProcessor;
import local.yunhua.fitenium.pojo.TestSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by yuyunhua on 2015-11-04.
 */
public class ExcelInterpreter implements Interpreter {

    private static final Logger log = LoggerFactory.getLogger(ExcelInterpreter.class);

    public TestSuite interpret(String filePath) {

        ExcelProcessor excel = new ExcelProcessor();
        Map<String, List<List<String>>> data = excel.getDataFromExcelFile(filePath);
        TestSuite testSuite = excel.process(data);
        return testSuite;

    }

}

