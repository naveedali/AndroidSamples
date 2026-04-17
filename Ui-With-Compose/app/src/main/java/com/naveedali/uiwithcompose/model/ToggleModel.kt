package com.naveedali.uiwithcompose.model

// ─────────────────────────────────────────────────────────────────────────────
// ToggleDemoType — one value per distinct demo variant.
//
// Organised into four groups:
//   • Switch variants      (SWITCH_*)
//   • Checkbox variants    (CHECKBOX_*)
//   • RadioButton variants (RADIO_*)
//   • Combined patterns    (FORM_*)
// ─────────────────────────────────────────────────────────────────────────────
enum class ToggleDemoType {

    // ── Switch ────────────────────────────────────────────────────────────────
    SWITCH_BASIC,           // Simple on/off Switch
    SWITCH_THUMB_ICON,      // Switch with an icon stamped into the thumb
    SWITCH_CUSTOM_COLORS,   // Override track & thumb colours via SwitchDefaults.colors()
    SWITCH_DISABLED,        // Switch in enabled=false state (both on & off shown)
    SWITCH_LABELED_ROW,     // Common pattern: Switch at end of a full-width label row

    // ── Checkbox ──────────────────────────────────────────────────────────────
    CHECKBOX_BASIC,         // Simple checked / unchecked Checkbox
    CHECKBOX_TRISTATE,      // Three states: checked, unchecked, indeterminate
    CHECKBOX_LABELED,       // Checkbox + Text in a clickable Row (the standard form pattern)
    CHECKBOX_GROUP,         // Several related checkboxes under a "Select all" parent
    CHECKBOX_DISABLED,      // Checkbox in enabled=false state

    // ── RadioButton ───────────────────────────────────────────────────────────
    RADIO_BASIC,            // Bare RadioButton — selected / unselected states
    RADIO_GROUP_VERTICAL,   // Vertical single-selection group with labels
    RADIO_GROUP_HORIZONTAL, // Horizontal single-selection group (e.g. size picker)
    RADIO_CUSTOM_COLORS,    // Override selected / unselected colours

    // ── Combined patterns ─────────────────────────────────────────────────────
    FORM_NOTIFICATIONS,     // Realistic settings form mixing Switches, Checkboxes, Radios
}

// ─────────────────────────────────────────────────────────────────────────────
// ToggleDemo — metadata carried by each card.
// ─────────────────────────────────────────────────────────────────────────────
data class ToggleDemo(
    val type: ToggleDemoType,
    val title: String,
    val description: String,
)

