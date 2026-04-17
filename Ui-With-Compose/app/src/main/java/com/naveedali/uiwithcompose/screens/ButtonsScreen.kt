package com.naveedali.uiwithcompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naveedali.uiwithcompose.model.ButtonDemo
import com.naveedali.uiwithcompose.model.ButtonDemoType
import com.naveedali.uiwithcompose.model.buttonDemos
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// ─────────────────────────────────────────────────────────────────────────────
// ButtonsScreen
//
// Entry-point composable. Renders a LazyColumn of demo cards, one per
// ButtonDemoType. Each card dispatches to the appropriate private composable
// via a `when` expression inside ButtonDemoCard.
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsScreen(onBack: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buttons") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor         = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor      = MaterialTheme.colorScheme.onPrimaryContainer,
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
                items = buttonDemos,
                key   = { _, demo -> demo.type.name }
            ) { index, demo ->
                ButtonDemoCard(index = index + 1, demo = demo)
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ButtonDemoCard
//
// Wraps each demo in a consistent Card with a numbered header and description.
// The `when` expression dispatches to the correct private demo composable.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun ButtonDemoCard(index: Int, demo: ButtonDemo) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ── Card header ───────────────────────────────────────────────────
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
                ButtonDemoType.FILLED             -> FilledButtonDemo()
                ButtonDemoType.FILLED_TONAL       -> FilledTonalButtonDemo()
                ButtonDemoType.OUTLINED           -> OutlinedButtonDemo()
                ButtonDemoType.TEXT_BUTTON        -> TextButtonDemo()
                ButtonDemoType.ELEVATED           -> ElevatedButtonDemo()
                ButtonDemoType.ICON_BUTTON        -> IconButtonDemo()
                ButtonDemoType.FILLED_ICON_BUTTON -> FilledIconButtonDemo()
                ButtonDemoType.OUTLINED_ICON_BUTTON -> OutlinedIconButtonDemo()
                ButtonDemoType.TONAL_ICON_BUTTON  -> TonalIconButtonDemo()
                ButtonDemoType.TOGGLE_ICON_BUTTON -> ToggleIconButtonDemo()
                ButtonDemoType.FAB                -> FabDemo()
                ButtonDemoType.SMALL_FAB          -> SmallFabDemo()
                ButtonDemoType.LARGE_FAB          -> LargeFabDemo()
                ButtonDemoType.EXTENDED_FAB       -> ExtendedFabDemo()
                ButtonDemoType.DISABLED           -> DisabledButtonsDemo()
                ButtonDemoType.LOADING            -> LoadingButtonDemo()
                ButtonDemoType.WITH_ICON          -> ButtonWithIconDemo()
                ButtonDemoType.BUTTON_SIZES       -> ButtonSizesDemo()
                ButtonDemoType.CUSTOM_COLORS      -> CustomColorsDemo()
                ButtonDemoType.CUSTOM_SHAPE       -> CustomShapeDemo()
                ButtonDemoType.SEGMENTED          -> SegmentedButtonDemo()
                ButtonDemoType.CHIP_ASSIST        -> AssistChipDemo()
                ButtonDemoType.CHIP_FILTER        -> FilterChipDemo()
                ButtonDemoType.CHIP_INPUT         -> InputChipDemo()
                ButtonDemoType.CHIP_SUGGESTION    -> SuggestionChipDemo()
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Material 3 button hierarchy ───────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 01 · Filled Button ────────────────────────────────────────────────────────
// The default `Button` composable renders a filled container.
// Use it for the single most-important action on a surface.
//
// onClick   — the lambda called when the user taps the button.
// enabled   — set false to prevent interaction (handled automatically by Compose).
// modifier  — standard layout modifier; fillMaxWidth() is common for form buttons.
@Composable
private fun FilledButtonDemo() {
    var clickCount by remember { mutableIntStateOf(0) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier            = Modifier.fillMaxWidth()
    ) {
        Button(onClick = { clickCount++ }) {
            Text("Submit")
        }
        if (clickCount > 0) {
            Text(
                text  = "Tapped $clickCount time${if (clickCount == 1) "" else "s"}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// ── 02 · Filled Tonal Button ──────────────────────────────────────────────────
// FilledTonalButton uses `secondaryContainer` as the background.
// Softer than Filled — use when two actions have similar importance.
@Composable
private fun FilledTonalButtonDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilledTonalButton(onClick = {}) {
            Text("Cancel")
        }
        Button(onClick = {}) {          // Pair with primary for visual hierarchy
            Text("Confirm")
        }
    }
}

// ── 03 · Outlined Button ──────────────────────────────────────────────────────
// OutlinedButton has no fill — only a visible border stroke.
// Low–medium emphasis; commonly paired with a primary Filled button.
@Composable
private fun OutlinedButtonDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        OutlinedButton(onClick = {}) {
            Text("Discard")
        }
        Button(onClick = {}) {
            Text("Save Draft")
        }
    }
}

// ── 04 · Text Button ──────────────────────────────────────────────────────────
// TextButton is the lowest-emphasis button — no container, no border.
// Ideal for inline actions or dialog button rows.
@Composable
private fun TextButtonDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        TextButton(onClick = {}) {
            Text("Cancel")
        }
        TextButton(onClick = {}) {
            Text("Learn More")
        }
    }
}

