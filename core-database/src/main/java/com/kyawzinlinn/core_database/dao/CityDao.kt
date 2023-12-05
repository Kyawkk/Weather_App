package com.kyawzinlinn.core_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kyawzinlinn.core_database.entities.CityEntity
import com.kyawzinlinn.core_network.model.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCity(city: CityEntity)

    @Transaction
    suspend fun addCityIfNotExisted(city: CityEntity) {
        val allCities = getAllCities()

        val exists = allCities.first().any { it.name == city.name }

        if (!exists) addCity(city)
    }

    @Query("select * from cities")
    fun getAllCities(): Flow<List<CityEntity>>

    @Delete
    suspend fun deleteCity(city: CityEntity)

}