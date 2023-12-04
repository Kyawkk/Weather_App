package com.kyawzinlinn.core_data.di

import com.kyawzinlinn.core_data.repository.CityRepository
import com.kyawzinlinn.core_data.repository.CityRepositoryImpl
import com.kyawzinlinn.core_data.repository.ForecastRepository
import com.kyawzinlinn.core_data.repository.ForecastsByHourRepository
import com.kyawzinlinn.core_data.repository.OfflineFirstForecastRepository
import com.kyawzinlinn.core_data.repository.OfflineFirstForecastsByHourRepository
import com.kyawzinlinn.core_database.dao.CityDao
import com.kyawzinlinn.core_database.dao.ForecastByHourDao
import com.kyawzinlinn.core_database.dao.ForecastDao
import com.kyawzinlinn.core_network.repository.WeatherNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesOfflineFirstForecastRepository(
        networkDataSource: WeatherNetworkDataSource,
        forecastDao: ForecastDao,
        forecastByHourDao: ForecastByHourDao
    ): ForecastRepository = OfflineFirstForecastRepository(networkDataSource, forecastByHourDao, forecastDao)

    @Provides
    fun providesOfflineFirstForecastsByHourRepository(
        forecastByHourDao: ForecastByHourDao
    ) : ForecastsByHourRepository = OfflineFirstForecastsByHourRepository(forecastByHourDao)

    @Provides
    fun providesCityRepository(
        cityDao: CityDao,
        networkDataSource: WeatherNetworkDataSource
    ) : CityRepository = CityRepositoryImpl(cityDao = cityDao, networkDataSource = networkDataSource)
}