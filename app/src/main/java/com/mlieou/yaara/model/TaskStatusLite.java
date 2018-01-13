package com.mlieou.yaara.model;

import com.mlieou.yaara.rpc.aria2.constant.Aria2RpcKey;

/**
 * Created by mengdi on 1/7/18.
 */

public class TaskStatusLite {

    public static final String[] REQUEST_KEY = {
            Aria2RpcKey.GID,
            Aria2RpcKey.TOTAL_LENGTH,
            Aria2RpcKey.COMPLETED_LENGTH,
            Aria2RpcKey.DOWNLOAD_SPEED,
            Aria2RpcKey.UPLOAD_SPEED
    };

    private String gid;
    private long downloadSpeed;
    private long uploadSpeed;
    private long completedLength;
    private long totalLength;
}
