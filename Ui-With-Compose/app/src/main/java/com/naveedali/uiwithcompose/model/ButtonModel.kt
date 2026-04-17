package com.naveedali.uiwithcompose.model

// ─────────────────────────────────────────────────────────────────────────────
// ButtonDemoType — one value per distinct button variant demonstrated.
//
// The enum drives two things:
//   1. The catalogue list below (determines what cards appear and in what order)
//   2. The `when` dispatch inside ButtonsScreen (decides which composable to show)
//
// Add new values here → add a matching `when` branch in ButtonsScreen.
// ─────────────────────────────────────────────────────────────────────────────
enum class ButtonDemoType {

    // ── Material 3 button hierarchy ──────────────────────────────────────────
    FILLED,                 // Primary CTA — highest visual weight
    FILLED_TONAL,           // Secondary CTA — medium visual weight, softer colour
    OUTLINED,               // Tertiary CTA — low emphasis, visible boundary
    TEXT_BUTTON,            // Low emphasis — no background, no border
    ELEVATED,               // Like filled-tonal but with a shadow

    // ── Icon buttons ─────────────────────────────────────────────────────────
    ICON_BUTTON,            // Tap target with only an icon, no label
    FILLED_ICON_BUTTON,     // Filled background icon button
    OUTLINED_ICON_BUTTON,   // Outlined icon button
    TONAL_ICON_BUTTON,      // Tonal (surface-variant) icon button
    TOGGLE_ICON_BUTTON,     // Stateful — toggled checked / unchecked

    // ── FAB variants ─────────────────────────────────────────────────────────
    FAB,                    // Standard 56 dp circular Floating Action Button
    SMALL_FAB,              // 40 dp — compact FAB for secondary actions
    LARGE_FAB,              // 96 dp — prominent hero-level FAB
    EXTENDED_FAB,           // FAB with an icon + text label

    // ── State & behaviour demos ──────────────────────────────────────────────
    DISABLED,               // All major styles shown in disabled state
    LOADING,                // Button locked while an async operation runs
    WITH_ICON,              // Filled button with leading icon
    BUTTON_SIZES,           // Custom sizes via Modifier
    CUSTOM_COLORS,          // Override default colours with ButtonDefaults.colors()
    CUSTOM_SHAPE,           // Override default shape with ButtonDefaults.shape
    SEGMENTED,              // SingleChoiceSegmentedButtonRow for mutually-exclusive options
    CHIP_ASSIST,            // AssistChip — contextual action hint
    CHIP_FILTER,            // FilterChip — toggleable filtering option
    CHIP_INPUT,             // InputChip — represents a selection or token
    CHIP_SUGGESTION,        // SuggestionChip — read-only hint / autocomplete
}

// ─────────────────────────────────────────────────────────────────────────────
// ButtonDemo — the metadata carried by each list item.
// ─────────────────────────────────────────────────────────────────────────────
data class ButtonDemo(
    val type: ButtonDemoType,
    val title: String,
    val description: String,
)

