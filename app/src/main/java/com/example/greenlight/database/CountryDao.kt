package com.example.greenlight.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.greenlight.models.AreaResponse

@Dao
interface CountryDao {

    @Query("SELECT * FROM characters")
    fun getAllCharacters() : LiveData<List<DataStore>>

    @Query("SELECT * FROM characters WHERE sales_country = :id")

    fun getCharacter(id: Int): LiveData<DataStore>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<DataStore>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: DataStore)
}