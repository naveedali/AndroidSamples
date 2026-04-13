package com.naveedali.uiwithcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme
import kotlinx.coroutines.launch

// ─────────────────────────────────────────────────────────────────────────────
// BottomNavItem — a simple data holder for one tab in the bottom bar.
//
// Keeping it as a local data class (rather than a sealed class) is fine here
// because the bottom nav items don't carry destination logic — that belongs
// in Screen.kt / AppNavGraph.kt.
// ─────────────────────────────────────────────────────────────────────────────
private data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)

private val bottomNavItems = listOf(
    BottomNavItem("Home",    Icons.Default.Home),
    BottomNavItem("Search",  Icons.Default.Search),
    BottomNavItem("Profile", Icons.Default.Person)
)

// ─────────────────────────────────────────────────────────────────────────────
// ScaffoldDemoScreen
//
// Purpose: demonstrate every slot that Scaffold exposes.
//
// Slots covered:
//   ① topBar          → TopAppBar  (back arrow + title + info action)
//   ② bottomBar       → NavigationBar  (3 tab items with selection state)
//   ③ floatingActionButton → FloatingActionButton  (triggers a Snackbar)
//   ④ snackbarHost    → SnackbarHost  (receives messages from the FAB)
//   ⑤ content         → LazyColumn  (main body, offset by innerPadding)
//
// onBack — called when the user presses the back arrow in the TopAppBar.
//          The caller (AppNavGraph) handles the actual navigation pop.
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)   // TopAppBar is still experimental in M3
@Composable
fun ScaffoldDemoScreen(onBack: () -> Unit = {}) {

    // ── State ────────────────────────────────────────────────────────────────

    // `remember` keeps this value alive across recompositions of this screen.
    // `mutableIntStateOf` is an optimised alternative to `mutableStateOf<Int>`
    // — prefer it whenever the state is a primitive Int.
    // `by` (property delegation) means we read/write `selectedTab` directly
    // instead of `selectedTab.value`.
    var selectedTab by remember { mutableIntStateOf(0) }

    // SnackbarHostState is the controller object for Snackbar messages.
    // It must be created here and passed to BOTH:
    //   • Scaffold's `snackbarHost` slot  (so Scaffold knows WHERE to draw it)
    //   • showSnackbar() calls            (so we can trigger messages)
    val snackbarHostState = remember { SnackbarHostState() }

    // rememberCoroutineScope() returns a CoroutineScope tied to this
    // composable's lifecycle. We need it because showSnackbar() is a
    // suspend function — it must run inside a coroutine.
    val scope = rememberCoroutineScope()

    // ── Scaffold ─────────────────────────────────────────────────────────────
    Scaffold(

        // ── ① topBar ─────────────────────────────────────────────────────────
        // TopAppBar is the Material 3 component for the bar at the top of
        // a screen. It has three areas:
        //   navigationIcon  → typically a back arrow or hamburger menu
        //   title           → the screen name or current context
        //   actions         → one or more icon buttons on the right side
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Scaffold Demo",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    // IconButton wraps an Icon inside a 48×48 dp touch target.
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"   // required for accessibility
                        )
                    }
                },
                actions = {
                    // You can add multiple action icons here.
                    IconButton(onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("ℹ️  This screen demonstrates all Scaffold slots.")
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info"
                        )
                    }
                },
                // scrollBehavior can be used to collapse/pin the TopAppBar as
                // the user scrolls. We leave it null (default = pinned) here.
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },

        // ── ② bottomBar ──────────────────────────────────────────────────────
        // NavigationBar is the Material 3 bottom navigation component.
        // It holds NavigationBarItem children, each with:
        //   icon     → the tab icon (required)
        //   label    → the text label below the icon
        //   selected → whether this tab is currently active
        //   onClick  → what happens when the user taps it
        //
        // We track which tab is selected with the `selectedTab` Int state above.
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = { Text(text = item.label) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        },

        // ── ③ floatingActionButton ────────────────────────────────────────────
        // FAB is rendered by Scaffold above the bottomBar automatically.
        // Scaffold also nudges the FAB away from the edge if a BottomBar is
        // present — you don't have to calculate padding manually.
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // showSnackbar is a suspend function — call it inside a
                    // coroutine launched with the scope we remembered above.
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "FAB tapped! — try the ℹ️ icon too",
                            actionLabel = "OK"
                        )
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Show snackbar"
                )
            }
        },

        // ── ④ snackbarHost ────────────────────────────────────────────────────
        // SnackbarHost is the composable that actually draws the Snackbar.
        // By passing our snackbarHostState here, Scaffold positions it
        // correctly (above the bottom bar, below the FAB).
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }

    ) { innerPadding ->

        // ── ⑤ content (main body) ─────────────────────────────────────────────
        // innerPadding is a PaddingValues that accounts for:
        //   • The height of the TopAppBar
        //   • The height of the NavigationBar
        //   • The system status bar and gesture-navigation bar insets
        //
        // ALWAYS apply it to the top-level composable in the content slot.
        // If you forget, your content will be hidden behind the top/bottom bars.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)          // ← critical — never skip this
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // Section title
            item {
                Text(
                    text = "Scaffold Slot Reference",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Tap the ℹ️ icon or the ＋ FAB to see the Snackbar in action.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // One info card per Scaffold slot
            item {
                ScaffoldSlotCard(
                    slotNumber = "①",
                    slotName = "topBar",
                    component = "TopAppBar",
                    description = "Bar fixed at the top of the screen. " +
                            "Holds a navigationIcon (← back / ☰ menu), " +
                            "a title, and optional action icons. " +
                            "Supports scroll behaviours (pinned, entering always, exiting until collapsed)."
                )
            }
            item {
                ScaffoldSlotCard(
                    slotNumber = "②",
                    slotName = "bottomBar",
                    component = "NavigationBar",
                    description = "Bar fixed at the bottom. " +
                            "Each NavigationBarItem needs an icon, a label, " +
                            "a selected flag, and an onClick. " +
                            "The selection state is driven by a remembered Int " +
                            "that you update on click."
                )
            }
            item {
                ScaffoldSlotCard(
                    slotNumber = "③",
                    slotName = "floatingActionButton",
                    component = "FloatingActionButton",
                    description = "Circular button that floats above the content. " +
                            "Scaffold automatically positions it above the bottomBar " +
                            "and avoids overlap — no manual padding needed."
                )
            }
            item {
                ScaffoldSlotCard(
                    slotNumber = "④",
                    slotName = "snackbarHost",
                    component = "SnackbarHost",
                    description = "Renders transient feedback messages. " +
                            "Create a SnackbarHostState with remember(), pass it here " +
                            "AND to showSnackbar(). " +
                            "showSnackbar() is a suspend function — call it inside " +
                            "a coroutine (rememberCoroutineScope + launch)."
                )
            }
            item {
                ScaffoldSlotCard(
                    slotNumber = "⑤",
                    slotName = "content",
                    component = "Your composable",
                    description = "The main body of the screen. " +
                            "The lambda receives innerPadding — a PaddingValues " +
                            "that accounts for the TopBar, BottomBar, and system bars. " +
                            "Apply it with Modifier.padding(innerPadding) on the root " +
                            "composable or your content will be obscured."
                )
            }

            // Visual innerPadding diagram
            item {
                InnerPaddingDiagram()
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ScaffoldSlotCard — one reference card per Scaffold slot.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun ScaffoldSlotCard(
    slotNumber: String,
    slotName: String,
    component: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Slot number badge
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = slotNumber,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Column {
                    // Scaffold parameter name in code style
                    Text(
                        text = slotName,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    // Material 3 component used in this slot
                    Text(
                        text = component,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            // Description
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// InnerPaddingDiagram — a visual representation of what innerPadding covers.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun InnerPaddingDiagram(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "innerPadding explained",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = "innerPadding = top (TopAppBar height + status bar) " +
                        "+ bottom (NavigationBar height + gesture bar) " +
                        "+ start/end (0 dp in this layout).\n\n" +
                        "Scaffold(content = { innerPadding ->\n" +
                        "    YourContent(\n" +
                        "        modifier = Modifier.padding(innerPadding)\n" +
                        "    )\n" +
                        "})",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScaffoldDemoScreenPreview() {
    UiWithComposeTheme {
        ScaffoldDemoScreen()
    }
}
