# Architecture Overview

WearFlex evaluates screen geometry in `commonMain` to eliminate layout guesswork across form factors.

---

## Viewport Classification (`ViewportType`)

1. **`CircularWear`**: Tailored for circular Wear OS displays, applying specialized padding, touch adjustments, and circular clipping bounds.
2. **`RectangularCompact`**: Designed for small square or rectangular wearable screens with constrained bounds.
3. **`ExpandedScreen`**: Optimized for standard smartphones, foldables, tablets, and desktop windows.

---

## Multiplatform Source Set Structure

- **`commonMain`**: Houses core adaptive scaffolds, viewport classification logic, shared composables, and universal UI modifiers.
- **`androidMain`**: Manages Android-specific context hooks, device integrations, and platform entry points.
- **`desktopMain`**: Tailored for Compose for Desktop optimizations and window bounds handling.
- **`iosMain`**: Provides iOS target hooks and multiplatform interop support.