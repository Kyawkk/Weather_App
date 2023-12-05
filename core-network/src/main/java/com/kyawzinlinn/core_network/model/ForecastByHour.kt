package com.kyawzinlinn.core_network.model

import com.google.gson.annotations.SerializedName

data class ForecastByHour(
    val condition: Condition,
    @SerializedName("feelslike_c") val feelsLikeInCelsius: String,
    val humidity: String,
    @SerializedName("temp_c")val tempInCelsius: Float,
    @SerializedName("is_day")val isDay: Int,
    @SerializedName("pressure_mb")val pressure: Float,
    val time: String,
    val uv: String,
    @SerializedName("wind_degree") val windDegree: String,
    @SerializedName("wind_dir") val windDirection: String,
    @SerializedName("wind_mph") val windSpeed: String
)