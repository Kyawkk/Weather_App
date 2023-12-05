package com.kyawzinlinn.core_data.repository

import android.util.Log
import com.kyawzinlinn.core_database.dao.ForecastByHourDao
import com.kyawzinlinn.core_database.dao.ForecastDao
import com.kyawzinlinn.core_database.entities.ForecastEntity
import com.kyawzinlinn.core_database.entities.toForecastByHourEntityList
import com.kyawzinlinn.core_database.entities.toForecastEntityList
import com.kyawzinlinn.core_network.repository.WeatherNetworkDataSource
import com.kyawzinlinn.core_network.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class OfflineFirstForecastRepository @Inject constructor(
    private val networkDataSource: WeatherNetworkDataSource,
    private val forecastByHourDao: ForecastByHourDao,
    private val forecastDao: ForecastDao
) : ForecastRepository {

    override suspend fun deleteAll() = forecastDao.deleteAll()
    override suspend fun getAllForecasts(location: String): Flow<Resource<List<ForecastEntity>>> = flow {
        val cachedForecasts = forecastDao.getAllForecasts(location).first()

        if (cachedForecasts.isEmpty() || cachedForecasts.size == 0) emit(Resource.Loading)
        else emit(Resource.Success(cachedForecasts))

        try {
            val networkForecasts = networkDataSource.getWeatherForecasts(location)
            forecastDao.insertAllData(forecastByHourDao,networkForecasts.toForecastEntityList(), networkForecasts.toForecastByHourEntityList())
            emit(Resource.Success(networkForecasts.toForecastEntityList()))

        } catch (e: Exception) {
            e.printStackTrace()
            if (cachedForecasts.isEmpty() ) emit(Resource.Error(e.message.toString()))
        }
    }
}