// ── 05 · Elevated Button ──────────────────────────────────────────────────────
// ElevatedButton renders a surface-coloured button with a drop shadow.
// Good on flat surfaces where a filled button would be too heavy.
@Composable
private fun ElevatedButtonDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ElevatedButton(onClick = {}) {
            Text("Share")
        }
        ElevatedButton(onClick = {}) {
            Text("Export")
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Icon Buttons ──────────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 06 · Icon Button (standard) ───────────────────────────────────────────────
// IconButton is a 48 dp × 48 dp tap target with no visible container.
// The touch target meets Material accessibility minimums even though the icon
// itself is only 24 dp.
//
// Always set a meaningful contentDescription so screen readers can describe it.
@Composable
private fun IconButtonDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
            Icon(Icons.Default.Favorite, contentDescription = "Favourite")
        }
        IconButton(onClick = {}) {
            Icon(Icons.Default.Share, contentDescription = "Share")
        }
        IconButton(onClick = {}) {
            Icon(Icons.Default.Search, contentDescription = "Search")
        }
        IconButton(onClick = {}) {
            Icon(Icons.Default.Edit, contentDescription = "Edit")
        }
        // Disabled icon button — same as enabled but greyed out automatically.
        IconButton(onClick = {}, enabled = false) {
            Icon(Icons.Default.Send, contentDescription = "Send (disabled)")
        }
    }
}

// ── 07 · Filled Icon Button ───────────────────────────────────────────────────
// FilledIconButton wraps the icon in a filled container.
// High-emphasis icon action — e.g. a primary Confirm or Send.
@Composable
private fun FilledIconButtonDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        FilledIconButton(onClick = {}) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
        FilledIconButton(onClick = {}) {
            Icon(Icons.Default.Send, contentDescription = "Send")
        }
        FilledIconButton(onClick = {}, enabled = false) {
            Icon(Icons.Default.Edit, contentDescription = "Edit (disabled)")
        }
    }
}

// ── 08 · Outlined Icon Button ─────────────────────────────────────────────────
// OutlinedIconButton has no fill — only a border ring. Medium emphasis.
@Composable
private fun OutlinedIconButtonDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        OutlinedIconButton(onClick = {}) {
            Icon(Icons.Default.Favorite, contentDescription = "Favourite")
        }
        OutlinedIconButton(onClick = {}) {
            Icon(Icons.Default.Share, contentDescription = "Share")
        }
        OutlinedIconButton(onClick = {}) {
            Icon(Icons.Default.Search, contentDescription = "Search")
        }
    }
}

