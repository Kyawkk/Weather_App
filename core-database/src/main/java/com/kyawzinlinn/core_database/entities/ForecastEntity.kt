package com.kyawzinlinn.core_database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kyawzinlinn.core_network.model.WeatherForecastResponse


@Entity("forecasts")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("country") val country: String = "",
    @ColumnInfo("lat") val lat: String = "",
    @ColumnInfo("lon") val lon: String = "",
    @ColumnInfo("date") val date: String = "",
    @ColumnInfo("name") val name: String = "",
    @ColumnInfo("icon") val icon: String = "",
    @ColumnInfo("uv") val uv: String = "",
    @ColumnInfo("sunset") val sunset: String = "",
    @ColumnInfo("sunrise") val sunrise: String = "",
    @ColumnInfo("isDay") val isDay: Boolean = false,
    @ColumnInfo("pressure") val pressure: String = "",
    @ColumnInfo("region") val region: String = "",
    @ColumnInfo("realFeel") val realFeel: String = "",
    @ColumnInfo("humidity") val humidity: String = "",
    @ColumnInfo("temperature") val temperature: String = "",
    @ColumnInfo("condition") val condition: String = "",
    @ColumnInfo("chanceOfRain") val chanceOfRain: String = "",
    @ColumnInfo("chanceOfSnow") val chanceOfSnow: String = "",
    @ColumnInfo("maxTemperature") val maxTemperature: String = "",
    @ColumnInfo("minTemperature") val minTemperature: String = "",
    @ColumnInfo("windSpeed") val windSpeed: String = "",
    @ColumnInfo("windDirection") val windDirection: String = "",
    @ColumnInfo("windDegree") val windDegree: String = "",
)


fun WeatherForecastResponse.toForecastEntityList(): List<ForecastEntity>{

    return this.forecast.forecastDay.map {
        ForecastEntity(
            country = this.location.country,
            lat = this.location.lat,
            lon = this.location.lon,
            date = it.date,
            name = this.location.name,
            region = this.location.region,
            humidity = this.current.humidity.toString(),
            sunrise = it.astro.sunrise,
            isDay = this.current.isDay == 1,
            sunset = it.astro.sunset,
            icon = it.day.condition.icon,
            uv = this.current.uv,
            pressure = this.current.pressure.toString(),
            realFeel = this.current.realFeel.toString(),
            temperature = this.current.temperature,
            condition = this.current.condition.text,
            chanceOfRain = it.day.chanceOfRain,
            chanceOfSnow = it.day.chanceOfSnow,
            maxTemperature = it.day.maxTemperature,
            minTemperature = it.day.minTemperature,
            windSpeed = this.current.windSpeed,
            windDegree = this.current.windDegree,
            windDirection = this.current.windDirection
        )
    }
}