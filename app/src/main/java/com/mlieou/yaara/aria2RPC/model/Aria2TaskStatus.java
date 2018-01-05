package com.mlieou.yaara.aria2RPC.model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengdi on 1/4/18.
 */

public class Aria2TaskStatus {

    private byte[] bitfield;
    private long completedLength;
    private int connections;
    private String dir;
    private long downloadSpeed;
    private int errorCode;
    private String errotMessage;
    private List<Aria2File> files;
    private String gid;
    private int numPieces;
    private long pieceLength;
    private String status;
    private long totalLength;
    private long uploadLength;
    private long uploadSpeed;

    // optional
    private List<String> followedBy;
    private String following;
    private String belongsTo;
    private long verifiedLength;
    private boolean verifyIntegrityPending;

    // bitTorrent only
    private String infoHash;
    private int numSeeders;
    private boolean seeder;
    private Aria2BitTorrent bitTorrent;

    public long getCompletedLength() {
        return completedLength;
    }

    public int getConnections() {
        return connections;
    }

    public String getDir() {
        return dir;
    }

    public long getDownloadSpeed() {
        return downloadSpeed;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrotMessage() {
        return errotMessage;
    }

    public List<Aria2File> getFiles() {
        return files;
    }

    public String getGid() {
        return gid;
    }

    public int getNumPieces() {
        return numPieces;
    }

    public long getPieceLength() {
        return pieceLength;
    }

    public String getStatus() {
        return status;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public long getUploadLength() {
        return uploadLength;
    }

    public long getUploadSpeed() {
        return uploadSpeed;
    }

    public static Aria2TaskStatus deserialize(String jsonStr) {
        // TODO
        return new Aria2TaskStatus();
    }

    public static List<Aria2TaskStatus> deserializeList(JSONArray array) {
        // TODO
        return new ArrayList<>();
    }
}
