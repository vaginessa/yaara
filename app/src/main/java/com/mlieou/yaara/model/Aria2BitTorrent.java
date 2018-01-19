package com.mlieou.yaara.model;

import java.util.List;

/**
 * Created by mlieou on 1/4/18.
 */

public class Aria2BitTorrent {
    private List<String> announceList;
    private String comment;
    private long creationDate;
    private String mode;
    private String info_name;

    public List<String> getAnnounceList() {
        return announceList;
    }

    public String getComment() {
        return comment;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public String getMode() {
        return mode;
    }

    public String getInfo_name() {
        return info_name;
    }
}
