package com.kyawzinlinn.core_network.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday") val forecastDay : List<ForecastDay>
)
