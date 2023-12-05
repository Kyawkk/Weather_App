package com.kyawzinlinn.core_database.entities

data class WeatherForecastData(
    val current: ForecastByHourEntity,
    val forecastsByHour: List<ForecastByHourEntity>
)
