package com.creativeblisslabs.wearflex

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * CompositionLocal allowing any nested composable to inspect the current [ViewportType]
 * without passing it down manually through every function signature.
 */
val LocalViewportType = compositionLocalOf<ViewportType> {
    ViewportType.CompactPhone(maxWidth = 360.dp, maxHeight = 800.dp)
}

/**
 * Root adaptive container that classifies the current screen using both hardware
 * roundness detection and window constraints.
 *
 * @param wearThresholdMax Maximum width/height threshold to classify a display as a wearable.
 * @param simulateRoundInPreview Force circular mode on desktop/emulator previews when width == height.
 * @param applyRadialSafePadding Automatically pad content away from circular bezels if true.
 * @param wearTimeOverlay Optional top overlay (e.g., watch time text) rendered only on wearables.
 */
@Composable
fun AdaptiveScreenScaffold(
    modifier: Modifier = Modifier,
    wearThresholdMax: Dp = 250.dp,
    simulateRoundInPreview: Boolean = true,
    applyRadialSafePadding: Boolean = false,
    wearTimeOverlay: (@Composable () -> Unit)? = null,
    content: @Composable (ViewportType) -> Unit
) {
    val isHardwareRound = isHardwareScreenRound()

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val width = maxWidth
        val height = maxHeight
        val isSubCompact = width <= wearThresholdMax && height <= wearThresholdMax

        // Detect circularity via hardware API or 1:1 aspect ratio fallback in previews/emulators
        val isAspectRatioSquare = kotlin.math.abs(width.value - height.value) < 4f
        val isRoundWear = isSubCompact && (isHardwareRound || (simulateRoundInPreview && isAspectRatioSquare))

        val viewportType: ViewportType = when {
            isRoundWear -> ViewportType.CircularWear(width, height)
            isSubCompact -> ViewportType.SquareWear(width, height)
            width < 600.dp -> ViewportType.CompactPhone(width, height)
            width < 840.dp -> ViewportType.MediumTablet(width, height)
            else -> ViewportType.ExpandedScreen(width, height)
        }

        CompositionLocalProvider(LocalViewportType provides viewportType) {
            val contentModifier = if (applyRadialSafePadding) {
                Modifier.fillMaxSize().radialSafePadding(viewportType)
            } else {
                Modifier.fillMaxSize()
            }

            Box(modifier = contentModifier) {
                content(viewportType)

                if (viewportType.isWearable && wearTimeOverlay != null) {
                    Box(modifier = Modifier.align(Alignment.TopCenter)) {
                        wearTimeOverlay()
                    }
                }
            }
        }
    }
}