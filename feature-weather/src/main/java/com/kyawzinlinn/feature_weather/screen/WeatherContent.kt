@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.kyawzinlinn.feature_weather.screen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import com.kyawzinlinn.core_database.util.removeDecimalPlace
import com.kyawzinlinn.core_ui.WeatherCard
import com.kyawzinlinn.weatherapp.ui.theme.poppinsFontFamily

@Composable
fun WeatherContent(
    weatherForecast: ForecastEntity?,
    isDay: Boolean,
    allForecastsByHour: List<ForecastByHourEntity>,
    modifier: Modifier = Modifier,
) {

    var isDarkMode = isSystemInDarkTheme()

    var temperature by remember { mutableStateOf("-") }
    var condition by remember { mutableStateOf("-") }
    var sunset by remember { mutableStateOf("-") }
    var sunrise by remember { mutableStateOf("-") }
    var humidity by remember { mutableStateOf("-") }
    var realFeel by remember { mutableStateOf("-") }
    var pressure by remember { mutableStateOf("-") }
    var windSpeed by remember { mutableStateOf("-") }
    var windDegree by remember { mutableStateOf("-") }
    var windDirection by remember { mutableStateOf("-") }
    var UV by remember { mutableStateOf("-") }
    var weatherIconUrl by remember { mutableStateOf("") }
    var allForecasts = remember { mutableStateListOf<ForecastByHourEntity?>(ForecastByHourEntity()) }

    LaunchedEffect(allForecastsByHour) {
        allForecasts.clear()
        allForecasts.addAll(allForecastsByHour)
    }

    LaunchedEffect(weatherIconUrl) {
        //Log.d("TAG", "WeatherContent: $weatherIconUrl")
    }

    LaunchedEffect(weatherForecast) {
        if (weatherForecast != null) {

            weatherIconUrl = weatherForecast.icon
            Log.d("TAG", "WeatherContent: $weatherIconUrl")

            temperature = weatherForecast.temperature.removeDecimalPlace() + "°"
            condition =
                "\n\n${weatherForecast.condition} ${weatherForecast.maxTemperature}°/${weatherForecast.minTemperature}° "
            sunrise = weatherForecast.sunrise ?: ""
            sunset = weatherForecast.sunset ?: ""
            realFeel = weatherForecast.temperature.removeDecimalPlace() + "°"
            humidity = weatherForecast.humidity.removeDecimalPlace() + "%"
            UV = weatherForecast.uv.removeDecimalPlace()
            windSpeed = weatherForecast.windSpeed ?: ""
            windDirection = weatherForecast.windDirection ?: ""
            windDegree = weatherForecast.windDegree ?: ""
            pressure = weatherForecast.pressure + " hPa"
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
            .animateContentSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(32.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = if (isDay && isDarkMode) Color.Black else Color.White,
                            fontSize = 96.sp
                        )
                    ) { append(temperature) }

                    withStyle(
                        style = SpanStyle(
                            color = if (isDay && isDarkMode) Color.Black else Color.White,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                    ) {
                        append(condition)
                    }
                }
            )
            Spacer(Modifier.height(12.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https:${weatherIconUrl.replace("64x64", "128x128")}")
                    .crossfade(true)
                    .build(),
                modifier = Modifier.scale(2f),
                contentDescription = null
            )

            Spacer(Modifier.height(32.dp))
            ForecastsByHourList(
                allForecastsByHour = allForecasts.map { it },
                onForecastByHourItemClick = {
                    if (it != null) {
                        weatherIconUrl = it.icon
                        temperature = it.temperature.toString().removeDecimalPlace() + "°"
                        condition = "\n\n${it.condition}"
                        realFeel = it.realFeelTemp.removeDecimalPlace() + "°"
                        humidity = it.humidity.removeDecimalPlace() + "%"
                        UV = it.uv.removeDecimalPlace()
                        pressure = it.pressure + " hPa"
                        windSpeed = it.windSpeed
                        windDegree = it.windDegree
                        windDirection = it.windDirection
                    }
                }
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(intrinsicSize = IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier.weight(0.5f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                WeatherDetailsCard(
                    forecasts = hashMapOf(
                        "Sunrise" to sunrise,
                        "Sunset" to sunset,
                    ),
                    modifier = Modifier.weight(1f)
                )
                WeatherDetailsCard(
                    forecasts = hashMapOf(
                        "Wind speed" to windSpeed,
                        "Wind degree" to windDegree,
                        "Wind direction" to windDirection,
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.5f)
            ) {
                WeatherDetailsCard(
                    forecasts = hashMapOf(
                        "Humidity" to humidity,
                        "Real Feel" to realFeel,
                        "UV" to UV,
                        "Pressure" to pressure,
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun WeatherDetailsCard(
    forecasts: HashMap<String, String>,
    modifier: Modifier = Modifier
) {
    WeatherCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            forecasts.toList().forEach {
                SpecHorizontalItem(it.first, it.second)
            }
        }
    }
}

@Composable
fun ForecastsByHourList(
    allForecastsByHour: List<ForecastByHourEntity?>,
    onForecastByHourItemClick: (ForecastByHourEntity?) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableIntStateOf(-1) }

    WeatherCard(modifier = modifier) {
        Column(modifier = Modifier.animateContentSize()) {
            Text(
                text = "24-hour forecast",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                itemsIndexed(allForecastsByHour) { index, item ->
                    key(item?.id) {
                        ForecastByHourItem(
                            selected = selectedIndex == index,
                            forecastByHourEntity = item,
                            onItemClick = {
                                onForecastByHourItemClick(item)
                                selectedIndex = index
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SpecHorizontalItem(
    name: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            fontSize = 14.sp,
            fontFamily = poppinsFontFamily,
            color = Color.LightGray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            modifier = Modifier,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun ForecastByHourItem(
    selected: Boolean,
    forecastByHourEntity: ForecastByHourEntity?,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onItemClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color.White else MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = getHourFromDateTime(forecastByHourEntity?.time),
                fontFamily = poppinsFontFamily,
                color = Color.LightGray,
                fontSize = 14.sp
            )
            Text(
                text = "${forecastByHourEntity?.temperature?.toInt()}",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https:${forecastByHourEntity?.icon}")
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
        }
    }
}
