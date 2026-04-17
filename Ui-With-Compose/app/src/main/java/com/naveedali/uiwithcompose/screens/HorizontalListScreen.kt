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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naveedali.uiwithcompose.model.HorizontalListDemo
import com.naveedali.uiwithcompose.model.HorizontalListDemoType
import com.naveedali.uiwithcompose.model.horizontalListDemos
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme

// ─────────────────────────────────────────────────────────────────────────────
// HorizontalListScreen
//
// Entry-point composable for LazyRow and horizontal-list learning samples.
// Each card demonstrates one common horizontal-list pattern used in apps.
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalListScreen(onBack: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Horizontal List") },
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

            itemsIndexed(
                items = horizontalListDemos,
                key = { _, demo -> demo.type.name }
            ) { index, demo ->
                HorizontalListDemoCard(index = index + 1, demo = demo)
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// HorizontalListDemoCard
//
// Shared wrapper with numbered title, description, and live LazyRow example.
// The `when` dispatch keeps the screen structure aligned with the rest of
// the learning catalog.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun HorizontalListDemoCard(index: Int, demo: HorizontalListDemo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Text(
                text = demo.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            when (demo.type) {
                HorizontalListDemoType.BASIC_TEXT_CHIPS -> BasicTextChipsDemo()
                HorizontalListDemoType.CONTENT_PADDING -> ContentPaddingDemo()
                HorizontalListDemoType.CARD_CAROUSEL -> CardCarouselDemo()
                HorizontalListDemoType.MIXED_VIEW_TYPES -> MixedViewTypesDemo()
                HorizontalListDemoType.CATEGORY_SELECTOR -> CategorySelectorDemo()
                HorizontalListDemoType.FEATURED_PLAYLISTS -> FeaturedPlaylistsDemo()
                HorizontalListDemoType.QUICK_ACTIONS -> QuickActionsDemo()
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Core LazyRow demos ───────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 01 · Basic text chips ────────────────────────────────────────────────────
// LazyRow is the horizontal sibling of LazyColumn.
// It only composes visible items, which makes it efficient for long lists.
@Composable
private fun BasicTextChipsDemo() {
    val tags = listOf("Compose", "Android", "Material 3", "State", "Navigation", "Animation")

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(tags, key = { it }) { label ->
            Surface(
                shape = RoundedCornerShape(999.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = label,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

// ── 02 · Content padding & spacing ───────────────────────────────────────────
// contentPadding adds start/end inset so the first and last items do not sit
// flush against the screen edge. This is one of the most useful LazyRow params.
@Composable
private fun ContentPaddingDemo() {
    val items = (1..5).map { "Item $it" }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items, key = { it }) { label ->
            ElevatedCard {
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(label, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

// ── 03 · Card carousel ───────────────────────────────────────────────────────
// Horizontal carousels are common for promotions, recommendations, and previews.
@Composable
private fun CardCarouselDemo() {
    val cards = listOf(
        "Productivity Tips" to "5 min read",
        "Compose Patterns" to "Workshop",
        "Team Updates" to "Today",
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(cards, key = { it.first }) { (title, subtitle) ->
            ElevatedCard {
                Column(
                    modifier = Modifier
                        .width(180.dp)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF42A5F5), Color(0xFF1565C0))
                                ),
                                shape = RoundedCornerShape(16.dp)
                            )
                    )
                    Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                    Text(
                        subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// ── 04 · Mixed view types ────────────────────────────────────────────────────
// A single LazyRow can render different item models and different composables.
// This is the horizontal equivalent of RecyclerView with multiple view types.
@Composable
private fun MixedViewTypesDemo() {
    val items = listOf(
        MixedRowItem.Banner("Weekly Highlights"),
        MixedRowItem.Action("Trending", Icons.AutoMirrored.Filled.TrendingUp),
        MixedRowItem.Action("Downloads", Icons.Default.Download),
        MixedRowItem.Promo("Top Rated", "Editors' pick"),
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(items, key = { it.key }) { item ->
            when (item) {
                is MixedRowItem.Banner -> {
                    ElevatedCard {
                        Row(
                            modifier = Modifier
                                .width(210.dp)
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(item.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                                Text("Swipe for more", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null)
                        }
                    }
                }
                is MixedRowItem.Action -> {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Column(
                            modifier = Modifier
                                .width(110.dp)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(item.icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
                            Text(item.label, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
                is MixedRowItem.Promo -> {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFF3E0),
                            contentColor = Color(0xFF8A4B00)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .width(160.dp)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(item.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                            Text(item.subtitle, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Real-world demos ─────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 05 · Category selector ───────────────────────────────────────────────────
// A horizontal category row is just a LazyRow with selectable items.
// Stable keys help Compose preserve item identity during recomposition.
@Composable
private fun CategorySelectorDemo() {
    val categories = listOf("All", "Design", "Code", "Research", "Notes", "Audio")
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(categories, key = { _, label -> label }) { index, label ->
            Surface(
                onClick = { selectedIndex = index },
                shape = RoundedCornerShape(999.dp),
                color = if (selectedIndex == index) MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                else MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = label,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = if (selectedIndex == index) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// ── 06 · Featured playlists ──────────────────────────────────────────────────
// Repeated rich tiles are a common content-feed pattern for media apps.
@Composable
private fun FeaturedPlaylistsDemo() {
    val playlists = listOf(
        Triple("Morning Focus", "24 tracks", Color(0xFF6A1B9A)),
        Triple("Deep Work", "18 tracks", Color(0xFF00897B)),
        Triple("Late Night Jazz", "32 tracks", Color(0xFF3949AB)),
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(playlists, key = { it.first }) { (title, subtitle, color) ->
            ElevatedCard {
                Column(
                    modifier = Modifier.width(170.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(color.copy(alpha = 0.75f), color)
                                ),
                                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Headphones,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                        Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

// ── 07 · Quick actions ───────────────────────────────────────────────────────
// Small horizontal dashboards are a great use-case for compact action cards.
@Composable
private fun QuickActionsDemo() {
    val actions = listOf(
        Triple("Boost", Icons.Default.Bolt, Color(0xFFFFF3E0)),
        Triple("Favorites", Icons.Default.Favorite, Color(0xFFFCE4EC)),
        Triple("Promote", Icons.Default.Campaign, Color(0xFFE8F5E9)),
        Triple("Top Rated", Icons.Default.Star, Color(0xFFE3F2FD)),
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(actions, key = { it.first }) { (label, icon, background) ->
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = background
            ) {
                Column(
                    modifier = Modifier
                        .width(120.dp)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(icon, contentDescription = null)
                    Text(label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                    Text("Tap shortcut", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Helper models
// ─────────────────────────────────────────────────────────────────────────────

private sealed class MixedRowItem(val key: String) {
    data class Banner(val title: String) : MixedRowItem("banner_$title")
    data class Action(val label: String, val icon: ImageVector) : MixedRowItem("action_$label")
    data class Promo(val title: String, val subtitle: String) : MixedRowItem("promo_$title")
}

@Preview(showBackground = true)
@Composable
private fun HorizontalListScreenPreview() {
    UiWithComposeTheme {
        HorizontalListScreen()
    }
}
