@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.kyawzinlinn.weatherapp.ui.navigation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kyawzinlinn.core_database.entities.ForecastEntity
import com.kyawzinlinn.core_database.util.convertDateToDay
import com.kyawzinlinn.core_network.util.Resource
import com.kyawzinlinn.feature_location.CityViewModel
import com.kyawzinlinn.feature_location.navigation.SearchCityNavigationDestination
import com.kyawzinlinn.feature_location.screen.SearchCityScreen
import com.kyawzinlinn.feature_weather.WeatherViewModel
import com.kyawzinlinn.feature_weather.screen.WeatherHomeNavigation
import com.kyawzinlinn.feature_weather.screen.WeatherScreen
import com.kyawzinlinn.weatherapp.SharedUiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun NavigationHost(
    sharedUiViewModel: SharedUiViewModel,
    weatherViewModel: WeatherViewModel,
    cityViewModel: CityViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val sharedUiState by sharedUiViewModel.uiState.collectAsState()
    val weatherUiState by weatherViewModel.weatherUiState.collectAsState()
    val cityUiState by cityViewModel.cityUiState.collectAsState()

    val forecastState by weatherUiState.allForecasts.collectAsState(Resource.Loading)
    val allForecastsByHour by weatherUiState.allForecastsByHour.collectAsState(emptyList())

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = WeatherHomeNavigation.route
    ) {
        composable(WeatherHomeNavigation.route) {

            var title by rememberSaveable { mutableStateOf("") }
            var allWeatherForecasts by remember { mutableStateOf(emptyList<ForecastEntity>()) }

            val pagerState = rememberPagerState(0) { allWeatherForecasts.size }

            LaunchedEffect(pagerState.currentPage) {
                withContext(Dispatchers.IO){
                    if (allWeatherForecasts.size != 0) {
                        sharedUiViewModel.updateDescription(convertDateToDay(allWeatherForecasts.get(pagerState.currentPage).date))
                        weatherViewModel.getAllForecastsByHour(allWeatherForecasts.get(pagerState.currentPage).date)
                    }
                }
            }

            LaunchedEffect(forecastState) {
                when (forecastState) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        title = (forecastState as Resource.Success<List<ForecastEntity>>).data?.get(0)?.name ?: ""
                        allWeatherForecasts = (forecastState as Resource.Success<List<ForecastEntity>>).data ?: emptyList()

                        sharedUiViewModel.apply {
                            updateDescription(convertDateToDay(allWeatherForecasts.get(0).date))
                            updateDayStatus(allWeatherForecasts.get(0).isDay)
                        }
                    }

                    is Resource.Error -> {
                        sharedUiViewModel.updateScreenStatus(isWeatherScreen = false)
                    }
                }
            }


            sharedUiViewModel.apply {
                updateTitle(title)
                updateScreenStatus(isWeatherScreen = true)
            }
            WeatherScreen(
                allWeatherForecastState = forecastState,
                allForecastsByHour = allForecastsByHour,
                pagerState = pagerState,
                onRetry = {
                    weatherViewModel.getWeatherForecastsByLocation(sharedUiState.title)
                }
            )
        }

        composable(SearchCityNavigationDestination.route) {
            val savedAllCities by cityUiState.savedCities.collectAsState(emptyList())

            sharedUiViewModel.apply {
                updateTitle("Search a city")
                updateScreenStatus(false)
            }

            SearchCityScreen(
                searchResults = cityUiState.searchResults,
                savedCities = savedAllCities,
                isDay = sharedUiState.isDay,
                onSearch = {
                    cityViewModel.resetSearchResults()
                    cityViewModel.searchCity(it)
                },
                onCityItemClick = {
                    cityViewModel.addCity(it)
                    //weatherViewModel.resetWeatherForecastsByLocation()
                    weatherViewModel.getWeatherForecastsByLocation(it.name)
                    navController.navigate(WeatherHomeNavigation.route)
                })
        }
    }
}