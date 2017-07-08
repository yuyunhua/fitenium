package local.yunhua.fitenium.api.finder;

import local.yunhua.fitenium.util.MapUtil;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyunhua on 2015-11-13.
 */
public class KeyFinder extends Finder {

    private static final Logger log = LoggerFactory.getLogger(KeyFinder.class);

    @Override
    public WebElement getElement(WebDriver driver, WebElement lastElement, String key) {

        List<WebElement> elements;

        if (null == lastElement) {
            elements = findElementsByText(driver, key);
        } else {
            elements = getElementByLastElement(lastElement, key);
            if (null == elements || 0 == elements.size()) {
                elements = findElementsByText(driver, key);
            }
        }


        if (null == elements || 0 == elements.size()) {
            return null;
        }
        return choose(elements, key);
    }


    /* 查找与控件位置最接近，且包含关键字的的控件 */
    private List<WebElement> getElementByLastElement(WebElement lastElement, String key) {
        List<WebElement> elements = new ArrayList<>();
        /* 目标控件列表不为0 或者 已经到达顶级控件时，停止循环 */
        while (null != lastElement && elements.size() == 0) {
            elements.addAll(findElementsByText(lastElement, key));
            try {
                /* 往父级迭代 */
                lastElement = lastElement.findElement(By.xpath(".."));
            } catch (InvalidSelectorException e) {
                // 已获取到顶级控件，跳出循环
                break;
            } catch (NullPointerException e) {
                break;
            }
        }
        return elements;
    }

    /* 在指定SearchContext中查找包含关键字的控件，并初步过滤掉不符合条件的控件，返回符合条件的控件列表 */
    private static List<WebElement> findElementsByText(SearchContext context, String key) {
        List<WebElement> elements = new ArrayList<>();
        /* 查找当前控件及后代控件中，文本包括关键字的控件 */
        By by = By.xpath("descendant-or-self::*[contains(text(), '" + key + "')]");
        elements.addAll(context.findElements(by));
        /* 查找当前控件及后代控件中，value属性中包括关键字的控件 */
        by = By.xpath("descendant-or-self::*[contains(@value, '" + key + "')]");
        elements.addAll(context.findElements(by));
        /* 查找当前控件及后代控件中，placeholder属性中包括关键字的控件 */
        by = By.xpath("descendant-or-self::*[contains(@placeholder, '" + key + "')]");
        elements.addAll(context.findElements(by));

        /* 初步过滤掉不符合条件的控件：script控件、未显示的控件 */
        int count = elements.size();
        for (int index = count - 1; index >= 0; index--) {
            WebElement element = elements.get(index);
            /* 忽略script标签
            * 忽略未显示的控件
            * */
            if ("script".equalsIgnoreCase(element.getTagName()) || !element.isDisplayed()) {
                elements.remove(index);
            }

        }

        return elements;
    }


    /* 二次比较，目标控件内部进行比较，选择最符合关键字的控件 */
    private static WebElement choose(List<WebElement> elements, String key) {

        int index = 0;
        int diff = -1;
        int newDiff;
        for (int i = 0; i < elements.size(); i++) {

            String text = elements.get(i).getText();
            if (null == text || text.equals("")) {
                text = elements.get(i).getAttribute("value");
            }

            newDiff = diff(text, key);

            if (newDiff >= 0 && (diff < 0 || diff > newDiff)) {
                diff = newDiff;
                index = i;
            }
        }

        WebElement element = elements.get(index);
        log.debug(MapUtil.getMapFromObject(element).toString());
        return element;

    }

    private static int diff(String text, String key) {
        if (text == null || key == null) {
            return -1;
        }

        if (!text.contains(key)) {
            return -1;
        }


        return text.length() - key.length();
    }

}
