package com.example.greenlight.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class DataStore(
    @PrimaryKey(autoGenerate = true)
    val sales_area: List<AreaResponse>,
    val sales_country: List<AreaResponse>,
    val sales_region: List<AreaResponse>,
    val sales_zone: List<AreaResponse>
) {
}