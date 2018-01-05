package com.mlieou.yaara.aria2RPC;

import android.util.Base64;
import android.util.Log;

import com.mlieou.yaara.aria2RPC.model.Aria2How;
import com.mlieou.yaara.aria2RPC.util.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by mengdi on 12/28/17.
 */

public class Aria2RpcRequest {
    private static final String TAG = "Aria2RpcRequest";

    private static final MediaType TYPE_JSON = MediaType.parse("application/json");

    // TODO omits position for now

    public static Request addUri(String id,
                                 String url,
                                 String secret,
                                 List<String> uris,
                                 HashMap<String, String> options,
                                 int position) {

        JSONArray params = new JSONArray();
        JSONHelper.addSecret(params, secret);
        JSONHelper.addList(params, uris);
        JSONHelper.addJsonObject(params, JSONHelper.convertHashMapToJsonObject(options));
        return buildRequest(id, url, params, Aria2RpcMethod.addUri);
    }

    public static Request addTorrent(String id,
                                     String url,
                                     String secret,
                                     File torrent,
                                     List<String> uris,
                                     HashMap<String, String> options,
                                     int position) {
        JSONArray params = new JSONArray();
        JSONHelper.addSecret(params, secret);

        // encode file as base64
        try (FileInputStream inputStream = new FileInputStream(torrent)) {
            byte[] fileByte = new byte[(int) torrent.length()];
            inputStream.read(fileByte);
            String torrentBase64 = Base64.encodeToString(fileByte, Base64.DEFAULT);
            params.put(torrentBase64);
        } catch (IOException e) {
            Log.i(TAG, "addTorrent: failed to read file");
        }

        JSONHelper.addList(params, uris);
        JSONHelper.addJsonObject(params, JSONHelper.convertHashMapToJsonObject(options));
        return buildRequest(id, url, params, Aria2RpcMethod.addTorrent);
    }

    public static Request addMetalink(String id,
                                      String url,
                                      String secret,
                                      String metalink,
                                      HashMap<String, String> options,
                                      int position) {
        JSONArray params = new JSONArray();
        JSONHelper.addSecret(params, secret);
        params.put(metalink);
        JSONHelper.addJsonObject(params, JSONHelper.convertHashMapToJsonObject(options));
        return buildRequest(id, url, params, Aria2RpcMethod.addMetalink);
    }

    public static Request remove(String id, String url, String secret, String gid) {
        return buildRequest(id, url, secret, gid, Aria2RpcMethod.remove);
    }

    public static Request forceRemove(String id, String url, String secret, String gid) {
        return buildRequest(id, url, secret, gid, Aria2RpcMethod.forceRemove);
    }

    public static Request pause(String id, String url, String secret, String gid) {
        return buildRequest(id, url, secret, gid, Aria2RpcMethod.pause);
    }

    public static Request pauseAll(String id, String url, String secret) {
        return buildRequest(id, url, secret, "", Aria2RpcMethod.pauseAll);
    }

    public static Request forcePause(String id, String url, String secret, String gid) {
        return buildRequest(id, url, secret, gid, Aria2RpcMethod.forcePause);
    }

    public static Request forcePauseAll(String id, String url, String secret) {
        return buildRequest(id, url, secret, "", Aria2RpcMethod.forcePauseAll);
    }

    public static Request unpause(String id, String url, String secret, String gid) {
        return buildRequest(id, url, secret, gid, Aria2RpcMethod.unpause);
    }

    public static Request unpauseAll(String id, String url, String secret) {
        return buildRequest(id, url, secret, "", Aria2RpcMethod.unpauseAll);
    }

    public static Request tellStatus(String id,
                                     String url,
                                     String secret,
                                     String gid,
                                     List<String> keys) {
        JSONArray params = new JSONArray();
        JSONHelper.addSecret(params, secret);
        params.put(gid);
        JSONHelper.addList(params, keys);
        return buildRequest(id, url, params, Aria2RpcMethod.tellStatus);
    }

    public static Request getUris(String id, String url, String secret, String gid) {
        return buildRequest(id, url, secret, gid, Aria2RpcMethod.getUris);
    }

    public static Request getFiles(String id, String url, String secret, String gid) {
        return buildRequest(id, url, secret, gid, Aria2RpcMethod.getFiles);
    }

    public static Request getPeers(String id, String url, String secret, String gid) {
        return buildRequest(id, url, secret, gid, Aria2RpcMethod.getPeers);
    }

    public static Request getServers(String id, String url, String secret, String gid) {
        return buildRequest(id, url, secret, gid, Aria2RpcMethod.getServers);
    }

    public static Request tellActive(String id, String url, String secret, List<String> keys) {
        return buildRequest(id, url, secret, keys, Aria2RpcMethod.tellActive);
    }

    public static Request tellWaiting(String id,
                                      String url,
                                      String secret,
                                      int offset,
                                      int num,
                                      List<String> keys) {
        return buildRequest(id, url, secret, offset, num, keys, Aria2RpcMethod.tellWaiting);
    }

    public static Request tellStopped(String id,
                                      String url,
                                      String secret,
                                      int offset,
                                      int num,
                                      List<String> keys) {
        return buildRequest(id, url, secret, offset, num, keys, Aria2RpcMethod.tellStopped);
    }

