package com.ljx.ologlib.formatter;

/**
 * Created by cz on 2016/8/27.
 */
public class FormatterConfig {
    public boolean border;
    public boolean singleLine;
    public char horizontalIndicate;
    public char verticalIndicate;

    public static FormatterConfig get(){
        return new FormatterConfig();
    }

    public FormatterConfig() {
        singleLine = true;
        border = true;
        horizontalIndicate=' ';
        verticalIndicate=' ';
    }

    public FormatterConfig setSingleLine(boolean singleLine){
        this.singleLine=singleLine;
        return this;
    }

    public FormatterConfig setHorizontalIndicate(char horizontalIndicate){
        this.horizontalIndicate=horizontalIndicate;
        return this;
    }
    public FormatterConfig setVerticalIndicate(char verticalIndicate){
        this.verticalIndicate=verticalIndicate;
        return this;
    }

    public FormatterConfig setLogBorder(boolean border){
        this.border=border;
        return this;
    }
}
