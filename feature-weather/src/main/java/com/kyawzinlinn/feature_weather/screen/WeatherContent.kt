@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.kyawzinlinn.feature_weather.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kyawzinlinn.core_database.entities.ForecastByHourEntity
import com.kyawzinlinn.core_database.entities.ForecastEntity
import com.kyawzinlinn.core_database.util.getHourFromDateTime
import com.kyawzinlinn.core_ui.WeatherCard
import com.kyawzinlinn.weatherapp.ui.theme.poppinsFontFamily

@Composable
fun WeatherContent(
    weatherForecast: ForecastEntity,
    allForecastsByHour: List<ForecastByHourEntity>,
    onUpdateDate: (String) -> Unit,
    refreshForecastByHour: (String) -> Unit,
    modifier: Modifier = Modifier
) {

   /* LaunchedEffect(true) {
        onUpdateDate(weatherForecast.localTime)
        refreshForecastByHour(weatherForecast.localTime)
    }*/

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(32.dp))
        Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 96.sp
                    )) {append(weatherForecast.temperature)}
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        append("\n${weatherForecast.condition}")
                    }
                }
            )

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https:${weatherForecast.icon.replace("64x64","128x128")}")
                    .crossfade(true)
                    .build(),
                modifier = Modifier.scale(2f),
                contentDescription = null
            )

            Spacer(Modifier.height(32.dp))
            ForecastsByHourList(
                allForecastsByHour = allForecastsByHour,
                onForecastByHourItemClick = {}
            )
        }
    }
}

@Composable
fun ForecastsByHourList(
    allForecastsByHour: List<ForecastByHourEntity>,
    onForecastByHourItemClick: (ForecastByHourEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    WeatherCard (modifier = modifier) {
        Column (modifier= Modifier.animateContentSize()) {
            Text(
                text = "Today",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold
            )
            LazyRow (horizontalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp)) {
                items(allForecastsByHour) {
                    ForecastByHourItem(
                        forecastByHourEntity = it,
                        onItemClick = {onForecastByHourItemClick(it)}
                    )
                }
            }
        }
    }
}

@Composable
fun ForecastByHourItem(
    forecastByHourEntity: ForecastByHourEntity,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card (
        onClick = onItemClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column (
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = getHourFromDateTime(forecastByHourEntity.time),
                fontFamily = poppinsFontFamily,
                color = Color.LightGray,
                fontSize = 14.sp
            )
            Text(
                text = "${forecastByHourEntity.temperature.toInt()}",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https:${forecastByHourEntity.icon}")
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
        }
    }
}