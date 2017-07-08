package local.yunhua.fitenium.api.checker;

import org.openqa.selenium.WebElement;

/**
 * Created by yuyunhua on 2015-11-19.
 */
public class CheckContext {

    /**
     * 断言：判定Web控件element是否符合属性列表中的属性
     * 若全部符合，返回true
     * 若其中某一或某几或全部不符合，返回false
     *
     * @param element Web控件
     * @param hopes   待校验属性列表
     * @return boolean类型，断言成功，返回true，断言失败，返回false
     */
    public static boolean check(WebElement element, Attribute... hopes) {
        if (null == hopes) {
            return true;
        }

        for (Attribute hope : hopes) {
            if (!check(element, hope)) {
                return false;
            }
        }

        return true;

    }


    /**
     * 断言：判定Web控件element是否符合属性attribute
     * 若符合，返回true，若不符合，返回false
     *
     * @param element   Web控件
     * @param attribute 待校验属性
     * @return boolean类型，断言成功，返回true，断言失败，返回false
     */
    private static boolean check(WebElement element, Attribute attribute) {
        switch (attribute) {
            case EDITABLE:
                return new Editable().check(element);
            case NOT_EDITABLE:
                return !(new Editable().check(element));
            case DISPLAYED:
                return new Displayed().check(element);
            default:
                return false;
        }
    }
}
