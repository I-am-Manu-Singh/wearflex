# WearFlex: Architecture, Purpose, & FAQs

This document outlines the architectural reasoning behind **WearFlex**, explains why it bridges a critical gap in Compose Multiplatform (CMP), and answers common questions regarding its design.

---

## 1. Why Create WearFlex If Wear OS Apps Are Separate?

Traditionally, Wear OS apps are built as entirely separate modules because a tiny 400x400 circular watch screen behaves fundamentally differently than a 6.8-inch smartphone. However, **real-world wearable apps are rarely isolated islands**. They rely heavily on a companion smartphone app for:
- **Authentication & Setup:** Entering credentials on a watch is cumbersome; companion phone apps handle initial onboarding.
- **Data Synchronization:** Workouts, media playlists, and user preferences sync continuously via companion data layers.
- **Unified Design Systems:** Modern cross-platform apps aim to maintain a cohesive brand and UI token system across phone, watch, desktop, and tablet targets without duplicating layout logic.

WearFlex bridges this gap by treating a wearable screen not as an alien codebase, but as a specialized **Viewport (`ViewportType.CircularWear`)** that can coexist seamlessly alongside mobile and desktop targets.

---

## 2. Why Do We Need WearFlex If KMP & CMP Already Exist?

Compose Multiplatform gives you the *ability* to render UI across platforms, but it **does not** provide form-factor geometry awareness out of the box.

- **The Problem with Raw CMP:** If you write a standard CMP layout and run it on a circular watch, your UI elements will get clipped at the corners, touch targets will be too small for curved surfaces, and padding calculations will break.
- **What WearFlex Solves:** Instead of writing manual screen-width checks, clipping math, and touch-target padding in every composable, `AdaptiveScreenScaffold` and `adaptiveTouchTarget()` handle geometry abstraction declaratively.

---

## 3. Frequently Asked Questions (FAQs)

### Q1: Is WearFlex only for Wear OS apps?
**No.** While it includes specialized utilities for circular wearable displays (`CircularWear`), WearFlex is a full multiplatform layout library supporting **Android phones, Wear OS watches, Desktop (JVM), and iOS** targets from a single `commonMain` source set.

### Q2: How is this different from Google's official `androidx.wear.compose` library?
Google’s `androidx.wear.compose` is native to Android and relies strictly on Android Jetpack. **WearFlex** is built on **Compose Multiplatform**, allowing you to share wearable-inspired adaptive UI logic across desktop and iOS targets where official Android libraries cannot compile.

### Q3: Why not just use `BoxWithConstraints` everywhere?
`BoxWithConstraints` provides raw pixel dimensions, but it forces repetitive conditional logic (`if (maxWidth < 300.dp)`) across every screen. WearFlex encapsulates these rules into semantic enums (`ViewportType.CircularWear` vs `ViewportType.ExpandedScreen`) and ergonomic modifiers.

### Q4: Can I use WearFlex in a traditional Android-only project?
Yes! You can consume the `:wearflex` shared library inside a standard Android application module without converting your entire app into a multiplatform project.