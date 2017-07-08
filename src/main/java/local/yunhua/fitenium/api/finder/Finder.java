package local.yunhua.fitenium.api.finder;

import local.yunhua.fitenium.api.checker.Attribute;
import local.yunhua.fitenium.api.checker.CheckContext;
import local.yunhua.fitenium.util.MapUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyunhua on 2015-11-13.
 */
public abstract class Finder {

    private static Logger logger = LoggerFactory.getLogger(Finder.class);

    public abstract WebElement getElement(WebDriver context, WebElement lastElement, String target);

    public WebElement getInputElement(WebDriver context, WebElement lastElement, String target) {
        WebElement element = getElement(context, lastElement, target);
        logger.debug("文本控件：" + MapUtil.getMapFromObject(element).toString());
        if (CheckContext.check(element, Attribute.EDITABLE)) {
            return element;
        }
        element = getEditableElement(element);
        logger.debug("可编辑控件：" + MapUtil.getMapFromObject(element).toString());
        return element;
    }


    public WebElement getEditableElement(WebElement label) {

        if (null == label) {
            return null;
        }

        /* 控件列表：保存查找过程中的控件 */
        List<WebElement> elements = new ArrayList<>();

        /* 控件列表不为空时跳出循环 */
        while (elements.size() == 0) {
            /* 将label控件的后代及自己（tag是input/textarea）加入到控件列表中 */
            elements.addAll(label.findElements(By.xpath("descendant-or-self::input | descendant-or-self::textarea")));

            /* 移除不显示、不可用、不可编辑的控件 */
            int size = elements.size();
            for (int index = size - 1; index >= 0; index--) {
                WebElement element = elements.get(index);
                if (element.isDisplayed() && CheckContext.check(element, Attribute.EDITABLE) && element.isEnabled()) {
                    /*控件必须符合条件：可视、可用、可编辑*/
                    continue;
                } else {
                    /*不符合条件的控件直接从列表中移除*/
                    elements.remove(index);
                }
            }

            try {
                /* 往父级迭代 */
                label = label.findElement(By.xpath(".."));
            } catch (InvalidSelectorException e) {
                /* 已获取到顶级控件，跳出循环 */
                break;
            } catch (NullPointerException e) {
                break;
            }
        }

        if (0 == elements.size()) {
            return null;
        }

        //return
        WebElement element = elements.get(0);
        return element;

    }


}
