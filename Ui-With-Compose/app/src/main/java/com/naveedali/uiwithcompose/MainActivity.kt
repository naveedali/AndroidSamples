package com.naveedali.uiwithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme

// ─────────────────────────────────────────────────────────────────────────────
// ComponentActivity vs AppCompatActivity — which one should I extend?
//
// AppCompatActivity  (from the older AndroidX AppCompat library)
//   • Was the standard base class before Jetpack Compose existed.
//   • Brings backward-compatible support for Action Bar, Toolbar, night-mode,
//     and View-based theme features (all things tied to the classic XML UI).
//   • Required when you mix traditional Views / Fragments with the older
//     support library APIs.
//
// ComponentActivity  (from androidx.activity)
//   • A lighter, more modern base class that strips out everything AppCompat
//     adds for the XML View system.
//   • It is the RECOMMENDED base class for pure Jetpack Compose projects
//     because Compose handles its own theming, navigation, and lifecycle.
//   • Still gives you full ViewModel support, result contracts,
//     permission launchers, and lifecycle events.
//
// Rule of thumb:
//   → Building with Jetpack Compose only?  ✅ use ComponentActivity
//   → Mixing Compose with legacy XML Views? ✅ use AppCompatActivity
// ─────────────────────────────────────────────────────────────────────────────
class MainActivity : ComponentActivity() {

    // onCreate is the Activity entry-point. It is called once when the Activity
    // is first created (e.g. when the app launches or after a process death).
    // savedInstanceState holds data that was saved before the Activity was last
    // destroyed (e.g. on screen rotation), or null on a fresh start.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enableEdgeToEdge() lets the app draw behind the system status bar and
        // navigation bar so the UI feels truly full-screen. The Scaffold below
        // uses WindowInsets (via innerPadding) to make sure your content is
        // never hidden underneath those system bars.
        enableEdgeToEdge()

