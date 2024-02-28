package com.kyawzinlinn.core_data.repository

import com.kyawzinlinn.core_database.dao.CityDao
import com.kyawzinlinn.core_database.entities.CityEntity
import com.kyawzinlinn.core_network.model.City
import com.kyawzinlinn.core_network.repository.WeatherNetworkDataSource
import com.kyawzinlinn.core_network.util.retrofit.RetrofitNetworkApi
import com.kyawzinlinn.core_network.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val networkDataSource: WeatherNetworkDataSource,
    private val cityDao: CityDao
) : CityRepository {
    override fun getAllCities(): Flow<List<CityEntity>> = cityDao.getAllCities()

    override suspend fun addCity(cityEntity: CityEntity) = cityDao.addCity(cityEntity)

    override suspend fun deleteCity(cityEntity: CityEntity) = cityDao.deleteCity(cityEntity)

    override suspend fun searchCity(query: String): Resource<List<City>>  {
        try {
            val response = networkDataSource.searchCity(query)
            return Resource.Success(response)
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
    }
}