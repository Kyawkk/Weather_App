package com.kyawzinlinn.core_data.repository

import com.kyawzinlinn.core_database.entities.CityEntity
import com.kyawzinlinn.core_network.model.City
import com.kyawzinlinn.core_network.util.Resource
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    suspend fun searchCity(query: String) : Resource<List<City>>
    suspend fun addCity(cityEntity: CityEntity)
    fun getAllCities(): Flow<List<CityEntity>>
    suspend fun deleteCity(cityEntity: CityEntity)
}