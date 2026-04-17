package com.naveedali.uiwithcompose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naveedali.uiwithcompose.model.RatingBarDemo
import com.naveedali.uiwithcompose.model.RatingBarDemoType
import com.naveedali.uiwithcompose.model.ratingBarDemos
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme
import kotlin.math.roundToInt

// ─────────────────────────────────────────────────────────────────────────────
// RatingBarScreen
//
// Entry-point composable for custom star rating examples.
// Material 3 does not provide a built-in RatingBar, so this screen demonstrates
// how to build one from smaller Compose primitives.
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingBarScreen(onBack: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rating Bar") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
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
                items = ratingBarDemos,
                key = { _, demo -> demo.type.name }
            ) { index, demo ->
                RatingBarDemoCard(index = index + 1, demo = demo)
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// RatingBarDemoCard
//
// Wrapper card with a numbered heading, description, and live demo content.
// Each enum case dispatches to one focused example composable.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun RatingBarDemoCard(index: Int, demo: RatingBarDemo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "%02d".format(index),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = demo.title,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Text(
                text = demo.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            when (demo.type) {
                RatingBarDemoType.BASIC_INTERACTIVE -> RatingBarBasicDemo()
                RatingBarDemoType.READ_ONLY_INDICATOR -> RatingBarReadOnlyDemo()
                RatingBarDemoType.FRACTIONAL_DISPLAY -> RatingBarFractionalDemo()
                RatingBarDemoType.SIZE_STYLES -> RatingBarSizeStylesDemo()
                RatingBarDemoType.COLOR_STYLES -> RatingBarColorStylesDemo()
                RatingBarDemoType.MAX_RATING_VARIANTS -> RatingBarMaxVariantsDemo()
                RatingBarDemoType.REVIEW_FORM -> RatingBarReviewFormDemo()
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Core demos ───────────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 01 · Basic interactive RatingBar ─────────────────────────────────────────
// The star row is fully custom, but the state pattern is the same as any other
// Compose input:
//   `rating`         — source of truth owned by the caller
//   `onRatingChange` — callback fired when the user taps a star
//
// Here each tap selects the whole-number score for that star position.
@Composable
private fun RatingBarBasicDemo() {
    var rating by rememberSaveable { mutableFloatStateOf(3f) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        StarRatingBar(
            rating = rating,
            onRatingChange = { rating = it },
        )
        Text(
            text = "Selected rating: ${rating.toInt()} / 5",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

// ── 02 · Read-only indicator ─────────────────────────────────────────────────
// Product cards and review summaries often display a score that is NOT editable.
// Passing `onRatingChange = null` makes the component display-only.
@Composable
private fun RatingBarReadOnlyDemo() {
    val averageRating = 4.8f

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "Noise Cancelling Headphones",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "1,284 verified reviews",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            StarRatingBar(
                rating = averageRating,
                onRatingChange = null,
                starSize = 20.dp
            )
            Text(
                text = "$averageRating / 5",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// ── 03 · Fractional display ──────────────────────────────────────────────────
// Many UIs display server-provided averages like 4.2 or 3.7.
// To render that visually, each star calculates a `fillFraction`:
//   1f   -> fully filled
//   0f   -> empty
//   0-1f -> partially filled star
@Composable
private fun RatingBarFractionalDemo() {
    val examples = listOf(2.5f, 3.4f, 4.7f)

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        examples.forEach { value ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Average: $value",
                    style = MaterialTheme.typography.bodyMedium
                )
                StarRatingBar(
                    rating = value,
                    onRatingChange = null,
                    starSize = 22.dp
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Style demos ──────────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 04 · Size styles ─────────────────────────────────────────────────────────
// A custom RatingBar is easy to scale because the star size is just a `Dp`
// parameter. The logic does not change; only the visual footprint does.
@Composable
private fun RatingBarSizeStylesDemo() {
    val sizes = listOf(
        "Compact" to 16.dp,
        "Default" to 24.dp,
        "Large" to 36.dp
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        sizes.forEach { (label, size) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(label, style = MaterialTheme.typography.bodyMedium)
                StarRatingBar(
                    rating = 4f,
                    onRatingChange = null,
                    starSize = size
                )
            }
        }
    }
}

// ── 05 · Color styles ────────────────────────────────────────────────────────
// Because the stars are regular Icons, brand styling is just a matter of
// passing different fill and empty colours.
@Composable
private fun RatingBarColorStylesDemo() {
    val styles = listOf(
        Triple("Standard", Color(0xFFFFB300), MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.25f)),
        Triple("Success", Color(0xFF2E7D32), Color(0xFF2E7D32).copy(alpha = 0.20f)),
        Triple("Premium", Color(0xFF6A1B9A), Color(0xFF6A1B9A).copy(alpha = 0.20f)),
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        styles.forEach { (label, filled, empty) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(label, style = MaterialTheme.typography.bodyMedium)
                StarRatingBar(
                    rating = 4f,
                    onRatingChange = null,
                    filledColor = filled,
                    emptyColor = empty
                )
            }
        }
    }
}

// ── 06 · Different max-rating scales ─────────────────────────────────────────
// `maxRating` controls how many star cells are rendered.
// The same helper can therefore support 5-star or 10-star layouts.
@Composable
private fun RatingBarMaxVariantsDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("5-star product rating", style = MaterialTheme.typography.bodyMedium)
            StarRatingBar(
                rating = 4f,
                onRatingChange = null
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("10-point satisfaction score", style = MaterialTheme.typography.bodyMedium)
            StarRatingBar(
                rating = 8f,
                maxRating = 10,
                onRatingChange = null,
                starSize = 18.dp,
                spacing = 2.dp
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Real-world example ───────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 07 · Review form ─────────────────────────────────────────────────────────
// This combines the star control with supporting text, which is how rating bars
// usually appear in production UIs instead of floating on their own.
@Composable
private fun RatingBarReviewFormDemo() {
    var rating by rememberSaveable { mutableFloatStateOf(4f) }
    var wouldRecommend by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "How was your stay?",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Tap a star to leave a quick score.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        StarRatingBar(
            rating = rating,
            onRatingChange = { rating = it },
            starSize = 32.dp
        )

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = reviewLabelFor(rating),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                HorizontalDivider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Would recommend", style = MaterialTheme.typography.bodyMedium)
                    RecommendationChip(
                        selected = wouldRecommend,
                        label = if (wouldRecommend) "Yes" else "No",
                        onClick = { wouldRecommend = !wouldRecommend }
                    )
                }

                Text(
                    text = "Summary: ${rating.toInt()}/5 stars • " + (
                        if (wouldRecommend) "Guest would recommend this place."
                        else "Guest would not recommend this place yet."
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Shared RatingBar helper ──────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// StarRatingBar
//
// A lightweight custom rating composable built from primitive Compose pieces.
// Rendering strategy per star:
//   1. Draw an "empty" star in a muted colour
//   2. Draw a second filled star above it
//   3. Clip the top layer horizontally according to the fill fraction
//
// This supports both whole and fractional display values without needing a
// dedicated third-party RatingBar library.
@Composable
private fun StarRatingBar(
    rating: Float,
    onRatingChange: ((Float) -> Unit)?,
    modifier: Modifier = Modifier,
    maxRating: Int = 5,
    starSize: Dp = 24.dp,
    spacing: Dp = 4.dp,
    filledColor: Color = Color(0xFFFFB300),
    emptyColor: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.25f),
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(maxRating) { index ->
            val starNumber = index + 1
            val fillFraction = (rating - index).coerceIn(0f, 1f)

            Box(
                modifier = Modifier
                    .size(starSize)
                    .then(
                        if (onRatingChange != null) {
                            Modifier.clickable(role = Role.Button) {
                                onRatingChange(starNumber.toFloat())
                            }
                        } else {
                            Modifier
                        }
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                // Base layer — the empty star silhouette.
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = emptyColor,
                    modifier = Modifier.matchParentSize()
                )

                // Top layer — the filled star clipped to a percentage of width.
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(starSize * fillFraction)
                        .clipToBounds()
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = filledColor,
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun RecommendationChip(
    selected: Boolean,
    label: String,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(999.dp),
        color = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
        else MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        )
    }
}

private fun reviewLabelFor(rating: Float): String = when (rating.roundToInt()) {
    1 -> "Very poor experience"
    2 -> "Needs improvement"
    3 -> "Good enough"
    4 -> "Really enjoyable stay"
    else -> "Excellent experience"
}

@Preview(showBackground = true)
@Composable
private fun RatingBarScreenPreview() {
    UiWithComposeTheme {
        RatingBarScreen()
    }
}
