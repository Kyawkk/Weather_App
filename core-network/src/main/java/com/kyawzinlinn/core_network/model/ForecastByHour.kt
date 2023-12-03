package com.kyawzinlinn.core_network.model

import com.google.gson.annotations.SerializedName

data class ForecastByHour(
    val cloud: Int,
    val condition: Condition,
    @SerializedName("feelslike_c") val feelsLikeInCelsius: Double,
    val humidity: Int,
    val temp_c: Double,
    val time: String,
    val uv: Double,
    @SerializedName("wind_degree") val windDegree: Int,
    @SerializedName("wind_dir") val windDirection: String,
    @SerializedName("wind_mph") val windMilePerHour: Double
)