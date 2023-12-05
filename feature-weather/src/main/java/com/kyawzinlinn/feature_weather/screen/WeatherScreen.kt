@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kyawzinlinn.core_database.entities.ForecastByHourEntity
import com.kyawzinlinn.core_database.entities.ForecastEntity
import com.kyawzinlinn.core_navigation.WeatherNavigationDestination
import com.kyawzinlinn.core_network.util.Resource
import com.kyawzinlinn.core_ui.ErrorScreen
import com.kyawzinlinn.core_ui.SliderDotIndicator

object WeatherHomeNavigation: WeatherNavigationDestination{
    override val route: String = "weather_home"
}

@Composable
fun WeatherScreen(
    allWeatherForecastState : Resource<List<ForecastEntity>>,
    allForecastsByHour: List<ForecastByHourEntity>,
    pagerState: PagerState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        WeatherContentPager(
            weatherForecastState = allWeatherForecastState,
            pagerState = pagerState,
            allForecastsByHour = allForecastsByHour,
            onRetry = onRetry
        )
    }
}

@Composable
fun WeatherContentPager(
    weatherForecastState: Resource<List<ForecastEntity>>,
    pagerState: PagerState,
    onRetry: () -> Unit,
    allForecastsByHour: List<ForecastByHourEntity>,
    modifier: Modifier = Modifier
) {
    var pageCount by remember { mutableStateOf(0) }
    var weatherForecasts by remember { mutableStateOf(emptyList<ForecastEntity>()) }

    LaunchedEffect(weatherForecastState) {
        if (weatherForecastState is Resource.Success) {
            pageCount = weatherForecastState.data?.size ?: 0
            weatherForecasts = weatherForecastState.data ?: emptyList()
        }
    }

    if (weatherForecastState is Resource.Error) {
        ErrorScreen(message = weatherForecastState.message, onRetry = onRetry)
    } else {
        Column {
            Box (modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                SliderDotIndicator(
                    totalCount = pageCount,
                    pagerState = pagerState
                )
            }

            HorizontalPager(state = pagerState, modifier = modifier) { page ->
                val forecast = weatherForecasts.get(page)

                WeatherContent(
                    weatherForecastState = weatherForecastState,
                    weatherForecast = forecast,
                    allForecastsByHour = allForecastsByHour
                )
            }
        }
    }
}