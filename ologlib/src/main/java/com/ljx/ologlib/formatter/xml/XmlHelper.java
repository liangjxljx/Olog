package com.ljx.ologlib.formatter.xml;

import android.text.TextUtils;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cz on 16/3/3.
 * 一个Android xml帮助类.可以解析字符串xml为dom对象,及一些其他用处
 */
public class XmlHelper {

    private static final String TAG = "XmlHelper";

    public static XmlElement parserText(String text) {
        XmlElement rootElement = null;
        try {
            if (!TextUtils.isEmpty(text)) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(text.getBytes());
                rootElement = parserStream(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootElement;
    }

    public static XmlElement parserFile(File file) {
        XmlElement rootElement = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            rootElement = parserStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rootElement;
    }

    public static XmlElement parserStream(InputStream inputStream) {
        XmlPullParser parser = Xml.newPullParser();
        ArrayList<String> tags = new ArrayList<>();
        XmlElement root = null;
        XmlElement element = null;
        try {
            parser.setInput(inputStream, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
//                        print(parser);
                        break;
                    case XmlPullParser.ENTITY_REF:
                        break;
                    case XmlPullParser.CDSECT:

                        break;
                    case XmlPullParser.TEXT:
                        String text = parser.getText();
                        if (!TextUtils.isEmpty(text.trim())) {
                            element.text = text;
                        }
                        break;
                    case XmlPullParser.START_TAG:// 开始元素事件
                        XmlElement createElement = createElement(parser, root, tags);
                        if (null == element) {
                            root = element = createElement;//根节点
                        } else {
                            if (element.level < createElement.level) {
                                createElement.parent = element;
                                element.getChildren().add(createElement);
                                element = createElement;
                            } else if (element.level == createElement.level) {
                                createElement.parent = element.parent;
                                element.parent.getChildren().add(createElement);
                                element = createElement;
                            } else if (element.level > createElement.level) {
                                XmlElement parent = element.parent;
                                for (int i = createElement.level; i < element.level; i++, parent = parent.parent)
                                    ;//回退父级
                                parent.getChildren().add(createElement);
                                createElement.parent = parent;
                                element = createElement;
                            }
                        }
                        tags.add(name);//添加开始标签
                        break;
                    case XmlPullParser.END_TAG:
                        tags.remove(name);//移除结束标签
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return root;
    }

    /**
     * 获得命令空间集
     *
     * @param parser
     * @return
     * @throws XmlPullParserException
     */
    private static ArrayList<XmlNameSpace> getNameSpace(XmlPullParser parser) {
        ArrayList<XmlNameSpace> nameSpaces = new ArrayList<>();
        try {
            int namespaceCount = parser.getNamespaceCount(parser.getDepth());
            for (int i = 0; i < namespaceCount; i++) {
                nameSpaces.add(new XmlNameSpace(parser.getNamespacePrefix(i), parser.getNamespaceUri(i)));
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return nameSpaces;
    }


    public static List<String> formatElementItems(XmlElement root, boolean spacing) {
        ArrayList<String> items=new ArrayList<>();
        formatChildElement(root, items, spacing);
        return items;
    }
    /**
     * 格式化element为字符串
     *
     * @param root
     * @return
     */
    public static String formatElement(XmlElement root, boolean spacing){
        List<String> items = formatElementItems(root, spacing);
        StringBuilder formatValue=new StringBuilder();
        for(int i=0;i<items.size();i++){
            formatValue.append(items.get(i));
        }
        return formatValue.toString();
    }

    /**
     * 格式化子元素
     *
     * @param element
     * @param formatItems
     */
    private static void formatChildElement(XmlElement element, List<String> formatItems, boolean spacing) {
        if (null != element) {
            formatItems.add(getFormatTab(element.level) + "<" + element.name);
            //添加命名空间
            int nameSpaceCount = element.getNameSpaceCount();
            if (0 < nameSpaceCount) {
                for (int i = 0; i < nameSpaceCount; i++) {
                    XmlNameSpace nameSpace = element.getNameSpace(i);
                    if (0 == i) {
                        formatItems.add(" xmlns:" + nameSpace.prefix + "=\"" + nameSpace.url + "\"\n");
                    } else {
                        formatItems.add(getFormatTab(element.level + 1) + "xmlns:" + nameSpace.prefix + "=\"" + nameSpace.url + "\"\n");
                    }
                }
            }
            //添加属性
            int attributeCount = element.getAttributeCount();
            if (0 < attributeCount) {
                for (int i = 0; i < attributeCount; i++) {
                    XmlAttribute attribute = element.getAttribute(i);
                    String nameSpace = !TextUtils.isEmpty(attribute.nameSpace) ? attribute.nameSpace + ":" : "";
                    if (0 == i) {
                        formatItems.add((0 < nameSpaceCount ? getFormatTab(element.level + 1) : " ") +
                                nameSpace + attribute.name + "=\"" + attribute.value + "\"" + ((1 == attributeCount) ? "" : "\n"));
                    } else {
                        formatItems.add(getFormatTab(element.level + 1) + nameSpace + attribute.name + "=\"" + attribute.value + "\"" + (attributeCount - 1 != i ? "\n" : ""));
                    }
                }
            }
            //添加取值 之所以用\n\t\n,而不是\n\n,是因为\n\n被过滤了.直接不打印.纳尼
            int childCount = element.getChildCount();
            String end = spacing ? "\n\t\n" : "\n";
            if (0 < childCount) {
                formatItems.add(">" + end);
                for (int i = 0; i < childCount; i++) {
                    formatChildElement(element.getChild(i), formatItems, spacing);
                }
                formatItems.add(getFormatTab(element.level) + "</" + element.name + ">" + end);
            } else if (!TextUtils.isEmpty(element.text)) {
                formatItems.add(">" + element.text.trim() + "</" + element.name + ">" + end);
            } else {
                formatItems.add("/>" + end);
            }
        }
    }


    /**
     * 获得格式化制表符
     *
     * @param level
     * @return
     */
    private final static String getFormatTab(int level) {
        String value = "";
        for (int i = 0; i < level; i++, value += "\t") ;
        return value;
    }

    private static XmlElement createElement(XmlPullParser parser, XmlElement root, ArrayList<String> tags) {
        XmlElement element = new XmlElement();
        element.name = parser.getName();
        element.level = tags.size();
        addAttribute(element, root, parser);
        return element;
    }

    private static void addAttribute(XmlElement element, XmlElement root, XmlPullParser parser) {
        int attributeCount = parser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = parser.getAttributeName(i);
            String url = parser.getAttributeNamespace(i);
            String attributeNamespace;
            if (null == root) {
                if (0 == i) {
                    element.getNameSpaces().addAll(getNameSpace(parser));
                }
                attributeNamespace = element.getNameSpacePrefix(url);
            } else {
                attributeNamespace = root.getNameSpacePrefix(url);
            }
            String attributeValue = parser.getAttributeValue(url, attributeName);
            element.getAttributes().add(new XmlAttribute(attributeName, attributeNamespace, attributeValue));
        }
    }

}
