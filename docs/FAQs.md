# Architecture, Purpose, & FAQs

## WearFlex: Beyond the Basic Width Check

If your library only checks `maxWidth < 250.dp`, no developer would add an external dependency just to save three lines of `BoxWithConstraints` code.

However, that basic width check is merely the entry hook. The real value of **WearFlex** is eliminating the repetitive math, platform quirks, and boilerplate required when sharing UI between a tiny circular watch, a phone, and a desktop window inside `commonMain`.

---

## 1. Why Create WearFlex If Wear OS Apps Are Separate Modules?

Traditionally, Wear OS apps are built as separate modules because a tiny 400x400 circular watch screen behaves differently than a 6.8-inch smartphone. However, real-world wearable apps are rarely isolated islands; they rely heavily on companion apps for:
* **Authentication & Setup:** Handling initial onboarding on a phone rather than typing credentials on a watch.
* **Data Synchronization:** Continuously syncing workouts, preferences, and media playlists.
* **Unified Design Systems:** Maintaining a cohesive brand and UI token system across devices without duplicating layout logic.

WearFlex bridges this gap by treating a wearable screen not as an alien codebase, but as a specialized **Viewport (`ViewportType.CircularWear`)** that coexists seamlessly alongside mobile and desktop targets.

---

## 2. Why Do We Need WearFlex If KMP & CMP Already Exist?

Compose Multiplatform gives you the ability to render UI across platforms, but it **does not** provide form-factor geometry awareness out of the box. WearFlex solves three critical gaps:

### A. The "Circle inside a Square" Geometry Problem (`CompactModifiers`)
A raw width check doesn't stop your UI from getting clipped by a round smartwatch bezel.
* To fit a rectangular layout inside a circular screen of diameter $D$ without corners getting cut off, the safe inscribed square requires an inset padding calculated as:
  $$\text{Inset} = \frac{D \times (1 - \frac{\sqrt{2}}{2})}{2} \approx 0.1464 \times D$$
* WearFlex automates this via `AdaptiveScreenScaffold` and `CompactModifiers`, delivering radial safe-zones out of the box.

### B. Google's Wear Compose Libraries Are NOT Multiplatform
* Google's official `androidx.wear.compose` libraries are strictly Android-only and cannot be imported in `commonMain` if you target Desktop or iOS.
* Standard `WindowSizeClass` groups a 412dp Pixel phone and a 192dp Galaxy Watch into the exact same `COMPACT` tier. WearFlex bridges this gap by introducing a wearable-aware tier.

### C. Centralizing Cross-Device Ergonomics
WearFlex replaces messy, repetitive conditional logic (`if (maxWidth < 225.dp)`) scattered across screens with clean, declarative multiplatform components.

---

## 4. Frequently Asked Questions (FAQs)

### Q1: If Wear OS, mobile, iOS, and desktop generate separate APK/build releases anyway, why do we need WearFlex to share UI code?
Even when packaging separate release artifacts (e.g., a Wear OS APK and an iOS IPA), duplicating your UI screens, padding math, and layout logic across separate codebases creates a massive maintenance overhead. Compose Multiplatform (CMP) lets you share up to 90%+ of your code in `commonMain`. WearFlex provides the missing geometry layer so a single screen definition can automatically adapt its shape, padding, and touch targets to a watch, phone, or desktop without rewriting views per platform.

### Q2: Is WearFlex only for Wear OS apps?
**No.** While it includes specialized utilities for circular wearable displays (`CircularWear`), WearFlex is a full multiplatform layout library supporting **Android phones, Wear OS watches, Desktop (JVM), and iOS** targets from a single `commonMain` source set.

### Q3: How is this different from Google's official `androidx.wear.compose` library?
Google’s library is native to Android and relies on Android Jetpack. **WearFlex** is built on **Compose Multiplatform**, enabling you to share wearable-inspired adaptive UI logic across desktop and iOS targets where official Android libraries cannot compile.

### Q4: Why not just use `BoxWithConstraints` everywhere?
`BoxWithConstraints` provides raw dimensions, but forces repetitive conditional logic across every screen. WearFlex encapsulates these rules into semantic enums (`ViewportType.CircularWear` vs `ViewportType.ExpandedScreen`) and ergonomic modifiers.

### Q5: Can I use WearFlex in a traditional Android-only project?
**Yes!** You can consume the `:wearflex` shared library inside a standard Android application module without converting your entire app into a multiplatform project.