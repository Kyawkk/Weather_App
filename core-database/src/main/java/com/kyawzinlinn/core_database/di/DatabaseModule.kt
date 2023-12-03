package com.kyawzinlinn.core_database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kyawzinlinn.core_database.WeatherDatabase
import com.kyawzinlinn.core_database.dao.ForecastByHourDao
import com.kyawzinlinn.core_database.dao.ForecastDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesWeatherDatabase(@ApplicationContext context: Context) : WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesForecastDao(weatherDatabase: WeatherDatabase): ForecastDao = weatherDatabase.forecastDao()

    @Provides
    fun providesForecastsByHourDao(weatherDatabase: WeatherDatabase): ForecastByHourDao = weatherDatabase.forecastsByHourDao()
}