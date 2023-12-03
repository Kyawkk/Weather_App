package com.kyawzinlinn.core_data.repository

import com.kyawzinlinn.core_database.dao.ForecastByHourDao
import com.kyawzinlinn.core_database.dao.ForecastDao
import com.kyawzinlinn.core_database.entities.ForecastEntity
import com.kyawzinlinn.core_database.entities.toForecastByHourEntityList
import com.kyawzinlinn.core_database.entities.toForecastEntityList
import com.kyawzinlinn.core_network.repository.WeatherNetworkDataSource
import com.kyawzinlinn.core_network.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class OfflineFirstForecastRepository(
    private val networkDataSource: WeatherNetworkDataSource,
    private val forecastByHourDao: ForecastByHourDao,
    private val forecastDao: ForecastDao
) : ForecastRepository {
    override fun getAllForecasts(location: String): Flow<Resource<List<ForecastEntity>>> = flow {
        emit(Resource.Loading())
        val cachedForecasts = forecastDao.getAllForecasts()
        cachedForecasts.map {
            Resource.Success(it)
        }

        try {
            val networkForecasts = networkDataSource.getWeatherForecasts(location)
            forecastDao.insertAllForecast(networkForecasts.toForecastEntityList())
            forecastByHourDao.insertAllForecastsByHour(networkForecasts.toForecastByHourEntityList())
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}