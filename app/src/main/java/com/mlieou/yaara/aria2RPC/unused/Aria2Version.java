package com.mlieou.yaara.aria2RPC.unused;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengdi on 1/4/18.
 */

public class Aria2Version {
    private String version;
    private List<String> enabledFeatures;

    public String getVersion() {
        return version;
    }

    public List<String> getEnabledFeatures() {
        return enabledFeatures;
    }

    public static Aria2Version deserialize(JSONObject jsonObject) throws JSONException {
        Aria2Version version = new Aria2Version();
        version.version = jsonObject.getString("version");
        JSONArray jsonArray = jsonObject.getJSONArray("enabledFeatures");
        version.enabledFeatures = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            version.enabledFeatures.add(jsonArray.getString(i));
        }
        return version;
    }
}
