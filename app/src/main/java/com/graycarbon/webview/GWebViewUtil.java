package com.graycarbon.webview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by GrayCarbon on 2018.4.11.
 * Info :
 */
public class GWebViewUtil {


    /**
     * 遍历并格式化json数据
     * 注：只对{"action":"toast_return","params":{"message":"Hello Word!"}}格式有效
     *
     * @param json {"action":"toast_return","params":{"message":"Hello Word!"}}
     * @return 格式化好的json数据
     */
    public static String formatJsonData(String json) {
        String formatJson = "";
        JSONObject rootObject;
        try {
            rootObject = new JSONObject(json);
            String action = rootObject.optString("action");
            JSONObject paramsObject = rootObject.getJSONObject("params");
            Iterator<String> iterator = paramsObject.keys();
            StringBuilder sb = new StringBuilder();
            sb.append("action :").append("\n\t\t  ").append(action).append("\n").append("params " +
                    ":\n");
            ArrayList<String> list = new ArrayList<>();
            int maxKeyLength = 0;
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = paramsObject.get(key);
                list.add(key + "|" + value);
                if (key.length() > maxKeyLength)
                    maxKeyLength = key.length();
            }
            if (sb.length() > 0) {
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        String[] kv = list.get(i).split("\\|");
                        String key = kv[0];
                        String value = kv[1];
                        sb.append("\n\t\t  ").append(key);
                        if (key.length() < maxKeyLength) {
                            for (int j = 0; j < (maxKeyLength - key.length()); j++) {
                                sb.append(" ");
                            }
                        }
                        sb.append(" : ").append(value).append("\n");
                    }
                }
                formatJson = sb.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return formatJson;
    }

}
