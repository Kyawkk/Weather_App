package com.kyawzinlinn.core_network.util.retrofit

import com.kyawzinlinn.core_network.model.City
import com.kyawzinlinn.core_network.model.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitNetworkApi {
    @GET ("forecast.json")
    suspend fun getWeatherForecasts(
        @Query("q") location: String
    ): WeatherForecastResponse

    @GET("search.json")
    suspend fun searchCity(
        @Query("q") query: String
    ) : List<City>
}