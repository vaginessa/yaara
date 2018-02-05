package com.mlieou.yaara.rpc.aria2.constant;

/**
 * Created by mlieou on 2/5/18.
 */

public interface Aria2RpcExitStatus {

    String CODE_0 = "all downloads were successful";
    String CODE_1 = "an unknown error occurred";
    String CODE_2 = "time out occurred";
    String CODE_3 = "a resource was not found";
    String CODE_4 = "aria2 saw the specified number of “resource not found” error";
    String CODE_5 = "a download aborted because download speed was too slow";
    String CODE_6 = "network problem occurred";
    String CODE_7 = "there were unfinished downloads";
    String CODE_8 = "remote server did not support resume";
    String CODE_9 = "there was not enough disk space available";
    String CODE_10 = "piece length was different from one in .aria2 control file";
    String CODE_11 = "aria2 was downloading same file at that moment";
    String CODE_12 = "aria2 was downloading same info hash torrent at that moment";
    String CODE_13 = "file already existed";
    String CODE_14 = "renaming file failed";
    String CODE_15 = "aria2 could not open existing file";
    String CODE_16 = "aria2 could not create new file or truncate existing file";
    String CODE_17 = "file I/O error occurred";
    String CODE_18 = "aria2 could not create directory";
    String CODE_19 = "name resolution failed";
    String CODE_20 = "aria2 could not parse Metalink document";
    String CODE_21 = "FTP command failed";
    String CODE_22 = "HTTP response header was bad or unexpected";
    String CODE_23 = "too many redirects occurred";
    String CODE_24 = "HTTP authorization failed";
    String CODE_25 = "aria2 could not parse bencoded file";
    String CODE_26 = ".torrent file was corrupted or missing information that aria2 needed";
    String CODE_27 = "Magnet URI was bad";
    String CODE_28 = "bad/unrecognized option was given or unexpected option argument was given";
    String CODE_29 = "the remote server was unable to handle the request due to a temporary overloading or maintenance";
    String CODE_30 = "aria2 could not parse JSON-RPC request";
    String CODE_31 = "";
    String CODE_32 = "checksum validation failed";

    String[] EXIT_STATUS = {
            CODE_0, CODE_1, CODE_2, CODE_3, CODE_4,
            CODE_5, CODE_6, CODE_7, CODE_8,
            CODE_9, CODE_10, CODE_11, CODE_12,
            CODE_13, CODE_14, CODE_15, CODE_16,
            CODE_17, CODE_18, CODE_19, CODE_20,
            CODE_21, CODE_22, CODE_23, CODE_24,
            CODE_25, CODE_26, CODE_27, CODE_28,
            CODE_29, CODE_30, CODE_31, CODE_32};
}
