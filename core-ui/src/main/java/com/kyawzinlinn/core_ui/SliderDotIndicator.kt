@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.kyawzinlinn.core_ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SliderDotIndicator(
    totalCount: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val dotColors = remember(pagerState.currentPage) {
        List(totalCount) { index ->
            derivedStateOf {
                if (index == pagerState.currentPage) {
                    Color.Black
                } else {
                    Color.LightGray
                }
            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        dotColors.forEach { animatedColorState ->
            val animatedColor by animatedColorState
            Box(
                modifier = modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(animatedColor)
            )
        }

    }
}