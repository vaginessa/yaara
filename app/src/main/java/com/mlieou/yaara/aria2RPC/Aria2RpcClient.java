package com.mlieou.yaara.aria2RPC;

import android.util.Log;

import com.mlieou.yaara.aria2RPC.model.ActiveTask;
import com.mlieou.yaara.aria2RPC.model.FileInfo;
import com.mlieou.yaara.aria2RPC.model.PeerInfo;
import com.mlieou.yaara.aria2RPC.model.ServerInfo;
import com.mlieou.yaara.aria2RPC.model.StoppedTask;
import com.mlieou.yaara.aria2RPC.model.UriStatus;
import com.mlieou.yaara.aria2RPC.model.WaitingTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    private static final String DEFAULT_ID = "";

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

    public String addUri(List<String> uris, HashMap<String, String> options, int position)
            throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.addUri(
                DEFAULT_ID,
                mUrl,
                mSecret,
                uris,
                options,
                position
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT);
        } else {
            return "";
        }
    }

    public String addTorrent(File torrent,
                             List<String> uris,
                             HashMap<String, String> options,
                             int position)
            throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.addTorrent(
                DEFAULT_ID,
                mUrl,
                mSecret,
                torrent,
                uris,
                options,
                position
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT);
        } else
            return "";
    }

    public List<String> addMetalink(String metalink,
                                    HashMap<String, String> options,
                                    int position)
            throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.addMetalink(
                DEFAULT_ID,
                mUrl,
                mSecret,
                metalink,
                options,
                position
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray resultArray = jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT);
            return JSONArrayHelper.convertToList(resultArray);
        } else
            return new ArrayList<>();
    }

    public String remove(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.remove(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT);
        } else
            return "";
    }

    public String forceRemove(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.forceRemove(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT);
        } else
            return "";
    }

    public String pause(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.pause(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT);
        } else
            return "";
    }

    public boolean pauseAll() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.pauseAll(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT).equals("OK");
        } else
            return false;
    }

    public String forcePause(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.forcePause(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT);
        } else
            return "";
    }

    public boolean forcePauseAll() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.forcePauseAll(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT).equals("OK");
        } else
            return false;
    }

    public String unpause(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.unpause(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT);
        } else
            return "";
    }

    public boolean unpauseAll() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.unpauseAll(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT).equals("OK");
        } else
            return false;
    }

    public HashMap<String, String> tellStatus(String gid, List<String> keys)
            throws IOException, JSONException{
        Response response = mClient.newCall(Aria2RpcRequest.tellStatus(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid,
                keys
        )).execute();
        if (response.isSuccessful()) {
            // TODO
            return new HashMap<>();
        } else
            return new HashMap<>();
    }

    public List<UriStatus> getUris(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getUris(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            if (jsonObject.has(Aria2RpcJsonLabel.ERROR))
                return new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT);
            return UriStatus.convertToList(jsonArray);
        }
        return new ArrayList<>();
    }

    public List<FileInfo> getFiles(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getFiles(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT);
            return FileInfo.convertToList(jsonArray);
        }
        return new ArrayList<>();
    }

    public List<PeerInfo> getPeers(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getPeers(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT);
            return PeerInfo.convertToList(jsonArray);
        }
        return new ArrayList<>();
    }

    public List<ServerInfo> getServers(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getServers(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT);
            return ServerInfo.convertToList(jsonArray);
        }
        return new ArrayList<>();
    }

}
