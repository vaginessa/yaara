package com.mlieou.yaara.model;

import com.mlieou.yaara.rpc.aria2.constant.Aria2RpcJsonConstant;
import com.mlieou.yaara.rpc.aria2.util.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlieou on 1/3/18.
 */

public class File {

    public class Uri {
        public String uri;
        public String status;
    }

    private int index;
    private String path;
    private long length;
    private long completedLength;
    private boolean selected;
    private List<Uri> uris;

    public int getIndex() {
        return index;
    }

    public String getPath() {
        return path;
    }

    public long getLength() {
        return length;
    }

    public long getCompletedLength() {
        return completedLength;
    }

    public boolean isSelected() {
        return selected;
    }

    public List<Uri> getUris() {
        return uris;
    }
}
