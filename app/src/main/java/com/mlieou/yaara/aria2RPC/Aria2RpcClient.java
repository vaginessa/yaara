package com.mlieou.yaara.aria2RPC;

import com.mlieou.yaara.aria2RPC.model.Aria2File;
import com.mlieou.yaara.aria2RPC.model.Aria2GlobalStat;
import com.mlieou.yaara.aria2RPC.model.Aria2How;
import com.mlieou.yaara.aria2RPC.model.Aria2Peer;
import com.mlieou.yaara.aria2RPC.model.Aria2Server;
import com.mlieou.yaara.aria2RPC.model.Aria2TaskStatus;
import com.mlieou.yaara.aria2RPC.model.Aria2Uri;
import com.mlieou.yaara.aria2RPC.model.Aria2Version;
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

    public Aria2TaskStatus tellStatus(String gid, List<String> keys)
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
            // TODO consider keys
            return Aria2TaskStatus.deserialize(
                    jsonObject.getJSONObject(Aria2RpcJsonLabel.RESULT).toString());
        } else
            return new Aria2TaskStatus();
    }

    public List<Aria2Uri> getUris(String gid) throws IOException, JSONException {
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
            return Aria2Uri.convertToList(jsonArray);
        }
        return new ArrayList<>();
    }

    public List<Aria2File> getFiles(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getFiles(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT);
            return Aria2File.convertToList(jsonArray);
        }
        return new ArrayList<>();
    }

    public List<Aria2Peer> getPeers(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getPeers(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT);
            return Aria2Peer.convertToList(jsonArray);
        }
        return new ArrayList<>();
    }

    public List<Aria2Server> getServers(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getServers(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONArray jsonArray = jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT);
            return Aria2Server.convertToList(jsonArray);
        }
        return new ArrayList<>();
    }

    public List<Aria2TaskStatus> tellActive(List<String> keys) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.tellActive(
                DEFAULT_ID,
                mUrl,
                mSecret,
                keys
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            // TODO consider keys
            return Aria2TaskStatus.deserializeList(
                    jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT));
        } else
            return new ArrayList<>();
    }

    public List<Aria2TaskStatus> tellWaiting(int offset, int num, List<String> keys)
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
            // TODO consider keys
            return Aria2TaskStatus.deserializeList(
                    jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT));
        } else
            return new ArrayList<>();
    }

    public List<Aria2TaskStatus> tellStopped(int offset, int num, List<String> keys)
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
            // TODO consider keys
            return Aria2TaskStatus.deserializeList(
                    jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT));
        } else
            return new ArrayList<>();
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
            return jsonObject.getInt(Aria2RpcJsonLabel.RESULT);
        } else
            return -1;
    }

    public int[] changeUri(String gid,
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
            JSONArray jsonArray = jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT);
            return new int[] {jsonArray.getInt(0), jsonArray.getInt(1)};
        } else
            return new int[] {0, 0};
    }

    public HashMap<String, String> getOption(String gid) throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getOption(
                DEFAULT_ID,
                mUrl,
                mSecret,
                gid
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONObject optionJson = jsonObject.getJSONObject(Aria2RpcJsonLabel.RESULT);
            return JSONHelper.toHashMap(optionJson);
        }
        return new HashMap<>();
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
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT).equals("OK");
        }
        return false;
    }

    public HashMap<String, String> getGlobalOption() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getGlobalOption(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONObject optionJson = jsonObject.getJSONObject(Aria2RpcJsonLabel.RESULT);
            return JSONHelper.toHashMap(optionJson);
        }
        return new HashMap<>();
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
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT).equals("OK");
        }
        return false;
    }

    public Aria2GlobalStat getGlobalStat() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getGlobalStat(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return Aria2GlobalStat.deserialize(
                    jsonObject.getJSONObject(Aria2RpcJsonLabel.RESULT));
        }
        return new Aria2GlobalStat();
    }

    public boolean purgeDownloadResult() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.purgeDownloadResult(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT).equals("OK");
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
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT).equals("OK");
        }
        return false;
    }

    public Aria2Version getVersion() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getVersion(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return Aria2Version.deserialize(
                    jsonObject.getJSONObject(Aria2RpcJsonLabel.RESULT));
        }
        return new Aria2Version();
    }

    public String getSessionInfo() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.getSessionInfo(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getJSONObject(Aria2RpcJsonLabel.RESULT).getString("sessionId");
        }
        return "";
    }

    public boolean shutdown() throws IOException, JSONException {
        Response response = mClient.newCall(Aria2RpcRequest.shutdown(
                DEFAULT_ID,
                mUrl,
                mSecret
        )).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT).equals("OK");
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
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT).equals("OK");
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
            return jsonObject.getString(Aria2RpcJsonLabel.RESULT).equals("OK");
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
            return JSONHelper.convertToList(jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT));
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
            return JSONHelper.convertToList(jsonObject.getJSONArray(Aria2RpcJsonLabel.RESULT));
        }
        return new ArrayList<>();
    }
}
