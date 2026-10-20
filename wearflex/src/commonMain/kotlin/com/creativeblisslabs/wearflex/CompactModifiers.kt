package com.creativeblisslabs.wearflex

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.defaultMinSize

/**
 * Automatically enforces minimum touch target sizes (48dp standard vs 32dp compact)
 * depending on whether the host viewport is wearable.
 */
fun Modifier.adaptiveTouchTarget(isWearable: Boolean): Modifier = composed {
    if (isWearable) {
        this.defaultMinSize(minWidth = 32.dp, minHeight = 32.dp)
    } else {
        this.defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
    }
}