package com.ljx.ologlib.formatter;

import android.text.TextUtils;
import com.ljx.ologlib.api.StackTraceUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cz on 16/3/3.
 */
public class JsonPrinter extends Printer<ArrayList<String>> {

    public JsonPrinter() {
        config = FormatterConfig.get();
    }

    @Override
    public ArrayList<String> format(String msg) {
        ArrayList<String> formatItems=new ArrayList<>();
        JSONException error = null;
        Map<String, Object> params = null;
        try {
            params = getParams(msg);
        } catch (JSONException e) {
            error = e;
        }
        if (null != params) {
            formatParams(0, formatItems, null, params);
        } else if (null != error) {
            //打印解析异常
            formatItems.add("{\n");
            formatItems.add("\t\"value\": " + msg + "\",\n");
            formatItems.add("\t\"error\":\"" + StackTraceUtils.getStackTraceString(error) + "\",\n");
            formatItems.add("}\n");
        }
        ArrayList<String> newItems=new ArrayList<>();
        if(config.singleLine){
            if(config.border){
                for (int i = 0; i < formatItems.size(); i++) {
                    newItems.add(config.horizontalIndicate+" "+ formatItems.get(i));
                }
            }
        } else {
            StringBuilder logBuilder=new StringBuilder();
            for(String item:formatItems){
                logBuilder.append((config.border?config.horizontalIndicate+" ":"")+item);
            }
            newItems=subItems(logBuilder.toString());
        }
        return newItems;
    }

    private void formatParams(int level, ArrayList<String> formatItems, String tag, Map<String, Object> params) {
        if (null != params) {
            if (!TextUtils.isEmpty(tag)) {
                formatItems.add(getFormatTab(level) + "\"" + tag + "\":{\n");
            } else {
                formatItems.add(getFormatTab(level) + "{\n");
            }
            int index = 0;
            int size = params.size();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String endFlag = (size - 1 == index++) ? "" : ",";
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof JSONArray) {
                    jsonArray(level + 1, formatItems, key, value);
                } else if (value instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) value;
                    jsonObject(level, formatItems, key, value, jsonObject);
                } else if (value instanceof String) {
                    formatItems.add(getFormatTab(level + 1) + "\"" + key + "\":\"" + value.toString() + "\"" + endFlag + "\n");
                } else {
                    formatItems.add(getFormatTab(level + 1) + "\"" + key + "\":" + value.toString() + endFlag + "\n");
                }
            }
            formatItems.add(getFormatTab(level) + "}\n");
        }
    }

    /**
     * 处理JSONarray信息
     *
     * @param level
     * @param formatItems
     * @param key
     * @param value
     */
    private void jsonArray(int level, ArrayList<String> formatItems, String key, Object value) {
        try {
            JSONArray jsonArray = (JSONArray) value;
            int length = jsonArray.length();
            formatItems.add(getFormatTab(level) + "\"" + key + "(" + length + ")\":" + "[\n");
            for (int i = 0; i < length; i++) {
                String endFlag = (length - 1 == i) ? "" : ",";
                Object object = jsonArray.get(i);
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    jsonObject(level, formatItems, key, value, jsonObject);
                } else if (object instanceof String) {
                    formatItems.add(getFormatTab(level + 1) + "\"" + key + "\":\"" + value.toString() + "\"" + endFlag + "\n");
                } else {
                    formatItems.add(getFormatTab(level + 1) + "\"" + key + "\":" + value.toString() + endFlag + "\n");
                }
            }
            formatItems.add(getFormatTab(level) + "]\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void jsonObject(int level, ArrayList<String> formatItems, String key, Object value, JSONObject jsonObject) {
        try {
            Map<String, Object> innerParams = getParamsFromJSONObject(jsonObject);
            if (null == innerParams || innerParams.isEmpty()) {
                formatItems.add(getFormatTab(level + 1) + "\"" + key + "\":" + value.toString() + ",\n");
            } else {
                formatParams(level + 1, formatItems, null, innerParams);
            }
        } catch (JSONException e) {
            formatItems.add(getFormatTab(level + 1) + "\"value\": " + value.toString() + "\",\n");
            formatItems.add(getFormatTab(level + 1) + "\"error\":\"" + StackTraceUtils.getStackTraceString(e) + "\",\n");
        }
    }


    /**
     * 从json字符串中获得map键值
     *
     * @param result
     * @return
     */
    private static Map<String, Object> getParams(String result) throws JSONException {
        Map<String, Object> params = null;
        if (!TextUtils.isEmpty(result)) {
            JSONObject jsonObject = new JSONObject(result);
            params = getParamsFromJSONObject(jsonObject);
        }
        return params;
    }

    private static Map<String, Object> getParamsFromJSONObject(JSONObject jsonObject) throws JSONException {
        Map<String, Object> params = null;
        if (null != jsonObject) {
            params = new LinkedHashMap<>();
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = jsonObject.opt(key);
                if (JSONObject.NULL.equals(value)) {
                    value = null;
                }
                if (null != value) {
                    params.put(key, value);
                }
            }
        }
        return params;
    }

}
