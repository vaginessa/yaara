package com.mlieou.yaara.aria2RPC.model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengdi on 1/3/18.
 */

public class PeerInfo {
    private boolean amChoking;
    // TODO bitfield
    private long downloadSpeed;
    private String ip;
    private boolean peerChoking;
    private String peerId;
    private int port;
    private boolean seeder;
    private long uploadSpeed;

    public PeerInfo(boolean amChoking,
                    long downloadSpeed,
                    String ip,
                    boolean peerChoking,
                    String peerId,
                    int port,
                    boolean seeder,
                    long uploadSpeed) {
        this.amChoking = amChoking;
        this.downloadSpeed = downloadSpeed;
        this.ip = ip;
        this.peerChoking = peerChoking;
        this.peerId = peerId;
        this.port = port;
        this.seeder = seeder;
        this.uploadSpeed = uploadSpeed;
    }

    public boolean isAmChoking() {
        return amChoking;
    }

    public long getDownloadSpeed() {
        return downloadSpeed;
    }

    public String getIp() {
        return ip;
    }

    public boolean isPeerChoking() {
        return peerChoking;
    }

    public String getPeerId() {
        return peerId;
    }

    public int getPort() {
        return port;
    }

    public boolean isSeeder() {
        return seeder;
    }

    public long getUploadSpeed() {
        return uploadSpeed;
    }

    public static List<PeerInfo> convertToList(JSONArray array) {
        // TODO
        return new ArrayList<>();
    }
}
