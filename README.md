# WearFlex 🚀

**WearFlex** is a Kotlin Multiplatform & Compose Multiplatform UI library designed to solve form-factor fragmentation between circular smartwatches (Wear OS) and expanded screens (phones, foldables, and tablets).

---

## Features

- **Adaptive Viewports (`ViewportType`):** Automatically detects screen geometry (`CircularWear`, `RectangularCompact`, `ExpandedScreen`)[cite: 7].
- **Write Once, Run Everywhere:** Share layouts seamlessly across Android, Desktop, and iOS targets using Compose Multiplatform.
- **Ergonomic Touch Targets:** Built-in modifiers designed to handle curved wearable screens and constrained touch areas.

---

## Installation

Add the local project dependency to your app module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation(project(":wearflex"))
}
```

## Quick Start

Wrap your application content with AdaptiveScreenScaffold to adapt your UI dynamically based on the current device viewport:

```kotlin
AdaptiveScreenScaffold { viewport ->
    Box(
    modifier = Modifier.fillMaxSize()
        .adaptiveTouchTarget(isWearable = viewport == ViewportType.CircularWear),
        contentAlignment = Alignment.Center
    ) {
    Text(text = "WearFlex Active!\nViewport: $viewport")
    }
}
```

## Multiplatform Target Support

- ✅ Android (androidMain)

- ✅ Desktop / JVM (desktopMain)

- ✅ iOS (iosMain)

- 🌐 Common Code (commonMain)

## License
Copyright © 2026 Creative Bliss Labs. All rights reserved.