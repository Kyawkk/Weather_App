package com.kyawzinlinn.weatherapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kyawzinlinn.core_ui.ErrorScreen
import com.kyawzinlinn.core_ui.R
import com.kyawzinlinn.core_ui.WeatherAppTopBar
import com.kyawzinlinn.feature_location.CityViewModel
import com.kyawzinlinn.feature_location.navigation.SearchCityNavigationDestination
import com.kyawzinlinn.feature_weather.WeatherViewModel
import com.kyawzinlinn.weatherapp.ui.navigation.NavigationHost
import com.kyawzinlinn.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherApp(
    modifier: Modifier = Modifier
) {
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val sharedUiViewModel: SharedUiViewModel = hiltViewModel()
    val cityViewModel: CityViewModel = hiltViewModel()

    val uiState by sharedUiViewModel.uiState.collectAsState()
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(uiState) {
        title = uiState.title
        description = uiState.description
    }

    val darkTheme = isSystemInDarkTheme()
    var isDarkMode by rememberSaveable { mutableStateOf(darkTheme) }

    WeatherAppTheme(darkTheme = isDarkMode) {
        val navController = rememberNavController()
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    Box {
                        WeatherAppTopBar(
                            backgroundColor = if (uiState.isWeatherScreen) Color.Black.copy(alpha = 0f) else MaterialTheme.colorScheme.background,
                            title = title,
                            description = description,
                            isDay = uiState.isDay,
                            isHomeScreen = uiState.isWeatherScreen,
                            onThemeIconClick = {
                                isDarkMode = it
                            },
                            onNavigationIconClick = {
                                if (uiState.isWeatherScreen) navController.navigate(
                                    SearchCityNavigationDestination.route
                                )
                                else navController.navigateUp()
                            }
                        )
                    }
                }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (uiState.isWeatherScreen) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(if (uiState.isDay) R.drawable.day else R.drawable.night)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = modifier.fillMaxSize()
                        )
                    }
                    NavigationHost(
                        navController = navController,
                        sharedUiViewModel = sharedUiViewModel,
                        weatherViewModel = weatherViewModel,
                        cityViewModel = cityViewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    )
                }
            }
        }
    }
}