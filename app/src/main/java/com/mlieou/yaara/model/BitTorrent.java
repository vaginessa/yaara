package com.mlieou.yaara.model;

import java.util.List;

/**
 * Created by mlieou on 1/4/18.
 */

public class BitTorrent {

    //    Fixme
//    private List<AnnounceURIList> announceList;
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

//    public List<AnnounceURIList> getAnnounceList() {
//        return announceList;
//    }

    public String getMode() {
        return mode;
    }

    public BitTorrentInfo getInfo() {
        return info;
    }

    public class BitTorrentInfo {
        public String name;
    }

    public class AnnounceURIList {
        public List<String> list;
    }
}
