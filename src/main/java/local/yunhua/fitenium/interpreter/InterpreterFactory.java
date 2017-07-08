package local.yunhua.fitenium.interpreter;

/**
 * Created by yuyunhua on 2015-11-04.
 */
public class InterpreterFactory {

    public static Interpreter getCompiler(String type) {
        Interpreter interpreter;

        switch (type.toUpperCase()) {
            case "XML":
                interpreter = new XmlInterpreter();
                break;
            case "EXCEL":
                interpreter = new ExcelInterpreter();
                break;
            default:
                interpreter = null;


        }

        return interpreter;
    }


}