    public static Request changePosition(String id,
                                         String url,
                                         String secret,
                                         String gid,
                                         int pos,
                                         Aria2How how) {
        JSONArray params = new JSONArray();
        JSONHelper.addSecret(params, secret);
        params.put(gid);
        params.put(pos);
        params.put(how);
        return buildRequest(id, url, params, Aria2RpcMethod.changePosition);
    }

    public static Request changeUri(String id,
                                    String url,
                                    String secret,
                                    String gid,
                                    int fileIndex,
                                    List<String> delUris,
                                    List<String> addUris,
                                    int position) {
        JSONArray params = new JSONArray();
        JSONHelper.addSecret(params, secret);
        params.put(gid);
        params.put(fileIndex);
        JSONHelper.addList(params, delUris);
        JSONHelper.addList(params, addUris);
        return buildRequest(id, url, params,Aria2RpcMethod.changeUri);
    }

    public static Request getOption(String id, String url, String secret, String gid) {
        return buildRequest(id, url, secret, gid, Aria2RpcMethod.getOption);
    }

    public static Request changeOption(String id,
                                    String url,
                                    String secret,
                                    String gid,
                                    HashMap<String, String> options) {
        JSONArray params = new JSONArray();
        JSONHelper.addSecret(params, secret);
        params.put(gid);
        JSONHelper.addJsonObject(params, JSONHelper.convertHashMapToJsonObject(options));
        return buildRequest(id, url, params, Aria2RpcMethod.changeOption);
    }

    public static Request getGlobalOption(String id, String url, String secret) {
        return buildRequest(id, url, secret, "", Aria2RpcMethod.getGlobalOption);
    }

    public static Request changeGlobalOption(String id,
                                             String url,
                                             String secret,
                                             HashMap<String, String> options) {
        JSONArray params = new JSONArray();
        JSONHelper.addSecret(params, secret);
        JSONHelper.addJsonObject(params, JSONHelper.convertHashMapToJsonObject(options));
        return buildRequest(id, url, params, Aria2RpcMethod.changeGlobalOption);
    }

    public static Request getGlobalStat(String id, String url, String secret) {
        return buildRequest(id, url, secret, "", Aria2RpcMethod.getGlobalStat);
    }

    public static Request purgeDownloadResult(String id, String url, String secret) {
        return buildRequest(id, url, secret, "", Aria2RpcMethod.purgeDownloadResult);
    }

    public static Request removeDownloadResult(String id, String url, String secret, String gid) {
        return buildRequest(id, url, secret, gid, Aria2RpcMethod.removeDownloadResult);
    }

    public static Request getVersion(String id, String url, String secret) {
        return buildRequest(id, url, secret, "", Aria2RpcMethod.getVersion);
    }

    public static Request getSessionInfo(String id, String url, String secret) {
        return buildRequest(id, url, secret, "", Aria2RpcMethod.getSessionInfo);
    }

    public static Request shutdown(String id, String url, String secret) {
        return buildRequest(id, url, secret, "", Aria2RpcMethod.shutdown);
    }

    public static Request forceShutdown(String id, String url, String secret) {
        return buildRequest(id, url, secret, "", Aria2RpcMethod.forceShutdown);
    }

    public static Request saveSession(String id, String url, String secret) {
        return buildRequest(id, url, secret, "", Aria2RpcMethod.saveSession);
    }

    public static Request multicall(String id, String url, String secret) {
        // TODO
        throw new UnsupportedOperationException("Not implemented");
    }

    public static Request listMethods(String id, String url) {
        return buildRequest(id, url, new JSONArray(), Aria2RpcMethod.listMethods);
    }

    public static Request listNotifications(String id, String url) {
        return buildRequest(id, url, new JSONArray(), Aria2RpcMethod.listNotifications);
    }

    private static Request buildRequest(String id,
                                        String url,
                                        String secret,
                                        int offset,
                                        int num,
                                        List<String> keys,
                                        String method) {
        JSONArray params = new JSONArray();
        JSONHelper.addSecret(params, secret);
        params.put(offset);
        params.put(num);
        JSONHelper.addList(params, keys);
        return buildRequest(id, url, params, method);
    }

    private static Request buildRequest(String id,
                                        String url,
                                        String secret,
                                        List<String> keys,
                                        String method) {
        JSONArray params = new JSONArray();
        JSONHelper.addSecret(params, secret);
        JSONHelper.addList(params, keys);
        return buildRequest(id, url, params, method);
    }

    private static Request buildRequest(String id,
                                        String url,
                                        String secret,
                                        String gid,
                                        String method) {
        JSONArray params = new JSONArray();
        JSONHelper.addSecret(params, secret);
        if (gid != null && gid.length() > 0) params.put(gid);
        return buildRequest(id, url, params, method);
    }

    private static Request buildRequest(String id, String url, JSONArray params, String method) {
        String jsonStr = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Aria2RpcJsonLabel.JSONRPC, Aria2RpcConstant.rpcServiceVersion);
            jsonObject.put(Aria2RpcJsonLabel.ID, id);
            jsonObject.put(Aria2RpcJsonLabel.METHOD, method);
            jsonObject.put(Aria2RpcJsonLabel.PARAMS, params);
            jsonStr = jsonObject.toString();
        } catch (JSONException e) {
            Log.i(TAG, "buildRequestContext: JSON parsing failed");
        }
        return new Request.Builder()
                .url(url)
                .post(RequestBody.create(TYPE_JSON, jsonStr))
                .build();
    }
}
