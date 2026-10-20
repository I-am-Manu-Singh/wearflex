package com.creativeblisslabs.wearflex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
 * An adaptive button that renders as a compact, full-width pill chip on Wear OS watches
 * and a standard Material 3 button on phones, tablets, and desktops.
 */
@Composable
fun WearFlexButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewportType: ViewportType = LocalViewportType.current,
    leadingIcon: (@Composable () -> Unit)? = null
) {
    val isWear = viewportType.isWearable
    val contentPadding = if (isWear) {
        PaddingValues(horizontal = 14.dp, vertical = 6.dp)
    } else {
        PaddingValues(horizontal = 24.dp, vertical = 12.dp)
    }

    Button(
        onClick = onClick,
        shape = CircleShape,
        contentPadding = contentPadding,
        modifier = modifier
            .heightIn(min = if (isWear) 38.dp else 48.dp)
            .let { if (isWear) it.fillMaxWidth(0.9f) else it }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leadingIcon != null) {
                leadingIcon()
                Spacer(Modifier.width(if (isWear) 6.dp else 8.dp))
            }
            Text(
                text = text,
                style = if (isWear) MaterialTheme.typography.labelMedium else MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * An adaptive card container that scales its corner radius and internal padding down
 * for circular and square smartwatches so content fits comfortably without clipping.
 */
@Composable
fun WearFlexCard(
    modifier: Modifier = Modifier,
    viewportType: ViewportType = LocalViewportType.current,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val isWear = viewportType.isWearable
    val shape = RoundedCornerShape(if (isWear) 20.dp else 16.dp)
    val innerPadding = if (isWear) {
        PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    } else {
        PaddingValues(16.dp)
    }

    if (onClick != null) {
        Card(
            onClick = onClick,
            shape = shape,
            modifier = modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = if (isWear) 2.dp else 4.dp)
        ) {
            Column(modifier = Modifier.padding(innerPadding), content = content)
        }
    } else {
        Card(
            shape = shape,
            modifier = modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = if (isWear) 2.dp else 4.dp)
        ) {
            Column(modifier = Modifier.padding(innerPadding), content = content)
        }
    }
}