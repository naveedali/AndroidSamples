package com.naveedali.uiwithcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.naveedali.uiwithcompose.R
import com.naveedali.uiwithcompose.model.ImageViewDemo
import com.naveedali.uiwithcompose.model.ImageViewDemoType
import com.naveedali.uiwithcompose.model.imageViewDemos
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme

// ─────────────────────────────────────────────────────────────────────────────
// Network image URLs used throughout this screen.
// picsum.photos is a free placeholder image service — seeded URLs always
// return the same image, making demos deterministic across runs.
// ─────────────────────────────────────────────────────────────────────────────
private object DemoUrls {
    const val LANDSCAPE  = "https://picsum.photos/seed/compose/600/400"
    const val PORTRAIT   = "https://picsum.photos/seed/android/400/600"
    const val SQUARE     = "https://picsum.photos/seed/kotlin/400/400"
    const val CROSSFADE  = "https://picsum.photos/seed/jetpack/600/400"
    const val TRANSFORM  = "https://picsum.photos/seed/material/400/400"
    const val BROKEN_URL = "https://invalid.example.com/no-image.png"  // intentionally bad
}

// ─────────────────────────────────────────────────────────────────────────────
// ImageViewsScreen
//
// Entry-point composable for the Image Views practice screen.
// Renders a LazyColumn of demo cards, one per ImageViewDemoType.
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageViewsScreen(onBack: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Image Views") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(Modifier.height(4.dp)) }

            itemsIndexed(
                items = imageViewDemos,
                key   = { _, demo -> demo.type.name }
            ) { index, demo ->
                ImageViewDemoCard(index = index + 1, demo = demo)
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ImageViewDemoCard
//
// Container card for a single demo. Dispatches to the matching variant
// composable based on demo.type via a `when` expression.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun ImageViewDemoCard(index: Int, demo: ImageViewDemo) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // ── Header ────────────────────────────────────────────────────────
            Row(
                verticalAlignment    = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text  = "%02d".format(index),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text  = demo.title,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Text(
                text  = demo.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // ── Live demo ─────────────────────────────────────────────────────
            when (demo.type) {
                ImageViewDemoType.LOCAL_VECTOR         -> LocalVectorDemo()
                ImageViewDemoType.CONTENT_SCALE        -> ContentScaleDemo()
                ImageViewDemoType.CLIP_SHAPES          -> ClipShapesDemo()
                ImageViewDemoType.WITH_BORDER          -> WithBorderDemo()
                ImageViewDemoType.COLOR_FILTER_TINT    -> ColorFilterTintDemo()
                ImageViewDemoType.COLOR_FILTER_GRAYSCALE -> ColorFilterGrayscaleDemo()
                ImageViewDemoType.COLOR_FILTER_SEPIA   -> ColorFilterSepiaDemo()
                ImageViewDemoType.ASPECT_RATIO         -> AspectRatioDemo()
                ImageViewDemoType.OVERLAY              -> OverlayDemo()
                ImageViewDemoType.ASYNC_BASIC          -> AsyncBasicDemo()
                ImageViewDemoType.ASYNC_PLACEHOLDER    -> AsyncPlaceholderDemo()
                ImageViewDemoType.ASYNC_ERROR          -> AsyncErrorDemo()
                ImageViewDemoType.ASYNC_CROSSFADE      -> AsyncCrossfadeDemo()
                ImageViewDemoType.ASYNC_TRANSFORMATION -> AsyncTransformationDemo()
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Local resource images ─────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 01 · Local Vector Drawable ────────────────────────────────────────────────
// painterResource() converts any drawable XML (including vector drawables)
// into a Painter that the Image composable can render.
//
// contentDescription is the accessibility label read by screen readers.
// Set it to null for purely decorative images.
@Composable
private fun LocalVectorDemo() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter            = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "App icon foreground — vector drawable",
            modifier           = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Image(
            painter            = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "App icon background — vector drawable",
            modifier           = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
        )
    }
}

// ── 02 · ContentScale ─────────────────────────────────────────────────────────
// ContentScale tells Compose how to scale the image to fill its layout bounds.
//
//   Crop        — scales UNIFORMLY to FILL the bounds; crops the overflow
//   Fit         — scales UNIFORMLY to FIT entirely inside the bounds (may letterbox)
//   FillBounds  — stretches NON-uniformly to fill exactly (may distort)
//   FillWidth   — scales to fill width; height may overflow or letterbox
//   FillHeight  — scales to fill height; width may overflow or letterbox
//   None        — no scaling — image rendered at its intrinsic pixel size
//   Inside      — like Fit but never upscales beyond intrinsic size
@Composable
private fun ContentScaleDemo() {
    val scales = listOf(
        "Crop"        to ContentScale.Crop,
        "Fit"         to ContentScale.Fit,
        "FillBounds"  to ContentScale.FillBounds,
        "FillWidth"   to ContentScale.FillWidth,
        "FillHeight"  to ContentScale.FillHeight,
        "None"        to ContentScale.None,
        "Inside"      to ContentScale.Inside
    )

    // LazyRow inside a LazyColumn is fine when the inner list is short and
    // the outer list items are cards. Avoid it when both lists are very long.
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(scales.size) { i ->
            val (label, scale) = scales[i]
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model              = DemoUrls.LANDSCAPE,
                    contentDescription = label,
                    contentScale       = scale,
                    modifier           = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text      = label,
                    fontSize  = 10.sp,
                    textAlign = TextAlign.Center,
                    color     = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// ── 03 · Shape Clipping ───────────────────────────────────────────────────────
// Modifier.clip(shape) masks the image to any Shape.
//   CircleShape          → perfect circle (great for avatars)
//   RoundedCornerShape   → rounded rectangle (card style)
//   CutCornerShape       → chamfered / diamond cut corners
//
// clip() must come BEFORE size/border modifiers that should be inside the clip,
// but AFTER size modifiers that define the bounds to clip against.
@Composable
private fun ClipShapesDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        ClipSample(label = "Circle",         shape = CircleShape)
        ClipSample(label = "Rounded\n16.dp", shape = RoundedCornerShape(16.dp))
        ClipSample(label = "CutCorner\n12dp",shape = CutCornerShape(12.dp))
    }
}

@Composable
private fun ClipSample(label: String, shape: androidx.compose.ui.graphics.Shape) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model              = DemoUrls.SQUARE,
            contentDescription = label,
            contentScale       = ContentScale.Crop,
            modifier           = Modifier
                .size(80.dp)
                .clip(shape)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text      = label,
            fontSize  = 10.sp,
            textAlign = TextAlign.Center,
            color     = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ── 04 · Image with Border ────────────────────────────────────────────────────
// Modifier.border() draws a stroke around the composable's bounds.
// Order matters: clip() first, then border() — so the ring follows the shape.
//
// Pattern for a coloured avatar ring:
//   Modifier.size(x).clip(CircleShape).border(width, color, CircleShape)
@Composable
private fun WithBorderDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        // Single-colour border
        AsyncImage(
            model              = DemoUrls.SQUARE,
            contentDescription = "Avatar with simple border",
            contentScale       = ContentScale.Crop,
            modifier           = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        // Gradient border (via a Brush)
        AsyncImage(
            model              = DemoUrls.SQUARE,
            contentDescription = "Avatar with gradient border",
            contentScale       = ContentScale.Crop,
            modifier           = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(
                    width  = 3.dp,
                    brush  = Brush.sweepGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary,
                            MaterialTheme.colorScheme.secondary
                        )
                    ),
                    shape  = CircleShape
                )
        )
        // Rounded rectangle border
        AsyncImage(
            model              = DemoUrls.SQUARE,
            contentDescription = "Image with rounded border",
            contentScale       = ContentScale.Crop,
            modifier           = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(16.dp))
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Color filters ─────────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 05 · Color Filter — Tint ──────────────────────────────────────────────────
// ColorFilter.tint(color, BlendMode) composites a solid colour over the image.
// BlendMode controls the mathematical operation:
//   Color     — replaces hue/saturation, keeps luminance (recolour effect)
//   Multiply  — darkens — useful for tinted overlays
//   SrcIn     — replaces all pixels with the tint where image is opaque
//   Screen    — lightens — like adding light
@Composable
private fun ColorFilterTintDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf(
            Triple("Original",                null,
                                              "—"),
            Triple("Tint\nBlendMode.Color",   ColorFilter.tint(Color(0xFF6650A4), BlendMode.Color),
                                              "Color"),
            Triple("Tint\nBlendMode.Multiply", ColorFilter.tint(Color(0xFFFF5722), BlendMode.Multiply),
                                              "Multiply")
        ).forEach { (label, filter, _) ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model              = DemoUrls.LANDSCAPE,
                    contentDescription = label,
                    contentScale       = ContentScale.Crop,
                    colorFilter        = filter,
                    modifier           = Modifier
                        .width(100.dp)
                        .height(70.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text      = label,
                    fontSize  = 9.sp,
                    textAlign = TextAlign.Center,
                    color     = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// ── 06 · Color Filter — Grayscale ─────────────────────────────────────────────
// ColorMatrix is a 4×5 matrix applied to [R, G, B, A] values of each pixel.
// setToSaturation(0f) produces a pure greyscale by mixing RGB channels evenly.
// setToSaturation(1f) = original; values > 1 = hyper-saturated.
@Composable
private fun ColorFilterGrayscaleDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        listOf(
            "Original (sat=1)"  to 1f,
            "Greyscale (sat=0)" to 0f,
            "Desaturated (0.3)" to 0.3f
        ).forEach { (label, saturation) ->
            Column(
                modifier              = Modifier.weight(1f),
                horizontalAlignment   = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model              = DemoUrls.LANDSCAPE,
                    contentDescription = label,
                    contentScale       = ContentScale.Crop,
                    colorFilter        = ColorFilter.colorMatrix(
                        ColorMatrix().apply { setToSaturation(saturation) }
                    ),
                    modifier           = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text      = label,
                    fontSize  = 9.sp,
                    textAlign = TextAlign.Center,
                    color     = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// ── 07 · Color Filter — Sepia ─────────────────────────────────────────────────
// Sepia is achieved with a custom ColorMatrix whose coefficients blend
// greyscale values into warm amber tones.
// The matrix layout: [R', G', B', A'] = M * [R, G, B, A, 1]
@Composable
private fun ColorFilterSepiaDemo() {
    val sepiaMatrix = ColorMatrix(
        floatArrayOf(
            // Each row = output channel contribution from [R, G, B, A, bias]
            0.393f, 0.769f, 0.189f, 0f, 0f,   // R'
            0.349f, 0.686f, 0.168f, 0f, 0f,   // G'
            0.272f, 0.534f, 0.131f, 0f, 0f,   // B'
            0f,     0f,     0f,     1f, 0f    // A' (preserve alpha)
        )
    )
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model              = DemoUrls.LANDSCAPE,
            contentDescription = "Original",
            contentScale       = ContentScale.Crop,
            modifier           = Modifier
                .weight(1f).height(90.dp).clip(RoundedCornerShape(8.dp))
        )
        Column(
            modifier            = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model              = DemoUrls.LANDSCAPE,
                contentDescription = "Sepia",
                contentScale       = ContentScale.Crop,
                colorFilter        = ColorFilter.colorMatrix(sepiaMatrix),
                modifier           = Modifier
                    .fillMaxWidth().height(90.dp).clip(RoundedCornerShape(8.dp))
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Sepia tone",
                fontSize = 10.sp,
                color    = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Layout helpers ────────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 08 · Aspect Ratio ─────────────────────────────────────────────────────────
// Modifier.aspectRatio(ratio) sets height = width / ratio (or vice versa).
// Combined with fillMaxWidth(), the image always fills the column width and
// the height self-adjusts — great for responsive hero images.
@Composable
private fun AspectRatioDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf(
            "16:9 (cinema)"    to (16f / 9f),
            "4:3 (photo)"      to (4f  / 3f),
            "1:1 (square)"     to 1f
        ).forEach { (label, ratio) ->
            Text(
                text  = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            AsyncImage(
                model              = DemoUrls.LANDSCAPE,
                contentDescription = label,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio)          // ← height is derived from width
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

// ── 09 · Image with Overlay ───────────────────────────────────────────────────
// Box is the Compose equivalent of FrameLayout — children are stacked on top
// of each other in the order they are declared.
//
// Pattern:
//   Box {
//       Image(...)                         ← background layer
//       Box(gradient background) { Text }  ← scrim + text on top
//   }
//
// The gradient scrim (Brush.verticalGradient from transparent → black)
// ensures the white text is always readable regardless of the image content.
@Composable
private fun OverlayDemo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(12.dp))
    ) {
        // Layer 1 — the photo
        AsyncImage(
            model              = DemoUrls.LANDSCAPE,
            contentDescription = null,
            contentScale       = ContentScale.Crop,
            modifier           = Modifier.fillMaxSize()
        )
        // Layer 2 — gradient scrim at the bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                    )
                )
        )
        // Layer 3 — text pinned to the bottom of the box
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Text(
                text       = "Mountain Landscape",
                color      = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize   = 14.sp
            )
            Text(
                text     = "Overlay text on top of image",
                color    = Color.White.copy(alpha = 0.8f),
                fontSize = 11.sp
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Coil AsyncImage variants ──────────────────────────────────────────────────
//
// AsyncImage (coil.compose.AsyncImage) is Coil's main composable. It:
//   1. Kicks off the image request on a background thread (no blocking the UI).
//   2. Observes the result as Compose state — triggers recomposition on success.
//   3. Handles caching (memory + disk) automatically.
//
// coil.compose.SubcomposeAsyncImage gives full control over loading / success /
// error states via named slots — useful when you need a custom loading skeleton.
// ─────────────────────────────────────────────────────────────────────────────

// ── 10 · AsyncImage Basic ─────────────────────────────────────────────────────
@Composable
private fun AsyncBasicDemo() {
    AsyncImage(
        model              = DemoUrls.LANDSCAPE,
        contentDescription = "Network image loaded by Coil",
        contentScale       = ContentScale.Crop,
        modifier           = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(8.dp))
    )
}

// ── 11 · AsyncImage with Placeholder ─────────────────────────────────────────
// `placeholder` is shown while the image request is in flight (loading state).
// Using a vector drawable keeps the APK small.
// SubcomposeAsyncImage's `loading` slot lets you render any composable
// (e.g. a shimmer skeleton) instead of a static drawable.
@Composable
private fun AsyncPlaceholderDemo() {
    SubcomposeAsyncImage(
        model              = DemoUrls.PORTRAIT,
        contentDescription = "Image with placeholder",
        contentScale       = ContentScale.Crop,
        modifier           = Modifier
            .fillMaxWidth()
            .aspectRatio(4f / 3f)
            .clip(RoundedCornerShape(8.dp)),
        loading = {
            // Custom loading slot — shown while request is in flight.
            Box(
                modifier         = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        tint     = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Loading…",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    )
}

// ── 12 · AsyncImage with Error Fallback ───────────────────────────────────────
// `error` is shown when the request fails — bad URL, no network, 404, etc.
// Here we intentionally use a broken URL to force the error state.
@Composable
private fun AsyncErrorDemo() {
    SubcomposeAsyncImage(
        model              = DemoUrls.BROKEN_URL,
        contentDescription = "Image error state",
        contentScale       = ContentScale.Crop,
        modifier           = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(8.dp)),
        error = {
            // Custom error slot — shown when the request fails.
            Box(
                modifier         = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector      = Icons.Default.BrokenImage,
                        contentDescription = null,
                        tint             = MaterialTheme.colorScheme.onErrorContainer,
                        modifier         = Modifier.size(36.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Failed to load image",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    )
}

// ── 13 · AsyncImage with Crossfade ────────────────────────────────────────────
// ImageRequest.Builder lets you configure advanced Coil options.
// crossfade(durationMs) animates the image in with a fade transition instead
// of popping in abruptly — especially visible on slow connections.
@Composable
private fun AsyncCrossfadeDemo() {
    val context = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(DemoUrls.CROSSFADE)
            .crossfade(durationMillis = 1000)   // 1 second fade-in
            .build(),
        contentDescription = "Image with crossfade animation",
        contentScale       = ContentScale.Crop,
        modifier           = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(8.dp))
    )
}

// ── 14 · AsyncImage with Circle Crop Transformation ───────────────────────────
// Coil Transformations modify the decoded Bitmap before it is cached or
// displayed. CircleCropTransformation produces a circular image at the
// decoding stage — more efficient than doing it via Modifier.clip() because
// the circular bitmap is cached on disk and in memory.
@Composable
private fun AsyncTransformationDemo() {
    val context = LocalContext.current

    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Modifier.clip approach (composable-level)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model              = DemoUrls.TRANSFORM,
                contentDescription = "Clip via Modifier",
                contentScale       = ContentScale.Crop,
                modifier           = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Modifier.clip\n(composable)",
                fontSize  = 9.sp,
                textAlign = TextAlign.Center,
                color     = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // CircleCropTransformation approach (bitmap-level, cached)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(DemoUrls.TRANSFORM)
                    .transformations(coil.transform.CircleCropTransformation())
                    .build(),
                contentDescription = "CircleCropTransformation",
                modifier           = Modifier.size(90.dp)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "CircleCropTransformation\n(bitmap cached)",
                fontSize  = 9.sp,
                textAlign = TextAlign.Center,
                color     = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────
@Preview(showBackground = true)
@Composable
fun ImageViewsScreenPreview() {
    UiWithComposeTheme {
        ImageViewsScreen()
    }
}
