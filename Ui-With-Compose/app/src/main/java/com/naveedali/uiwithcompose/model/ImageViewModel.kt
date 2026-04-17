package com.naveedali.uiwithcompose.model

// ─────────────────────────────────────────────────────────────────────────────
// ImageViewDemoType
//
// Tags every image-loading pattern demonstrated in ImageViewsScreen.
//
// Grouped into three areas:
//   LOCAL  — images sourced from drawable resources already in the APK
//   FILTER — visual transformations applied to a local image
//   NETWORK — images loaded from the internet via Coil's AsyncImage
// ─────────────────────────────────────────────────────────────────────────────
enum class ImageViewDemoType {

    // ── Local resource images ─────────────────────────────────────────────────
    LOCAL_VECTOR,           // Image(painter = painterResource(R.drawable.xxx))
    CONTENT_SCALE,          // ContentScale: Crop, Fit, FillBounds, FillWidth, None, Inside
    CLIP_SHAPES,            // clip(CircleShape) / RoundedCornerShape / CutCornerShape
    WITH_BORDER,            // Modifier.border(...) — avatar-style ring

    // ── Color filters ─────────────────────────────────────────────────────────
    COLOR_FILTER_TINT,      // ColorFilter.tint(color, BlendMode)
    COLOR_FILTER_GRAYSCALE, // ColorMatrix — setToSaturation(0f)
    COLOR_FILTER_SEPIA,     // ColorMatrix — manual sepia coefficients

    // ── Aspect ratio ─────────────────────────────────────────────────────────
    ASPECT_RATIO,           // Modifier.aspectRatio(16f / 9f)

    // ── Box overlay ──────────────────────────────────────────────────────────
    OVERLAY,                // Box { Image + gradient scrim + Text on top }

    // ── Network (Coil AsyncImage) ─────────────────────────────────────────────
    ASYNC_BASIC,            // AsyncImage(model = url)
    ASYNC_PLACEHOLDER,      // placeholder = painterResource(...)
    ASYNC_ERROR,            // error = painterResource(...) with a broken URL
    ASYNC_CROSSFADE,        // ImageRequest with crossfade(durationMs)
    ASYNC_TRANSFORMATION    // ImageRequest with CircleCropTransformation
}

// ─────────────────────────────────────────────────────────────────────────────
// ImageViewDemo
//
// A pure data holder that describes one image demo card.
//   type        → drives which composable variant is rendered
//   title       → card header
//   description → one-line explanation of the concept shown
// ─────────────────────────────────────────────────────────────────────────────
data class ImageViewDemo(
    val type: ImageViewDemoType,
    val title: String,
    val description: String
)

// ─────────────────────────────────────────────────────────────────────────────
// imageViewDemos
//
// Ordered catalogue. Add or reorder entries here — the screen updates automatically.
// ─────────────────────────────────────────────────────────────────────────────
val imageViewDemos: List<ImageViewDemo> = listOf(

    ImageViewDemo(
        type        = ImageViewDemoType.LOCAL_VECTOR,
        title       = "Local Vector Drawable",
        description = "Image(painter = painterResource(R.drawable.xxx)) — renders an XML vector."
    ),
    ImageViewDemo(
        type        = ImageViewDemoType.CONTENT_SCALE,
        title       = "ContentScale Variants",
        description = "Controls how the image is scaled to fill its bounds. Shows all 7 modes."
    ),
    ImageViewDemo(
        type        = ImageViewDemoType.CLIP_SHAPES,
        title       = "Shape Clipping",
        description = "Modifier.clip() with CircleShape, RoundedCornerShape, and CutCornerShape."
    ),
    ImageViewDemo(
        type        = ImageViewDemoType.WITH_BORDER,
        title       = "Image with Border",
        description = "Modifier.border() draws a stroke ring — combine with clip() for avatars."
    ),
    ImageViewDemo(
        type        = ImageViewDemoType.COLOR_FILTER_TINT,
        title       = "Color Filter — Tint",
        description = "ColorFilter.tint(color, BlendMode) overlays a colour using a blend mode."
    ),
    ImageViewDemo(
        type        = ImageViewDemoType.COLOR_FILTER_GRAYSCALE,
        title       = "Color Filter — Grayscale",
        description = "ColorMatrix().setToSaturation(0f) removes all colour from the image."
    ),
    ImageViewDemo(
        type        = ImageViewDemoType.COLOR_FILTER_SEPIA,
        title       = "Color Filter — Sepia",
        description = "Custom ColorMatrix coefficients produce a warm sepia-tone effect."
    ),
    ImageViewDemo(
        type        = ImageViewDemoType.ASPECT_RATIO,
        title       = "Aspect Ratio",
        description = "Modifier.aspectRatio(16f/9f) constrains height relative to the width."
    ),
    ImageViewDemo(
        type        = ImageViewDemoType.OVERLAY,
        title       = "Image with Overlay",
        description = "Box stacks a gradient scrim + Text on top of the image — common card pattern."
    ),
    ImageViewDemo(
        type        = ImageViewDemoType.ASYNC_BASIC,
        title       = "AsyncImage — Basic",
        description = "Coil's AsyncImage loads a URL on a background thread, then renders it."
    ),
    ImageViewDemo(
        type        = ImageViewDemoType.ASYNC_PLACEHOLDER,
        title       = "AsyncImage — Placeholder",
        description = "placeholder param shows a drawable while the network request is in flight."
    ),
    ImageViewDemo(
        type        = ImageViewDemoType.ASYNC_ERROR,
        title       = "AsyncImage — Error Fallback",
        description = "error param shows a fallback drawable when the URL fails or is invalid."
    ),
    ImageViewDemo(
        type        = ImageViewDemoType.ASYNC_CROSSFADE,
        title       = "AsyncImage — Crossfade",
        description = "ImageRequest.crossfade(ms) animates the image in after loading completes."
    ),
    ImageViewDemo(
        type        = ImageViewDemoType.ASYNC_TRANSFORMATION,
        title       = "AsyncImage — Circle Crop Transform",
        description = "CircleCropTransformation applied server-side via Coil's transformation API."
    )
)
