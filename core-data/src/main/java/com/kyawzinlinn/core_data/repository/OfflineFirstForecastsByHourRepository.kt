package com.kyawzinlinn.core_data.repository

import com.kyawzinlinn.core_database.dao.ForecastByHourDao
import com.kyawzinlinn.core_database.entities.ForecastByHourEntity
import kotlinx.coroutines.flow.Flow

class OfflineFirstForecastsByHourRepository (
    private val forecastByHourDao: ForecastByHourDao
) : ForecastsByHourRepository {
    override fun getForecastByHourById(id: String): Flow<ForecastByHourEntity> = forecastByHourDao.getAllForecastsByHourById(id)

    override suspend fun deleteAll() = forecastByHourDao.deleteAll()
    override fun getForecastsByHourByDate(date: String): Flow<List<ForecastByHourEntity>> = forecastByHourDao.getForecastByHourByDate(date)
}