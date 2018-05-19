package com.mlieou.yaara.model

/**
 * Created by mlieou on 5/19/18.
 */

class BitTorrent(val announceList : List<List<String>>,
                 val comment : String,
                 val creationDate : Long,
                 val mode : String,
                 val bitTorrentInfo: BitTorrentInfo)

class BitTorrentInfo(val name: String)