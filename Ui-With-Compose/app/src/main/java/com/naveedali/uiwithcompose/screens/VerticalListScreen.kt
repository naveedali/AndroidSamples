package com.naveedali.uiwithcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme
import kotlinx.coroutines.delay

// ─────────────────────────────────────────────────────────────────────────────
// VerticalListScreen
//
// Entry-point composable for LazyColumn learning samples.
// This screen uses tabs instead of a card catalogue so each list pattern can
// use the full screen area:
//   • Normal list
//   • Searchable content
//   • Pull to refresh
//   • Sticky headers
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerticalListScreen(onBack: () -> Unit = {}) {
    val tabs = listOf("Normal", "Search", "Refresh", "Headers")
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Vertical List") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // TabRow is a clean way to switch between list patterns while
            // keeping the screen structure simple and easy to compare.
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> NormalVerticalListTab()
                1 -> SearchableVerticalListTab()
                2 -> PullToRefreshVerticalListTab()
                else -> StickyHeadersVerticalListTab()
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Tab 1 · Normal list ──────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// Basic LazyColumn example with stable keys, spacing, and content padding.
// This is the foundational pattern behind feeds, settings pages, and task lists.
@Composable
private fun NormalVerticalListTab() {
    val articles = remember {
        listOf(
            ListRowItem("Compose Basics", "Learn state, layout, and modifiers.", Icons.Default.Description),
            ListRowItem("Material 3 Patterns", "Use Scaffold, AppBars, Buttons, and Cards.", Icons.Default.Folder),
            ListRowItem("Team Notes", "Capture ideas and next steps for the sprint.", Icons.Default.Person),
            ListRowItem("Navigation Setup", "Wire routes and screen transitions cleanly.", Icons.Default.Description),
            ListRowItem("UI Review", "Check spacing, typography, and accessibility.", Icons.Default.Folder),
            ListRowItem("Release Checklist", "Verify QA, screenshots, and notes before shipping.", Icons.Default.Person),
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ScreenIntro(
            title = "Normal LazyColumn",
            description = "A standard vertical list with spacing, padding, and stable item keys."
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(articles, key = { it.title }) { item ->
                StandardListRow(item = item)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Tab 2 · Searchable content ───────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// Searchable lists are just regular lists filtered by query state.
// The search field lives above the LazyColumn and the filtered data drives items().
@Composable
private fun SearchableVerticalListTab() {
    val allItems = remember {
        listOf(
            ListRowItem("Android Architecture", "ViewModel, state hoisting, repository layer", Icons.Default.Description),
            ListRowItem("Compose Animation", "AnimatedVisibility and animate*AsState", Icons.Default.Folder),
            ListRowItem("Design Tokens", "Colors, spacing, and type scales", Icons.Default.Person),
            ListRowItem("Navigation Testing", "Back stack and route validation", Icons.Default.Description),
            ListRowItem("Pull To Refresh", "Refreshing content with gesture support", Icons.Default.Folder),
            ListRowItem("Sticky Headers", "Sectioned lists grouped by category", Icons.Default.Person),
        )
    }
    var query by rememberSaveable { mutableStateOf("") }

    val filteredItems = remember(query, allItems) {
        if (query.isBlank()) allItems
        else {
            allItems.filter {
                it.title.contains(query, ignoreCase = true) ||
                    it.subtitle.contains(query, ignoreCase = true)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenIntro(
            title = "Searchable LazyColumn",
            description = "Filter the backing list based on search text and render only matching rows."
        )

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true,
            label = { Text("Search topics") },
            placeholder = { Text("Try: animation, design, sticky") }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (filteredItems.isEmpty()) {
                item {
                    EmptyStateCard(
                        title = "No matches found",
                        subtitle = "Try a different keyword or clear the search query."
                    )
                }
            } else {
                items(filteredItems, key = { it.title }) { item ->
                    StandardListRow(item = item)
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Tab 3 · Pull to refresh ──────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// PullToRefreshBox wraps scrollable content and coordinates the gesture.
// When refresh begins, we simulate network work and then update the list state.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PullToRefreshVerticalListTab() {
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    var refreshCount by rememberSaveable { mutableIntStateOf(0) }
    var refreshSeed by rememberSaveable { mutableLongStateOf(0L) }
    val pullState = rememberPullToRefreshState()

    val items = remember(refreshSeed) {
        listOf(
            "Latest updates",
            "Project dashboard",
            "Weekly recap",
            "Message inbox",
            "Upload status",
            "Build results"
        ).mapIndexed { index, title ->
            ListRowItem(
                title = title,
                subtitle = "Refresh cycle ${(refreshSeed + 1)} • Item ${index + 1}",
                icon = if (index % 2 == 0) Icons.Default.Folder else Icons.Default.Description
            )
        }
    }

    if (isRefreshing) {
        androidx.compose.runtime.LaunchedEffect(refreshSeed) {
            delay(1200)
            refreshSeed += 1
            refreshCount += 1
            isRefreshing = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenIntro(
            title = "Pull To Refresh",
            description = "Wrap a LazyColumn in PullToRefreshBox and toggle `isRefreshing` while new data loads."
        )

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                if (!isRefreshing) isRefreshing = true
            },
            state = pullState,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = "Refreshed $refreshCount ${if (refreshCount == 1) "time" else "times"}",
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                items(items, key = { it.title + it.subtitle }) { item ->
                    StandardListRow(item = item)
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Tab 4 · Sticky headers ───────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// stickyHeader keeps a section label pinned at the top while its group scrolls.
// This is especially useful for contacts, countries, months, and categorized data.
@Composable
private fun StickyHeadersVerticalListTab() {
    val groupedContacts = remember {
        linkedMapOf(
            "A" to listOf("Adeel Khan", "Aisha Malik", "Ali Raza", "Ahsan", "Arslan"),
            "B" to listOf("Bilal Ahmed", "Bushra Noor","Babar"),
            "C" to listOf("Cyra Hasan", "Celine Joseph"),
            "D" to listOf("Danish Shah", "Dua Fatima","Dilawar","Danyal"),
        )
    }
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenIntro(
            title = "Sticky Headers",
            description = "Section labels stay pinned while items in that group scroll underneath."
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            groupedContacts.forEach { (section, people) ->
                stickyHeader(key = "header_$section") {
                    HeaderChip(section = section)
                }

                items(people, key = { name -> "$section-$name" }) { name ->
                    ContactRow(name = name)
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Shared helpers
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun ScreenIntro(
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun StandardListRow(item: ListRowItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun EmptyStateCard(
    title: String,
    subtitle: String,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun HeaderChip(section: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = "Section $section",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ContactRow(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(
                    color = Color(0xFFE3F2FD),
                    shape = MaterialTheme.shapes.small
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color(0xFF1565C0)
            )
        }
        Column {
            Text(name, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Tap to open profile",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private data class ListRowItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
)

@Preview(showBackground = true)
@Composable
private fun VerticalListScreenPreview() {
    UiWithComposeTheme {
        VerticalListScreen()
    }
}
