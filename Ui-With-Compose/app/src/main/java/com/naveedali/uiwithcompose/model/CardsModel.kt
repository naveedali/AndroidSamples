package com.naveedali.uiwithcompose.model

// ─────────────────────────────────────────────────────────────────────────────
// CardDemoType — one value per distinct Card variant.
//
// Covers the three main Material 3 card families plus common real-world
// layouts such as clickable cards, media cards, and summary panels.
// ─────────────────────────────────────────────────────────────────────────────
enum class CardDemoType {

    // ── Material 3 card families ────────────────────────────────────────────
    BASIC,               // Standard Card with default elevation
    ELEVATED,            // ElevatedCard with stronger separation from background
    OUTLINED,            // OutlinedCard with a visible border instead of shadow

    // ── Interaction & styling demos ─────────────────────────────────────────
    CLICKABLE,           // Entire card surface acts as one tap target
    CUSTOM_COLORS,       // CardDefaults.cardColors() styling examples
    CUSTOM_SHAPES,       // Rounded / cut-corner visual variations

    // ── Real-world layouts ──────────────────────────────────────────────────
    MEDIA,               // Header image area + supporting text
    ACTIONS,             // Card with actions such as save / share / open
    SETTINGS_SUMMARY,    // Information-dense summary card
}

// ─────────────────────────────────────────────────────────────────────────────
// CardDemo — metadata rendered in each demo card.
// ─────────────────────────────────────────────────────────────────────────────
data class CardDemo(
    val type: CardDemoType,
    val title: String,
    val description: String,
)

// ─────────────────────────────────────────────────────────────────────────────
// cardDemos — ordered catalogue rendered on the Cards screen.
// ─────────────────────────────────────────────────────────────────────────────
val cardDemos: List<CardDemo> = listOf(
    CardDemo(
        type = CardDemoType.BASIC,
        title = "Card — Basic",
        description = "The default Material 3 Card provides a surface with shape, color, " +
                "and subtle elevation. It works well for grouping related content."
    ),
    CardDemo(
        type = CardDemoType.ELEVATED,
        title = "ElevatedCard — Raised Surface",
        description = "ElevatedCard increases elevation to separate content more strongly " +
                "from the background. Useful for featured or prominent content."
    ),
    CardDemo(
        type = CardDemoType.OUTLINED,
        title = "OutlinedCard — Bordered Surface",
        description = "OutlinedCard uses a stroke border instead of relying on shadow alone. " +
                "It is often useful in flatter or denser interfaces."
    ),
    CardDemo(
        type = CardDemoType.CLICKABLE,
        title = "Card — Clickable",
        description = "Cards can act as large tap targets by using the `onClick` overload. " +
                "This is common for navigation rows, product cards, and article previews."
    ),
    CardDemo(
        type = CardDemoType.CUSTOM_COLORS,
        title = "Card — Custom Colors",
        description = "CardDefaults.cardColors() lets you override container and content " +
                "colors for featured, warning, or brand-specific card styles."
    ),
    CardDemo(
        type = CardDemoType.CUSTOM_SHAPES,
        title = "Card — Custom Shapes",
        description = "Cards accept any Shape, such as rounded corners or cut corners. " +
                "Shape helps define the visual personality of a UI."
    ),
    CardDemo(
        type = CardDemoType.MEDIA,
        title = "Real-World Example — Media Card",
        description = "A classic content pattern with a visual header area followed by " +
                "title, metadata, and supporting copy."
    ),
    CardDemo(
        type = CardDemoType.ACTIONS,
        title = "Real-World Example — Card with Actions",
        description = "Cards often include inline actions such as save, share, retry, or " +
                "open details. Keep the primary content readable and the actions secondary."
    ),
    CardDemo(
        type = CardDemoType.SETTINGS_SUMMARY,
        title = "Real-World Example — Settings Summary Card",
        description = "A summary card can collect several related values in one place, " +
                "such as subscription status, storage usage, or account settings."
    ),
)
