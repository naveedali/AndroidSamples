package com.naveedali.uiwithcompose.model

// ─────────────────────────────────────────────────────────────────────────────
// DialogDemoType — one value per distinct alert / dialog variant.
//
// The list mixes Material 3 AlertDialog patterns with lower-level custom
// Dialog implementations so learners can see both the simple and flexible
// approaches Compose offers.
// ─────────────────────────────────────────────────────────────────────────────
enum class DialogDemoType {

    // ── AlertDialog patterns ────────────────────────────────────────────────
    ALERT_BASIC,           // Standard title + text + confirm/dismiss buttons
    ALERT_CONFIRMATION,    // Action confirmation dialog before applying a change
    ALERT_DESTRUCTIVE,     // Higher-risk action with stronger visual warning
    ALERT_SINGLE_CHOICE,   // Single-selection list inside a dialog

    // ── Custom Dialog patterns ──────────────────────────────────────────────
    DIALOG_CUSTOM_CARD,    // Custom surface and free-form content
    DIALOG_FORM,           // Small form-like dialog with local input state
    DIALOG_PROGRESS,       // Loading/progress dialog that blocks dismissal

    // ── BottomSheet patterns ────────────────────────────────────────────────
    SHEET_BASIC,           // Introductory modal bottom sheet
    SHEET_ACTIONS,         // Action-sheet style list of quick actions
    SHEET_CONFIRMATION,    // Confirmation flow presented as a bottom sheet
}

// ─────────────────────────────────────────────────────────────────────────────
// DialogDemo — metadata carried by each list card.
// ─────────────────────────────────────────────────────────────────────────────
data class DialogDemo(
    val type: DialogDemoType,
    val title: String,
    val description: String,
)

// ─────────────────────────────────────────────────────────────────────────────
// dialogDemos — ordered catalogue rendered on the Dialogs screen.
// ─────────────────────────────────────────────────────────────────────────────
val dialogDemos: List<DialogDemo> = listOf(

    // ── AlertDialog patterns ────────────────────────────────────────────────
    DialogDemo(
        type = DialogDemoType.ALERT_BASIC,
        title = "AlertDialog — Basic",
        description = "The standard Material confirmation pattern with `title`, `text`, " +
                "`confirmButton`, and `dismissButton`. Use it for short, focused decisions."
    ),
    DialogDemo(
        type = DialogDemoType.ALERT_CONFIRMATION,
        title = "AlertDialog — Confirmation",
        description = "A practical example that asks the user to confirm before applying a " +
                "state change. This is a common pattern for settings, sign-out, or publishing flows."
    ),
    DialogDemo(
        type = DialogDemoType.ALERT_DESTRUCTIVE,
        title = "AlertDialog — Destructive Action",
        description = "For risky actions like delete or reset. The wording should be explicit " +
                "about consequences, and the confirm button often uses the error color."
    ),
    DialogDemo(
        type = DialogDemoType.ALERT_SINGLE_CHOICE,
        title = "AlertDialog — Single Choice",
        description = "Dialogs can host regular Compose content, not just plain text. This " +
                "example uses radio-button rows inside the body to pick one option."
    ),

    // ── Custom Dialog patterns ──────────────────────────────────────────────
    DialogDemo(
        type = DialogDemoType.DIALOG_CUSTOM_CARD,
        title = "Dialog — Custom Card Layout",
        description = "Use `Dialog` when AlertDialog's fixed structure is too limiting. You " +
                "provide your own Surface/Card and can arrange any content you need."
    ),
    DialogDemo(
        type = DialogDemoType.DIALOG_FORM,
        title = "Dialog — Small Form",
        description = "A custom dialog can host short forms such as rename, invite, or note-entry " +
                "flows. Keep the form short so a full screen is not required."
    ),
    DialogDemo(
        type = DialogDemoType.DIALOG_PROGRESS,
        title = "Dialog — Loading / Blocking Progress",
        description = "A loading dialog is useful when the current action must finish before " +
                "the user can continue. `DialogProperties` can disable outside-click dismissal."
    ),

    // ── BottomSheet patterns ────────────────────────────────────────────────
    DialogDemo(
        type = DialogDemoType.SHEET_BASIC,
        title = "Bottom Sheet — Basic",
        description = "A ModalBottomSheet slides up from the bottom and is great for " +
                "supplementary content that feels lighter than a full dialog."
    ),
    DialogDemo(
        type = DialogDemoType.SHEET_ACTIONS,
        title = "Bottom Sheet — Action Sheet",
        description = "Bottom sheets often work well as mobile-friendly action menus. " +
                "This pattern is ideal for share, archive, duplicate, or move actions."
    ),
    DialogDemo(
        type = DialogDemoType.SHEET_CONFIRMATION,
        title = "Bottom Sheet — Confirmation",
        description = "Some apps present confirmations in a bottom sheet instead of an alert. " +
                "This gives more room for explanation and larger touch targets."
    ),
)
