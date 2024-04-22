package com.example.bottomnavigation.model

import java.io.Serializable
import java.util.Date

data class Events(
    var id:Int,
    var title:String,
    var description:String,
    var date: Date,
    var image: String,
    var flag:Boolean = true
):Serializable
