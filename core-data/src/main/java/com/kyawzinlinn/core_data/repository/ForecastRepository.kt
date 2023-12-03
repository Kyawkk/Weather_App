package com.kyawzinlinn.core_data.repository

import com.kyawzinlinn.core_database.entities.ForecastEntity
import com.kyawzinlinn.core_network.util.Resource
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {
    fun getAllForecasts(location: String) : Flow<Resource<List<ForecastEntity>>>
}