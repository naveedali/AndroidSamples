package com.naveedali.uiwithcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme

// ─────────────────────────────────────────────────────────────────────────────
// GridScreen
//
// Entry-point composable for grid-based layout samples.
// This screen uses tabs so each grid type can take over the full content area:
//   • Simple Grid
//   • Staggered Grid
//   • Multi Span Grid
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridScreen(onBack: () -> Unit = {}) {
    val tabs = listOf("Simple", "Staggered", "Multi Span")
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Grid") },
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
                0 -> SimpleGridTab()
                1 -> StaggeredGridTab()
                else -> MultiSpanGridTab()
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Tab 1 · Simple Grid ──────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// LazyVerticalGrid arranges items in regular rows/columns.
// GridCells.Fixed(2) creates exactly two columns regardless of screen width.
@Composable
private fun SimpleGridTab() {
    val tiles = listOf(
        GridTile("Photos", Icons.Default.Collections, Color(0xFFE3F2FD)),
        GridTile("Layouts", Icons.Default.Dashboard, Color(0xFFE8F5E9)),
        GridTile("Ideas", Icons.Default.AutoAwesome, Color(0xFFFFF3E0)),
        GridTile("Gallery", Icons.Default.Collections, Color(0xFFF3E5F5)),
        GridTile("Boards", Icons.Default.Dashboard, Color(0xFFE0F2F1)),
        GridTile("Explore", Icons.Default.AutoAwesome, Color(0xFFFFEBEE)),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        GridIntro(
            title = "Simple Grid",
            description = "A fixed two-column LazyVerticalGrid. Great for dashboards, albums, or shortcut tiles."
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tiles, key = { it.title }) { tile ->
                GridTileCard(tile = tile, height = 120.dp)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Tab 2 · Staggered Grid ───────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// Staggered grids are useful when item heights vary, such as photo feeds,
// masonry galleries, or mixed editorial cards.
@Composable
private fun StaggeredGridTab() {
    val cards = listOf(
        StaggeredTile("Mountains", 110.dp, Color(0xFF90CAF9)),
        StaggeredTile("Design Notes", 170.dp, Color(0xFFA5D6A7)),
        StaggeredTile("Travel Plans", 130.dp, Color(0xFFFFCC80)),
        StaggeredTile("Reading List", 190.dp, Color(0xFFCE93D8)),
        StaggeredTile("Mood Board", 145.dp, Color(0xFF80CBC4)),
        StaggeredTile("Recipes", 210.dp, Color(0xFFEF9A9A)),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        GridIntro(
            title = "Staggered Grid",
            description = "LazyVerticalStaggeredGrid lets items keep different heights instead of forcing a strict row structure."
        )

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalItemSpacing = 12.dp,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(cards, key = { _, item -> item.title }) { _, item ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = item.color.copy(alpha = 0.42f)),
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(item.height)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(item.color.copy(alpha = 0.55f), item.color)
                                )
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Tab 3 · Multi Span Grid ──────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// Multi-span grids let some items take more columns than others.
// This is ideal for section banners, hero cards, or "featured" items mixed
// with regular grid cells.
@Composable
private fun MultiSpanGridTab() {
    val sections = listOf(
        SpanItem.Banner("Featured Collection", "A full-width hero card across the grid"),
        SpanItem.Standard("Summer Vibes", Icons.Default.Collections),
        SpanItem.Standard("Workspace", Icons.Default.Dashboard),
        SpanItem.Wide("Team Highlights", Icons.Default.AutoAwesome),
        SpanItem.Standard("Sketches", Icons.Default.Collections),
        SpanItem.Standard("Reports", Icons.Default.Dashboard),
        SpanItem.Banner("Recommended For You", "Another example using maxLineSpan for section emphasis"),
        SpanItem.Standard("Concepts", Icons.Default.AutoAwesome),
        SpanItem.Standard("Exports", Icons.Default.Collections),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        GridIntro(
            title = "Multi Span Grid",
            description = "Grid item spans allow banners and featured cards to stretch across multiple columns."
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = sections,
                key = { it.key },
                span = { item ->
                    when (item) {
                        is SpanItem.Banner -> GridItemSpan(maxLineSpan)
                        is SpanItem.Wide -> GridItemSpan(2)
                        is SpanItem.Standard -> GridItemSpan(1)
                    }
                }
            ) { item ->
                when (item) {
                    is SpanItem.Banner -> BannerGridCard(item)
                    is SpanItem.Wide -> WideGridCard(item)
                    is SpanItem.Standard -> StandardGridCard(item)
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Shared helpers
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun GridIntro(
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
private fun GridTileCard(
    tile: GridTile,
    height: androidx.compose.ui.unit.Dp,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = tile.background),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = tile.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = tile.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun BannerGridCard(item: SpanItem.Banner) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun WideGridCard(item: SpanItem.Wide) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(18.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(item.icon, contentDescription = null)
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun StandardGridCard(item: SpanItem.Standard) {
    GridTileCard(
        tile = GridTile(
            title = item.title,
            icon = item.icon,
            background = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        ),
        height = 112.dp
    )
}

private data class GridTile(
    val title: String,
    val icon: ImageVector,
    val background: Color,
)

private data class StaggeredTile(
    val title: String,
    val height: androidx.compose.ui.unit.Dp,
    val color: Color,
)

private sealed class SpanItem(val key: String) {
    data class Banner(val title: String, val subtitle: String) : SpanItem("banner_$title")
    data class Wide(val title: String, val icon: ImageVector) : SpanItem("wide_$title")
    data class Standard(val title: String, val icon: ImageVector) : SpanItem("standard_$title")
}

@Preview(showBackground = true)
@Composable
private fun GridScreenPreview() {
    UiWithComposeTheme {
        GridScreen()
    }
}
