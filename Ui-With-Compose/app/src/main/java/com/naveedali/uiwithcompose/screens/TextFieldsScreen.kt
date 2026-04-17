package com.naveedali.uiwithcompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naveedali.uiwithcompose.model.TextFieldDemo
import com.naveedali.uiwithcompose.model.TextFieldDemoType
import com.naveedali.uiwithcompose.model.textFieldDemos
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme

// ─────────────────────────────────────────────────────────────────────────────
// TextFieldsScreen
//
// Renders every TextField variant defined in TextFieldModel.kt.
// Each variant gets its own DemoCard containing:
//   • a header row with title + type badge
//   • a description explaining what parameter / pattern is being shown
//   • the live TextField composable itself
//
// State design:
//   Each field owns its own remembered state so they work independently.
//   `rememberSaveable` is used where state should survive screen rotation.
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldsScreen(onBack: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Text Fields") },
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

            // itemsIndexed gives us both the 0-based index (for the badge number)
            // and the TextFieldDemo model object.
            itemsIndexed(
                items = textFieldDemos,
                key = { _, demo -> demo.type.name }   // stable key = no re-composition on scroll
            ) { index, demo ->
                TextFieldDemoCard(
                    index = index + 1,
                    demo = demo
                )
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// TextFieldDemoCard
//
// One card per TextFieldDemo. Delegates rendering of the actual field to
// a dedicated composable based on demo.type. This keeps the `when` dispatch
// clean and each variant fully self-contained.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun TextFieldDemoCard(index: Int, demo: TextFieldDemo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // ── Header ────────────────────────────────────────────────────────
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
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // ── Description ───────────────────────────────────────────────────
            Text(
                text = demo.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // ── Live demo ─────────────────────────────────────────────────────
            // Dispatch to the matching variant composable.
            when (demo.type) {
                TextFieldDemoType.BASIC              -> BasicTextFieldDemo()
                TextFieldDemoType.OUTLINED           -> OutlinedTextFieldDemo()
                TextFieldDemoType.WITH_LABEL         -> WithLabelDemo()
                TextFieldDemoType.WITH_PLACEHOLDER   -> WithPlaceholderDemo()
                TextFieldDemoType.WITH_LEADING_ICON  -> WithLeadingIconDemo()
                TextFieldDemoType.WITH_TRAILING_ICON -> WithTrailingIconDemo()
                TextFieldDemoType.PASSWORD           -> PasswordFieldDemo()
                TextFieldDemoType.EMAIL              -> EmailFieldDemo()
                TextFieldDemoType.PHONE              -> PhoneFieldDemo()
                TextFieldDemoType.NUMERIC            -> NumericFieldDemo()
                TextFieldDemoType.MULTILINE          -> MultilineFieldDemo()
                TextFieldDemoType.WITH_COUNTER       -> WithCounterDemo()
                TextFieldDemoType.WITH_ERROR         -> WithErrorDemo()
                TextFieldDemoType.WITH_PREFIX_SUFFIX -> WithPrefixSuffixDemo()
                TextFieldDemoType.READ_ONLY          -> ReadOnlyFieldDemo()
                TextFieldDemoType.DISABLED           -> DisabledFieldDemo()
                TextFieldDemoType.SEARCH             -> SearchFieldDemo()
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Individual TextField variant composables
//
// Each function is private — it only exists to be called from the `when` above.
// Every function owns its own `rememberSaveable` state so fields are isolated.
// ─────────────────────────────────────────────────────────────────────────────

// ── 01 Basic ─────────────────────────────────────────────────────────────────
// The minimal TextField. `value` holds the current string; `onValueChange`
// is called on every keystroke and must update the state — otherwise the
// field appears read-only. This is the unidirectional data-flow (UDF) pattern.
@Composable
private fun BasicTextFieldDemo() {
    var text by rememberSaveable { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier.fillMaxWidth()
    )
}

// ── 02 Outlined ───────────────────────────────────────────────────────────────
// OutlinedTextField is a drop-in replacement for TextField with an outlined
// border style instead of a filled container. The API is identical.
@Composable
private fun OutlinedTextFieldDemo() {
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier.fillMaxWidth()
    )
}

// ── 03 With Label ─────────────────────────────────────────────────────────────
// label is a composable slot shown inside the field when empty/unfocused
// and animated above the field when it gains focus or has content.
// It replaces the need for a separate Text label above the field.
@Composable
private fun WithLabelDemo() {
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Full Name") },
        modifier = Modifier.fillMaxWidth()
    )
}

// ── 04 With Placeholder ───────────────────────────────────────────────────────
// placeholder is shown when value is empty AND the field is focused.
// Unlike label, it does NOT animate — it simply disappears when typing begins.
// Use placeholder for hint text ("e.g. john@example.com").
@Composable
private fun WithPlaceholderDemo() {
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Email") },
        placeholder = { Text("e.g. john@example.com") },
        modifier = Modifier.fillMaxWidth()
    )
}

// ── 05 With Leading Icon ──────────────────────────────────────────────────────
// leadingIcon is a composable slot rendered inside the field on the left.
// Commonly used for contextual icons (search, person, lock, location).
// The icon size is automatically constrained to 24 dp by the slot.
@Composable
private fun WithLeadingIconDemo() {
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Username") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null   // decorative — label already describes purpose
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

// ── 06 With Trailing Icon ─────────────────────────────────────────────────────
// trailingIcon is rendered on the right side of the field.
// A common pattern: show a clear (✕) button only when the field has content.
// The `if` inside the slot makes the icon appear/disappear reactively.
@Composable
private fun WithTrailingIconDemo() {
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Search") },
        trailingIcon = {
            if (text.isNotEmpty()) {
                // IconButton gives the icon a 48 dp touch target for accessibility.
                IconButton(onClick = { text = "" }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear input"
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

// ── 07 Password ───────────────────────────────────────────────────────────────
// PasswordVisualTransformation replaces every character with a bullet (•).
// The trailingIcon toggles between VisualTransformation.None (visible)
// and PasswordVisualTransformation (hidden).
// `rememberSaveable` persists the visibility toggle across rotation.
@Composable
private fun PasswordFieldDemo() {
    var password by rememberSaveable { mutableStateOf("") }
    var isVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Password") },
        leadingIcon = {
            Icon(Icons.Default.Lock, contentDescription = null)
        },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(
                    imageVector = if (isVisible) Icons.Default.VisibilityOff
                                  else           Icons.Default.Visibility,
                    contentDescription = if (isVisible) "Hide password" else "Show password"
                )
            }
        },
        // Switch transformation based on visibility state
        visualTransformation = if (isVisible) VisualTransformation.None
                               else           PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction    = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

// ── 08 Email ──────────────────────────────────────────────────────────────────
// KeyboardType.Email surfaces the @ key prominently on soft keyboards.
// ImeAction.Next moves the focus to the next focusable field when tapped,
// which is the standard UX for multi-field forms.
@Composable
private fun EmailFieldDemo() {
    var email by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Email Address") },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction    = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            // Move focus to the next field when the user taps "Next" on the keyboard.
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        singleLine = true,
        modifier   = Modifier.fillMaxWidth()
    )
}

// ── 09 Phone ──────────────────────────────────────────────────────────────────
// KeyboardType.Phone opens the numeric + symbol keyboard optimised for
// phone number entry (includes +, *, #).
@Composable
private fun PhoneFieldDemo() {
    var phone by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = phone,
        onValueChange = { phone = it },
        label = { Text("Phone Number") },
        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction    = ImeAction.Done
        ),
        singleLine = true,
        modifier   = Modifier.fillMaxWidth()
    )
}

// ── 10 Numeric ────────────────────────────────────────────────────────────────
// KeyboardType.Number shows the numeric keyboard.
// The `onValueChange` lambda filters non-digit characters with `all { it.isDigit() }`,
// so even if a user pastes text it is rejected — only digits reach the state.
@Composable
private fun NumericFieldDemo() {
    var amount by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = amount,
        onValueChange = { input ->
            // Accept the new value only if every character is a digit.
            if (input.all { it.isDigit() }) amount = input
        },
        label = { Text("Quantity") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction    = ImeAction.Done
        ),
        singleLine = true,
        modifier   = Modifier.fillMaxWidth()
    )
}

// ── 11 Multiline ──────────────────────────────────────────────────────────────
// minLines sets the initial minimum height (field never collapses below this).
// maxLines caps growth — once exceeded the field scrolls internally.
// Do NOT set singleLine = true — that overrides minLines/maxLines.
@Composable
private fun MultilineFieldDemo() {
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Bio / Notes") },
        minLines = 3,
        maxLines = 6,
        modifier = Modifier.fillMaxWidth()
    )
}

