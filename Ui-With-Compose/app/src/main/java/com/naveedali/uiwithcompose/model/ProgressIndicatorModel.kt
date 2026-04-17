package com.naveedali.uiwithcompose.model

// ─────────────────────────────────────────────────────────────────────────────
// ProgressDemoType — one value per distinct progress indicator variant.
//
// Covers both Material 3 progress components:
//   • CircularProgressIndicator
//   • LinearProgressIndicator
//
// The demos also include realistic UI patterns such as loading cards and
// buffered/download progress so learners see where these indicators fit.
// ─────────────────────────────────────────────────────────────────────────────
enum class ProgressDemoType {

    // ── Circular indicators ────────────────────────────────────────────────
    CIRCULAR_INDETERMINATE,   // Spinner when total progress is unknown
    CIRCULAR_DETERMINATE,     // Ring that fills according to a known value
    CIRCULAR_STYLES,          // Size and color variations

    // ── Linear indicators ──────────────────────────────────────────────────
    LINEAR_INDETERMINATE,     // Sweeping bar for unknown-duration work
    LINEAR_DETERMINATE,       // Straight bar driven by a Float progress value
    LINEAR_BUFFERED,          // Simulates buffered/downloaded vs played/used

    // ── Real-world patterns ────────────────────────────────────────────────
    LOADING_CARD,             // Loading state inside a content card
    FILE_UPLOAD,              // Example task with percentage + progress bar
}

// ─────────────────────────────────────────────────────────────────────────────
// ProgressDemo — metadata rendered in each demo card.
// ─────────────────────────────────────────────────────────────────────────────
data class ProgressDemo(
    val type: ProgressDemoType,
    val title: String,
    val description: String,
)

// ─────────────────────────────────────────────────────────────────────────────
// progressDemos — ordered catalogue rendered on the Progress screen.
// ─────────────────────────────────────────────────────────────────────────────
val progressDemos: List<ProgressDemo> = listOf(
    ProgressDemo(
        type = ProgressDemoType.CIRCULAR_INDETERMINATE,
        title = "CircularProgressIndicator — Indeterminate",
        description = "Use the indeterminate circular spinner when work is in progress but " +
                "you do not know the exact completion percentage yet."
    ),
    ProgressDemo(
        type = ProgressDemoType.CIRCULAR_DETERMINATE,
        title = "CircularProgressIndicator — Determinate",
        description = "Drive the indicator with a `progress` Float from `0f..1f` when the " +
                "task has a measurable completion value."
    ),
    ProgressDemo(
        type = ProgressDemoType.CIRCULAR_STYLES,
        title = "CircularProgressIndicator — Styles",
        description = "A custom size and color can help the indicator fit different contexts " +
                "such as inline controls, cards, or full-screen waiting states."
    ),
    ProgressDemo(
        type = ProgressDemoType.LINEAR_INDETERMINATE,
        title = "LinearProgressIndicator — Indeterminate",
        description = "Linear indeterminate progress works well for page-level loading, " +
                "refresh states, and top-of-screen loading bars."
    ),
    ProgressDemo(
        type = ProgressDemoType.LINEAR_DETERMINATE,
        title = "LinearProgressIndicator — Determinate",
        description = "Use a determinate linear bar when users benefit from seeing how much " +
                "of a task is already complete."
    ),
    ProgressDemo(
        type = ProgressDemoType.LINEAR_BUFFERED,
        title = "LinearProgressIndicator — Buffered / Secondary Progress",
        description = "A realistic media/download pattern can show one value for buffered " +
                "content and another for consumed/completed progress."
    ),
    ProgressDemo(
        type = ProgressDemoType.LOADING_CARD,
        title = "Real-World Example — Loading Card",
        description = "A content card often swaps between a loading state and the final " +
                "result. This pattern keeps feedback close to the content area."
    ),
    ProgressDemo(
        type = ProgressDemoType.FILE_UPLOAD,
        title = "Real-World Example — File Upload",
        description = "File transfers are a classic determinate-progress use case: show a " +
                "label, percentage, and a clear sense of completion."
    ),
)
