package local.yunhua.fitenium.interpreter;

import local.yunhua.fitenium.interpreter.xml.NodeType;
import local.yunhua.fitenium.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuyunhua on 2015-11-04.
 */
public class XmlInterpreter implements Interpreter {

    private static final Logger log = LoggerFactory.getLogger(XmlInterpreter.class);

    public TestSuite interpret(String filePath) {

        File file = new File(filePath);
        if (!file.exists()) {
            log.error("文件不存在：" + filePath);
            return null;
        }

        TestSuite testSuite = new TestSuite();

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element root = document.getDocumentElement();
            if (null == root) {
                return testSuite;
            }

            NodeList nodeList = root.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                if (Node.ELEMENT_NODE != node.getNodeType()) {
                    continue;
                }

                String nodeName = node.getNodeName();
                NodeType nodeType;
                try {
                    nodeType = NodeType.valueOf(nodeName.toUpperCase());
                } catch (Exception e) {
                    log.error("无法识别的节点: " + nodeName);
                    continue;
                }


                switch (nodeType) {
                    case CASE:
                        testSuite.addTestCase(getCase(node));
                        break;
                    case PROPERTIES:
                        testSuite.addProperties(getProperties(node));
                        break;
                    case DATA:
                        testSuite.addDataSource(getData(node));
                        break;
                    default:
                        log.error("无法识别的节点：" + nodeType);
                }


            }


        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return testSuite;

    }

    public Map<String, List<Map<String, String>>> getData(Node node) {
        String id;
        try {
            id = node.getAttributes().getNamedItem("id").getNodeValue();
        } catch (Exception e) {
            return null;
        }

        List<Map<String, String>> dataList = new ArrayList<>();
        NodeList stepNodeList = node.getChildNodes();
        for (int j = 0; j < stepNodeList.getLength(); j++) {
            Node stepNode = stepNodeList.item(j);
            if (Node.ELEMENT_NODE == stepNode.getNodeType()) {
                dataList.add(getField(stepNode));
            }
        }

        Map<String, List<Map<String, String>>> map = new HashMap<>();
        map.put(id, dataList);
        return map;
    }


    public Map<String, String> getField(Node node) {
        NodeList list = node.getChildNodes();
        Map<String, String> field = new HashMap<>();
        for (int j = 0; j < list.getLength(); j++) {
            Node stepNode = list.item(j);
            if (Node.ELEMENT_NODE == stepNode.getNodeType()) {
                Map<String, String> fieldAttributes = getAttributes(stepNode);
                String key = fieldAttributes.get("key");
                String value = fieldAttributes.get("value");
                if (null != key) {
                    field.put(key, value);
                }
            }
        }
        return field;
    }

    public TestCase getCase(Node node) {

        NodeList stepNodeList = node.getChildNodes();
        TestCase testCase = new TestCase();

        Map<String, String> caseAttributes = getAttributes(node);

        testCase.setDataSource(caseAttributes.get("data-source"));
        testCase.setName(caseAttributes.get("name"));
        testCase.setDesc(caseAttributes.get("desc"));

        for (int j = 0; j < stepNodeList.getLength(); j++) {
            Node stepNode = stepNodeList.item(j);
            if (Node.ELEMENT_NODE != stepNode.getNodeType()) {
                continue;
            }


            String nodeName = stepNode.getNodeName();
            NodeType nodeType;
            try {
                nodeType = NodeType.valueOf(nodeName.toUpperCase());
            } catch (Exception e) {
                log.error("无法识别的节点: " + nodeName);
                continue;
            }


            switch (nodeType) {
                case STEP:
                    TestStep step = getStep(stepNode);
                    testCase.addTestStep(step);
                    break;
                case FORM:
                    testCase.addTestStep(getDataModule(stepNode));
                    break;
                default:
                    log.error("无法识别的节点：" + nodeType);
            }


        }

        return testCase;
    }

    public List<KeyValuePair> getProperties(Node node) {
        /* 初始化属性列表 */
        List<KeyValuePair> properties = new ArrayList<>();

        /* 遍历子节点，获取属性 */
        NodeList nodeList = node.getChildNodes();
        for (int index = 0; index < nodeList.getLength(); index++) {
            Node childNode = nodeList.item(index);
            if (Node.ELEMENT_NODE != childNode.getNodeType()) {
                continue;
            }

            /*
            * 获取节点的key及value属性
            * */
            Map<String, String> attributes = getAttributes(childNode);
            String key = attributes.get("key");
            String value = attributes.get("value");
            /*
            * 如果key非空，添加到properties中
            * */
            if (null != key) {
                properties.add(new KeyValuePair(key, value));
            }
        }

        return properties;
    }

    /* 获取节点的属性，存储在Map中 */
    private Map<String, String> getAttributes(Node node) {
        Map<String, String> nodeAttributes = new HashMap<>();
        for (int index = 0; index < node.getAttributes().getLength(); index++) {
            Node attrNode = node.getAttributes().item(index);
            nodeAttributes.put(attrNode.getNodeName(), attrNode.getNodeValue());
        }
        log.debug(nodeAttributes.toString());
        return nodeAttributes;
    }


    /**
     * 解析step节点
     */
    private TestStep getStep(Node stepNode) {

        TestStep step = new TestStep();


        Map<String, String> stepAttributes = getAttributes(stepNode);

        // 获取target属性
        step.setTarget(stepAttributes.get("target"));
                /* 获取value属性 */
        step.setValue(stepAttributes.get("value"));
                /* 获取by属性 */
        step.setByType(stepAttributes.get("by"));
                /* 获取field属性 */
        step.setField(stepAttributes.get("field"));
                /* 获取operation属性 */
        String operation = stepAttributes.get("action");
        Action action;
        try {
            action = Action.valueOf(operation.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Invalid actions: " + operation.toUpperCase());
            action = Action.CLICK;
        } catch (NullPointerException e) {
            action = Action.CLICK;
        }
        step.setAction(action);

        log.debug(step.toString());

        return step;

    }


    /**
     * 解析data-module
     */
    private List<TestStep> getDataModule(Node node) {

        // 初始化步骤列表
        List<TestStep> stepList = new ArrayList<>();

        Map<String, String> stepAttributes = getAttributes(node);


        // 获取trigger属性
        String trigger = stepAttributes.get("trigger");
        if (trigger != null) {
            TestStep step = new TestStep(trigger, stepAttributes.get("triggerBy"), Action.CLICK, null);
            stepList.add(step);
        }


        for (int j = 0; j < node.getChildNodes().getLength(); j++) {
            Node itemNode = node.getChildNodes().item(j);
            if (Node.ELEMENT_NODE != itemNode.getNodeType()) {
                continue;
            }


            String nodeName = itemNode.getNodeName();
            NodeType nodeType;
            try {
                nodeType = NodeType.valueOf(nodeName.toUpperCase());
            } catch (Exception e) {
                log.error("无法识别的节点: " + nodeName);
                continue;
            }


            switch (nodeType) {
                case ITEM:
                    TestStep step = getItem(itemNode);
                    stepList.add(step);
                    break;
                default:
                    log.error("无法识别的节点：" + nodeType);
            }


        }

        // 获取trigger属性
        String next = stepAttributes.get("next");
        if (next != null) {
            TestStep step = new TestStep(next, stepAttributes.get("nextBy"), Action.CLICK, null);
            stepList.add(step);
        }

        return stepList;

    }


    /**
     * 解析item节点
     */
    private TestStep getItem(Node stepNode) {
        TestStep step = getStep(stepNode);
        step.setAction(Action.TYPE);
        return step;

    }


    private void processNode(Node node) {


        if (Node.ELEMENT_NODE != node.getNodeType()) {
            return;
        }


        String nodeName = node.getNodeName();
        NodeType nodeType;
        try {
            nodeType = NodeType.valueOf(nodeName.toUpperCase());
        } catch (Exception e) {
            return;
        }

        switch (nodeType) {
            case ITEM:
                getItem(node);
                break;
            case STEP:
                getStep(node);
                break;
            case PROPERTIES:
                getProperties(node);
                break;
            case CASE:
                getCase(node);
                break;
            case DATA:
                getData(node);
                break;
            default:
                break;
        }


    }
}

