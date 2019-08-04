package com.ljx.ologlib.formatter.xml;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by cz on 16/3/3.
 * xml元素
 */
public class XmlElement {
    public String name;
    public String text;
    public int level;
    public XmlElement parent;
    private final ArrayList<XmlNameSpace> nameSpaces;
    private final ArrayList<XmlElement> children;
    private final ArrayList<XmlAttribute> attributes;

    public XmlElement() {
        nameSpaces = new ArrayList<>();
        children = new ArrayList<>();
        attributes = new ArrayList<>();
    }

    public ArrayList<XmlAttribute> getAttributes() {
        return attributes;
    }

    public int getChildCount() {
        return children.size();
    }

    public int getNameSpaceCount() {
        return nameSpaces.size();
    }


    public ArrayList<XmlNameSpace> getNameSpaces() {
        return nameSpaces;
    }

    public XmlNameSpace getNameSpace(int index) {
        XmlNameSpace nameSpace = null;
        int size = nameSpaces.size();
        if (-1 < index && index < size) {
            nameSpace = nameSpaces.get(index);
        }
        return nameSpace;
    }

    /**
     * 根据指定url获取前缀
     *
     * @param url
     * @return
     */
    public String getNameSpacePrefix(String url) {
        String prefix = null;
        if (!TextUtils.isEmpty(url)) {
            int size = nameSpaces.size();
            for (int i = 0; i < size; i++) {
                XmlNameSpace nameSpace = nameSpaces.get(i);
                if (url.equals(nameSpace.url)) {
                    prefix = nameSpace.prefix;
                    break;
                }
            }
        }
        return prefix;
    }


    /**
     * 获得指定名称引用值
     *
     * @param name
     * @return
     */
    public String getAttributeValue(String name) {
        String value = null;
        if (!TextUtils.isEmpty(name)) {
            int size = attributes.size();
            for (int i = 0; i < size; i++) {
                XmlAttribute attribute = attributes.get(i);
                if (name.equals(attribute.name)) {
                    value = attribute.value;
                    break;
                }

            }
        }
        return value;
    }

    /**
     * 获得指定位置引用
     *
     * @param index
     * @return
     */
    public XmlAttribute getAttribute(int index) {
        XmlAttribute attribute = null;
        int size = attributes.size();
        if (-1 < index && index < size) {
            attribute = attributes.get(index);
        }
        return attribute;
    }

    /**
     * 获取指定位置引用值
     *
     * @param index
     * @return
     */
    public String getAttributeValue(int index) {
        String value = null;
        int size = attributes.size();
        if (-1 < index && index < size) {
            value = attributes.get(index).value;
        }
        return value;
    }

    /**
     * 获得引用个数
     *
     * @return
     */
    public int getAttributeCount() {
        return attributes.size();
    }

    public XmlElement getChild(int index) {
        XmlElement element = null;
        int size = children.size();
        if (-1 < index && index < size) {
            element = children.get(index);
        }
        return element;
    }

    public ArrayList<XmlElement> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
