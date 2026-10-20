# WearFlex API Reference (`v1.1.0`)

```text
Package: com.creativeblisslabs.wearflex
```
## 1. `ViewportType` (Sealed Interface)

Represents the semantic classification of the active window alongside its real-time dimensions and physical display shape.

```kotlin
@Immutable
sealed interface ViewportType {
    val maxWidth: Dp
    val maxHeight: Dp
    val isRound: Boolean
    val isWearable: Boolean
    val isCircular: Boolean
}
```

| Subtype | Trigger Condition | isRound | isWearable |
| :--- | :--- | :--- | :--- |
| `ViewportType.CircularWear` | width <= wearThresholdMax<br>AND hardware screen is round<br>(or 1:1 preview) | true | true |
| `ViewportType.SquareWear` | width <= wearThresholdMax<br>AND screen is rectangular/square | false | true |
| `ViewportType.CompactPhone` | width < 600.dp (Standard portrait mobile phone) | false | false |
| `ViewportType.MediumTablet` | 600.dp <= width < 840.dp<br>(Foldable, small tablet, compact desktop) | false | false |
| `ViewportType.ExpandedScreen` | width >= 840.dp (Desktop monitor, large tablet, landscape) | false | false |


## 2. AdaptiveScreenScaffold & LocalViewportType
- **LocalViewportType** - A ProvidableCompositionLocal<ViewportType> provided automatically by
  AdaptiveScreenScaffold. Allows any child composable to inspect the active ViewportType without passing parameters down the tree.
```kotlin
val currentViewport = LocalViewportType.current
```

- **AdaptiveScreenScaffold** - Root layout container that measures constraints, queries hardware screen roundness, provides LocalViewportType, and optionally renders a top watch-face time overlay.
```kotlin
@Composable
fun AdaptiveScreenScaffold(
    modifier: Modifier = Modifier,
    wearThresholdMax: Dp = 250.dp,
    simulateRoundInPreview: Boolean = true,
    applyRadialSafePadding: Boolean = false,
    wearTimeOverlay: (@Composable () -> Unit)? = null,
    content: @Composable (ViewportType) -> Unit
)
```

## 3. WearFlexLazyColumn

A pure commonMain alternative to Wear OS's ScalingLazyColumn.

On CircularWear: Smoothly scales down (minBezelScale) and fades (minBezelAlpha) items as they scroll toward the top and bottom curved screen edges.

On Phone/Tablet/Desktop: Renders items at 100% scale and opacity like a standard LazyColumn.
```kotlin
@Composable
fun <T> WearFlexLazyColumn(
    items: List<T>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    viewportType: ViewportType = LocalViewportType.current,
    contentPadding: PaddingValues = rememberWearFlexListPadding(viewportType),
    verticalArrangement: Arrangement.Vertical = ...,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    enableRotaryScroll: Boolean = true,
    minBezelScale: Float = 0.68f,
    minBezelAlpha: Float = 0.35f,
    key: ((index: Int, item: T) -> Any)? = null,
    itemContent: @Composable (index: Int, item: T) -> Unit
)
```

## 4. Adaptive Modifiers & Helpers
- **Modifier.radialSafePadding** - Calculates and applies the exact inscribed square inset ($\approx 14.64\%$ of screen diameter) on CircularWear, 8.dp on SquareWear, and standardPadding (16.dp) on larger screens.

```kotlin
fun Modifier.radialSafePadding(
    viewportType: ViewportType,
    standardPadding: Dp = 16.dp
): Modifier
```

- **Modifier.wearFlexRotaryScroll** - Binds both physical Wear OS rotating crowns/bezels and Desktop mouse scroll wheels to any ScrollableState.

```kotlin
@Composable
    fun Modifier.wearFlexRotaryScroll(
    scrollableState: ScrollableState,
    scrollMultiplier: Float = 1.25f
): Modifier
```

- **Modifier.adaptiveContentWidth** - Prevents UI elements from stretching excessively wide on desktop/tablet screens by capping maximum width (640.dp default), while filling 100% width on phones and watches.
```kotlin
fun Modifier.adaptiveContentWidth(
    viewportType: ViewportType,
    maxExpandedWidth: Dp = 640.dp
): Modifier
```
- **Modifier.wearFlexTouchTarget** - Enforces ergonomic minimum touch target sizes (40.dp on wearables, 48.dp on mobile/desktop).
```kotlin
fun Modifier.wearFlexTouchTarget(viewportType: ViewportType): Modifier
```
- **rememberWearFlexListPadding** - Returns PaddingValues with extra vertical clearance ($22\%$ of
  height) on circular watches so the first and last items in a list can scroll into the center viewing zone.
```kotlin
@Composable
fun rememberWearFlexListPadding(
    viewportType: ViewportType = LocalViewportType.current,
    standardHorizontal: Dp = 16.dp,
    standardVertical: Dp = 12.dp
): PaddingValues
```
## 5. Adaptive UI Components
- **WearFlexButton** - Renders a compact, full-width pill chip (38.dp min height, labelMedium
  text) on smartwatches and a standard Material 3 button (48.dp min height, labelLarge text) on phones and desktops.
```kotlin
@Composable
fun WearFlexButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewportType: ViewportType = LocalViewportType.current,
    leadingIcon: (@Composable () -> Unit)? = null
)
```
- **WearFlexCard** - Renders an elevated card with compact internal padding (12.dp horizontal, 8.dp
  vertical) and 20.dp rounded corners on wearables, or 16.dp padding on phones/desktops.
```kotlin
@Composable
fun WearFlexCard(
    modifier: Modifier = Modifier,
    viewportType: ViewportType = LocalViewportType.current,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
)
```
## 6. Platform Expect/Actual Functions
- **isHardwareScreenRound()** - Queries LocalConfiguration.current.isScreenRound on Android/Wear
  OS and returns false on Desktop JVM and iOS targets.
```kotlin
@Composable
expect fun isHardwareScreenRound(): Boolean
```
## Step 3: Build and Run

1. Sync your Gradle project in Android Studio.

2. Click the green Play/Run button to deploy the app to your target device or emulator.

3. Verify that the viewport correctly identifies your form factor (e.g., ExpandedScreen on phones
   or CircularWear on smartwatches).

