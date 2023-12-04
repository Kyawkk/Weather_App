package com.kyawzinlinn.weatherapp

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
    private val _uiState = MutableStateFlow(SharedUiState())
    val uiState: StateFlow<SharedUiState> = _uiState

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun updateAddLocationIconVisibilityStatus(showAddLocationIcon: Boolean) {
        _uiState.update { it.copy(showAddLocationIcon = showAddLocationIcon) }
    }

    fun updateAddThemeIconVisibilityStatus(showThemeIcon: Boolean) {
        _uiState.update { it.copy(showThemeIcon = showThemeIcon) }
    }

    fun updateAppBarColor(appBarColor: Color) {
        _uiState.update { it.copy(appBarColor = appBarColor) }
    }

    fun updateAppTransparencyStatus(isTransparent: Boolean) {
        _uiState.update { it.copy(isTransparent = isTransparent) }
    }

}

data class SharedUiState(
    val title: String = DefaultConfig.TITLE,
    val description: String = DefaultConfig.DESCRIPTION,
    val showAddLocationIcon: Boolean = DefaultConfig.SHOW_ADD_LOCATION_ICON,
    val appBarColor: Color = DefaultConfig.DEFAULT_APP_BAR_COLOR,
    val isTransparent: Boolean = DefaultConfig.IS_TRANSPARENT,
    val showThemeIcon : Boolean = DefaultConfig.SHOW_THEME_ICON
)