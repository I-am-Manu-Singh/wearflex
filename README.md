# WearFlex 🚀

**WearFlex** is a Kotlin Multiplatform & Compose Multiplatform UI library designed to solve form-factor fragmentation between circular smartwatches (Wear OS) and expanded screens (phones, foldables, and tablets).



## Features

- **Adaptive Viewports (`ViewportType`):** Automatically detects screen geometry (`CircularWear`, `RectangularCompact`, `ExpandedScreen`)[cite: 7].
- **Write Once, Run Everywhere:** Share layouts seamlessly across Android, Desktop, and iOS targets using Compose Multiplatform.
- **Ergonomic Touch Targets:** Built-in modifiers designed to handle curved wearable screens and constrained touch areas.



## Project Structure
```
wearflex/
├── build.gradle.kts
└── src/
├── commonMain/kotlin/com/creativeblisslabs/wearflex/
│   ├── ViewportType.kt
│   ├── ScreenShapeDetector.kt
│   ├── CompactModifiers.kt
│   ├── AdaptiveScaffold.kt
│   ├── WearFlexLazyColumn.kt
│   ├── WearFlexRotaryScroll.kt
│   └── WearFlexComponents.kt
├── androidMain/kotlin/com/creativeblisslabs/wearflex/
│   └── ScreenShapeDetector.android.kt
├── desktopMain/kotlin/com/creativeblisslabs/wearflex/
│   └── ScreenShapeDetector.desktop.kt
└── iosMain/kotlin/com/creativeblisslabs/wearflex/
└── ScreenShapeDetector.ios.kt
```
## Installation (via JitPack)

### Step 1: Add the JitPack repository
In your root **`settings.gradle.kts`**:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("[https://jitpack.io](https://jitpack.io)") }
    }
}
```

## Step 2: Add the dependency

In your shared module's build.gradle.kts (commonMain):

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("com.github.I-am-Manu-Singh.wearflex:wearflex:1.1.0")
        }
    }
}
```


## Quick Start

Wrap your root screen in AdaptiveScreenScaffold and use WearFlexLazyColumn, WearFlexButton, and WearFlexCard to automatically adapt across watch faces, phones, and desktop windows:
```kotlin
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.creativeblisslabs.wearflex.*

@Composable
fun MyMultiplatformScreen() {
    AdaptiveScreenScaffold(
        wearTimeOverlay = {
            Text("10:09", style = MaterialTheme.typography.labelSmall)
        }
    ) { viewportType ->
        val items = listOf(
            "Mode: ${viewportType::class.simpleName}",
            "Bounds: ${viewportType.maxWidth} x ${viewportType.maxHeight}",
            "Circular Display: ${viewportType.isRound}",
            "Curved Edge Scaling",
            "Crown & Wheel Scroll"
        )

        WearFlexLazyColumn(items = items) { index, title ->
            if (index == 0) {
                WearFlexButton(
                    text = title,
                    onClick = { /* Handle click */ }
                )
            } else {
                WearFlexCard {
                    Text(text = title)
                }
            }
        }
    }
}
```

**Core Geometry**: The Inscribed Square Formula -
To prevent rectangular UI content from clipping against a circular smartwatch bezel of diameter $D$, WearFlex automatically calculates the inscribed safe square inset:

```html
Inset = [D × (1 - √2 / 2)] / 2 ≈ 0.1464 × D

```
- Apply it anywhere with a single modifier:

```kotlin
Box(modifier = Modifier.radialSafePadding(LocalViewportType.current)) {
    // Guaranteed never to clip on round watch faces!
}
```



## Multiplatform Target Support

- ✅ Android (androidMain)

- ✅ Desktop / JVM (desktopMain)

- ✅ iOS (iosMain)

- 🌐 Common Code (commonMain)

## License

Released under the MIT License. Created by Manu Singh.