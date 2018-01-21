package com.mlieou.yaara.model;

import com.google.gson.annotations.Expose;
import com.mlieou.yaara.rpc.aria2.constant.Aria2RpcKey;

import java.util.List;

/**
 * Created by mlieou on 1/7/18.
 */

public class TaskStatus {

    public static final String[] REQUEST_DELTA_UPDATE = {
            Aria2RpcKey.GID,
            Aria2RpcKey.TOTAL_LENGTH,
            Aria2RpcKey.COMPLETED_LENGTH,
            Aria2RpcKey.DOWNLOAD_SPEED,
            Aria2RpcKey.UPLOAD_SPEED,
            Aria2RpcKey.STATUS
    };

    public static final String[] REQUEST_FULL_UPDATE = {
            Aria2RpcKey.GID,
            Aria2RpcKey.TOTAL_LENGTH,
            Aria2RpcKey.COMPLETED_LENGTH,
            Aria2RpcKey.DOWNLOAD_SPEED,
            Aria2RpcKey.UPLOAD_SPEED,
            Aria2RpcKey.STATUS,
            Aria2RpcKey.BIT_TORRENT,
            Aria2RpcKey.DIR,
            Aria2RpcKey.FILES
    };

    // task name for display
    @Expose(serialize = false, deserialize = false)
    private String taskName;

    private String gid;
    private String status;
    private long totalLength;
    private long completedLength;
    private long uploadLength;
    private String bitfield;
    private long downloadSpeed;
    private long uploadSpeed;
    private String infoHash;
    private int numSeeders;
    private boolean seeder;
    private int pieceLength;
    private int numPieces;
    private int connections;
    private int errorCode;
    private String errorMessage;
    private List<String> followedBy;
    private String following;
    private String belongsTo;
    private String dir;
    private List<File> files;
    private BitTorrent bittorrent;
    private int verifiedLength;
    private boolean verifyIntegrityPending;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getGid() {
        return gid;
    }

    public String getStatus() {
        return status;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public long getCompletedLength() {
        return completedLength;
    }

    public long getUploadLength() {
        return uploadLength;
    }

    public String getBitfield() {
        return bitfield;
    }

    public long getDownloadSpeed() {
        return downloadSpeed;
    }

    public long getUploadSpeed() {
        return uploadSpeed;
    }

    public String getInfoHash() {
        return infoHash;
    }

    public int getNumSeeders() {
        return numSeeders;
    }

    public boolean isSeeder() {
        return seeder;
    }

    public int getPieceLength() {
        return pieceLength;
    }

    public int getNumPieces() {
        return numPieces;
    }

    public int getConnections() {
        return connections;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<String> getFollowedBy() {
        return followedBy;
    }

    public String getFollowing() {
        return following;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public String getDir() {
        return dir;
    }

    public List<File> getFiles() {
        return files;
    }

    public BitTorrent getBittorrent() {
        return bittorrent;
    }

    public int getVerifiedLength() {
        return verifiedLength;
    }

    public boolean isVerifyIntegrityPending() {
        return verifyIntegrityPending;
    }
}
