package com.ljx.ologlib.formatter;

import com.ljx.ologlib.formatter.xml.XmlElement;
import com.ljx.ologlib.formatter.xml.XmlHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cz on 16/3/3.
 */
public class XmlPrinter extends Printer<List<String>> {
    private boolean spacing;

    public XmlPrinter(){
        config = FormatterConfig.get();
    }

    @Override
    public List<String> format(String xml) {
        XmlElement element = XmlHelper.parserText(xml);
        List<String> newItems=new ArrayList<>();
        List<String> items = XmlHelper.formatElementItems(element, spacing);
        StringBuilder cache=new StringBuilder();
        for(int i=0;i<items.size();i++){
            String item = items.get(i);
            cache.append(item);
            if(item.endsWith("\n")){
                newItems.add((config.border?config.horizontalIndicate+" ":"")+cache.toString());
                cache.delete(0,cache.length());
            }
        }
        if(!config.singleLine){
            cache.delete(0,cache.length());
            for(int i=0;i<newItems.size();i++){
                cache.append(newItems.get(i));
            }
            newItems=subItems(cache.toString());
        }
        return newItems;
    }

    /**
     * 输出带间距
     *
     * @param spacing
     */
    public void setSpacing(boolean spacing) {
        this.spacing = spacing;
    }

}
