package com.naveedali.collapsingtoolbar.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
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
 * Demonstrates MediumTopAppBar + exitUntilCollapsedScrollBehavior.
 *
 * Behavior: The medium title collapses to a small title when scrolling up,
 * but does NOT re-expand until the list is scrolled all the way back to the top.
 * Great for detail screens where re-expanding the header would feel jarring.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediumTopBarScreen() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "Bird Specialties")
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options",
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
                    title = "exitUntilCollapsedScrollBehavior",
                    description = "The title collapses on scroll up and stays collapsed " +
                        "until you scroll all the way back to the top of the list.",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }
            items(birdList) { bird ->
                BirdCard(bird = bird)
            }
        }
    }
}
