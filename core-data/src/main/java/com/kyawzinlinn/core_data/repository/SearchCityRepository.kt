package com.kyawzinlinn.core_data.repository

import com.kyawzinlinn.core_network.model.City

interface SearchCityRepository {
    fun searchCity(query: String) : List<City>
}