// ─────────────────────────────────────────────────────────────────────────────
// buttonDemos — the ordered catalogue shown on the Buttons screen.
// ─────────────────────────────────────────────────────────────────────────────
val buttonDemos: List<ButtonDemo> = listOf(

    // ── Material 3 hierarchy ──────────────────────────────────────────────────
    ButtonDemo(
        type        = ButtonDemoType.FILLED,
        title       = "Filled Button",
        description = "Highest emphasis. Use for the single primary action on a screen (e.g. Save, Submit). " +
                      "Background = primary colour; text = onPrimary.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.FILLED_TONAL,
        title       = "Filled Tonal Button",
        description = "Medium emphasis. A softer alternative to Filled when two actions have similar importance. " +
                      "Background = secondaryContainer; text = onSecondaryContainer.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.OUTLINED,
        title       = "Outlined Button",
        description = "Medium–low emphasis. Pairs well with a Filled button as a secondary action. " +
                      "Has a visible stroke border, no fill.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.TEXT_BUTTON,
        title       = "Text Button",
        description = "Lowest emphasis. Used for inline tertiary actions where visual weight would be distracting. " +
                      "No background, no border — label only.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.ELEVATED,
        title       = "Elevated Button",
        description = "Like FilledTonal but elevated with a drop shadow. Stands out on flat or image surfaces. " +
                      "Background = surface; tint = primary.",
    ),

    // ── Icon buttons ──────────────────────────────────────────────────────────
    ButtonDemo(
        type        = ButtonDemoType.ICON_BUTTON,
        title       = "Icon Button",
        description = "A 48 dp tap target containing only an icon — no label. " +
                      "Use for toolbars, app bars, and densely packed controls.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.FILLED_ICON_BUTTON,
        title       = "Filled Icon Button",
        description = "Icon button with a filled container. High-emphasis icon action " +
                      "such as a primary send or confirm.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.OUTLINED_ICON_BUTTON,
        title       = "Outlined Icon Button",
        description = "Icon button with a visible border — medium emphasis. " +
                      "Useful when you need an icon action to stand out slightly.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.TONAL_ICON_BUTTON,
        title       = "Tonal Icon Button",
        description = "Icon button with a secondaryContainer background — medium emphasis. " +
                      "Good for secondary icon actions in a group.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.TOGGLE_ICON_BUTTON,
        title       = "Toggle Icon Button",
        description = "Stateful icon button that switches between checked and unchecked — " +
                      "e.g. Favourite, Bookmark, Like. `checked` + `onCheckedChange` drive the state.",
    ),

    // ── FAB variants ──────────────────────────────────────────────────────────
    ButtonDemo(
        type        = ButtonDemoType.FAB,
        title       = "FAB — Standard",
        description = "Floating Action Button (56 dp). Promotes the primary action of a screen. " +
                      "Should appear only once per screen.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.SMALL_FAB,
        title       = "FAB — Small",
        description = "Compact 40 dp FAB for secondary or contextual actions that need less prominence " +
                      "than the primary FAB.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.LARGE_FAB,
        title       = "FAB — Large",
        description = "Hero-sized 96 dp FAB for screens where the primary action deserves extra visual weight, " +
                      "e.g. Compose in a mail app.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.EXTENDED_FAB,
        title       = "Extended FAB",
        description = "FAB with an icon and a text label. More descriptive than the icon-only FAB. " +
                      "Use when the action might not be obvious from the icon alone.",
    ),

    // ── State & behaviour ─────────────────────────────────────────────────────
    ButtonDemo(
        type        = ButtonDemoType.DISABLED,
        title       = "Disabled State",
        description = "All major button styles shown in the disabled state. " +
                      "Pass `enabled = false` — Compose automatically applies disabled colours and removes click handling.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.LOADING,
        title       = "Loading / In-Progress Button",
        description = "Button that disables itself and shows a CircularProgressIndicator while an async operation " +
                      "is running. Pattern: `isLoading` state toggles `enabled` and the label composable.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.WITH_ICON,
        title       = "Button with Leading Icon",
        description = "Filled button that places an Icon before the label. " +
                      "Use the `contentPadding` + Row trick or the built-in `icon` slot.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.BUTTON_SIZES,
        title       = "Custom Sizes",
        description = "Buttons can be resized via Modifier.height() and Modifier.fillMaxWidth(). " +
                      "Combine with contentPadding to keep the label well-centred.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.CUSTOM_COLORS,
        title       = "Custom Colours",
        description = "ButtonDefaults.buttonColors() / outlinedButtonColors() / textButtonColors() let you " +
                      "override containerColor, contentColor, disabledContainerColor, and disabledContentColor.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.CUSTOM_SHAPE,
        title       = "Custom Shape",
        description = "The `shape` parameter accepts any Shape (CircleShape, RoundedCornerShape, CutCornerShape, " +
                      "RectangleShape). Default for Material 3 buttons is CircleShape (fully rounded).",
    ),
    ButtonDemo(
        type        = ButtonDemoType.SEGMENTED,
        title       = "Segmented Button",
        description = "SingleChoiceSegmentedButtonRow provides a compact toggle group where exactly one option " +
                      "is selected at a time — e.g. Day / Week / Month view selector.",
    ),

    // ── Chips ─────────────────────────────────────────────────────────────────
    ButtonDemo(
        type        = ButtonDemoType.CHIP_ASSIST,
        title       = "Assist Chip",
        description = "Contextual helper chip that suggests a smart action — e.g. 'Add to calendar'. " +
                      "Not toggleable; triggers an action on click.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.CHIP_FILTER,
        title       = "Filter Chip",
        description = "Toggleable chip used to filter a list or dataset. Shows a check icon when selected. " +
                      "`selected` + `onSelectedChange` drive state.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.CHIP_INPUT,
        title       = "Input Chip",
        description = "Represents a value the user has selected (e.g. an email address or tag). " +
                      "Can show an avatar/icon and a dismiss (×) trailing icon.",
    ),
    ButtonDemo(
        type        = ButtonDemoType.CHIP_SUGGESTION,
        title       = "Suggestion Chip",
        description = "Read-only chip that proposes a value — e.g. autocomplete suggestions below a text field. " +
                      "Not toggleable; triggers an action on click.",
    ),
)
