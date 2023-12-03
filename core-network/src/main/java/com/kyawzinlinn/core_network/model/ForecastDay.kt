package com.kyawzinlinn.core_network.model

import com.google.gson.annotations.SerializedName

data class ForecastDay(
    val date: String,
    val astro: Astro,
    val hour : List<ForecastByHour>
)