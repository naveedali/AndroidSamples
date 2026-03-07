package com.naveedali.collapsingtoolbar.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.naveedali.collapsingtoolbar.components.BirdCard
import com.naveedali.collapsingtoolbar.components.InfoBanner
import com.naveedali.collapsingtoolbar.data.birdList

/**
 * Demonstrates TopAppBar + pinnedScrollBehavior.
 *
 * Behavior: The toolbar never collapses — it stays always visible.
 * The only change on scroll is the container color and elevation/shadow,
 * which provide a visual cue that content is scrolling behind the bar.
 * Ideal for screens where context (the title) must always be visible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinnedScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Pinned Toolbar")
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorites",
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp,
            ),
        ) {
            item {
                InfoBanner(
                    title = "pinnedScrollBehavior",
                    description = "The toolbar never collapses. Scroll the list and " +
                        "notice the toolbar's background color changes to show elevation — " +
                        "indicating content is scrolling behind it.",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }
            items(birdList) { bird ->
                BirdCard(bird = bird)
            }
        }
    }
}
