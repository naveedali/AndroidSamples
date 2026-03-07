package com.naveedali.collapsingtoolbar.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.naveedali.collapsingtoolbar.components.BirdCard
import com.naveedali.collapsingtoolbar.components.InfoBanner
import com.naveedali.collapsingtoolbar.data.birdList

/**
 * Demonstrates LargeTopAppBar + enterAlwaysScrollBehavior.
 *
 * Behavior: The large title collapses to a small title as you scroll up.
 * When you scroll DOWN even slightly, the toolbar re-expands and slides back in.
 * The toolbar collapses and re-enters on every scroll gesture — hence "enterAlways".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeTopBarScreen() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = "Birds Encyclopedia")
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
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
                    title = "enterAlwaysScrollBehavior",
                    description = "The large title collapses when scrolling up. " +
                        "On any downward scroll the toolbar immediately re-enters from the top.",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }
            items(birdList) { bird ->
                BirdCard(bird = bird)
            }
        }
    }
}
