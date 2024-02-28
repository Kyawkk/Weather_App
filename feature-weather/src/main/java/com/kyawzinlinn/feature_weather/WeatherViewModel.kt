package com.kyawzinlinn.feature_weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawzinlinn.core_data.repository.ForecastRepository
import com.kyawzinlinn.core_data.repository.ForecastsByHourRepository
import com.kyawzinlinn.core_database.entities.ForecastByHourEntity
import com.kyawzinlinn.core_database.entities.ForecastEntity
import com.kyawzinlinn.core_network.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val forecastsByHourRepository: ForecastsByHourRepository
) : ViewModel() {

    private val _weatherUiState = MutableStateFlow(WeatherUiState())
    val weatherUiState: StateFlow<WeatherUiState> = _weatherUiState.asStateFlow()

    private val _forecastEntities = MutableStateFlow<List<ForecastEntity>>(emptyList())
    val forecastEntities: StateFlow<List<ForecastEntity>> = _forecastEntities.asStateFlow()

    private val _allForecastsByHour = MutableStateFlow<List<ForecastByHourEntity>>(emptyList())
    val allForecastsByHour: StateFlow<List<ForecastByHourEntity>> = _allForecastsByHour.asStateFlow()

    fun resetWeatherForecastsByLocation() {
        _weatherUiState.update {
            it.copy(allForecasts = emptyFlow())
        }
    }

    fun getWeatherForecastsByLocation(location: String) {
        viewModelScope.launch {
            val forecasts = forecastRepository.getAllForecasts(location)

            forecasts.collect {
                Log.d("TAG", "getWeatherForecastsByLocation: $it")
                when (it) {
                    is Resource.Success -> _forecastEntities.value = it.data as List<ForecastEntity>
                    else -> {}
                }
            }


            _weatherUiState.update {
                it.copy(allForecasts = forecasts)
            }
        }
    }

    fun getAllForecastsByHour(date: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val allForecastsByHour = forecastsByHourRepository.getForecastsByHourByDate(date)

            allForecastsByHour.collect {
                _allForecastsByHour.value = it
            }

            /*_weatherUiState.update {
                it.copy(allForecastsByHour = allForecastsByHour)
            }*/
        }
    }
}

data class WeatherUiState(
    val allForecasts: Flow<Resource<List<ForecastEntity>>> = emptyFlow(),
    val allForecastsByHour: Flow<List<ForecastByHourEntity>> = emptyFlow()
)