package com.naveedali.uiwithcompose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naveedali.uiwithcompose.model.ToggleDemo
import com.naveedali.uiwithcompose.model.ToggleDemoType
import com.naveedali.uiwithcompose.model.toggleDemos
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme

// ─────────────────────────────────────────────────────────────────────────────
// TogglesScreen
//
// Entry-point composable for the Switches / RadioButtons / Checkboxes screen.
// Renders a LazyColumn of demo cards, one per ToggleDemoType.
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TogglesScreen(onBack: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Switches, Checkboxes & Radios") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor             = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor          = MaterialTheme.colorScheme.onPrimaryContainer,
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

            itemsIndexed(
                items = toggleDemos,
                key   = { _, demo -> demo.type.name }
            ) { index, demo ->
                ToggleDemoCard(index = index + 1, demo = demo)
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ToggleDemoCard
//
// Wrapper card with a numbered header, description, and the live demo.
// `when` dispatches to the matching private composable.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun ToggleDemoCard(index: Int, demo: ToggleDemo) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ── Header ────────────────────────────────────────────────────────
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text  = "%02d".format(index),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text  = demo.title,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Text(
                text  = demo.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // ── Live demo ─────────────────────────────────────────────────────
            when (demo.type) {
                ToggleDemoType.SWITCH_BASIC          -> SwitchBasicDemo()
                ToggleDemoType.SWITCH_THUMB_ICON     -> SwitchThumbIconDemo()
                ToggleDemoType.SWITCH_CUSTOM_COLORS  -> SwitchCustomColorsDemo()
                ToggleDemoType.SWITCH_DISABLED       -> SwitchDisabledDemo()
                ToggleDemoType.SWITCH_LABELED_ROW    -> SwitchLabeledRowDemo()
                ToggleDemoType.CHECKBOX_BASIC        -> CheckboxBasicDemo()
                ToggleDemoType.CHECKBOX_TRISTATE     -> CheckboxTristateDemo()
                ToggleDemoType.CHECKBOX_LABELED      -> CheckboxLabeledDemo()
                ToggleDemoType.CHECKBOX_GROUP        -> CheckboxGroupDemo()
                ToggleDemoType.CHECKBOX_DISABLED     -> CheckboxDisabledDemo()
                ToggleDemoType.RADIO_BASIC           -> RadioBasicDemo()
                ToggleDemoType.RADIO_GROUP_VERTICAL  -> RadioGroupVerticalDemo()
                ToggleDemoType.RADIO_GROUP_HORIZONTAL -> RadioGroupHorizontalDemo()
                ToggleDemoType.RADIO_CUSTOM_COLORS   -> RadioCustomColorsDemo()
                ToggleDemoType.FORM_NOTIFICATIONS    -> FormNotificationsDemo()
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Switch demos ──────────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 01 · Basic Switch ─────────────────────────────────────────────────────────
// Switch is the go-to component for a binary setting.
// `checked`         — current on/off state (must be hoisted)
// `onCheckedChange` — called with the new value after the user interaction
// The thumb slides from left (false) to right (true) with a spring animation.
@Composable
private fun SwitchBasicDemo() {
    var checked by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(
            text  = if (checked) "Enabled" else "Disabled",
            style = MaterialTheme.typography.bodyMedium
        )
        Switch(
            checked         = checked,
            onCheckedChange = { checked = it }
        )
    }
}

// ── 02 · Switch with Thumb Icon ───────────────────────────────────────────────
// SwitchDefaults.Thumb accepts an `icon` composable parameter.
// The icon changes between states to add a secondary visual cue.
// This is especially useful for users with colour-vision deficiency.
@Composable
private fun SwitchThumbIconDemo() {
    var checked by rememberSaveable { mutableStateOf(true) }

    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(
            text  = if (checked) "Notifications on" else "Notifications off",
            style = MaterialTheme.typography.bodyMedium
        )
        Switch(
            checked         = checked,
            onCheckedChange = { checked = it },
            thumbContent    = {
                // The icon inside the thumb — sized to SwitchDefaults.IconSize (16.dp)
                Icon(
                    imageVector        = if (checked) Icons.Default.Check
                                         else         Icons.Default.Close,
                    contentDescription = null,
                    modifier           = Modifier.size(SwitchDefaults.IconSize)
                )
            }
        )
    }
}

// ── 03 · Switch with Custom Colours ───────────────────────────────────────────
// SwitchDefaults.colors() lets you override every colour slot:
//   checkedTrackColor     — track fill when switch is ON
//   checkedThumbColor     — circle colour when switch is ON
//   uncheckedTrackColor   — track fill when switch is OFF
//   uncheckedThumbColor   — circle colour when switch is OFF
//   uncheckedBorderColor  — track border when OFF
@Composable
private fun SwitchCustomColorsDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

        // Green "eco" switch
        var eco by rememberSaveable { mutableStateOf(false) }
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text("Eco Mode", style = MaterialTheme.typography.bodyMedium)
            Switch(
                checked         = eco,
                onCheckedChange = { eco = it },
                colors          = SwitchDefaults.colors(
                    checkedTrackColor   = Color(0xFF2E7D32),  // dark green
                    checkedThumbColor   = Color.White
                )
            )
        }

        // Red "danger" switch
        var danger by rememberSaveable { mutableStateOf(false) }
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text("Developer Mode", style = MaterialTheme.typography.bodyMedium)
            Switch(
                checked         = danger,
                onCheckedChange = { danger = it },
                colors          = SwitchDefaults.colors(
                    checkedTrackColor   = MaterialTheme.colorScheme.error,
                    checkedThumbColor   = MaterialTheme.colorScheme.onError
                )
            )
        }
    }
}

