package com.kyawzinlinn.weatherapp

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawzinlinn.core_ui.DefaultConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedUiViewModel @Inject constructor() : ViewModel() {

    companion object{
        const val TAG = "SharedUiViewModel"
    }

    private val _uiState = MutableStateFlow(SharedUiState())
    val uiState: StateFlow<SharedUiState> = _uiState

    fun updateErrorMessage(message: String) {
        _uiState.update { it.copy(
            errorMessage = message,
            showAddLocationIcon = false,
            description = ""
        ) }
    }

    fun updateScreenStatus(isWeatherScreen: Boolean) {
        _uiState.update {
            it.copy(
                isWeatherScreen = isWeatherScreen
            )
        }
    }

    fun updateTitle(title: String) {
        Log.d(TAG, "updateTitle: $title")
        _uiState.update { it.copy(title = title) }
    }

    fun updateDayStatus(isDay: Boolean) {
        _uiState.update { it.copy(isDay = isDay) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

}

data class SharedUiState(
    val title: String = DefaultConfig.TITLE,
    val description: String = DefaultConfig.DESCRIPTION,
    val isDay: Boolean = true,
    val isWeatherScreen: Boolean = false,
    val showAddLocationIcon: Boolean = DefaultConfig.SHOW_ADD_LOCATION_ICON,
    val appBarColor: Color = DefaultConfig.DEFAULT_APP_BAR_COLOR,
    val isTransparent: Boolean = DefaultConfig.IS_TRANSPARENT,
    val showThemeIcon : Boolean = DefaultConfig.SHOW_THEME_ICON,
    val errorMessage: String = ""
)