package com.example.greenlight.models

import androidx.room.Entity
import androidx.room.PrimaryKey


//@Entity(tableName = "characters")
data class AreaResponse(
//    @PrimaryKey(autoGenerate = true)
    val ResponseData: ResponseData,
    val ResponseStatus: Int,
    val Success: Boolean
)