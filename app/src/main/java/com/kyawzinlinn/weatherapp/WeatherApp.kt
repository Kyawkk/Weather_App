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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
    val weatherViewModel : WeatherViewModel = hiltViewModel()
    val sharedUiViewModel: SharedUiViewModel = hiltViewModel()
    val cityViewModel: CityViewModel = hiltViewModel()

    val uiState by sharedUiViewModel.uiState.collectAsState()
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(uiState) {
        title = uiState.title
        description = uiState.description
    }

    LaunchedEffect(Unit) {
        weatherViewModel.getWeatherForecastsByLocation("Yangon")
    }

    val darkTheme = isSystemInDarkTheme()
    var isDarkMode by rememberSaveable { mutableStateOf(darkTheme) }

    WeatherAppTheme (darkTheme = isDarkMode) {
        val navController = rememberNavController()
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    Box {
                        WeatherAppTopBar(
                            backgroundColor = uiState.appBarColor,
                            title = title,
                            description = description,
                            showThemeIcon = uiState.showThemeIcon,
                            showAddCityIcon = uiState.showAddLocationIcon,
                            onThemeIconClick = {
                                isDarkMode = it
                            },
                            onNavigationIconClick = {
                                if (uiState.isTransparent) navController.navigate(SearchCityNavigationDestination.route)
                                else navController.navigateUp()
                            }
                        )
                    }
                }
            ) {
                Box (modifier = Modifier.fillMaxSize()) {
                    val gradient = Brush.linearGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        start = Offset.Zero,
                        end = Offset.Infinite,
                        tileMode = TileMode.Clamp
                    )

                    if (uiState.isTransparent) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://raw.githubusercontent.com/SidharthMudgil/mosam/main/app/src/main/res/drawable/bg_day.jpg")
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = modifier.fillMaxSize()
                        )
                    }
                    /*Spacer(
                        modifier = Modifier.fillMaxSize().background(brush = gradient)
                    )*/
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