@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class
)

package com.kyawzinlinn.feature_weather.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.kyawzinlinn.core_database.entities.ForecastByHourEntity
import com.kyawzinlinn.core_database.entities.ForecastEntity
import com.kyawzinlinn.core_navigation.WeatherNavigationDestination
import com.kyawzinlinn.core_ui.SliderDotIndicator
import com.kyawzinlinn.feature_weather.WeatherViewModel

object WeatherHomeNavigation : WeatherNavigationDestination {
    override val route: String = "weather_home"
}

@Composable
fun WeatherScreen(
    city: String,
    isDay: Boolean,
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
) {
    val allWeatherForecasts by weatherViewModel.forecastEntities.collectAsState()
    val allForecastsByHour by weatherViewModel.allForecastsByHour.collectAsState()
    val pagerState = rememberPagerState(0) { allWeatherForecasts.size }

    LaunchedEffect(Unit) { weatherViewModel.getWeatherForecastsByLocation(city) }

    WeatherContentPager(
        weatherForecastEntities = allWeatherForecasts,
        pagerState = pagerState,
        isDay = isDay,
        modifier = modifier,
        allForecastsByHour = allForecastsByHour,
        refreshForecastsByHour = weatherViewModel::getAllForecastsByHour
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherContentPager(
    weatherForecastEntities: List<ForecastEntity>,
    pagerState: PagerState,
    isDay: Boolean,
    refreshForecastsByHour: (String) -> Unit,
    allForecastsByHour: List<ForecastByHourEntity>,
    modifier: Modifier = Modifier
) {
    var pageCount by remember { mutableIntStateOf(0) }
    var weatherForecasts by remember { mutableStateOf(emptyList<ForecastEntity>()) }
    var forecastEntity by remember { mutableStateOf<ForecastEntity?>(null) }

    LaunchedEffect(weatherForecastEntities) {
        weatherForecasts = weatherForecastEntities
        pageCount = weatherForecasts.size
        if (weatherForecasts.isNotEmpty()) forecastEntity = weatherForecasts[0]
        if (weatherForecasts.isNotEmpty()) refreshForecastsByHour(weatherForecasts[pagerState.currentPage].date)
    }

    LaunchedEffect(pagerState.currentPage) {
        if (weatherForecasts.isNotEmpty()) forecastEntity = weatherForecasts[pagerState.currentPage]
        if (weatherForecasts.isNotEmpty()) refreshForecastsByHour(weatherForecasts[pagerState.currentPage].date)
    }

    Column (modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            SliderDotIndicator(
                totalCount = pageCount,
                pagerState = pagerState
            )
        }

        HorizontalPager(state = pagerState, modifier = modifier) { page ->
            WeatherContent(
                weatherForecast = forecastEntity,
                isDay = isDay,
                allForecastsByHour = allForecastsByHour
            )
        }
    }
}