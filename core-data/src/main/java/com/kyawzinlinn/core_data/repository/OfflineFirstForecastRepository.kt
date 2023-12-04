package com.kyawzinlinn.core_data.repository

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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfflineFirstForecastRepository(
    private val networkDataSource: WeatherNetworkDataSource,
    private val forecastByHourDao: ForecastByHourDao,
    private val forecastDao: ForecastDao
) : ForecastRepository {

    override suspend fun getAllForecasts(location: String): Flow<Resource<List<ForecastEntity>>> = flow {
        //emit(Resource.Loading)
        val cachedForecasts = forecastDao.getAllForecasts().first()

        if (cachedForecasts.isEmpty() || cachedForecasts.size == 0) emit(Resource.Loading)
        else emit(Resource.Success(cachedForecasts))

        try {
            val networkForecasts = networkDataSource.getWeatherForecasts(location)

            CoroutineScope(Dispatchers.IO).launch {
                if (cachedForecasts.size != 0) {
                    forecastDao.deleteAll()
                    forecastByHourDao.deleteAll()
                }
                forecastDao.insertAllForecast(networkForecasts.toForecastEntityList())
                forecastByHourDao.insertAllForecastsByHour(networkForecasts.toForecastByHourEntityList())
            }

            emit(Resource.Success(networkForecasts.toForecastEntityList()))
        } catch (e: Exception) {
            e.printStackTrace()
            if (cachedForecasts.isEmpty() ) emit(Resource.Error(e.message.toString()))
        }
    }
}