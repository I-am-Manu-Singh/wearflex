package com.creativeblisslabs.wearflex

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
actual fun isHardwareScreenRound(): Boolean {
    return LocalConfiguration.current.isScreenRound
}