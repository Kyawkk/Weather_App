@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)

package com.kyawzinlinn.feature_weather.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kyawzinlinn.core_database.entities.ForecastByHourEntity
import com.kyawzinlinn.core_database.entities.ForecastEntity
import com.kyawzinlinn.core_navigation.WeatherNavigationDestination
import com.kyawzinlinn.core_network.util.Resource
import com.kyawzinlinn.core_ui.SliderDotIndicator

object WeatherHomeNavigation: WeatherNavigationDestination{
    override val route: String = "weather_home"
}

@Composable
fun WeatherScreen(
    allWeatherForecasts : List<ForecastEntity>,
    allForecastsByHour: List<ForecastByHourEntity>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        WeatherContentPager(
            weatherForecasts = allWeatherForecasts,
            pagerState = pagerState,
            allForecastsByHour = allForecastsByHour,
        )
    }
}

@Composable
fun WeatherContentPager(
    weatherForecasts: List<ForecastEntity>,
    pagerState: PagerState,
    allForecastsByHour: List<ForecastByHourEntity>,
    modifier: Modifier = Modifier
) {

    Column {
        Box (modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            SliderDotIndicator(
                totalCount = weatherForecasts.size,
                pagerState = pagerState
            )
        }
        HorizontalPager(state = pagerState, modifier = modifier) { page ->
            val forecast = weatherForecasts.get(page)

            WeatherContent(
                weatherForecast = forecast,
                allForecastsByHour = allForecastsByHour,
                onUpdateDate = {},
                refreshForecastByHour = {}
            )
        }
    }
}