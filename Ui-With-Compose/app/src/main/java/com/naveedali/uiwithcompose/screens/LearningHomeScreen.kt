package com.naveedali.uiwithcompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
// Practice screen catalogue — ordered list of UI components to build.
//
// List<Pair<title, subtitle>>  keeps this dependency-free.
// We'll graduate to a proper data class once the list needs more fields
// (e.g. an icon or a "completed" flag).
// ─────────────────────────────────────────────────────────────────────────────
val practiceScreens: List<Pair<String, String>> = listOf(
    "TopBar, BottomBar & Content"         to "Scaffold slots, WindowInsets, innerPadding",
    "Labels"                              to "Text styles, fonts, colors, maxLines, overflow",
    "Buttons"                             to "Button, OutlinedButton, TextButton, IconButton, FAB",
    "Image Views"                         to "Image, AsyncImage (Coil), contentScale, clipping",
    "Text Fields"                         to "TextField, OutlinedTextField, keyboard options, validation",
    "Switches, RadioButtons & Checkboxes" to "State-driven toggle components",
    "Rating Bar"                          to "Custom star rating with Row + Icon",
    "Dialogs & Alerts"                    to "AlertDialog, BottomSheet, DatePickerDialog",
    "Progress Indicators"                 to "CircularProgressIndicator, LinearProgressIndicator",
    "Cards"                               to "Card, ElevatedCard, OutlinedCard, clickable cards",
    "Snackbars & Toasts"                  to "SnackbarHost, SnackbarResult, coroutine scope",
    "Horizontal List"                     to "LazyRow, item keys, content padding",
    "Vertical List"                       to "LazyColumn, stickyHeader, pull-to-refresh",
    "Grid"                                to "LazyVerticalGrid, LazyHorizontalGrid, adaptive columns",
    "Login Page"                          to "Form layout combining TextField, Button, validation",
    "Bottom Navigation"                   to "NavigationBar, NavHost, back-stack management",
    "Animations"                          to "AnimatedVisibility, animate*AsState, Transition API"
)

// ─────────────────────────────────────────────────────────────────────────────
// LearningHomeScreen
//
// The root screen — a scrollable index of every practice topic.
//
// onItemClick — a lambda that the screen calls when a row is tapped,
//               passing the 0-based index of the tapped item.
//               The caller (AppNavGraph) decides which destination to open.
//               This pattern is called "event hoisting": the screen owns the
//               UI but delegates decisions to its parent.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun LearningHomeScreen(
    onItemClick: (index: Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    // LearningHomeScreen owns its own Scaffold so it controls edge-to-edge
    // padding independently from any parent Scaffold.
    Scaffold { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // Header — rendered once at the top of the list.
            item {
                Text(
                    text = "Compose UI Practice",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            // itemsIndexed exposes the 0-based index alongside each item.
            // `key` lets Compose match list items across recompositions
            // without relying solely on position — important for animations
            // and correct state restoration when items are added/removed.
            itemsIndexed(
                items = practiceScreens,
                key = { index, _ -> index }
            ) { index, (title, subtitle) ->
                PracticeScreenItem(
                    number = index + 1,
                    title = title,
                    subtitle = subtitle,
                    // Forward the tap event up to the caller with the index.
                    onClick = { onItemClick(index) }
                )
            }

            // Bottom breathing room so the last card doesn't sit flush with
            // the gesture-navigation bar.
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PracticeScreenItem
//
// A tappable Material 3 Card showing a numbered step, title, and subtitle.
//
// onClick is passed in from LearningHomeScreen which got it from AppNavGraph.
// The item doesn't know or care where navigation goes — it just fires onClick.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun PracticeScreenItem(
    number: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Card(onClick = ...) makes the entire card surface tappable with a
    // built-in ripple effect. No need to wrap in a Box + clickable modifier.
    Card(
        onClick = onClick,
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
                text = "%02d".format(number),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
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

@Preview(showBackground = true)
@Composable
fun LearningHomeScreenPreview() {
    UiWithComposeTheme {
        LearningHomeScreen()
    }
}
