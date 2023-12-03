package com.kyawzinlinn.core_network.retrofit

import retrofit2.http.GET

interface RetrofitNetworkApi {
    @GET
    suspend fun getWeatherForecasts(

    )
}