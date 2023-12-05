@file:OptIn(ExperimentalMaterial3Api::class)

package com.kyawzinlinn.core_ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.kyawzinlinn.weatherapp.ui.theme.poppinsFontFamily

@Composable
fun WeatherAppTopBar(
    backgroundColor: Color,
    title: String,
    isDay: Boolean,
    isHomeScreen: Boolean,
    description: String = "",
    onNavigationIconClick: () -> Unit = {},
    onThemeIconClick: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val darkTheme = isSystemInDarkTheme()
    var isDarkMode by rememberSaveable { mutableStateOf(darkTheme) }

    CenterAlignedTopAppBar(
        modifier = modifier,
        actions = {
            if (isHomeScreen) {
                IconButton(onClick = {
                    isDarkMode = !isDarkMode
                    onThemeIconClick(isDarkMode)
                }) {
                    Icon(
                        painter = if (isDarkMode) painterResource(R.drawable.light_mode) else painterResource(
                            R.drawable.dark_mode
                        ),
                        tint = if (isDay && isDarkMode) Color.Black else Color.White,
                        contentDescription = null
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = if (isHomeScreen) Icons.Default.Add else Icons.Default.ArrowBack,
                    tint = if (isDay && isDarkMode) Color.Black else Color.White,
                    modifier = Modifier,
                    contentDescription = null
                )
            }
        },
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(title.trim().isNotEmpty()) {
                    Text(
                        text = title,
                        color = if (isDay && isDarkMode) Color.Black else Color.White
                    )
                }
                AnimatedVisibility(
                    visible = isHomeScreen,
                ) {
                    AnimatedText(
                        text = description,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor
        )
    )
}
