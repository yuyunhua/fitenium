package local.yunhua.fitenium.interpreter.excel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import local.yunhua.fitenium.pojo.KeyValuePair;
import local.yunhua.fitenium.pojo.TestCase;
import local.yunhua.fitenium.pojo.TestStep;
import local.yunhua.fitenium.pojo.TestSuite;
import local.yunhua.fitenium.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelProcessor {

    private Logger logger = LoggerFactory.getLogger(ExcelProcessor.class);

    public Map<String, List<List<String>>> getDataFromExcelFile(String filePath) {

        Workbook book = null;
        Map<String, List<List<String>>> bookData = new HashMap<>();

        try {

            //构建Workbook对象, 只读Workbook对象
            //直接从本地文件创建Workbook
            InputStream inputStream = new FileInputStream(filePath);
            book = Workbook.getWorkbook(inputStream);

            Sheet[] sheets = book.getSheets();
            for (Sheet sheet : sheets) {

                List<List<String>> sheetData = new ArrayList<>();

                /**
                 * 逐行处理，将行转换成字符串列表，并添加到页数据中
                 */
                for (int index = 0; index < sheet.getRows(); index++) {
                    List<String> row = new ArrayList<>();
                    for (Cell cell : sheet.getRow(index)) {
                        row.add(cell.getContents());
                    }
                    sheetData.add(row);
                }

                bookData.put(sheet.getName(), sheetData);
            }

        } catch (FileNotFoundException | NoSuchFileException e) {
            logger.debug("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != book) {
                book.close();
            }
        }

        return bookData;
    }

    /**
     *
     * @param data
     * @return
     */
    public List<KeyValuePair> getProperties(List<List<String>> data) {
        List<KeyValuePair> properties = new ArrayList<>();

        /**
         *
         */
        for (int index = 1; index < data.size(); index++) {
            List<String> row = data.get(index);
            int length = row.size();

            String key = length > 0 ? row.get(0) : null;
            String value = length > 1 ? row.get(1) : null;
            String note = length > 2 ? row.get(2) : null;


            if (null != key && !key.startsWith("#")) {
                KeyValuePair property = new KeyValuePair(key, value, note);
                properties.add(property);
            }

        }
        return properties;
    }


    public TestSuite process(Map<String, List<List<String>>> bookData) {

        TestSuite suite = new TestSuite();


        for (Map.Entry<String, List<List<String>>> entry : bookData.entrySet()) {

            switch (entry.getKey()) {
                case Const.CONFIG_NAME:
                    suite.addProperties(getProperties(entry.getValue()));
                    break;
                case Const.KEYWORDS_NAME:
                    break;
                default:
                    List<TestCase> cases = getCases(entry.getValue());
                    cases.forEach((tc) -> suite.addTestCase(tc));
            }
        }

        try {
            logger.info(JsonUtil.getJsonFromObject(suite));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suite;

    }

    public List<TestCase> getCases(List<List<String>> data) {

        List<TestCase> cases = new ArrayList<>();

        List<List<String>> caseData = null;
        int iterations = 0;

        for (List<String> row : data) {
            int length = row.size();
            if (0 == length) {
                if (null != caseData) {
                    cases.addAll(getCases(caseData, iterations));
                    caseData = null;
                    iterations = 0;
                }
                continue;
            }

            if (null == caseData) {
                caseData = new ArrayList<>();
            }
            iterations = length - 3 > iterations ? length - 3 : iterations;
            caseData.add(row);
        }

        if (null != caseData) {
            cases.addAll(getCases(caseData, iterations));
        }

        return cases;

    }


    public List<TestCase> getCases(final List<List<String>> data, final int iterations) {

        List<TestCase> cases = new ArrayList<>();


        for (int index = 0; index < iterations; index++) {
            TestCase tc = new TestCase();
            for (List<String> row : data) {


                int length = row.size();

                String by = length > 0 ? row.get(0) : null;
                String target = length > 1 ? row.get(1) : null;
                String action = length > 2 ? row.get(2) : null;
                String value = length > 3 + index ? row.get(3 + index) : null;

                if (null != target && !"".equals(target.trim())) {
                    TestStep step = new TestStep(target, by, action, value);
                    tc.addTestStep(step);
                }


            }

            if (null != tc) {
                cases.add(tc);
            }
        }

        try {

            logger.info(JsonUtil.getJsonFromObject(getCaseData(data, iterations)));
        } catch (Exception e) {
            logger.info("h");

        }

        return cases;

    }


    public List<Map<String, String>> getCaseData(final List<List<String>> data, final int iterations) {

        List<Map<String, String>> caseData = new ArrayList<>();

        for (int index = 0; index < iterations; index++) {

            Map<String, String> iterationData = new HashMap<>();


            for (int i = 0; i < data.size(); i++) {

                List<String> row = data.get(i);
                int length = row.size();
                String key = "field_" + i;
                String value = length > 3 + index ? row.get(3 + index) : null;

                if (null != value) {
                    iterationData.put(key, value);
                }


            }

            caseData.add(iterationData);

        }

        return caseData;

    }

}
