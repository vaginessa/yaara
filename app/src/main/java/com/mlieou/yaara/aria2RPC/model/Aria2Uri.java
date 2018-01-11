package com.mlieou.yaara.aria2RPC.model;

import com.mlieou.yaara.aria2RPC.Aria2RpcJsonLabel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengdi on 1/2/18.
 */


public class Aria2Uri {
    private String uri;
    private String status;

    public Aria2Uri(String uri, String status) {
        this.uri = uri;
        this.status = status;
    }

    public String getUri() {
        return uri;
    }

    public String getStatus() {
        return status;
    }

    public static List<Aria2Uri> convertToList(JSONArray array) throws JSONException {
        List<Aria2Uri> list = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            list.add(new Aria2Uri(object.getString(Aria2RpcJsonLabel.URI),
                    object.getString(Aria2RpcJsonLabel.STATUS)));
        }
        return list;
    }
}