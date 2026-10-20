# Getting Started with WearFlex (`v1.1.0`)

### Configure Repositories -

1. Open your root `settings.gradle.kts`& `build.gradle.kts` and ensure JitPack is listed in 
`dependencyResolutionManagement`& `maven-publish` is added in plugins:
  
```kotlin
//build.gradle.kts
plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.library)
    `maven-publish`
}
```

```kotlin
//settings.gradle.kts
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("[https://jitpack.io](https://jitpack.io)") }
    }
}
```
2. Add the Dependency -

In your shared KMP module (shared/build.gradle.kts or composeApp/build.gradle.kts), add WearFlex to commonMain:

```kotlin
//build.gradle.kts
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("com.github.I-am-Manu-Singh.wearflex:wearflex:1.1.0")
        }
    }
}
```


3. Testing Demo - Add in your Activity
```kotlin
package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.creativeblisslabs.wearflex.AdaptiveScreenScaffold
import com.creativeblisslabs.wearflex.ViewportType
import com.creativeblisslabs.wearflex.WearFlexButton
import com.creativeblisslabs.wearflex.WearFlexCard
import com.creativeblisslabs.wearflex.WearFlexLazyColumn
import com.creativeblisslabs.wearflex.adaptiveContentWidth

@Composable
@Preview
fun App() {
    MaterialTheme {
        AdaptiveScreenScaffold(
            wearTimeOverlay = {
                Text("10:09", style = MaterialTheme.typography.labelSmall)
            }
        ) { viewportType ->
            val sampleItems = remember {
                listOf(
                    "Viewport: ${viewportType::class.simpleName}",
                    "Size: ${viewportType.maxWidth} x ${viewportType.maxHeight}",
                    "Round Screen: ${viewportType.isRound}",
                    "Bezel Scaling Active",
                    "Rotary Scroll Ready"
                )
            }

            WearFlexLazyColumn(
                items = sampleItems,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .adaptiveContentWidth(viewportType)
            ) { index, item ->
                if (index == 0) {
                    WearFlexButton(
                        text = item,
                        onClick = {}
                    )
                } else {
                    WearFlexCard {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
```
### Upgrading from v1.0.0 to v1.1.0 -

In v1.1.0, ViewportType was upgraded from a basic enum class to a data-carrying sealed interface so you can read maxWidth, maxHeight, and isRound directly from any ViewportType instance.

- In v1.0.0 (Enum comparison):
```kotlin
when (viewportType) {
    ViewportType.CircularWear -> ...
}
```
- In v1.1.0 (Sealed Interface type check or helper booleans):

```kotlin
when (viewportType) {
    is ViewportType.CircularWear -> Text("Round Watch: ${viewportType.maxWidth}")
    is ViewportType.SquareWear -> Text("Square Watch")
    is ViewportType.CompactPhone -> Text("Phone")
    is ViewportType.MediumTablet -> Text("Tablet / Foldable")
    is ViewportType.ExpandedScreen -> Text("Desktop (${viewportType.maxWidth})")
}
// Or use convenient shorthand properties anywhere:
    if (viewportType.isWearable) { ... }
    if (viewportType.isCircular) { ... }
```
### Wearflex  1.1.0 vs 1.0.0

```text
1. This update transforms wearflex into a complete multiplatform wearable & adaptive toolkit with:
2. True Hardware Round-Screen Detection (expect/actual): Uses Android's native Configuration.
3. isScreenRound API on Android/Wear OS and cleanly falls back on Desktop & iOS.
4. iOS Multiplatform Support: Adds iosX64(), iosArm64(), and iosSimulatorArm64() targets so 
5. consuming KMP apps never hit variant mismatch errors.
6. WearFlexLazyColumn (commonMain ScalingLazyColumn): Automatically scales down and fades items near the top and bottom curved bezels on circular watches, while behaving like a normal LazyColumn on phones, tablets, and desktops.
7. Unified Rotary & Mouse-Wheel Input (Modifier.wearFlexRotaryScroll): Connects physical watch rotating crowns/bezels and desktop mouse wheels to any ScrollableState with a single modifier.
8. Adaptive Ergonomic Components (WearFlexButton & WearFlexCard): Automatically adjusts heights, corner radii, typography, and radial inset padding based on the active ViewportType.
```