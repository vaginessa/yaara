package com.mlieou.yaara.model;

import java.util.List;

/**
 * Created by mlieou on 1/3/18.
 */

public class File {

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

    public class Uri {
        public String uri;
        public String status;
    }
}
