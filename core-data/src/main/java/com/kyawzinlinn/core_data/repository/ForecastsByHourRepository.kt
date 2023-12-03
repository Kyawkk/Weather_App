package com.kyawzinlinn.core_data.repository

import com.kyawzinlinn.core_database.entities.ForecastByHourEntity
import kotlinx.coroutines.flow.Flow

interface ForecastsByHourRepository {
    fun getAllForecastsByHour() : Flow<List<ForecastByHourEntity>>
    fun getForecastByHourByDate(date: String) : Flow<ForecastByHourEntity>
}