// ─────────────────────────────────────────────────────────────────────────────
// toggleDemos — ordered catalogue rendered on the Toggles screen.
// ─────────────────────────────────────────────────────────────────────────────
val toggleDemos: List<ToggleDemo> = listOf(

    // ── Switch ────────────────────────────────────────────────────────────────
    ToggleDemo(
        type        = ToggleDemoType.SWITCH_BASIC,
        title       = "Switch — Basic",
        description = "Switch is the Compose equivalent of a toggle button. " +
                      "`checked: Boolean` holds the current state; `onCheckedChange` is called " +
                      "when the user taps or slides it. Always hoist state to the caller."
    ),
    ToggleDemo(
        type        = ToggleDemoType.SWITCH_THUMB_ICON,
        title       = "Switch — Thumb Icon",
        description = "Pass a SwitchDefaults.Thumb with an `icon` composable to stamp " +
                      "an icon into the thumb. The icon typically changes between states " +
                      "(e.g. check ↔ cross) to reinforce meaning beyond colour alone."
    ),
    ToggleDemo(
        type        = ToggleDemoType.SWITCH_CUSTOM_COLORS,
        title       = "Switch — Custom Colours",
        description = "SwitchDefaults.colors() lets you override every colour token: " +
                      "checkedTrackColor, checkedThumbColor, uncheckedTrackColor, " +
                      "uncheckedThumbColor, uncheckedBorderColor, and their disabled variants."
    ),
    ToggleDemo(
        type        = ToggleDemoType.SWITCH_DISABLED,
        title       = "Switch — Disabled State",
        description = "Pass `enabled = false` to prevent interaction. Compose applies " +
                      "disabledCheckedTrackColor / disabledUncheckedTrackColor automatically. " +
                      "Disabled state is important for communicating unavailable options."
    ),
    ToggleDemo(
        type        = ToggleDemoType.SWITCH_LABELED_ROW,
        title       = "Switch — Labeled Row (Settings pattern)",
        description = "The most common real-world pattern: a full-width Row with a title + " +
                      "description on the left and a Switch on the right. The entire Row " +
                      "is made clickable via Modifier.clickable { } so the label also toggles."
    ),

    // ── Checkbox ──────────────────────────────────────────────────────────────
    ToggleDemo(
        type        = ToggleDemoType.CHECKBOX_BASIC,
        title       = "Checkbox — Basic",
        description = "Checkbox has three relevant parameters: `checked: Boolean`, " +
                      "`onCheckedChange: ((Boolean) -> Unit)?`, and `enabled: Boolean`. " +
                      "Pass null for onCheckedChange to make it read-only."
    ),
    ToggleDemo(
        type        = ToggleDemoType.CHECKBOX_TRISTATE,
        title       = "Checkbox — Tristate (Indeterminate)",
        description = "TriStateCheckbox accepts a `ToggleableState` (On, Off, Indeterminate). " +
                      "Use Indeterminate when a parent option partially covers child options, " +
                      "e.g. a 'Select all' header whose children are partially checked."
    ),
    ToggleDemo(
        type        = ToggleDemoType.CHECKBOX_LABELED,
        title       = "Checkbox — With Clickable Label",
        description = "Wrap a Checkbox and Text in a Row with Modifier.clickable { } so " +
                      "tapping the label also toggles the checkbox. " +
                      "This is the standard accessible pattern for form checkboxes."
    ),
    ToggleDemo(
        type        = ToggleDemoType.CHECKBOX_GROUP,
        title       = "Checkbox — Group with Select All",
        description = "A parent TriStateCheckbox drives child checkboxes. The parent state " +
                      "is derived: On if all children are checked, Off if none, " +
                      "Indeterminate if some. Clicking the parent toggles all children."
    ),
    ToggleDemo(
        type        = ToggleDemoType.CHECKBOX_DISABLED,
        title       = "Checkbox — Disabled State",
        description = "Pass `enabled = false` to prevent interaction while retaining visual " +
                      "state. Both checked-disabled and unchecked-disabled are shown here."
    ),

    // ── RadioButton ───────────────────────────────────────────────────────────
    ToggleDemo(
        type        = ToggleDemoType.RADIO_BASIC,
        title       = "RadioButton — Basic",
        description = "RadioButton renders a single circular toggle. It does NOT manage " +
                      "mutual exclusion itself — that is your responsibility via shared state. " +
                      "`selected: Boolean` and `onClick: (() -> Unit)?` are the key params."
    ),
    ToggleDemo(
        type        = ToggleDemoType.RADIO_GROUP_VERTICAL,
        title       = "RadioButton — Vertical Group",
        description = "The canonical single-selection pattern: one state variable holds " +
                      "the selected index. Each RadioButton + Text Row sets `selected = " +
                      "(selectedIndex == i)` and `onClick = { selectedIndex = i }`."
    ),
    ToggleDemo(
        type        = ToggleDemoType.RADIO_GROUP_HORIZONTAL,
        title       = "RadioButton — Horizontal Group",
        description = "Same logic as a vertical group but laid out in a Row. " +
                      "Useful for short option sets like size pickers (S / M / L / XL) " +
                      "or alignment selectors."
    ),
    ToggleDemo(
        type        = ToggleDemoType.RADIO_CUSTOM_COLORS,
        title       = "RadioButton — Custom Colours",
        description = "RadioButtonDefaults.colors() accepts `selectedColor` and " +
                      "`unselectedColor`. Use it to match a brand palette or to create " +
                      "colour-coded option groups."
    ),

    // ── Combined ──────────────────────────────────────────────────────────────
    ToggleDemo(
        type        = ToggleDemoType.FORM_NOTIFICATIONS,
        title       = "Real-World Form — Notifications Settings",
        description = "A realistic settings card combining all three toggle types: " +
                      "a master Switch, feature Checkboxes, and a RadioButton frequency group. " +
                      "All state is local; in a real app it would be hoisted to a ViewModel."
    ),
)
