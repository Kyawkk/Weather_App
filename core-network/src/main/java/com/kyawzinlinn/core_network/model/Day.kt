package com.kyawzinlinn.core_network.model

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("avghumidity") val humidity: String,
    @SerializedName("avgtemp_c") val temperature: String,
    val condition: Condition,
    @SerializedName("daily_chance_of_rain") val chanceOfRain: String,
    @SerializedName("daily_chance_of_snow") val chanceOfSnow: String,
    @SerializedName("maxtemp_c") val maxTemperature: String,
    @SerializedName("maxwind_mph") val windSpeed: String,
    @SerializedName("mintemp_c") val minTemperature: String,
    val uv: String
)