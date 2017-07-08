package local.yunhua.fitenium.api.checker;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyunhua on 2015-11-19.
 */
public class Editable extends Checker {

    @Override
    public boolean check(WebElement element) {

        try {

            switch (element.getTagName().toLowerCase()) {
                case "input":
                    /* 检查input的type属性是否为null或text或password */
                    String type = element.getAttribute("type");
                    if (null != type) {
                        List<String> allowTypes = new ArrayList<String>();
                        allowTypes.add("text");
                        allowTypes.add("password");
                        if (!allowTypes.contains(type.toLowerCase())) {
                            return false;
                        }
                    }
                    break;
                case "textarea":
                    break;
                default:
                    return false;
            }

            /* 检查readonly属性，如果有readonly属性 */
            if (element.getAttribute("readonly") != null) {
                return false;
            }

            /* 检查disabled属性，如果有disabled属性 */
            if (element.getAttribute("disabled") != null) {
                return false;
            }


        } catch (ElementNotVisibleException e) {
            /* 不可见的控件返回false */
            return false;
        } catch (WebDriverException e) {
            /* 不可编辑的控件返回false */
            return false;
        } catch (Exception e) {
            /* 不可编辑的控件返回false */
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
