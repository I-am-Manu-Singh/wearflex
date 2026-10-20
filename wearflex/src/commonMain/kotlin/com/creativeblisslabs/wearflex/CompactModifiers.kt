package com.creativeblisslabs.wearflex

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Geometric constant: (1 - sqrt(2)/2) / 2 ≈ 0.1464466f.
 * Represents the exact inset percentage required to inscribe a square inside a circle of diameter D.
 */
private const val CIRCULAR_INSCRIBED_INSET_FACTOR = 0.1464f

/**
 * Applies radial safe-zone padding on circular watch screens so corner content is never
 * clipped by the physical circular bezel. On rectangular screens, applies standard padding.
 */
fun Modifier.radialSafePadding(
    viewportType: ViewportType,
    standardPadding: Dp = 16.dp
): Modifier {
    return when (viewportType) {
        is ViewportType.CircularWear -> {
            val minDimension = minOf(viewportType.maxWidth, viewportType.maxHeight)
            val radialInset = minDimension * CIRCULAR_INSCRIBED_INSET_FACTOR
            this.padding(radialInset)
        }
        is ViewportType.SquareWear -> {
            this.padding(8.dp)
        }
        else -> {
            this.padding(standardPadding)
        }
    }
}

/**
 * Calculates optimal content PaddingValues for scrollable lists based on the active viewport.
 * On circular watches, adds generous top and bottom padding so the first and last list items
 * can scroll into the widest center portion of the circular screen.
 */
@Composable
fun rememberWearFlexListPadding(
    viewportType: ViewportType = LocalViewportType.current,
    standardHorizontal: Dp = 16.dp,
    standardVertical: Dp = 12.dp
): PaddingValues {
    return when (viewportType) {
        is ViewportType.CircularWear -> {
            val horizontalInset = viewportType.maxWidth * 0.10f
            val verticalInset = viewportType.maxHeight * 0.22f
            PaddingValues(horizontal = horizontalInset, vertical = verticalInset)
        }
        is ViewportType.SquareWear -> {
            PaddingValues(horizontal = 10.dp, vertical = 12.dp)
        }
        else -> {
            PaddingValues(horizontal = standardHorizontal, vertical = standardVertical)
        }
    }
}

/**
 * Constrains maximum content width on large desktop/tablet screens for readability,
 * while filling the available width on mobile and wearables.
 */
fun Modifier.adaptiveContentWidth(
    viewportType: ViewportType,
    maxExpandedWidth: Dp = 640.dp
): Modifier {
    return when (viewportType) {
        is ViewportType.ExpandedScreen, is ViewportType.MediumTablet -> {
            this.widthIn(max = maxExpandedWidth).fillMaxWidth()
        }
        else -> {
            this.fillMaxWidth()
        }
    }
}

/**
 * Enforces wearable-friendly minimum touch target heights (40.dp on watch, 48.dp on phone/desktop).
 */
fun Modifier.wearFlexTouchTarget(viewportType: ViewportType): Modifier {
    val minSize = if (viewportType.isWearable) 40.dp else 48.dp
    return this.defaultMinSize(minWidth = minSize, minHeight = minSize)
}