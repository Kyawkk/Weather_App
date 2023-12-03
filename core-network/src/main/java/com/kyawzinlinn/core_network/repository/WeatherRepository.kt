package com.kyawzinlinn.core_network.repository

import com.kyawzinlinn.core_network.model.City
import com.kyawzinlinn.core_network.model.WeatherForecastResponse

interface WeatherRepository {
    suspend fun getWeatherForecasts(location: String) : WeatherForecastResponse
    suspend fun searchCity(query: String): List<City>
}