// ── 09 · Tonal Icon Button ────────────────────────────────────────────────────
// FilledTonalIconButton uses secondaryContainer as background — gentler than
// FilledIconButton; good for secondary icon actions in a group.
@Composable
private fun TonalIconButtonDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        FilledTonalIconButton(onClick = {}) {
            Icon(Icons.Default.Favorite, contentDescription = "Favourite")
        }
        FilledTonalIconButton(onClick = {}) {
            Icon(Icons.Default.Bookmark, contentDescription = "Bookmark")
        }
        FilledTonalIconButton(onClick = {}) {
            Icon(Icons.Default.Share, contentDescription = "Share")
        }
    }
}

// ── 10 · Toggle Icon Button ───────────────────────────────────────────────────
// IconToggleButton is stateful — it exposes `checked: Boolean` and
// `onCheckedChange: (Boolean) -> Unit` just like a Checkbox.
//
// Pattern:
//   var checked by remember { mutableStateOf(false) }
//   IconToggleButton(checked = checked, onCheckedChange = { checked = it }) { ... }
//
// The icon inside typically switches to reflect state (hollow ↔ filled).
@Composable
private fun ToggleIconButtonDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        // ── Favourite toggle ──────────────────────────────────────────────────
        var isFavourited by rememberSaveable { mutableStateOf(false) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconToggleButton(
                checked         = isFavourited,
                onCheckedChange = { isFavourited = it }
            ) {
                Icon(
                    imageVector = if (isFavourited) Icons.Default.Favorite
                                  else              Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavourited) "Remove from favourites"
                                         else              "Add to favourites",
                    tint = if (isFavourited) MaterialTheme.colorScheme.primary
                           else              MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text  = if (isFavourited) "Liked" else "Like",
                style = MaterialTheme.typography.labelSmall,
                color = if (isFavourited) MaterialTheme.colorScheme.primary
                        else              MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // ── Bookmark toggle ───────────────────────────────────────────────────
        var isBookmarked by rememberSaveable { mutableStateOf(false) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconToggleButton(
                checked         = isBookmarked,
                onCheckedChange = { isBookmarked = it }
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Bookmark
                                  else              Icons.Default.BookmarkBorder,
                    contentDescription = if (isBookmarked) "Remove bookmark"
                                         else              "Add bookmark",
                    tint = if (isBookmarked) MaterialTheme.colorScheme.secondary
                           else              MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text  = if (isBookmarked) "Saved" else "Save",
                style = MaterialTheme.typography.labelSmall,
                color = if (isBookmarked) MaterialTheme.colorScheme.secondary
                        else              MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // ── Star toggle ───────────────────────────────────────────────────────
        var isStarred by rememberSaveable { mutableStateOf(false) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconToggleButton(
                checked         = isStarred,
                onCheckedChange = { isStarred = it }
            ) {
                Icon(
                    imageVector        = Icons.Default.Star,
                    contentDescription = if (isStarred) "Unstar" else "Star",
                    tint = if (isStarred) Color(0xFFFFC107)   // amber
                           else           MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text  = if (isStarred) "Starred" else "Star",
                style = MaterialTheme.typography.labelSmall,
                color = if (isStarred) Color(0xFFFFC107)
                        else           MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── FAB variants ──────────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 11 · Standard FAB ────────────────────────────────────────────────────────
// FloatingActionButton defaults to 56 dp and uses primaryContainer.
// In real apps it goes in Scaffold's `floatingActionButton` slot — shown inline
// here for demo purposes.
@Composable
private fun FabDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        FloatingActionButton(onClick = {}) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

// ── 12 · Small FAB ───────────────────────────────────────────────────────────
// SmallFloatingActionButton is 40 dp — use for secondary or density-heavy UIs.
@Composable
private fun SmallFabDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        SmallFloatingActionButton(onClick = {}) {
            Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(18.dp))
        }
    }
}

// ── 13 · Large FAB ───────────────────────────────────────────────────────────
// LargeFloatingActionButton is 96 dp — used when the primary action deserves
// extra prominence (e.g. Compose button in a mail app).
@Composable
private fun LargeFabDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        LargeFloatingActionButton(onClick = {}) {
            Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(36.dp))
        }
    }
}

// ── 14 · Extended FAB ────────────────────────────────────────────────────────
// ExtendedFloatingActionButton accepts both `icon` and `text` slots.
// Use when the icon alone might not communicate the action clearly enough.
@Composable
private fun ExtendedFabDemo() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ExtendedFloatingActionButton(
            onClick = {},
            icon    = { Icon(Icons.Default.Edit, contentDescription = null) },
            text    = { Text("Compose") }
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── State & behaviour demos ───────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 15 · Disabled state ───────────────────────────────────────────────────────
// Pass `enabled = false` to any button composable. Compose automatically:
//   • Applies disabledContainerColor / disabledContentColor
//   • Removes the click handler (onClick is never called)
//   • Sets the semantics role to disabled (accessible)
@Composable
private fun DisabledButtonsDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {}, enabled = false)           { Text("Filled") }
            FilledTonalButton(onClick = {}, enabled = false) { Text("Tonal") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = {}, enabled = false)   { Text("Outlined") }
            TextButton(onClick = {}, enabled = false)       { Text("Text") }
            ElevatedButton(onClick = {}, enabled = false)   { Text("Elevated") }
        }
    }
}

