package com.mlieou.yaara.model

/**
 * Created by mlieou on 5/19/18.
 */

class File(val index : Int,
           val path : String,
           val length : Long,
           val completedLength : Long,
           val selected : Boolean,
           val uris : List<Uri>)

class Uri(val uri: String, val status : String)