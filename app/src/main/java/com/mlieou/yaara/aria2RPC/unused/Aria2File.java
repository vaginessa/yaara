package com.mlieou.yaara.aria2RPC.unused;

import com.mlieou.yaara.aria2RPC.constant.Aria2RpcJsonConstant;
import com.mlieou.yaara.aria2RPC.util.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengdi on 1/3/18.
 */

public class Aria2File {
    private long completedLength;
    private int index;
    private long length;
    private String path;
    private boolean selected;
    private List<String> uris;

    public Aria2File(long completedLength, int index, long length, String path, boolean selected, List<String> uris) {
        this.completedLength = completedLength;
        this.index = index;
        this.length = length;
        this.path = path;
        this.selected = selected;
        this.uris = uris;
    }

    public Aria2File(long completedLength, int index, long length, String path, boolean selected) {
        this.completedLength = completedLength;
        this.index = index;
        this.length = length;
        this.path = path;
        this.selected = selected;
        this.uris = new ArrayList<>();
    }

    public long getCompletedLength() {
        return completedLength;
    }

    public int getIndex() {
        return index;
    }

    public long getLength() {
        return length;
    }

    public String getPath() {
        return path;
    }

    public boolean isSelected() {
        return selected;
    }

    public List<String> getUris() {
        return uris;
    }

    public static List<Aria2File> convertToList(JSONArray array) throws JSONException {
        List<Aria2File> aria2FileList = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            long completedLength = object.getLong(Aria2RpcJsonConstant.COMPLETED_LENGTH);
            int index = object.getInt(Aria2RpcJsonConstant.INDEX);
            long length = object.getLong(Aria2RpcJsonConstant.LENGTH);
            String path = object.getString(Aria2RpcJsonConstant.PATH);
            boolean isSelected = object.getBoolean(Aria2RpcJsonConstant.SELECTED);

            JSONArray urisArray = object.getJSONArray(Aria2RpcJsonConstant.URIS);
            List<String> uris = JSONHelper.convertToList(urisArray);

            aria2FileList.add(new Aria2File(completedLength,
                    index, length, path, isSelected, uris));
        }

        return aria2FileList;
    }
}
