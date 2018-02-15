package com.mlieou.yaara.model;

import java.util.List;

/**
 * Created by mlieou on 1/4/18.
 */

public class BitTorrent {

    private List<List<String>> announceList;
    private String comment;
    private long creationDate;
    private String mode;
    private BitTorrentInfo info;

    public String getComment() {
        return comment;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public List<List<String>> getAnnounceList() {
        return announceList;
    }

    public String getMode() {
        return mode;
    }

    public BitTorrentInfo getInfo() {
        return info;
    }

    public class BitTorrentInfo {
        public String name;
    }

}
