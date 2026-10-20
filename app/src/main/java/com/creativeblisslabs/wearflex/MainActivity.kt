package com.example.myapplication

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

// Import WearFlex components
import com.creativeblisslabs.wearflex.AdaptiveScreenScaffold
import com.creativeblisslabs.wearflex.ViewportType
import com.creativeblisslabs.wearflex.wearFlexTouchTarget

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
                                .wearFlexTouchTarget(viewport),
                            contentAlignment = Alignment.Center
                        ) {
                            val modeDescription = when (viewport) {
                                is ViewportType.CircularWear -> "Circular Watch"
                                is ViewportType.SquareWear -> "Square Watch"
                                is ViewportType.CompactPhone -> "Phone"
                                is ViewportType.MediumTablet -> "Tablet"
                                is ViewportType.ExpandedScreen -> "Desktop"
                            }
                            Text(text = "WearFlex Active!\nViewport: $modeDescription")
                        }
                    }
                }
            }
        }
    }
}