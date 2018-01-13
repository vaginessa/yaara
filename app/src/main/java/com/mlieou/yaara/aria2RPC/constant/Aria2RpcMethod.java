package com.mlieou.yaara.aria2RPC.constant;

/**
 * Created by mengdi on 12/28/17.
 */

/* All the available RPC methods (1.33.1) */
public interface Aria2RpcMethod {
    String addUri = "aria2.addUri";
    String addTorrent = "aria2.addTorrent";
    String addMetalink = "aria2.addMetalink";
    String remove = "aria2.remove";
    String forceRemove = "aria2.forceRemove";
    String pause = "aria2.pause";
    String pauseAll = "aria2.pauseAll";
    String forcePause = "aria2.forcePause";
    String forcePauseAll = "aria2.forcePauseAll";
    String unpause = "aria2.unpause";
    String unpauseAll = "aria2.unpauseAll";
    String tellStatus = "aria2.tellStatus";
    String getUris = "aria2.getUris";
    String getFiles = "aria2.getFiles";
    String getPeers = "aria2.getPeers";
    String getServers = "aria2.getServers";
    String tellActive = "aria2.tellActive";
    String tellWaiting = "aria2.tellWaiting";
    String tellStopped = "aria2.tellStopped";
    String changePosition = "aria2.changePosition";
    String changeUri = "aria2.changeUri";
    String getOption = "aria2.getOption";
    String changeOption = "aria2.changeOption";
    String getGlobalOption = "aria2.getGlobalOption";
    String changeGlobalOption = "aria2.changeGlobalOption";
    String getGlobalStat = "aria2.getGlobalStat";
    String purgeDownloadResult = "aria2.purgeDownloadResult";
    String removeDownloadResult = "aria2.removeDownloadResult";
    String getVersion = "aria2.getVersion";
    String getSessionInfo = "aria2.getSessionInfo";
    String shutdown = "aria2.shutdown";
    String forceShutdown = "aria2.forceShutdown";
    String saveSession = "aria2.saveSession";

    String multicall = "system.multicall";
    String listMethods = "system.listMethods";
    String listNotifications = "system.listNotifications";
}
