package com.ljx.ologlib.formatter;

import java.util.ArrayList;

/**
 * Created by cz on 16/3/3.
 * 打印对象
 */
public abstract class Printer<T> {
    //the android log max length is 4000,but need print some info.so I set max length is 3000
    private static final int MAX_LENGTH = 2000;
    private static final int MAX_SKIP_LENGTH = 500;
    protected FormatterConfig config;

    public void setConfig(FormatterConfig config){
        this.config=config;
    }

    /**
     * 格式化消息
     *
     * @param msg
     * @return
     */
    public abstract T format(String msg);
    
    protected String getIndicatorString(int length){
        String indication=new String();
        if(config!=null){
            for(int i=0;i<length;indication+=config.horizontalIndicate+" ",i+=2);
        }
        return indication;
    }
    /**
     * 获得格式化制表符
     *
     * @param level
     * @return
     */
    protected final String getFormatTab(int level) {
        String value = "";
        for (int i = 0; i < level; i++, value += "\t") ;
        return value;
    }

    protected final ArrayList<String> subItems(String msg) {
        int length = msg.length();
        int count = (0==length%MAX_LENGTH)?length / MAX_LENGTH:length/MAX_LENGTH+1;
        ArrayList<String> items = new ArrayList<>();
        int start = 0,end;
        for (int i = 0; i < count; i++) {
            end= Math.min(length, (i+1)*MAX_LENGTH);
            if(end!=length){
                char lastChar = msg.charAt(end);
                if('\n'!=lastChar){
                    int index = msg.indexOf("\n", end);
                    if(-1<index&&index<end+MAX_SKIP_LENGTH){
                        end+=(index-end);
                    }
                }
            }
            if (0 == i) {
                //the first
                items.add(msg.substring(start,end));
            } else if (count - 1 == i) {
                //the last
                items.add(msg.substring(start, length));
            } else {
                //center
                items.add(msg.substring(start, end));
            }
            start=end;
        }
        return items;
    }
}
