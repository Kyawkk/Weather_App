@file:OptIn(ExperimentalFoundationApi::class)

package com.kyawzinlinn.weatherapp.ui.navigation

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kyawzinlinn.core_database.entities.ForecastEntity
import com.kyawzinlinn.core_database.util.convertDateToDay
import com.kyawzinlinn.core_network.model.City
import com.kyawzinlinn.core_network.util.Resource
import com.kyawzinlinn.feature_location.CityViewModel
import com.kyawzinlinn.feature_location.navigation.SearchCityNavigationDestination
import com.kyawzinlinn.feature_location.screen.SearchCityScreen
import com.kyawzinlinn.feature_weather.WeatherViewModel
import com.kyawzinlinn.feature_weather.screen.WeatherHomeNavigation
import com.kyawzinlinn.feature_weather.screen.WeatherScreen
import com.kyawzinlinn.weatherapp.SharedUiViewModel

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

            sharedUiViewModel.apply {
                updateAppBarColor(Color.Transparent.copy(0f))
                updateAppTransparencyStatus(true)
                updateAddLocationIconVisibilityStatus(true)
                updateAddThemeIconVisibilityStatus(true)
            }

            var title by rememberSaveable { mutableStateOf("") }
            var allWeatherForecasts by remember { mutableStateOf(emptyList<ForecastEntity>()) }

            val pagerState = rememberPagerState(0) { allWeatherForecasts.size }

            LaunchedEffect(pagerState.currentPage) {
                if (allWeatherForecasts.size != 0) sharedUiViewModel.updateDescription(
                    convertDateToDay(allWeatherForecasts.get(pagerState.currentPage).localTime)
                )
            }

            LaunchedEffect(pagerState.currentPage) {
                if (allWeatherForecasts.size != 0) weatherViewModel.getAllForecastsByHour(
                    allWeatherForecasts.get(pagerState.currentPage).localTime
                )
            }

            LaunchedEffect(forecastState) {
                when (forecastState) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        title =
                            (forecastState as Resource.Success<List<ForecastEntity>>).data?.get(0)?.name
                                ?: ""
                        allWeatherForecasts =
                            (forecastState as Resource.Success<List<ForecastEntity>>).data
                                ?: emptyList()


                        sharedUiViewModel.updateDescription(
                            convertDateToDay(
                                allWeatherForecasts.get(
                                    0
                                ).localTime
                            )
                        )
                    }

                    is Resource.Error -> {
                        sharedUiViewModel.updateDescription((forecastState as Resource.Error).message)
                    }
                }
            }


            sharedUiViewModel.apply {
                updateTitle(title)
            }
            WeatherScreen(
                allWeatherForecasts = allWeatherForecasts,
                allForecastsByHour = allForecastsByHour,
                pagerState = pagerState
            )
        }

        composable(SearchCityNavigationDestination.route) {
            var searchResults by remember { mutableStateOf(emptyList<City>()) }
            val savedAllCities by cityUiState.savedCities.collectAsState(emptyList())

            sharedUiViewModel.apply {
                updateTitle("Search a city")
                updateAppBarColor(MaterialTheme.colorScheme.background)
                updateAppTransparencyStatus(false)
                updateAddLocationIconVisibilityStatus(false)
                updateAddThemeIconVisibilityStatus(false)
            }

            SearchCityScreen(
                searchResults = cityUiState.searchResults,
                savedCities = savedAllCities,
                onSearch = {
                    cityViewModel.resetSearchResults()
                    cityViewModel.searchCity(it)
                },
                onCityItemClick = {
                    cityViewModel.addCity(it)
                    weatherViewModel.resetWeatherForecastsByLocation()
                    weatherViewModel.getWeatherForecastsByLocation(it.name)
                    navController.navigate(WeatherHomeNavigation.route)
                })
        }
    }
}