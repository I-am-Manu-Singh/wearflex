# WearFlex Architecture (`v1.1.0`)
WearFlex uses Kotlin's default hierarchy template (`applyDefaultHierarchyTemplate()`) to compile cleanly across all major Compose Multiplatform targets without variant mismatches:


## Multiplatform Source Set Structure

```text
commonMain
 ├── androidMain (Android Phones, Tablets, Foldables, Wear OS Watches)
 ├── desktopMain (macOS, Windows, Linux JVM)
 └── iosMain
      ├── iosX64
      ├── iosArm64
      └── iosSimulatorArm64 iOS target hooks and multiplatform interop support.