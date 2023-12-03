package com.kyawzinlinn.core_database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kyawzinlinn.core_network.model.Location
import com.kyawzinlinn.core_network.model.WeatherForecastResponse


@Entity("forecasts")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("country") val country: String,
    @ColumnInfo("lat") val lat: String,
    @ColumnInfo("lon") val lon: String,
    @ColumnInfo("localTime") val localTime: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("region") val region: String,
    @ColumnInfo("humidity") val humidity: String,
    @ColumnInfo("temperature") val temperature: String,
    @ColumnInfo("condition") val condition: String,
    @ColumnInfo("chanceOfRain") val chanceOfRain: String,
    @ColumnInfo("chanceOfSnow") val chanceOfSnow: String,
    @ColumnInfo("maxTemperature") val maxTemperature: String,
    @ColumnInfo("minTemperature") val minTemperature: String,
    @ColumnInfo("windSpeed") val windSpeed: String,
)

fun WeatherForecastResponse.toForecastEntityList(): List<ForecastEntity>{
    return this.forecast.forecastDay.map {
        ForecastEntity(
            country = this.location.country,
            lat = this.location.lat,
            lon = this.location.lon,
            localTime = this.location.localtime,
            name = this.location.name,
            region = this.location.region,
            humidity = it.day.humidity,
            temperature = it.day.temperature,
            condition = it.day.condition.text,
            chanceOfRain = it.day.chanceOfRain,
            chanceOfSnow = it.day.chanceOfSnow,
            maxTemperature = it.day.maxTemperature,
            minTemperature = it.day.minTemperature,
            windSpeed = it.day.windSpeed,
        )
    }
}