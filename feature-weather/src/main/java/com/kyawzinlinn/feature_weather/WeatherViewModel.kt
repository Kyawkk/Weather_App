package com.kyawzinlinn.feature_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawzinlinn.core_data.repository.ForecastRepository
import com.kyawzinlinn.core_data.repository.ForecastsByHourRepository
import com.kyawzinlinn.core_database.entities.ForecastByHourEntity
import com.kyawzinlinn.core_database.entities.ForecastEntity
import com.kyawzinlinn.core_network.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor (
    private val forecastRepository: ForecastRepository,
    private val forecastsByHourRepository: ForecastsByHourRepository
) : ViewModel() {

    private val _weatherUiState = MutableStateFlow(WeatherUiState())
    val weatherUiState : StateFlow<WeatherUiState> = _weatherUiState.asStateFlow()

    fun resetWeatherForecastsByLocation() {
        _weatherUiState.update {
            it.copy(allForecasts = emptyFlow())
        }
    }

    fun getWeatherForecastsByLocation(location: String) {
        viewModelScope.launch {
            val forecasts = forecastRepository.getAllForecasts(location)
            _weatherUiState.update {
                it.copy(allForecasts = forecasts)
            }
        }
    }

    fun getAllForecastsByHour(date: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val allForecastsByHour = forecastsByHourRepository.getForecastsByHourByDate(date)
            _weatherUiState.update {
                it.copy(allForecastsByHour = allForecastsByHour)
            }
        }
    }
}

data class WeatherUiState(
    val allForecasts: Flow<Resource<List<ForecastEntity>>> = emptyFlow(),
    val allForecastsByHour: Flow<List<ForecastByHourEntity>> = emptyFlow()
)