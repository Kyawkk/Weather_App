package com.kyawzinlinn.feature_location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawzinlinn.core_data.repository.CityRepository
import com.kyawzinlinn.core_database.entities.CityEntity
import com.kyawzinlinn.core_network.model.City
import com.kyawzinlinn.core_network.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val cityRepository: CityRepository
) : ViewModel() {
    private val _cityUiState = MutableStateFlow(CityUiState())
    val cityUiState: StateFlow<CityUiState> = _cityUiState.asStateFlow()

    init {
        getSavedCities()
    }

    fun resetSearchResults(){
        _cityUiState.update {
            it.copy(searchResults = Resource.Loading)
        }
    }

    fun deleteCity(cityEntity: CityEntity) {
        viewModelScope.launch {
            cityRepository.deleteCity(cityEntity)
        }
    }

    fun searchCity(query: String) {
        viewModelScope.launch {
            _cityUiState.update {
                it.copy(searchResults = cityRepository.searchCity(query))
            }
        }
    }

    fun addCity (cityEntity: CityEntity) {
        viewModelScope.launch {
            cityRepository.addCity(cityEntity)
        }
    }

    fun getSavedCities(){
        viewModelScope.launch {
            _cityUiState.update {
                it.copy(savedCities = cityRepository.getAllCities())
            }
        }
    }

}

data class CityUiState(
    val searchResults : Resource<List<City>> = Resource.Loading,
    val savedCities: Flow<List<CityEntity>> = emptyFlow()
)