        // setContent { } replaces the traditional setContentView(R.layout.xxx).
        // Everything inside this lambda is a Composable tree — the entire UI is
        // described in Kotlin rather than in XML layout files.
        setContent {

            // ─────────────────────────────────────────────────────────────────
            // UiWithComposeTheme
            //
            // This is an auto-generated wrapper found in ui/theme/Theme.kt.
            // It applies your app's Material 3 color scheme, typography, and
            // shapes to every Composable nested inside it.
            //
            // Why wrap everything in it?
            //   • Composables like Text, Button, Card, etc. read their colours
            //     and fonts from the nearest MaterialTheme in the tree.
            //   • Without this wrapper those components fall back to generic
            //     Material defaults rather than YOUR brand colours.
            //   • It also handles light/dark mode automatically based on the
            //     device setting.
            //
            // The name is derived from your app name — yours is "UiWithCompose",
            // so the generated theme is "UiWithComposeTheme".
            // ─────────────────────────────────────────────────────────────────
            UiWithComposeTheme {

                // ─────────────────────────────────────────────────────────────
                // Scaffold
                //
                // Scaffold is a layout helper from Material 3 that provides
                // the standard "slots" (named areas) for a screen:
                //
                //   topBar        → AppBar / TopAppBar
                //   bottomBar     → BottomNavigationBar
                //   floatingActionButton → FAB
                //   snackbarHost  → Snackbar messages
                //   content       → the main body of the screen  ← we're here
                //
                // Using Scaffold is optional but strongly recommended because:
                //   1. It positions each slot correctly relative to the others.
                //   2. It computes `innerPadding` — the combined insets needed
                //      to keep your content visible (not hidden under a TopBar,
                //      BottomBar, or system bars).
                //
                // The `content` lambda receives `innerPadding` (a PaddingValues
                // object). You MUST apply it to your root composable (via
                // Modifier.padding(innerPadding)) so content is never obscured.
                // ─────────────────────────────────────────────────────────────
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // innerPadding is passed down so content starts below any
                    // TopBar / above any BottomBar that Scaffold manages.
                    LearningHomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Practice screens — the ordered list of UI components to build and study.
//
// Each entry is a Pair<title, subtitle> so we can show a short description
// alongside the screen name without adding a separate data class right now.
//
// Added beyond the original 10 items:
//   • Text Fields   — essential for any form or search UI
//   • Dialogs & Alerts — alerts, confirmation dialogs, bottom sheets
//   • Progress Indicators — circular & linear loading states
//   • Cards          — the primary Material 3 surface container
//   • Snackbars & Toasts — brief feedback messages
//
// Feel free to reorder or extend this list as you progress.
// ─────────────────────────────────────────────────────────────────────────────
val practiceScreens: List<Pair<String, String>> = listOf(
    "TopBar, BottomBar & Content"   to "Scaffold slots, WindowInsets, innerPadding",
    "Labels"                        to "Text styles, fonts, colors, maxLines, overflow",
    "Buttons"                       to "Button, OutlinedButton, TextButton, IconButton, FAB",
    "Image Views"                   to "Image, AsyncImage (Coil), contentScale, clipping",
    "Text Fields"                   to "TextField, OutlinedTextField, keyboard options, validation",
    "Switches, RadioButtons & Checkboxes" to "State-driven toggle components",
    "Rating Bar"                    to "Custom star rating with Row + Icon",
    "Dialogs & Alerts"              to "AlertDialog, BottomSheet, DatePickerDialog",
    "Progress Indicators"           to "CircularProgressIndicator, LinearProgressIndicator",
    "Cards"                         to "Card, ElevatedCard, OutlinedCard, clickable cards",
    "Snackbars & Toasts"            to "SnackbarHost, SnackbarResult, coroutine scope",
    "Horizontal List"               to "LazyRow, item keys, content padding",
    "Vertical List"                 to "LazyColumn, stickyHeader, pull-to-refresh",
    "Grid"                          to "LazyVerticalGrid, LazyHorizontalGrid, adaptive columns",
    "Login Page"                    to "Form layout combining TextField, Button, validation",
    "Bottom Navigation"             to "NavigationBar, NavHost, back-stack management",
    "Animations"                    to "AnimatedVisibility, animate*AsState, Transition API"
)

// ─────────────────────────────────────────────────────────────────────────────
// LearningHomeScreen
//
// Displays a scrollable index of every practice screen.
// Uses LazyColumn instead of Column so the list recycles rows efficiently —
// only the visible items are composed at any given time.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun LearningHomeScreen(modifier: Modifier = Modifier) {

    // LazyColumn is the Compose equivalent of RecyclerView.
    // `verticalArrangement` adds even spacing between each item.
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        // Header item — shown once at the top of the list.
        item {
            Text(
                text = "Compose UI Practice",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        // itemsIndexed provides both the 0-based index and the item value.
        // Using `key = { index, _ -> index }` helps Compose efficiently
        // recompose only the rows that actually changed.
        itemsIndexed(
            items = practiceScreens,
            key = { index, _ -> index }
        ) { index, (title, subtitle) ->
            PracticeScreenItem(
                number = index + 1,   // display as 1-based
                title = title,
                subtitle = subtitle
            )
        }

        // Bottom spacing so the last card isn't flush with the nav bar.
        item {
            androidx.compose.foundation.layout.Spacer(
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PracticeScreenItem
//
// A single row rendered as a Material 3 Card.
// Splitting this into its own Composable keeps LearningHomeScreen readable
// and makes the row independently previewable and testable.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun PracticeScreenItem(
    number: Int,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    // Card provides a rounded, elevated surface — the standard Material 3
    // container for list rows and summary tiles.
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Step number badge
            Text(
                text = "%02d".format(number),   // e.g. "01", "12"
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Title + subtitle stacked vertically inside a Column.
            androidx.compose.foundation.layout.Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// @Preview lets Android Studio render this Composable in the Design panel
// without running the app on a device or emulator.
// `showBackground = true` adds a white background so the preview is easier to read.
//
// Best practice: always wrap preview content in your app theme so colours match
// exactly what users will see at runtime.
@Preview(showBackground = true)
@Composable
fun LearningHomeScreenPreview() {
    UiWithComposeTheme {
        LearningHomeScreen()
    }
}
