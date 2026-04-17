package com.naveedali.uiwithcompose.model

// ─────────────────────────────────────────────────────────────────────────────
// HorizontalListDemoType — one value per LazyRow / horizontal-list pattern.
//
// These demos cover core LazyRow concepts such as item spacing, content
// padding, stable keys, and mixed item types rendered in one horizontal list.
// ─────────────────────────────────────────────────────────────────────────────
enum class HorizontalListDemoType {

    // ── Core LazyRow patterns ───────────────────────────────────────────────
    BASIC_TEXT_CHIPS,       // Small horizontally scrolling text chips
    CONTENT_PADDING,        // Leading/trailing breathing room around items
    CARD_CAROUSEL,          // Reusable card items in a horizontal row
    MIXED_VIEW_TYPES,       // Different visual item types in the same LazyRow

    // ── Real-world patterns ─────────────────────────────────────────────────
    CATEGORY_SELECTOR,      // Selectable tabs / categories
    FEATURED_PLAYLISTS,     // Media-style content row
    QUICK_ACTIONS,          // Action shortcuts / compact dashboard tiles
}

// ─────────────────────────────────────────────────────────────────────────────
// HorizontalListDemo — metadata rendered in each demo card.
// ─────────────────────────────────────────────────────────────────────────────
data class HorizontalListDemo(
    val type: HorizontalListDemoType,
    val title: String,
    val description: String,
)

// ─────────────────────────────────────────────────────────────────────────────
// horizontalListDemos — ordered catalogue rendered on the Horizontal List screen.
// ─────────────────────────────────────────────────────────────────────────────
val horizontalListDemos: List<HorizontalListDemo> = listOf(
    HorizontalListDemo(
        type = HorizontalListDemoType.BASIC_TEXT_CHIPS,
        title = "LazyRow — Basic Text Chips",
        description = "The simplest horizontal list: a LazyRow of compact items. " +
                "This is useful for tags, filters, and short labels."
    ),
    HorizontalListDemo(
        type = HorizontalListDemoType.CONTENT_PADDING,
        title = "LazyRow — Content Padding & Spacing",
        description = "contentPadding adds breathing room at the start and end of the row, " +
                "while horizontalArrangement controls space between items."
    ),
    HorizontalListDemo(
        type = HorizontalListDemoType.CARD_CAROUSEL,
        title = "LazyRow — Card Carousel",
        description = "A common production pattern: a horizontally scrollable row of cards " +
                "for promos, recommendations, or article previews."
    ),
    HorizontalListDemo(
        type = HorizontalListDemoType.MIXED_VIEW_TYPES,
        title = "LazyRow — Mixed View Types",
        description = "A horizontal list can render different item layouts in the same row. " +
                "This is useful for banners, actions, and content previews together."
    ),
    HorizontalListDemo(
        type = HorizontalListDemoType.CATEGORY_SELECTOR,
        title = "Real-World Example — Category Selector",
        description = "A horizontal category strip is a common way to switch between sections " +
                "or filter a screen without taking much vertical space."
    ),
    HorizontalListDemo(
        type = HorizontalListDemoType.FEATURED_PLAYLISTS,
        title = "Real-World Example — Featured Playlists",
        description = "Media and shopping apps often use a repeated horizontal row of rich tiles " +
                "with image area, title, and subtitle."
    ),
    HorizontalListDemo(
        type = HorizontalListDemoType.QUICK_ACTIONS,
        title = "Real-World Example — Quick Actions",
        description = "Compact action cards inside a LazyRow work well for dashboards, home " +
                "screens, and productivity shortcuts."
    ),
)
