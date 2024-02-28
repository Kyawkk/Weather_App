package com.kyawzinlinn.core_database.entities

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kyawzinlinn.core_database.util.convertDate
import com.kyawzinlinn.core_network.model.ForecastByHour
import com.kyawzinlinn.core_network.model.WeatherForecastResponse

@Stable
@Entity("forecastsByHour")
data class ForecastByHourEntity(
    @PrimaryKey(autoGenerate = true) val id: Int= 0,
    @ColumnInfo("condition")val condition: String="",
    @ColumnInfo("realFeelTemp")val realFeelTemp: String="",
    @ColumnInfo("humidity")val humidity: String="",
    @ColumnInfo("temperature")val temperature: Float = 0.0f,
    @ColumnInfo("date")val date: String="",
    @ColumnInfo("time")val time: String="",
    @ColumnInfo("isDay")val isDay: Int=0,
    @ColumnInfo("icon")val icon: String="",
    @ColumnInfo("pressure")val pressure: String="",
    @ColumnInfo("uv")val uv: String="",
    @ColumnInfo("windDegree")val windDegree: String="",
    @ColumnInfo("windDirection")val windDirection: String="",
    @ColumnInfo("windSpeed")val windSpeed: String="",
)

fun WeatherForecastResponse.toForecastByHourEntityList(): List<ForecastByHourEntity> {
    var convertedList = mutableListOf<ForecastByHourEntity>()
    this.forecast.forecastDay.forEach {
        it.hour.forEach { forecastByHour ->
            convertedList.add(
                ForecastByHourEntity(
                    condition = forecastByHour.condition.text,
                    realFeelTemp = forecastByHour.feelsLikeInCelsius,
                    humidity = forecastByHour.humidity,
                    temperature = forecastByHour.tempInCelsius,
                    date = it.date,
                    isDay = forecastByHour.isDay,
                    time = forecastByHour.time,
                    icon = forecastByHour.condition.icon,
                    uv = forecastByHour.uv,
                    pressure = forecastByHour.pressure.toInt().toString(),
                    windDegree = forecastByHour.windDegree,
                    windDirection = forecastByHour.windDirection,
                    windSpeed = forecastByHour.windSpeed
                )
            )
        }
    }
    return convertedList.toList()
}
