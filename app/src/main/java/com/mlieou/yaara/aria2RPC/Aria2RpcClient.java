package com.mlieou.yaara.aria2RPC;

import com.mlieou.yaara.aria2RPC.constant.Aria2RpcJsonConstant;
import com.mlieou.yaara.aria2RPC.model.Aria2How;
import com.mlieou.yaara.aria2RPC.util.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by mengdi on 12/26/17.
 */

public class Aria2RpcClient {
    
    private static final String EMPTY_STRING = "";

    private static final String DEFAULT_ID = EMPTY_STRING;

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
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT);
        } else {
            return EMPTY_STRING;
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
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT);
        } else
            return EMPTY_STRING;
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
            JSONArray resultArray = jsonObject.getJSONArray(Aria2RpcJsonConstant.RESULT);
            return JSONHelper.convertToList(resultArray);
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
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT);
        } else
            return EMPTY_STRING;
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
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT);
        } else
            return EMPTY_STRING;
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
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT);
        } else
            return EMPTY_STRING;
    }

    public boolean pauseAll() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.pauseAll(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT).equals(Aria2RpcJsonConstant.OK);
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
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT);
        } else
            return EMPTY_STRING;
    }

    public boolean forcePauseAll() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.forcePauseAll(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT).equals(Aria2RpcJsonConstant.OK);
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
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT);
        } else
            return EMPTY_STRING;
    }

    public boolean unpauseAll() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.unpauseAll(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT).equals(Aria2RpcJsonConstant.OK);
        } else
            return false;
    }

    public String tellStatus(String gid, List<String> keys)
            throws IOException, JSONException{
        Response response = mClient.newCall(Aria2RpcRequest.tellStatus(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid,
                keys
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONObject(Aria2RpcJsonConstant.RESULT).toString();
        } else
            return EMPTY_STRING;
    }

    public String getUris(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getUris(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            if (jsonObject.has(Aria2RpcJsonConstant.ERROR))
                return EMPTY_STRING;
            return jsonObject.getJSONArray(Aria2RpcJsonConstant.RESULT).toString();
        }
        return EMPTY_STRING;
    }

    public String getFiles(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getFiles(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONArray(Aria2RpcJsonConstant.RESULT).toString();
        }
        return EMPTY_STRING;
    }

    public String getPeers(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getPeers(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONArray(Aria2RpcJsonConstant.RESULT).toString();
        }
        return EMPTY_STRING;
    }

    public String getServers(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getServers(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONArray(Aria2RpcJsonConstant.RESULT).toString();
        }
        return EMPTY_STRING;
    }

    public String tellActive(List<String> keys) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.tellActive(
                DEFAULT_ID,
                mUrl,
                mSecret,
                keys
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONArray(Aria2RpcJsonConstant.RESULT).toString();
        } else
            return EMPTY_STRING;
    }

    public String tellWaiting(int offset, int num, List<String> keys)
            throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.tellWaiting(
                DEFAULT_ID,
                mUrl,
                mSecret,
                offset,
                num,
                keys
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONArray(Aria2RpcJsonConstant.RESULT).toString();
        } else
            return EMPTY_STRING;
    }

    public String tellStopped(int offset, int num, List<String> keys)
            throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.tellStopped(
                DEFAULT_ID,
                mUrl,
                mSecret,
                offset,
                num,
                keys
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONArray(Aria2RpcJsonConstant.RESULT).toString();
        } else
            return EMPTY_STRING;
    }

    public int changePosition(String gid, int pos, Aria2How how)
            throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.changePosition(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid,
                pos,
                how
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getInt(Aria2RpcJsonConstant.RESULT);
        } else
            return -1;
    }

    public String changeUri(String gid,
                           int fileIndex,
                           List<String> delUris,
                           List<String> addUris,
                           int position)
            throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.changeUri(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid,
                fileIndex,
                delUris,
                addUris,
                position
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONArray(Aria2RpcJsonConstant.RESULT).toString();
        } else
            return EMPTY_STRING;
    }

    public String getOption(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getOption(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONObject(Aria2RpcJsonConstant.RESULT).toString();
        }
        return EMPTY_STRING;
    }

    public boolean changeOption(String gid, HashMap<String, String> options)
            throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.changeOption(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid,
                options
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT)
                    .equals(Aria2RpcJsonConstant.OK);
        }
        return false;
    }

    public String getGlobalOption() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getGlobalOption(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONObject(Aria2RpcJsonConstant.RESULT).toString();
        }
        return EMPTY_STRING;
    }

    public boolean changeGlobalOption(HashMap<String, String> options)
            throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.changeGlobalOption(
                DEFAULT_ID,
                mUrl,
                mSecret,
                options
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT)
                    .equals(Aria2RpcJsonConstant.OK);
        }
        return false;
    }

    public String getGlobalStat() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getGlobalStat(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONObject(Aria2RpcJsonConstant.RESULT).toString();
        }
        return EMPTY_STRING;
    }

    public boolean purgeDownloadResult() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.purgeDownloadResult(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT)
                    .equals(Aria2RpcJsonConstant.OK);
        }
        return false;
    }

    public boolean removeDownloadResult(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.removeDownloadResult(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT)
                    .equals(Aria2RpcJsonConstant.OK);
        }
        return false;
    }

    public String getVersion() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getVersion(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONObject(Aria2RpcJsonConstant.RESULT).toString();
        }
        return EMPTY_STRING;
    }

    public String getSessionInfo() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getSessionInfo(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONObject(Aria2RpcJsonConstant.RESULT)
                    .getString(Aria2RpcJsonConstant.SESSION_ID);
        }
        return EMPTY_STRING;
    }

    public boolean shutdown() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.shutdown(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT)
                    .equals(Aria2RpcJsonConstant.OK);
        }
        return false;
    }

    public boolean forceShutdown() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.forceShutdown(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT)
                    .equals(Aria2RpcJsonConstant.OK);
        }
        return false;
    }

    public boolean saveSession() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.saveSession(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonConstant.RESULT).equals(Aria2RpcJsonConstant.OK);
        }
        return false;
    }

    // TODO: multi-call

    public List<String> listMethods() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.listMethods(
                DEFAULT_ID,
                mUrl
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return JSONHelper.convertToList(jsonObject.getJSONArray(Aria2RpcJsonConstant.RESULT));
        }
        return new ArrayList<>();
    }

    public List<String> listNotifications() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.listNotifications(
                DEFAULT_ID,
                mUrl
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return JSONHelper.convertToList(jsonObject.getJSONArray(Aria2RpcJsonConstant.RESULT));
        }
        return new ArrayList<>();
    }
}
