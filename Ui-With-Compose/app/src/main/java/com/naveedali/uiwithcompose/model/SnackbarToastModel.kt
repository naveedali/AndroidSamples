package com.naveedali.uiwithcompose.model

// ─────────────────────────────────────────────────────────────────────────────
// FeedbackDemoType — one value per Snackbar / Toast demo variant.
//
// Snackbars are Compose-first transient messages tied to a Scaffold.
// Toasts are platform-level Android popups shown via Context.
// This screen teaches both so learners understand when each fits.
// ─────────────────────────────────────────────────────────────────────────────
enum class FeedbackDemoType {

    // ── Snackbar demos ──────────────────────────────────────────────────────
    SNACKBAR_BASIC,          // Basic message shown through SnackbarHostState
    SNACKBAR_ACTION,         // Snackbar with action button
    SNACKBAR_RESULT,         // Handling SnackbarResult.ActionPerformed / Dismissed
    SNACKBAR_CUSTOM_HOST,    // Custom visual styling via SnackbarHost

    // ── Toast demos ─────────────────────────────────────────────────────────
    TOAST_BASIC,             // Standard short toast
    TOAST_DURATION,          // Short vs long duration examples
    TOAST_POSITIONED,        // Toast with custom gravity

    // ── Real-world pattern ──────────────────────────────────────────────────
    FORM_FEEDBACK,           // Save flow showing both Snackbar and Toast styles
}

// ─────────────────────────────────────────────────────────────────────────────
// FeedbackDemo — metadata rendered in each demo card.
// ─────────────────────────────────────────────────────────────────────────────
data class FeedbackDemo(
    val type: FeedbackDemoType,
    val title: String,
    val description: String,
)

// ─────────────────────────────────────────────────────────────────────────────
// feedbackDemos — ordered catalogue rendered on the Snackbars & Toasts screen.
// ─────────────────────────────────────────────────────────────────────────────
val feedbackDemos: List<FeedbackDemo> = listOf(
    FeedbackDemo(
        type = FeedbackDemoType.SNACKBAR_BASIC,
        title = "Snackbar — Basic",
        description = "The most common Compose feedback pattern. Use SnackbarHostState " +
                "and call `showSnackbar()` from a coroutine."
    ),
    FeedbackDemo(
        type = FeedbackDemoType.SNACKBAR_ACTION,
        title = "Snackbar — With Action",
        description = "Snackbars can present an optional action such as Undo, Retry, or View. " +
                "This works well for reversible operations."
    ),
    FeedbackDemo(
        type = FeedbackDemoType.SNACKBAR_RESULT,
        title = "Snackbar — Handling Result",
        description = "showSnackbar() returns a SnackbarResult so the caller can react " +
                "differently when the action is pressed versus when the message is dismissed."
    ),
    FeedbackDemo(
        type = FeedbackDemoType.SNACKBAR_CUSTOM_HOST,
        title = "Snackbar — Custom Host Styling",
        description = "SnackbarHost can render your own Snackbar composable, which allows " +
                "custom colors, shape, icons, and layout while keeping the same queueing behavior."
    ),
    FeedbackDemo(
        type = FeedbackDemoType.TOAST_BASIC,
        title = "Toast — Basic",
        description = "Toast is the classic Android transient message. It is simple and useful " +
                "for quick platform-style feedback, especially outside a Compose Scaffold."
    ),
    FeedbackDemo(
        type = FeedbackDemoType.TOAST_DURATION,
        title = "Toast — Duration",
        description = "Android offers SHORT and LONG toast durations. Use them sparingly and " +
                "keep the message brief so it stays readable."
    ),
    FeedbackDemo(
        type = FeedbackDemoType.TOAST_POSITIONED,
        title = "Toast — Position / Gravity",
        description = "A Toast can be positioned with gravity, though default placement is usually " +
                "best unless a specific design reason exists."
    ),
    FeedbackDemo(
        type = FeedbackDemoType.FORM_FEEDBACK,
        title = "Real-World Example — Save Feedback",
        description = "A practical example comparing when a Snackbar is better than a Toast: " +
                "Snackbars are stronger for actions; Toasts are lighter for passive confirmation."
    ),
)
