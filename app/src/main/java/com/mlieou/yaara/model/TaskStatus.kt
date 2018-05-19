package com.mlieou.yaara.model

import com.google.gson.annotations.Expose
import com.mlieou.yaara.rpc.aria2.constant.Aria2RpcKey

/**
 * Created by mlieou on 5/19/18.
 */
class TaskStatus {

    // task name for display
    @Expose(serialize = false, deserialize = false)
    var taskName: String? = null

    val gid: String? = null
    val status: String? = null
    val totalLength: Long = 0
    val completedLength: Long = 0
    val uploadLength: Long = 0
    val bitfield: String? = null
    val downloadSpeed: Long = 0
    val uploadSpeed: Long = 0
    val infoHash: String? = null
    val numSeeders: Int = 0
    val isSeeder: Boolean = false
    val pieceLength: Int = 0
    val numPieces: Int = 0
    val connections: Int = 0
    val errorCode: Int = 0
    val errorMessage: String? = null
    val followedBy: List<String>? = null
    val following: String? = null
    val belongsTo: String? = null
    val dir: String? = null
    val files: List<File>? = null
    val bittorrent: BitTorrent? = null
    val verifiedLength: Int = 0
    val isVerifyIntegrityPending: Boolean = false

    companion object {

        val REQUEST_DELTA_UPDATE = arrayOf(Aria2RpcKey.GID, Aria2RpcKey.TOTAL_LENGTH, Aria2RpcKey.COMPLETED_LENGTH, Aria2RpcKey.DOWNLOAD_SPEED, Aria2RpcKey.UPLOAD_SPEED, Aria2RpcKey.STATUS)

        val REQUEST_FULL_UPDATE = arrayOf(Aria2RpcKey.GID, Aria2RpcKey.TOTAL_LENGTH, Aria2RpcKey.COMPLETED_LENGTH, Aria2RpcKey.DOWNLOAD_SPEED, Aria2RpcKey.UPLOAD_SPEED, Aria2RpcKey.STATUS, Aria2RpcKey.BIT_TORRENT, Aria2RpcKey.DIR, Aria2RpcKey.FILES)
    }
}
