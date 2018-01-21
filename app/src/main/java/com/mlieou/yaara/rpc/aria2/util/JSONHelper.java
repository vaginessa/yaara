package com.mlieou.yaara.rpc.aria2.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by mlieou on 12/28/17.
 */

public class JSONHelper {

    private static final String TOKEN = "token:";

    public static void addList(JSONArray target, List<String> list) {
        if (list == null || list.size() == 0) return;
        JSONArray listArray = new JSONArray();
        for (String element : list)
            listArray.put(element);
        target.put(listArray);
    }

    public static void addSecret(JSONArray target, String secret) {
        if (secret == null || secret.length() == 0) return;
        target.put(TOKEN + secret);
    }

    public static void addJsonObject(JSONArray target, JSONObject object) {
        if (object == null) return;
        target.put(object);
    }

    public static JSONObject convertHashMapToJsonObject(HashMap<String, String> map) {
        if (map == null || map.size() == 0) return new JSONObject();
        JSONObject object = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : map.entrySet())
                object.put(entry.getKey(), entry.getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static HashMap<String, String> toHashMap(JSONObject jsonObject) throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String val = jsonObject.getString(key);
            map.put(key, val);
        }
        return map;
    }

    public static List<String> convertToList(JSONArray array) throws JSONException {
        List<String> stringList = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            stringList.add(array.getString(i));
        }
        return stringList;
    }
}
