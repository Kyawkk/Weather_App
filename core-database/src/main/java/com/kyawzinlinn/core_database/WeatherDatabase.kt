package com.kyawzinlinn.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kyawzinlinn.core_database.dao.CityDao
import com.kyawzinlinn.core_database.dao.ForecastByHourDao
import com.kyawzinlinn.core_database.dao.ForecastDao
import com.kyawzinlinn.core_database.entities.CityEntity
import com.kyawzinlinn.core_database.entities.ForecastByHourEntity
import com.kyawzinlinn.core_database.entities.ForecastEntity

@Database(entities = arrayOf(ForecastEntity::class,ForecastByHourEntity::class,CityEntity::class), version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun forecastDao() : ForecastDao
    abstract fun forecastsByHourDao() : ForecastByHourDao
    abstract fun cityDao(): CityDao
}