// ── 12 With Counter ───────────────────────────────────────────────────────────
// supportingText is a composable slot rendered below the field.
// We use it to show "current / max" character count.
// onValueChange clamps the input to MAX_CHARS so the user can't exceed the limit.
@Composable
private fun WithCounterDemo() {
    val maxChars = 120
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { if (it.length <= maxChars) text = it },
        label = { Text("Description") },
        minLines = 3,
        maxLines = 5,
        supportingText = {
            // Align the counter to the end of the row.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text  = "${text.length} / $maxChars",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (text.length >= maxChars) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

// ── 13 Error State ────────────────────────────────────────────────────────────
// isError = true turns the border and label red.
// supportingText renders the error message below the field.
// Best practice: only show the error after the user has interacted with the
// field (isDirty flag) — avoid showing errors on an untouched form.
@Composable
private fun WithErrorDemo() {
    var email by rememberSaveable { mutableStateOf("") }
    // isDirty tracks whether the user has typed anything yet.
    var isDirty by remember { mutableStateOf(false) }

    val isError = isDirty && !email.contains("@")

    OutlinedTextField(
        value = email,
        onValueChange = {
            email = it
            isDirty = true   // mark dirty on first keystroke
        },
        label = { Text("Email") },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text  = "Please enter a valid email address",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        modifier   = Modifier.fillMaxWidth()
    )
}

// ── 14 Prefix & Suffix ────────────────────────────────────────────────────────
// prefix is rendered inside the field to the left of the typed text.
// suffix is rendered to the right of the typed text.
// Both are composable slots — you can put Text, Icon, or anything else.
// Useful for currency symbols, domain suffixes, units (kg, %, px).
@Composable
private fun WithPrefixSuffixDemo() {
    var amount by rememberSaveable { mutableStateOf("") }
    var domain by rememberSaveable { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

        // Price field with $ prefix
        OutlinedTextField(
            value = amount,
            onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) amount = it },
            label  = { Text("Price") },
            prefix = { Text("$ ") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier   = Modifier.fillMaxWidth()
        )

        // Username field with @company.com suffix
        OutlinedTextField(
            value = domain,
            onValueChange = { domain = it },
            label  = { Text("Work Username") },
            suffix = { Text(" @company.com") },
            singleLine = true,
            modifier   = Modifier.fillMaxWidth()
        )
    }
}

// ── 15 Read-Only ──────────────────────────────────────────────────────────────
// readOnly = true prevents the user from editing the value but:
//   • The field still receives focus.
//   • Text IS selectable and copyable.
//   • The soft keyboard does NOT appear.
// Use this for displaying values that come from a source of truth the user
// shouldn't change directly (e.g. auto-filled account ID, computed total).
@Composable
private fun ReadOnlyFieldDemo() {
    OutlinedTextField(
        value    = "ACC-20240417-X9F2",
        onValueChange = {},          // no-op — value is external/computed
        label    = { Text("Account ID") },
        readOnly = true,
        modifier = Modifier.fillMaxWidth()
    )
}

// ── 16 Disabled ───────────────────────────────────────────────────────────────
// enabled = false:
//   • Greys out the field with reduced opacity.
//   • Blocks taps, focus, and keyboard input entirely.
//   • The value CANNOT be selected or copied.
// Use when the field is conditionally unavailable (e.g. a promo code input
// before the user opts in to a promotion).
@Composable
private fun DisabledFieldDemo() {
    OutlinedTextField(
        value    = "Not available",
        onValueChange = {},
        label    = { Text("Promo Code") },
        enabled  = false,
        modifier = Modifier.fillMaxWidth()
    )
}

// ── 17 Search Bar ─────────────────────────────────────────────────────────────
// The `shape` parameter overrides the default rectangle/stadium shape.
// RoundedCornerShape(50) produces a pill/capsule — the typical search-bar style.
// Combining a custom shape with colours overridden via `colors` parameter
// gives full visual control without a custom composable.
@Composable
private fun SearchFieldDemo() {
    var query by rememberSaveable { mutableStateOf("") }

    TextField(
        value = query,
        onValueChange = { query = it },
        placeholder = { Text("Search…") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search")
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { query = "" }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                }
            }
        },
        shape = RoundedCornerShape(50),          // pill shape
        colors = TextFieldDefaults.colors(
            // Remove the bottom indicator line so it feels like a true search bar.
            focusedIndicatorColor   = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor  = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction    = ImeAction.Search
        ),
        singleLine = true,
        modifier   = Modifier.fillMaxWidth()
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────
@Preview(showBackground = true)
@Composable
fun TextFieldsScreenPreview() {
    UiWithComposeTheme {
        TextFieldsScreen()
    }
}
