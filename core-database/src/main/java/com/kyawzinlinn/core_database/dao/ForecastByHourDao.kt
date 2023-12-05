package com.kyawzinlinn.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kyawzinlinn.core_database.entities.ForecastByHourEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastByHourDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllForecastsByHour(forecastsByHours: List<ForecastByHourEntity>)

    @Query("select * from forecastsByHour where date =:date")
    fun getForecastByHourByDate(date: String) : Flow<List<ForecastByHourEntity>>

    @Query("select * from forecastsByHour where id =:id")
    fun getAllForecastsByHourById(id: String) : Flow<ForecastByHourEntity>

    @Query("select * from forecastsByHour")
    fun getAllForecastsByHour() :Flow<List<ForecastByHourEntity>>

    @Query("delete from forecastsByHour")
    fun deleteAll()

}