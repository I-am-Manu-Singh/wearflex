package com.creativeblisslabs.wearflex

import androidx.compose.runtime.Composable

/**
 * Queries the underlying platform hardware configuration to determine if the physical
 * display is circular.
 */
@Composable
expect fun isHardwareScreenRound(): Boolean