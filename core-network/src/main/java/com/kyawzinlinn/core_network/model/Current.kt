package com.kyawzinlinn.core_network.model

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("temp_c") val temperature: String,
    @SerializedName("is_day") val isDay: Int,
    val condition: Condition,
    @SerializedName("wind_mph") val windSpeed: String,
    @SerializedName("wind_degree") val windDegree: String,
    @SerializedName("wind_dir") val windDirection: String,
    @SerializedName("pressure_mb") val pressure: Float,
    @SerializedName("humidity") val humidity: Float,
    @SerializedName("feelslike_c") val realFeel: Float,
    val uv: String
)