// ── 16 · Loading / in-progress button ────────────────────────────────────────
// Pattern for an async-aware button:
//   1. `isLoading` state — set true when the action starts, false when done.
//   2. `enabled = !isLoading` — prevents double-submission.
//   3. The button content switches between a label and a progress indicator.
//
// `rememberCoroutineScope()` returns a CoroutineScope tied to the composable's
// lifecycle — safe to launch coroutines from onClick.
@Composable
private fun LoadingButtonDemo() {
    var isLoading by remember { mutableStateOf(false) }
    val scope     = rememberCoroutineScope()

    Column(
        modifier            = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick  = {
                if (!isLoading) {
                    isLoading = true
                    // Simulate a 2-second async operation
                    scope.launch {
                        delay(2_000)
                        isLoading = false
                    }
                }
            },
            enabled  = !isLoading,
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            if (isLoading) {
                // Replace label with a small progress indicator
                CircularProgressIndicator(
                    modifier  = Modifier.size(18.dp),
                    color     = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
                Spacer(Modifier.width(8.dp))
                Text("Loading…")
            } else {
                Text("Upload File")
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text  = if (isLoading) "Processing… (tap is blocked)" else "Tap to simulate upload",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ── 17 · Button with leading icon ────────────────────────────────────────────
// Button's `content` lambda is a `RowScope`, so you can place an Icon and Text
// side by side. Use `Spacer(Modifier.width(8.dp))` as the gap (or rely on the
// default icon spacing from ButtonDefaults.IconSpacing).
@Composable
private fun ButtonWithIconDemo() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier            = Modifier.fillMaxWidth()
    ) {
        // Leading icon on a Filled button
        Button(onClick = {}) {
            Icon(
                imageVector        = Icons.Default.Send,
                contentDescription = null,
                modifier           = Modifier.size(ButtonDefaults.IconSize)    // 18.dp
            )
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))                 // 8.dp
            Text("Send")
        }

        // Leading icon on an Outlined button
        OutlinedButton(onClick = {}) {
            Icon(
                imageVector        = Icons.Default.Share,
                contentDescription = null,
                modifier           = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            Text("Share")
        }

        // Trailing icon on a Text button
        TextButton(onClick = {}) {
            Text("Learn More")
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            Icon(
                imageVector        = Icons.Default.Star,
                contentDescription = null,
                modifier           = Modifier.size(ButtonDefaults.IconSize)
            )
        }
    }
}

// ── 18 · Custom sizes ─────────────────────────────────────────────────────────
// Buttons are sized via Modifier — Compose provides no fixed size enum.
//   fillMaxWidth()   → full-width form button
//   height(48.dp)    → taller tap target
//   contentPadding   → adjusts the internal padding so text isn't cramped
@Composable
private fun ButtonSizesDemo() {
    Column(
        modifier            = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Full-width (typical form submit button)
        Button(
            onClick  = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Full-width Button")
        }

        // Tall button (48 dp height instead of the default 40 dp)
        Button(
            onClick  = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text("Tall Button — 56 dp")
        }

        // Small / compact button (custom padding)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick        = {},
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("Compact", style = MaterialTheme.typography.labelSmall)
            }
            Button(
                onClick        = {},
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("Small", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

// ── 19 · Custom colours ───────────────────────────────────────────────────────
// ButtonDefaults.buttonColors() lets you override every colour token:
//   containerColor         — background when enabled
//   contentColor           — label / icon colour when enabled
//   disabledContainerColor — background when disabled
//   disabledContentColor   — label / icon colour when disabled
//
// The same pattern works for filledTonalButtonColors(), outlinedButtonColors(),
// textButtonColors(), and elevatedButtonColors().
@Composable
private fun CustomColorsDemo() {
    Column(
        modifier            = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Red danger button
        Button(
            onClick = {},
            colors  = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor   = MaterialTheme.colorScheme.onError
            )
        ) {
            Text("Delete Account")
        }

        // Custom brand-colour tonal button
        FilledTonalButton(
            onClick = {},
            colors  = ButtonDefaults.filledTonalButtonColors(
                containerColor = Color(0xFF1A73E8),   // Google-blue
                contentColor   = Color.White
            )
        ) {
            Text("Sign in with Google")
        }

        // Success-green outlined button
        OutlinedButton(
            onClick  = {},
            colors   = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF2E7D32)      // dark green
            ),
            border   = androidx.compose.foundation.BorderStroke(
                1.dp, Color(0xFF2E7D32)
            )
        ) {
            Icon(
                imageVector        = Icons.Default.Check,
                contentDescription = null,
                modifier           = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            Text("Approved")
        }
    }
}

// ── 20 · Custom shapes ────────────────────────────────────────────────────────
// The `shape` parameter accepts any `Shape`. Material 3 defaults to CircleShape
// (fully pill-shaped). Override to match your design language.
@Composable
private fun CustomShapeDemo() {
    Column(
        modifier            = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Fully rounded (default Material 3 / pill shape)
        Button(
            onClick = {},
            shape   = RoundedCornerShape(50)   // == CircleShape for a button
        ) {
            Text("Pill Shape (default)")
        }

        // Rectangular — zero rounding
        Button(
            onClick = {},
            shape   = RectangleShape
        ) {
            Text("Rectangle Shape")
        }

        // Slightly rounded corners (like Material 2 style)
        Button(
            onClick = {},
            shape   = RoundedCornerShape(8.dp)
        ) {
            Text("Rounded 8 dp")
        }

        // Cut / chamfered corners
        Button(
            onClick = {},
            shape   = CutCornerShape(12.dp)
        ) {
            Text("Cut Corner Shape")
        }
    }
}

// ── 21 · Segmented Button ─────────────────────────────────────────────────────
// SingleChoiceSegmentedButtonRow — a compact toggle group where exactly one
// option is selected at a time. Similar to a RadioGroup but more visual.
//
// SegmentedButton parameters:
//   selected   — is this option currently chosen?
//   onClick    — callback when the user taps this segment
//   shape      — SegmentedButtonDefaults.itemShape() ensures correct rounding
//               (first/middle/last segments have different rounded corners)
//   label      — composable label slot
//   icon       — icon slot (shows a check when selected by default)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SegmentedButtonDemo() {
    val options  = listOf("Day", "Week", "Month")
    var selected by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier            = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    selected = (selected == index),
                    onClick  = { selected = index },
                    shape    = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    )
                ) {
                    Text(label)
                }
            }
        }
        Text(
            text  = "Selected: ${options[selected]}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Chips ─────────────────────────────────────────────────────────────────────
//
// Chips are compact interactive elements — think of them as small, focused
// buttons with a specific semantic role. Material 3 defines 4 chip types.
// ─────────────────────────────────────────────────────────────────────────────

// ── 22 · Assist Chip ─────────────────────────────────────────────────────────
// AssistChip is for contextual smart actions — e.g. "Add to calendar", "Get directions".
// It is NOT toggleable; it triggers an action on click (like a Button).
// `leadingIcon` accepts any composable (usually an Icon).
@Composable
private fun AssistChipDemo() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            AssistChip(
                onClick      = {},
                label        = { Text("Add to calendar") },
                leadingIcon  = {
                    Icon(
                        imageVector        = Icons.Default.CalendarToday,
                        contentDescription = null,
                        modifier           = Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )
        }
        item {
            AssistChip(
                onClick = {},
                label   = { Text("Share") },
                leadingIcon = {
                    Icon(
                        imageVector        = Icons.Default.Share,
                        contentDescription = null,
                        modifier           = Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )
        }
        item {
            AssistChip(
                onClick  = {},
                label    = { Text("Disabled") },
                enabled  = false
            )
        }
    }
}

// ── 23 · Filter Chip ──────────────────────────────────────────────────────────
// FilterChip is TOGGLEABLE — used to filter a list.
// When `selected = true`, it shows a leading check icon automatically.
// `selected` + `onSelectedChange` provide state — hoist to caller when needed.
@Composable
private fun FilterChipDemo() {
    val filters = remember {
        mutableStateOf(
            listOf(
                "Kotlin" to true,
                "Compose" to true,
                "Android" to false,
                "Coroutines" to false,
                "Flow" to true,
            )
        )
    }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(filters.value.size) { i ->
            val (label, selected) = filters.value[i]
            FilterChip(
                selected         = selected,
                onClick          = {
                    // Toggle the selected state for this chip
                    filters.value = filters.value.toMutableList().also {
                        it[i] = label to !selected
                    }
                },
                label            = { Text(label) },
                leadingIcon      = if (selected) {
                    {
                        Icon(
                            imageVector        = Icons.Default.Check,
                            contentDescription = "Selected",
                            modifier           = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else null
            )
        }
    }
}

// ── 24 · Input Chip ───────────────────────────────────────────────────────────
// InputChip represents a value the user has entered or selected — e.g. an email
// recipient, a tag, or a search token. It can show a leading avatar/icon and a
// trailing `×` dismiss icon to remove itself.
@Composable
private fun InputChipDemo() {
    val tags = remember {
        mutableStateOf(
            listOf("naveed@example.com", "john@example.com", "android@kotlin.dev")
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text  = "To:",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(tags.value.size) { i ->
                val tag = tags.value[i]
                InputChip(
                    selected     = false,   // InputChip is not typically toggled
                    onClick      = {},
                    label        = { Text(tag) },
                    leadingIcon  = {
                        Icon(
                            imageVector        = Icons.Default.Person,
                            contentDescription = null,
                            modifier           = Modifier.size(InputChipDefaults.AvatarSize)
                        )
                    },
                    trailingIcon = {
                        // Dismiss icon removes the chip
                        Icon(
                            imageVector        = Icons.Default.Close,
                            contentDescription = "Remove $tag",
                            modifier           = Modifier
                                .size(InputChipDefaults.AvatarSize)
                                .also { /* onClick is handled by the chip's own click area */ }
                        )
                    },
                    // Override chip's onClick to remove the tag when the chip is tapped
                    // (in a real app you'd handle the X separately via a separate modifier)
                )
            }
        }
        Text(
            text  = "Tap a chip to see it selected; in a real app the × removes the tag.",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ── 25 · Suggestion Chip ──────────────────────────────────────────────────────
// SuggestionChip is read-only — it proposes a value but is never shown as
// "selected". Trigger an action on click (e.g. auto-fill a text field).
@Composable
private fun SuggestionChipDemo() {
    val suggestions = listOf("Jetpack Compose", "Material 3", "Kotlin Coroutines",
                             "StateFlow", "Room Database")
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(suggestions.size) { i ->
            SuggestionChip(
                onClick = {},
                label   = { Text(suggestions[i]) },
                icon    = if (i == 0) {
                    {
                        Icon(
                            imageVector        = Icons.Default.Star,
                            contentDescription = null,
                            modifier           = Modifier.size(SuggestionChipDefaults.IconSize),
                            tint               = Color(0xFFFFC107)
                        )
                    }
                } else null
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────
@Preview(showBackground = true)
@Composable
fun ButtonsScreenPreview() {
    UiWithComposeTheme {
        ButtonsScreen()
    }
}
