package com.kyawzinlinn.core_network.model

data class WeatherForecastResponse(
    val location: Location,
    val current: Current,
    val forecast : Forecast
)
