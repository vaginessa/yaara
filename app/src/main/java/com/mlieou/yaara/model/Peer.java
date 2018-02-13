package com.mlieou.yaara.model;

/**
 * Created by mlieou on 2/11/18.
 */

public class Peer {
    private boolean amChoking;
    private String bitfield;
    private long downloadSpeed;
    private String ip;
    private boolean peerChoking;
    private String peerId;
    private int port;
    private boolean seeder;
    private long uploadSpeed;

    public long getDownloadSpeed() {
        return downloadSpeed;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public long getUploadSpeed() {
        return uploadSpeed;
    }
}
