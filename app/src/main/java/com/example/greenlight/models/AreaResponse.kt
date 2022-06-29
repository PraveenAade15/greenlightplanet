package com.example.greenlight.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class AreaResponse(

    val ResponseData: ResponseData,
    val ResponseStatus: Int,
    val Success: Boolean
)