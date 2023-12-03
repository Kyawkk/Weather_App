package com.kyawzinlinn.core_data.repository

import com.kyawzinlinn.core_database.dao.ForecastByHourDao
import com.kyawzinlinn.core_database.entities.ForecastByHourEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OfflineFirstForecastsByHourRepository (
    private val forecastByHourDao: ForecastByHourDao
) : ForecastsByHourRepository {
    override fun getAllForecastsByHour(): Flow<List<ForecastByHourEntity>> = forecastByHourDao.getAllForecastsByHour()

    override fun getForecastByHourByDate(date: String): Flow<ForecastByHourEntity> = forecastByHourDao.getForecastByHourByDate(date)
}