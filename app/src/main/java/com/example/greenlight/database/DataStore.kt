package com.example.greenlight.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "articles"
)
data class DataStore(

    val sales_area: List<AreaResponse>?,
    val sales_country: List<AreaResponse>?,
    val sales_region: List<AreaResponse>?,
    val sales_zone: List<AreaResponse>?
) {

}