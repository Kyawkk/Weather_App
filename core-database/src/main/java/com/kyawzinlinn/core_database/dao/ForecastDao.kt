package com.kyawzinlinn.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kyawzinlinn.core_database.entities.ForecastByHourEntity
import com.kyawzinlinn.core_database.entities.ForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllForecast(forecasts : List<ForecastEntity>)

    @Query("select * from forecasts where name = :location")
    fun getAllForecasts(location: String) : Flow<List<ForecastEntity>>

    @Query("delete from forecasts")
    fun deleteAll()

    @Transaction
    suspend fun insertAllData(
        forecastByHourDao: ForecastByHourDao,
        forecastEntityList: List<ForecastEntity>,
        forecastsByHourEntityList: List<ForecastByHourEntity>
    ) {
        deleteAll()
        forecastByHourDao.deleteAll()
        insertAllForecast(forecastEntityList)
        forecastByHourDao.insertAllForecastsByHour(forecastsByHourEntityList)
    }
}