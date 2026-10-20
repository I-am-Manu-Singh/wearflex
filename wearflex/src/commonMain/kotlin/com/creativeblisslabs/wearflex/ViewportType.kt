package com.creativeblisslabs.wearflex

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

/**
 * Semantic classification of the active device viewport, bridging the gap between
 * sub-compact Wear OS displays, standard mobile phones, and desktop monitors.
 */
@Immutable
sealed interface ViewportType {
    val maxWidth: Dp
    val maxHeight: Dp
    val isRound: Boolean

    /** Circular smartwatch screen (e.g., Pixel Watch, Galaxy Watch). */
    data class CircularWear(
        override val maxWidth: Dp,
        override val maxHeight: Dp
    ) : ViewportType {
        override val isRound: Boolean = true
    }

    /** Square or rectangular smartwatch / micro-display. */
    data class SquareWear(
        override val maxWidth: Dp,
        override val maxHeight: Dp
    ) : ViewportType {
        override val isRound: Boolean = false
    }

    /** Standard mobile phone in portrait orientation (< 600.dp). */
    data class CompactPhone(
        override val maxWidth: Dp,
        override val maxHeight: Dp
    ) : ViewportType {
        override val isRound: Boolean = false
    }

    /** Foldable inner display, small tablet, or compact desktop window (600.dp - 839.dp). */
    data class MediumTablet(
        override val maxWidth: Dp,
        override val maxHeight: Dp
    ) : ViewportType {
        override val isRound: Boolean = false
    }

    /** Large tablet, desktop window, or landscape layout (>= 840.dp). */
    data class ExpandedScreen(
        override val maxWidth: Dp,
        override val maxHeight: Dp
    ) : ViewportType {
        override val isRound: Boolean = false
    }

    val isWearable: Boolean
        get() = this is CircularWear || this is SquareWear

    val isCircular: Boolean
        get() = this is CircularWear
}