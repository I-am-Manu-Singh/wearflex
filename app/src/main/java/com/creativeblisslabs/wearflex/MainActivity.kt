package com.creativeblisslabs.wearflex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AdaptiveScreenScaffold { viewport ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .adaptiveTouchTarget(isWearable = viewport == ViewportType.CircularWear),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "WearFlex Active!\nViewport: $viewport")
                        }
                    }
                }
            }
        }
    }
}