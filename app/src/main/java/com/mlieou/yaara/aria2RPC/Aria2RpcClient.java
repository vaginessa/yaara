package com.mlieou.yaara.aria2RPC;

import android.util.Log;

import com.mlieou.yaara.aria2RPC.model.ActiveTask;
import com.mlieou.yaara.aria2RPC.model.StoppedTask;
import com.mlieou.yaara.aria2RPC.model.WaitingTask;

import org.json.JSONObject;

import java.io.IOException;
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

    private List<ActiveTask> mActiveTaskList;
    private List<WaitingTask> mWaitingTaskList;
    private List<StoppedTask> mStoppedTaskList;

    private Request request = new Request.Builder()
            .url("http://10.24.233.100:6800/jsonrpc")
            .post(RequestBody.create(
                    MediaType.parse("application/json"),
                    "{\"id\":1, \"method\": \"aria2.tellActive\"}"
            )).build();

    public Aria2RpcClient(String hostname, int port, String requestPath, String secret) {
        mClient = new OkHttpClient();
        mUrl = "http://" + hostname + ":" + port + "/" + requestPath;
        mSecret = secret;
    }

    public Aria2RpcClient(String hostname, int port, String requestPath) {
        this(hostname, port, requestPath, null);
    }

    public Aria2RpcClient() {
        this("10.24.233.100", 6800, "jsonrpc");
    }

    public void getRawJson() {
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String msg = response.body().string();
                Log.i(TAG, "onResponse: " + msg);
            }
        });
    }

    public List<ActiveTask> getActiveTaskList() {
        return mActiveTaskList;
    }

    public List<WaitingTask> getWaitingTaskList() {
        return mWaitingTaskList;
    }

    public List<StoppedTask> getStoppedTaskList() {
        return mStoppedTaskList;
    }
}
