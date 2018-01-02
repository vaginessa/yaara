package com.mlieou.yaara.aria2RPC;

import android.util.Log;

import com.mlieou.yaara.aria2RPC.model.ActiveTask;
import com.mlieou.yaara.aria2RPC.model.StoppedTask;
import com.mlieou.yaara.aria2RPC.model.WaitingTask;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mengdi on 12/26/17.
 */

public class Aria2RpcClient {
    private static final String TAG = "Aria2RpcClient";

    private OkHttpClient mClient;
    private String mSecret;
    private String mUrl;

    public Aria2RpcClient(String hostname, int port, String requestPath, String secret) {
        mClient = new OkHttpClient();
        mUrl = "http://" + hostname + ":" + port + "/" + requestPath;
        mSecret = secret;
    }

    public Aria2RpcClient(String hostname, int port, String requestPath) {
        this(hostname, port, requestPath, null);
    }

    public void addUri(List<String> uri, HashMap<String, String> options, int position) throws IOException {
        Response response = mClient.newCall(Aria2RpcRequest.addUri(
                "",
                mUrl,
                mSecret,
                uri,
                options,
                position)).execute();
    }
}
