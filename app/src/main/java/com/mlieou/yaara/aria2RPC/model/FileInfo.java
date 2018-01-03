package com.mlieou.yaara.aria2RPC.model;

import com.mlieou.yaara.aria2RPC.Aria2RpcJsonLabel;
import com.mlieou.yaara.aria2RPC.JSONArrayHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengdi on 1/3/18.
 */

public class FileInfo {
    private long completedLength;
    private int index;
    private long length;
    private String path;
    private boolean selected;
    private List<String> uris;

    public FileInfo(long completedLength, int index, long length, String path, boolean selected, List<String> uris) {
        this.completedLength = completedLength;
        this.index = index;
        this.length = length;
        this.path = path;
        this.selected = selected;
        this.uris = uris;
    }

    public FileInfo(long completedLength, int index, long length, String path, boolean selected) {
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

    public static List<FileInfo> convertToList(JSONArray array) throws JSONException {
        List<FileInfo> fileInfoList = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            long completedLength = object.getLong(Aria2RpcJsonLabel.COMPLETED_LENGTH);
            int index = object.getInt(Aria2RpcJsonLabel.INDEX);
            long length = object.getLong(Aria2RpcJsonLabel.LENGTH);
            String path = object.getString(Aria2RpcJsonLabel.PATH);
            boolean isSelected = object.getBoolean(Aria2RpcJsonLabel.SELECTED);

            JSONArray urisArray = object.getJSONArray(Aria2RpcJsonLabel.URIS);
            List<String> uris = JSONArrayHelper.convertToList(urisArray);

            fileInfoList.add(new FileInfo(completedLength,
                    index, length, path, isSelected, uris));
        }

        return fileInfoList;
    }
}
