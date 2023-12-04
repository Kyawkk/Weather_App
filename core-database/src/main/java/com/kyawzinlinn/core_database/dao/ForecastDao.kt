package com.kyawzinlinn.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kyawzinlinn.core_database.entities.ForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllForecast(forecasts : List<ForecastEntity>)

    @Query("select * from forecasts")
    fun getAllForecasts() : Flow<List<ForecastEntity>>

    @Query("delete from forecasts")
    fun deleteAll()
}