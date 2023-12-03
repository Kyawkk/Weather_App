package com.kyawzinlinn.core_network.repository

import com.kyawzinlinn.core_network.model.City
import com.kyawzinlinn.core_network.model.WeatherForecastResponse
import com.kyawzinlinn.core_network.retrofit.RetrofitNetworkApi
import javax.inject.Inject

class WeatherNetworkDataSource @Inject constructor (
    private val retrofitNetworkApi: RetrofitNetworkApi
) : WeatherRepository {
    override suspend fun getWeatherForecasts(location: String): WeatherForecastResponse = retrofitNetworkApi.getWeatherForecasts(location)
    override suspend fun searchCity(query: String): List<City> = retrofitNetworkApi.searchCity(query)
}