package com.creativeblisslabs.wearflex

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.SubcomposeLayout

@Composable
fun AdaptiveScreenScaffold(
    content: @Composable (viewport: ViewportType) -> Unit
) {
    SubcomposeLayout { constraints ->
        val maxWidth = constraints.maxWidth
        val maxHeight = constraints.maxHeight

        // Dynamic viewport heuristics
        val viewportType = when {
            maxWidth == maxHeight && maxWidth < 500 -> ViewportType.CircularWear
            maxWidth < 600 && maxHeight < 600 -> ViewportType.RectangularCompact
            else -> ViewportType.ExpandedScreen
        }

        val measurable = subcompose(Unit) {
            content(viewportType)
        }.first()

        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }
}