package local.yunhua.fitenium.runner;

/**
 * Created by yuyunhua on 2015-11-04.
 */
public class RunnerFactory {

    public static Runner getRunner() {
        return new DataDrivenRunner();
    }
}
