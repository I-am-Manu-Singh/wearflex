package com.creativeblisslabs.wearflex

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.pow

/**
 * A multiplatform adaptive list that behaves like `ScalingLazyColumn` on circular smartwatches
 * (scaling and fading items near the curved top/bottom bezels) and like a standard `LazyColumn`
 * on rectangular phones, tablets, and desktops.
 */
@Composable
fun <T> WearFlexLazyColumn(
    items: List<T>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    viewportType: ViewportType = LocalViewportType.current,
    contentPadding: PaddingValues = rememberWearFlexListPadding(viewportType),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(
        if (viewportType.isWearable) 6.dp else 12.dp,
        Alignment.CenterVertically
    ),
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    enableRotaryScroll: Boolean = true,
    minBezelScale: Float = 0.68f,
    minBezelAlpha: Float = 0.35f,
    key: ((index: Int, item: T) -> Any)? = null,
    itemContent: @Composable (index: Int, item: T) -> Unit
) {
    val rotaryModifier = if (enableRotaryScroll) {
        modifier.wearFlexRotaryScroll(state)
    } else {
        modifier
    }

    LazyColumn(
        modifier = rotaryModifier,
        state = state,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior
    ) {
        itemsIndexed(items = items, key = key) { index, item ->
            if (viewportType.isCircular) {
                Box(
                    modifier = Modifier.graphicsLayer {
                        val layoutInfo = state.layoutInfo
                        val visibleItem = layoutInfo.visibleItemsInfo.find { it.index == index }
                        if (visibleItem != null) {
                            val viewportHeight = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset
                            if (viewportHeight > 0) {
                                val viewportCenter = layoutInfo.viewportStartOffset + (viewportHeight / 2f)
                                val itemCenter = visibleItem.offset + (visibleItem.size / 2f)
                                val normalizedDistance = (abs(viewportCenter - itemCenter) / (viewportHeight / 2f))
                                    .coerceIn(0f, 1f)

                                // Smooth quadratic falloff near the top and bottom curved bezels
                                val curveFactor = 1f - normalizedDistance.pow(1.8f)
                                val scale = minBezelScale + (1f - minBezelScale) * curveFactor
                                val alphaVal = minBezelAlpha + (1f - minBezelAlpha) * curveFactor

                                scaleX = scale
                                scaleY = scale
                                alpha = alphaVal
                            }
                        }
                    }
                ) {
                    itemContent(index, item)
                }
            } else {
                itemContent(index, item)
            }
        }
    }
}