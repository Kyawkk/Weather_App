package com.kyawzinlinn.core_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kyawzinlinn.core_database.entities.CityEntity
import com.kyawzinlinn.core_network.model.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCity(city: CityEntity)

    @Query("select * from cities")
    fun getAllCities(): Flow<List<CityEntity>>

    @Delete
    suspend fun deleteCity(city: CityEntity)

}