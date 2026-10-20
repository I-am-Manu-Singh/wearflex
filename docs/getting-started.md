# Getting Started with WearFlex

Follow this guide to integrate **WearFlex** into your multiplatform project and start building adaptive UIs for both smartwatches and expanded screens.

---

## Step 1: Add the Module Dependency

In your consuming application's `app/build.gradle.kts` file, link the shared local `:wearflex` library module:

```kotlin
dependencies {
    implementation(project(":wearflex"))
}
```

## Step 2: Initialize the Adaptive Scaffold

Inside your Android ComponentActivity or shared Compose entry point, use AdaptiveScreenScaffold 
to wrap your user interface. It will automatically detect screen geometry and expose the active viewport state:

```
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
import com.creativeblisslabs.wearflex.AdaptiveScreenScaffold
import com.creativeblisslabs.wearflex.ViewportType

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
```
## Step 3: Build and Run

1. Sync your Gradle project in Android Studio.

2. Click the green Play/Run button to deploy the app to your target device or emulator.

3. Verify that the viewport correctly identifies your form factor (e.g., ExpandedScreen on phones 
or CircularWear on smartwatches).