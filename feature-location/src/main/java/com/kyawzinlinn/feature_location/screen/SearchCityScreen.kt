@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.kyawzinlinn.feature_location.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kyawzinlinn.core_database.entities.CityEntity
import com.kyawzinlinn.core_database.entities.toCityEntity
import com.kyawzinlinn.core_navigation.exitApp
import com.kyawzinlinn.core_network.model.City
import com.kyawzinlinn.core_network.util.Resource
import com.kyawzinlinn.core_ui.ErrorScreen
import com.kyawzinlinn.core_ui.Loading
import com.kyawzinlinn.core_ui.R
import com.kyawzinlinn.core_ui.SearchBar
import kotlinx.coroutines.launch

@Composable
fun SearchCityScreen(
    searchResults: Resource<List<City>>,
    savedCities: List<CityEntity>,
    isDay: Boolean,
    onDeleteCityItemClick: (CityEntity) -> Unit,
    onCityItemClick: (CityEntity) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldShowSuqggestions by remember { mutableStateOf(false) }
    var value by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        coroutineScope.launch {
            // Exit the app when the back button is pressed
            exitApp(context)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            value = value,
            onValueChange = {
                value = it
                if (value.trim().isNotEmpty()) onSearch(value)
            },
            onSearch = onSearch,
            shouldShowSuggestions = {
                shouldShowSuqggestions = it
            }
        )
        Spacer(Modifier.height(16.dp))

        if (value.isNotEmpty()) {
            when (searchResults) {
                is Resource.Loading -> Loading()
                is Resource.Success -> {
                    SearchResultList(
                        searchResults = searchResults.data ?: emptyList(),
                        onCityItemClick = onCityItemClick
                    )
                }

                is Resource.Error -> ErrorScreen(
                    searchResults.message,
                    onRetry = { onSearch(value) })
            }
        } else {
                SavedCitiesList(isDay = isDay, savedCities = savedCities,onCityItemClick = onCityItemClick, onDeleteCityItemClick = onDeleteCityItemClick)
        }
    }
}

@Composable
fun SearchResultList(
    searchResults: List<City>,
    onCityItemClick: (CityEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(searchResults) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onCityItemClick(it.toCityEntity()) }) {
                Text(
                    text = "${it.name}, ${it.country}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun SavedCitiesList(
    isDay: Boolean,
    savedCities: List<CityEntity>,
    onCityItemClick: (CityEntity) -> Unit,
    onDeleteCityItemClick: (CityEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(savedCities) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                onClick = { onCityItemClick(it) }) {
                Box (contentAlignment = Alignment.CenterStart) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(if (isDay) R.drawable.day else R.drawable.night)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier.fillMaxSize()
                    )
                    Spacer(
                        modifier = modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(0.6f))
                    )
                    Row (
                        modifier = Modifier.fillMaxSize().background(Color.Gray),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "${it.name}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Text(
                                text = it.country,
                                color = Color.LightGray,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                        IconButton(onClick = {onDeleteCityItemClick(it)}){
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}