package com.creativeblisslabs.wearflex

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import kotlinx.coroutines.launch

/**
 * Connects both Wear OS physical rotating crown/bezel input and Desktop mouse-wheel scroll
 * input directly to any [ScrollableState] (such as `LazyListState` or `ScrollState`).
 */
@Composable
fun Modifier.wearFlexRotaryScroll(
    scrollableState: ScrollableState,
    scrollMultiplier: Float = 1.25f
): Modifier {
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        runCatching { focusRequester.requestFocus() }
    }

    return this
        .onRotaryScrollEvent { event ->
            coroutineScope.launch {
                scrollableState.scrollBy(event.verticalScrollPixels * scrollMultiplier)
            }
            true
        }
        .pointerInput(scrollableState) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    if (event.type == PointerEventType.Scroll) {
                        val deltaY = event.changes.firstOrNull()?.scrollDelta?.y ?: 0f
                        if (deltaY != 0f) {
                            coroutineScope.launch {
                                scrollableState.scrollBy(deltaY * 45f * scrollMultiplier)
                            }
                        }
                    }
                }
            }
        }
        .focusRequester(focusRequester)
        .focusable()
}