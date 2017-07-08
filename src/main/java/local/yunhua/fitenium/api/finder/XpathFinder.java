package local.yunhua.fitenium.api.finder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yuyunhua on 2015-11-13.
 */

/**
 *
 */
public class XpathFinder extends Finder {

    private static final Logger log = LoggerFactory.getLogger(XpathFinder.class);

    /**
     * 根据给出的xpath，查询并返回相应的元素
     *
     * @param driver WebDriver对象
     * @param lastElement 上个操作对象，由于是xpath，不启作用
     * @param xpath xpath
     * @return 如果在driver中，根据xpath存在相应的元素，返回该元素；如果不存在，返回空
     */
    @Override
    public WebElement getElement(WebDriver driver, WebElement lastElement, String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    /**
     * 根据给出的xpath，查询并返回相应的输入元素
     *
     * @param driver WebDriver，浏览器对象
     * @param lastElement 上个操作对象，由于是xpath，不启作用
     * @param target String类型，xpath
     * @return 如果查找到对应的输入元素，返回该元素；如果未找到，返回空
     */
    @Override
    public WebElement getInputElement(WebDriver driver, WebElement lastElement, String target) {
        WebElement element = getElement(driver, lastElement, target);

        try {
            element.sendKeys(" \b");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return element;
    }
}
