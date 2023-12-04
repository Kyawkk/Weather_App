package com.kyawzinlinn.core_data.repository

import com.kyawzinlinn.core_database.entities.ForecastByHourEntity
import kotlinx.coroutines.flow.Flow

interface ForecastsByHourRepository {
    fun getForecastByHourById(id: String) : Flow<ForecastByHourEntity>
    fun getForecastsByHourByDate(date: String) : Flow<List<ForecastByHourEntity>>
}