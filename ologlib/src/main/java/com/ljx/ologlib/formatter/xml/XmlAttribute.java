package com.ljx.ologlib.formatter.xml;

/**
 * Created by cz on 16/3/3.
 */
public class XmlAttribute {
    public String name;
    public String nameSpace;
    public String value;

    public XmlAttribute(String name, String nameSpace, String value) {
        this.name = name;
        this.nameSpace = nameSpace;
        this.value = value;
    }
}
