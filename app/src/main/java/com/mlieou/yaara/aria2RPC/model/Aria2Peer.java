package com.mlieou.yaara.aria2RPC.model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengdi on 1/3/18.
 */

public class Aria2Peer {
    private boolean amChoking;
    private byte[] bitfield;
    private long downloadSpeed;
    private String ip;
    private boolean peerChoking;
    private String peerId;
    private int port;
    private boolean seeder;
    private long uploadSpeed;

    public Aria2Peer(boolean amChoking,
                     byte[] bitfield,
                     long downloadSpeed,
                     String ip,
                     boolean peerChoking,
                     String peerId,
                     int port,
                     boolean seeder,
                     long uploadSpeed) {
        this.amChoking = amChoking;
        this.bitfield = bitfield;
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

    public byte[] getBitfield() {
        return bitfield;
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

    public static List<Aria2Peer> convertToList(JSONArray array) {
        // TODO
        return new ArrayList<>();
    }
}
