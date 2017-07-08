/**
 * Created by yuyunhua on 2015-11-05.
 */

package local.yunhua.fitenium.job;

import local.yunhua.fitenium.interpreter.Interpreter;
import local.yunhua.fitenium.interpreter.InterpreterFactory;
import local.yunhua.fitenium.pojo.KeyValuePair;
import local.yunhua.fitenium.pojo.TestSuite;
import local.yunhua.fitenium.result.SuiteResult;
import local.yunhua.fitenium.runner.Runner;
import local.yunhua.fitenium.runner.RunnerFactory;
import local.yunhua.fitenium.util.JsonUtil;
import local.yunhua.fitenium.util.ResultSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Job {

    private static final Logger log = LoggerFactory.getLogger(Job.class);


    public String run(String fileType, String filePath) {
        File caseFile = new File(filePath);

        File dir;
        if (caseFile.isDirectory()) {
            dir = caseFile;
        } else {
            dir = caseFile.getParentFile();
        }

        String resultDir = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        ResultSaver.setPath(dir.getAbsolutePath() + "\\" + resultDir);

        // step 1: 编译测试用例文件，生成测试用例
        Interpreter interpreter = InterpreterFactory.getCompiler(fileType);
        TestSuite testSuite = interpreter.interpret(filePath);
        try {
            log.info(JsonUtil.getJsonFromObject(testSuite));

        } catch (Exception e) {
            e.printStackTrace();
        }


        for (KeyValuePair property : testSuite.getProperties()) {
            System.setProperty(property.getKey(), property.getValue().toString());
        }

        if (0 == testSuite.getSize()) {
            log.error("未找到测试用例");
            return "";
        }

        // step 2: 运行测试用例，生成测试结果
        String resultStr = null;
        try {
            Runner runner = RunnerFactory.getRunner();
            SuiteResult result = runner.run(testSuite);
            resultStr = JsonUtil.getJsonFromObject(result);
        } catch (Exception e) {
            resultStr = "异常";
            e.printStackTrace();
        }


        // step 3: 保存运行结果
        ResultSaver.saveResult(resultStr);

        return resultDir;

    }

}
