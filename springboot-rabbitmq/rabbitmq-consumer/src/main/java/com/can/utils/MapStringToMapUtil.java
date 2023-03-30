package com.can.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: MapStringToMapUtil
 * @description: TODO 类描述
 * @author: zhengcan
 * @date: 2023/3/30
 **/
public class MapStringToMapUtil {
    public static Map<String,String> mapStringToMap(String str){
        str = str.substring(1, str.length()-1);
        String[] strs = str.split(",");
        Map<String,String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split("=")[0];
            String value = string.split("=")[1];
            // 去掉头部空格
            String key1 = key.trim();
            String value1 = value.trim();
            map.put(key1, value1);
        }
        return map;
    }

    public static Map<String, String> mapStringToMap2(String str,int entryNum ) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",",entryNum);
        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }

}
