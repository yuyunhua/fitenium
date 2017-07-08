package local.yunhua.fitenium.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuyunhua on 2015-11-03.
 */
public class TestSuite {


    private List<KeyValuePair> properties;
    private List<TestCase> testCases = new ArrayList<>();
    private Map<String, List<Map<String, String>>> dataMap;

    public TestSuite() {
        testCases = new ArrayList<>();
        properties = new ArrayList<>();
        dataMap = new HashMap<>();
    }

    public List<KeyValuePair> getProperties() {
        return properties;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void addTestCase(TestCase testCase) {
        if (null == testCases) {
            testCases = new ArrayList<>();
        }
        testCases.add(testCase);
    }

    public void addProperty(KeyValuePair property) {
        properties.add(property);
    }

    public Map<String, List<Map<String, String>>> getDataMap() {
        return dataMap;
    }

    public void addDataSource(Map<String, List<Map<String, String>>> dataSource) {
        for (Map.Entry<String, List<Map<String, String>>> entry : dataSource.entrySet()) {
            this.dataMap.put(entry.getKey(), entry.getValue());
        }
    }

    public void  addProperties(List<KeyValuePair> properties) {
        this.properties.addAll(properties);
    }

    public int getSize() {
        return testCases.size();
    }

}
