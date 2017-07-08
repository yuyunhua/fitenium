package local.yunhua.fitenium.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyunhua on 2015-11-03.
 */
public class TestCase {


    private List<TestStep> tc = new ArrayList<>();
    private String dataSource;
    private String name;
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<TestStep> getTestSteps() {
        return tc;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public void addTestStep(TestStep ts) {
        if (null == tc) {
            tc = new ArrayList<>();
        }
        tc.add(ts);
    }

    public void addTestStep(List<TestStep> list) {
        if (null == tc) {
            tc = new ArrayList<>();
        }
        tc.addAll(list);
    }


}
