@file:OptIn(ExperimentalMaterial3Api::class)

package com.kyawzinlinn.core_ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun WeatherAppTopBar(
    backgroundColor: Color,
    title: String,
    showAddCityIcon: Boolean = true,
    showThemeIcon: Boolean = true,
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
            if (showThemeIcon) {
                IconButton(onClick = {
                    isDarkMode = !isDarkMode
                    onThemeIconClick(isDarkMode)
                }) {
                    Icon(
                        painter = if (isDarkMode) painterResource(R.drawable.light_mode) else painterResource(
                            R.drawable.dark_mode
                        ),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = if (showAddCityIcon) Icons.Default.Add else Icons.Default.ArrowBack,
                    tint = if(showAddCityIcon) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground,
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
                        color = if (showAddCityIcon) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                    )
                }
                AnimatedVisibility(
                    visible = showThemeIcon,
                ) {
                    AnimatedText(
                        text = description,
                        fontSize = 14.sp,
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
