package com.mlieou.yaara.aria2RPC;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mengdi on 12/28/17.
 */

public class JSONArrayHelper {

    private static final String TOKEN = "token:";

    public static void addList(JSONArray target, List<String> list) {
        if (list == null || list.size() == 0)   return;
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
        if (map == null || map.size() == 0) return null;
        JSONObject object;
        try {
            object = new JSONObject();
            for (Map.Entry<String, String> entry : map.entrySet())
                object.put(entry.getKey(), entry.getValue());
        } catch (JSONException e) {
            object = null;
        }
        return object;
    }
}