package com.creativeblisslabs.wearflex

/**
 * Represents the layout posture and physical constraints of the host viewport.
 */
enum class ViewportType {
    /// Circular watch displays (e.g., Pixel Watch, Galaxy Watch)
    CircularWear,
    /// Square wearables or highly constrained compact window bounds
    RectangularCompact,
    /// Standard mobile, tablet, or desktop viewports
    ExpandedScreen
}