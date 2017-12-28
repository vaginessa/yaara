package com.mlieou.yaara.aria2RPC;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by mengdi on 12/28/17.
 */

public class RequestBuilder {
    private static final String TAG = "RequestBuilder";

    private final MediaType TYPE_JSON = MediaType.parse("application/json");

    public Request tellActive(String id, String url, String secret, List<String> keys) {
        JSONArray params = new JSONArray();
        if (secret != null) params.put(secret);
        if (keys != null) {
            JSONArray keyArray = new JSONArray();
            for (String key : keys)
                keyArray.put(key);
            params.put(keyArray);
        }
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(TYPE_JSON,
                        buildRequestContext(id, Aria2RpcMethod.tellActive, params)))
                .build();
        return request;
    }

    private static String buildRequestContext(String id, String method, JSONArray params) {
        String jsonStr = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("jsonrpc", Aria2RpcConstant.rpcServiceVersion);
            jsonObject.put("id", id);
            jsonObject.put("method", method);
            jsonObject.put("params", params);
            jsonStr = jsonObject.toString();
        } catch (JSONException e) {
            Log.i(TAG, "buildRequestContext: failed");
        }
        return jsonStr;
    }
}