// ── 04 · Disabled Switch ──────────────────────────────────────────────────────
// `enabled = false` prevents all interaction and applies muted colours.
// Always keep the visual state (checked/unchecked) to communicate the locked
// value to the user — don't hide disabled controls.
@Composable
private fun SwitchDisabledDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text("Disabled — Off", style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Switch(checked = false, onCheckedChange = null, enabled = false)
        }
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text("Disabled — On", style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Switch(checked = true, onCheckedChange = null, enabled = false)
        }
    }
}

// ── 05 · Switch — Labeled Row (Settings pattern) ──────────────────────────────
// The full-width clickable row pattern used in real settings screens.
//
// Key technique: add Modifier.clickable(role = Role.Switch) { toggle() } to the
// Row. This:
//   • Extends the tap target to the entire row (not just the switch thumb)
//   • Annotates the semantics node with Role.Switch so TalkBack reads it correctly
//   • Shows a ripple across the whole row width
@Composable
private fun SwitchLabeledRowDemo() {
    var wifi       by rememberSaveable { mutableStateOf(true) }
    var bluetooth  by rememberSaveable { mutableStateOf(false) }
    var location   by rememberSaveable { mutableStateOf(true) }

    val items = listOf(
        Triple("Wi-Fi",     "Connect to nearby networks", wifi)     to { wifi = !wifi },
        Triple("Bluetooth", "Pair with nearby devices",   bluetooth) to { bluetooth = !bluetooth },
        Triple("Location",  "Allow apps to access location", location) to { location = !location },
    )

    Column {
        items.forEachIndexed { i, (info, toggle) ->
            val (title, subtitle, state) = info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // The entire row is a single tap target
                    .clickable(role = Role.Switch, onClick = toggle)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(title,    style = MaterialTheme.typography.bodyMedium)
                    Text(subtitle, style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Switch(
                    checked         = state,
                    // null here because the Row's clickable already handles toggling
                    onCheckedChange = null
                )
            }
            if (i < items.lastIndex) HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Checkbox demos ────────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 06 · Basic Checkbox ───────────────────────────────────────────────────────
// Checkbox parameters mirror Switch:
//   `checked`         — Boolean state
//   `onCheckedChange` — called with new value; pass null for read-only
//   `enabled`         — false = greyed out and non-interactive
@Composable
private fun CheckboxBasicDemo() {
    var checked by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier          = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Checkbox(
            checked         = checked,
            onCheckedChange = { checked = it }
        )
        Text(
            text  = if (checked) "Checked  ✓" else "Unchecked",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// ── 07 · Tristate Checkbox ────────────────────────────────────────────────────
// TriStateCheckbox uses `ToggleableState` instead of Boolean:
//   ToggleableState.On           — all children selected
//   ToggleableState.Off          — no children selected
//   ToggleableState.Indeterminate — some children selected (shows a dash)
//
// `onClick` (not `onCheckedChange`) is the callback — it doesn't receive a value
// because the next state is determined by your own logic.
@Composable
private fun CheckboxTristateDemo() {
    var state by rememberSaveable { mutableStateOf(ToggleableState.Indeterminate) }

    Row(
        modifier          = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TriStateCheckbox(
            state   = state,
            onClick = {
                // Cycle: Indeterminate → On → Off → Indeterminate
                state = when (state) {
                    ToggleableState.Indeterminate -> ToggleableState.On
                    ToggleableState.On            -> ToggleableState.Off
                    ToggleableState.Off           -> ToggleableState.Indeterminate
                }
            }
        )
        Text(
            text  = "State: $state  (tap to cycle)",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// ── 08 · Checkbox with Clickable Label ────────────────────────────────────────
// Accessibility requirement: the label text must also toggle the checkbox.
// Pattern:
//   Row(Modifier.clickable(role = Role.Checkbox) { toggle() }) {
//       Checkbox(checked = x, onCheckedChange = null)   ← null: Row owns the click
//       Text(...)
//   }
// Setting onCheckedChange = null on the Checkbox prevents double-firing.
@Composable
private fun CheckboxLabeledDemo() {
    val options = remember {
        mutableStateOf(
            listOf(
                "I agree to the Terms of Service" to false,
                "Subscribe to newsletter"         to true,
                "Enable analytics"                to false,
            )
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        options.value.forEachIndexed { i, (label, checked) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // Role.Checkbox tells TalkBack what kind of control this is
                    .clickable(role = Role.Checkbox) {
                        options.value = options.value.toMutableList().also {
                            it[i] = label to !checked
                        }
                    }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Checkbox(
                    checked         = checked,
                    onCheckedChange = null   // Row.clickable owns the interaction
                )
                Text(label, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

// ── 09 · Checkbox Group with Select All ───────────────────────────────────────
// Classic "Select All" pattern:
//   • Parent state is DERIVED from children — never set manually
//   • derivedStateOf { } recomputes only when the underlying list changes
//   • Clicking the parent toggles ALL children at once
@Composable
private fun CheckboxGroupDemo() {
    // The items and their checked states
    var items by rememberSaveable {
        mutableStateOf(
            listOf(
                "Kotlin"    to true,
                "Java"      to false,
                "Swift"     to false,
                "TypeScript" to true,
            )
        )
    }

    // Derive parent state from children — do not store it separately
    val parentState by remember {
        derivedStateOf {
            when {
                items.all  { it.second } -> ToggleableState.On
                items.none { it.second } -> ToggleableState.Off
                else                      -> ToggleableState.Indeterminate
            }
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {

        // ── Parent "Select All" row ───────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(role = Role.Checkbox) {
                    // If any are unchecked, check all; otherwise uncheck all
                    val newValue = parentState != ToggleableState.On
                    items = items.map { (label, _) -> label to newValue }
                }
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TriStateCheckbox(
                state   = parentState,
                onClick = null   // Row.clickable owns the interaction
            )
            Text(
                text       = "Select all languages",
                style      = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        HorizontalDivider(modifier = Modifier.padding(start = 40.dp, bottom = 4.dp))

        // ── Child rows ────────────────────────────────────────────────────────
        items.forEachIndexed { i, (label, checked) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(role = Role.Checkbox) {
                        items = items.toMutableList().also { it[i] = label to !checked }
                    }
                    .padding(start = 16.dp, top = 4.dp, bottom = 4.dp),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Checkbox(
                    checked         = checked,
                    onCheckedChange = null
                )
                Text(label, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

// ── 10 · Disabled Checkbox ────────────────────────────────────────────────────
// Shows both checked-disabled and unchecked-disabled states.
// Compose applies `disabledCheckedColor` / `disabledUncheckedColor` automatically.
@Composable
private fun CheckboxDisabledDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Checkbox(checked = false, onCheckedChange = null, enabled = false)
            Text("Disabled off", style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Checkbox(checked = true, onCheckedChange = null, enabled = false)
            Text("Disabled on", style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── RadioButton demos ─────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 11 · Basic RadioButton ────────────────────────────────────────────────────
// RadioButton shows its selected/unselected visual state but does NOT enforce
// mutual exclusion — you must track which option is selected in shared state.
// `selected`  — whether this particular button is chosen
// `onClick`   — called when the user taps; set null for read-only
@Composable
private fun RadioBasicDemo() {
    var selected by rememberSaveable { mutableIntStateOf(0) }

    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        repeat(3) { i ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                RadioButton(
                    selected = (selected == i),
                    onClick  = { selected = i }
                )
                Text("Option ${i + 1}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

// ── 12 · Vertical RadioButton Group ──────────────────────────────────────────
// The standard pattern for a mutually-exclusive vertical option list.
// One `selectedIndex` drives all RadioButtons — selecting one automatically
// deselects the others because their `selected` parameter re-evaluates.
@Composable
private fun RadioGroupVerticalDemo() {
    val themes  = listOf("System default", "Light", "Dark", "High contrast")
    var selected by rememberSaveable { mutableIntStateOf(0) }

    Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
        themes.forEachIndexed { index, label ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // Role.RadioButton ensures TalkBack reads this as a radio group item
                    .clickable(role = Role.RadioButton) { selected = index }
                    .padding(vertical = 4.dp),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButton(
                    selected = (selected == index),
                    onClick  = null    // Row.clickable owns the interaction
                )
                Text(label, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

// ── 13 · Horizontal RadioButton Group ────────────────────────────────────────
// Same mutual-exclusion logic in a Row — compact for short option sets.
// Works well for size pickers, rating scales, or view-mode selectors.
@Composable
private fun RadioGroupHorizontalDemo() {
    val sizes    = listOf("XS", "S", "M", "L", "XL")
    var selected by rememberSaveable { mutableIntStateOf(2) }   // default: M

    Column(
        modifier            = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            sizes.forEachIndexed { index, label ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable(role = Role.RadioButton) { selected = index }
                ) {
                    RadioButton(
                        selected = (selected == index),
                        onClick  = null
                    )
                    Text(
                        label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (selected == index) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        Text(
            text  = "Selected size: ${sizes[selected]}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

// ── 14 · RadioButton Custom Colours ──────────────────────────────────────────
// RadioButtonDefaults.colors() accepts:
//   `selectedColor`   — fill colour of the outer ring + inner dot when selected
//   `unselectedColor` — colour of the outer ring when not selected
//   `disabledSelectedColor` / `disabledUnselectedColor` — for disabled state
@Composable
private fun RadioCustomColorsDemo() {
    val priorities = listOf(
        Triple("Low",    0, Color(0xFF2E7D32)),   // green
        Triple("Medium", 1, Color(0xFFF57F17)),   // amber
        Triple("High",   2, Color(0xFFC62828)),   // red
    )
    var selected by rememberSaveable { mutableIntStateOf(0) }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        priorities.forEach { (label, index, color) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(role = Role.RadioButton) { selected = index }
                    .padding(vertical = 4.dp),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButton(
                    selected = (selected == index),
                    onClick  = null,
                    colors   = RadioButtonDefaults.colors(
                        selectedColor   = color,
                        unselectedColor = color.copy(alpha = 0.5f)
                    )
                )
                Text(
                    text  = "$label priority",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (selected == index) color
                            else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Combined pattern ─────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 15 · Realistic Notifications Settings Form ────────────────────────────────
// A realistic card combining:
//   • A master Switch that enables / disables the whole section
//   • Checkboxes for per-feature notification types
//   • A RadioButton group for delivery frequency
//
// The Checkbox and RadioButton rows are alpha-dimmed when the master switch is
// off to communicate that they are conditionally inactive.
@Composable
private fun FormNotificationsDemo() {
    // Master toggle — controls whether the whole section is interactive
    var masterEnabled by rememberSaveable { mutableStateOf(true) }

    // Per-type checkboxes
    var emailNotif    by rememberSaveable { mutableStateOf(true) }
    var pushNotif     by rememberSaveable { mutableStateOf(true) }
    var smsNotif      by rememberSaveable { mutableStateOf(false) }

    // Delivery frequency (RadioButton group)
    val frequencies    = listOf("Immediately", "Hourly digest", "Daily digest")
    var freqSelected  by rememberSaveable { mutableIntStateOf(0) }

    // When master is off the inner controls are visually dimmed
    val childAlpha = if (masterEnabled) 1f else 0.4f

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // ── Master switch ─────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(role = Role.Switch) { masterEnabled = !masterEnabled }
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (masterEnabled) Icons.Default.Notifications
                                  else               Icons.Default.NotificationsOff,
                    contentDescription = null,
                    tint = if (masterEnabled) MaterialTheme.colorScheme.primary
                           else               MaterialTheme.colorScheme.onSurfaceVariant
                )
                Column {
                    Text("Notifications", style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold)
                    Text("Receive updates and alerts",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Switch(
                checked         = masterEnabled,
                onCheckedChange = null,  // Row owns the click
                thumbContent    = {
                    Icon(
                        imageVector = if (masterEnabled) Icons.Default.Check
                                      else               Icons.Default.Close,
                        contentDescription = null,
                        modifier    = Modifier.size(SwitchDefaults.IconSize)
                    )
                }
            )
        }

        HorizontalDivider()

        // ── Notification type checkboxes ─────────────────────────────────────
        SectionLabel("Notify via", alpha = childAlpha)

        CheckboxRow(
            label   = "Email",
            checked = emailNotif,
            enabled = masterEnabled,
            onToggle = { emailNotif = it }
        )
        CheckboxRow(
            label    = "Push notification",
            checked  = pushNotif,
            enabled  = masterEnabled,
            onToggle = { pushNotif = it }
        )
        CheckboxRow(
            label    = "SMS",
            checked  = smsNotif,
            enabled  = masterEnabled,
            onToggle = { smsNotif = it }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

        // ── Frequency radio group ─────────────────────────────────────────────
        SectionLabel("Frequency", alpha = childAlpha)

        frequencies.forEachIndexed { index, label ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = masterEnabled, role = Role.RadioButton) {
                        freqSelected = index
                    }
                    .padding(start = 4.dp, top = 2.dp, bottom = 2.dp),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                RadioButton(
                    selected = (freqSelected == index),
                    onClick  = null,
                    enabled  = masterEnabled
                )
                Text(
                    text  = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (masterEnabled) MaterialTheme.colorScheme.onSurface
                            else               MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// ── Private helpers for FormNotificationsDemo ─────────────────────────────────

@Composable
private fun SectionLabel(text: String, alpha: Float = 1f) {
    Text(
        text     = text,
        style    = MaterialTheme.typography.labelMedium,
        color    = MaterialTheme.colorScheme.primary.copy(alpha = alpha),
        modifier = Modifier.padding(start = 4.dp, top = 4.dp, bottom = 2.dp)
    )
}

@Composable
private fun CheckboxRow(
    label: String,
    checked: Boolean,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, role = Role.Checkbox) { onToggle(!checked) }
            .padding(start = 4.dp, top = 2.dp, bottom = 2.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Checkbox(
            checked         = checked,
            onCheckedChange = null,
            enabled         = enabled
        )
        Text(
            text  = label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (enabled) MaterialTheme.colorScheme.onSurface
                    else         MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────
@Preview(showBackground = true)
@Composable
fun TogglesScreenPreview() {
    UiWithComposeTheme {
        TogglesScreen()
    }
}
