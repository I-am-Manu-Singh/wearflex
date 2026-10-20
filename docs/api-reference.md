# API Reference

Detailed reference documentation for WearFlex core classes, scaffolds, and UI modifiers.

---

## 1. `ViewportType`
An enum representing the detected screen geometry and form factor.

- **Values:**
    - `CircularWear`: Represents circular smartwatch displays (e.g., Wear OS).
    - `RectangularCompact`: Represents square or rectangular wearable screens.
    - `ExpandedScreen`: Represents standard phones, tablets, foldables, and desktop windows[cite: 7].

---

## 2. `AdaptiveScreenScaffold`

A primary layout container that automatically evaluates the screen bounds and exposes the active `ViewportType` to its content lambda.

### Signature
```kotlin
@Composable
fun AdaptiveScreenScaffold(
    content: @Composable (viewport: ViewportType) -> Unit
)
```
## 3. Usage Example

```kotlin
AdaptiveScreenScaffold { viewport ->
    // Build your adaptive UI here based on the viewport
}
```

## 4. Modifier.adaptiveTouchTarget()
A custom UI modifier that adjusts touch target sizes and padding depending on whether the target platform is a wearable device.

Signature
```kotlin
fun Modifier.adaptiveTouchTarget(isWearable: Boolean): Modifier
```

Parameters
- isWearable: Boolean flag indicating if the component resides on a constrained or circular 
wearable display.