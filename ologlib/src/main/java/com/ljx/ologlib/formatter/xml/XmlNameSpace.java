package com.ljx.ologlib.formatter.xml;

/**
 * Created by cz on 16/3/3.
 * xml命令空间
 */
public class XmlNameSpace {
    public String prefix;//前缀
    public String url;//链接

    public XmlNameSpace(String prefix, String url) {
        this.prefix = prefix;
        this.url = url;
    }

    @Override
    public String toString() {
        return prefix + ":" + url + "\n";
    }
}
