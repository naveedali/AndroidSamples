package com.naveedali.uiwithcompose.model

// ─────────────────────────────────────────────────────────────────────────────
// TextFieldDemoType
//
// An enum that tags every distinct TextField variant demonstrated in the app.
// Using an enum (rather than ad-hoc strings) means:
//   • The compiler catches typos at compile time.
//   • `when` expressions over the enum are exhaustive — no missed cases.
//   • Adding a new variant requires adding it here first, then the compiler
//     guides you to every `when` block that needs updating.
// ─────────────────────────────────────────────────────────────────────────────
enum class TextFieldDemoType {

    // Visual variants
    BASIC,              // Filled TextField — the default Material 3 style
    OUTLINED,           // OutlinedTextField — border instead of filled background

    // Content helpers
    WITH_LABEL,         // Animated label that floats above the field on focus
    WITH_PLACEHOLDER,   // Ghost text shown when the field is empty and unfocused
    WITH_LEADING_ICON,  // Icon on the left (e.g. search, person, lock)
    WITH_TRAILING_ICON, // Icon on the right (e.g. clear ✕, dropdown ▾)

    // Secure / specialised input
    PASSWORD,           // Obscured text + visibility toggle IconButton
    EMAIL,              // keyboardType = Email, imeAction = Next
    PHONE,              // keyboardType = Phone, numeric soft keyboard
    NUMERIC,            // keyboardType = Number, digit-only input filter

    // Layout variants
    MULTILINE,          // minLines / maxLines > 1, grows vertically
    WITH_COUNTER,       // Shows current length vs max length below the field

    // Validation
    WITH_ERROR,         // isError = true + supporting error text below

    // Decorators
    WITH_PREFIX_SUFFIX, // prefix (e.g. "$") and suffix (e.g. ".com") inside the field

    // Interaction states
    READ_ONLY,          // readOnly = true — displays value, prevents editing
    DISABLED,           // enabled = false — greyed out, not tappable

    // Custom layout
    SEARCH              // Rounded search-bar style with search + clear icons
}

// ─────────────────────────────────────────────────────────────────────────────
// TextFieldDemo
//
// A pure data holder — no UI logic — that describes one TextField variant.
// The screen renders a card for each instance in the catalogue list below.
//
// Fields:
//   type        — drives which composable variant is rendered
//   title       — short human-readable name shown as the card header
//   description — one-line explanation of what this variant demonstrates
// ─────────────────────────────────────────────────────────────────────────────
data class TextFieldDemo(
    val type: TextFieldDemoType,
    val title: String,
    val description: String
)

// ─────────────────────────────────────────────────────────────────────────────
// textFieldDemos
//
// The ordered catalogue of every TextField variant to display.
// Add or reorder entries here — the screen list updates automatically.
// ─────────────────────────────────────────────────────────────────────────────
val textFieldDemos: List<TextFieldDemo> = listOf(

    TextFieldDemo(
        type        = TextFieldDemoType.BASIC,
        title       = "Basic TextField",
        description = "Default filled style. value + onValueChange drive the state."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.OUTLINED,
        title       = "OutlinedTextField",
        description = "Border-only variant — no filled background. Same API as TextField."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.WITH_LABEL,
        title       = "With Label",
        description = "label animates from inside the field to above it on focus."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.WITH_PLACEHOLDER,
        title       = "With Placeholder",
        description = "placeholder is shown when the field is empty and not focused."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.WITH_LEADING_ICON,
        title       = "With Leading Icon",
        description = "leadingIcon slot — search, person, lock, etc."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.WITH_TRAILING_ICON,
        title       = "With Trailing Icon",
        description = "trailingIcon slot — clear button that resets the value to empty."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.PASSWORD,
        title       = "Password Field",
        description = "PasswordVisualTransformation hides text. Trailing icon toggles visibility."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.EMAIL,
        title       = "Email Field",
        description = "keyboardType = Email surfaces the @ key. imeAction = Next moves focus."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.PHONE,
        title       = "Phone Field",
        description = "keyboardType = Phone opens the numeric + symbols keyboard."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.NUMERIC,
        title       = "Numeric Field",
        description = "keyboardType = Number + input filter rejects non-digit characters."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.MULTILINE,
        title       = "Multiline Field",
        description = "minLines=3, maxLines=6. Field grows vertically as text is added."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.WITH_COUNTER,
        title       = "With Character Counter",
        description = "supportingText shows current / max length. Input is capped at the max."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.WITH_ERROR,
        title       = "Error State",
        description = "isError=true colours the border red and shows an error message below."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.WITH_PREFIX_SUFFIX,
        title       = "Prefix & Suffix",
        description = "prefix renders inside the field on the left; suffix on the right."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.READ_ONLY,
        title       = "Read-Only",
        description = "readOnly=true shows a value that cannot be edited but can be selected."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.DISABLED,
        title       = "Disabled",
        description = "enabled=false greys out the field and blocks all interaction."
    ),
    TextFieldDemo(
        type        = TextFieldDemoType.SEARCH,
        title       = "Search Bar",
        description = "Rounded pill shape via shape param. Leading search icon, trailing clear."
    )
)
