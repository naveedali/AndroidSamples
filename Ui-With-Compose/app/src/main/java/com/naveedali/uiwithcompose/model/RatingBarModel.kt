package com.naveedali.uiwithcompose.model

// ─────────────────────────────────────────────────────────────────────────────
// RatingBarDemoType — one value per distinct demo variant.
//
// Material 3 does not ship a built-in RatingBar composable, so these demos
// show common custom star-rating patterns built with Row + Icon + state.
// ─────────────────────────────────────────────────────────────────────────────
enum class RatingBarDemoType {

    // ── Core patterns ────────────────────────────────────────────────────────
    BASIC_INTERACTIVE,      // Standard 5-star tap-to-rate control
    READ_ONLY_INDICATOR,    // Non-interactive display for averages / summaries
    FRACTIONAL_DISPLAY,     // Partial star fill for values like 4.3 / 5

    // ── Styling variants ─────────────────────────────────────────────────────
    SIZE_STYLES,            // Same component in compact / default / hero sizes
    COLOR_STYLES,           // Brand-coloured variants using custom tint values
    MAX_RATING_VARIANTS,    // Different scales such as 5-star and 10-star

    // ── Real-world example ───────────────────────────────────────────────────
    REVIEW_FORM,            // A mini review card combining rating + feedback
}

// ─────────────────────────────────────────────────────────────────────────────
// RatingBarDemo — metadata carried by each card.
// ─────────────────────────────────────────────────────────────────────────────
data class RatingBarDemo(
    val type: RatingBarDemoType,
    val title: String,
    val description: String,
)

// ─────────────────────────────────────────────────────────────────────────────
// ratingBarDemos — ordered catalogue rendered on the RatingBar screen.
// ─────────────────────────────────────────────────────────────────────────────
val ratingBarDemos: List<RatingBarDemo> = listOf(

    // ── Core patterns ────────────────────────────────────────────────────────
    RatingBarDemo(
        type = RatingBarDemoType.BASIC_INTERACTIVE,
        title = "RatingBar — Basic Interactive",
        description = "The most common pattern: a row of tappable stars backed by a " +
                "`rating` state value. Each star maps its position to a numeric score " +
                "and updates the state when tapped."
    ),
    RatingBarDemo(
        type = RatingBarDemoType.READ_ONLY_INDICATOR,
        title = "RatingBar — Read-Only Indicator",
        description = "Use a non-interactive version when showing an existing product or " +
                "review score. Pass `onRatingChange = null` so the component becomes a " +
                "pure visual indicator."
    ),
    RatingBarDemo(
        type = RatingBarDemoType.FRACTIONAL_DISPLAY,
        title = "RatingBar — Fractional Display",
        description = "Average ratings are rarely whole numbers. This sample renders " +
                "partial star fills by clipping the filled layer based on the decimal " +
                "portion of each star."
    ),

    // ── Styling variants ─────────────────────────────────────────────────────
    RatingBarDemo(
        type = RatingBarDemoType.SIZE_STYLES,
        title = "RatingBar — Size Styles",
        description = "The same star-rating logic can be reused at different visual sizes. " +
                "Compact sizes work inside lists; larger sizes work better in detail pages " +
                "or onboarding flows."
    ),
    RatingBarDemo(
        type = RatingBarDemoType.COLOR_STYLES,
        title = "RatingBar — Color Styles",
        description = "A custom RatingBar is easy to theme because the star colors are just " +
                "parameters. This is useful for brand palettes, category-specific ratings, " +
                "or status-coded feedback."
    ),
    RatingBarDemo(
        type = RatingBarDemoType.MAX_RATING_VARIANTS,
        title = "RatingBar — Different Rating Scales",
        description = "Not every product uses a 5-star scale. This demo shows how the same " +
                "component can support different `maxRating` values such as 5 and 10."
    ),

    // ── Real-world example ───────────────────────────────────────────────────
    RatingBarDemo(
        type = RatingBarDemoType.REVIEW_FORM,
        title = "Real-World Example — Leave a Review",
        description = "A realistic review card that combines a tappable star rating with " +
                "contextual helper text and a quick recommendation summary."
    ),
)
