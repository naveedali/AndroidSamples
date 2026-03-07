package com.naveedali.collapsingtoolbar.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naveedali.collapsingtoolbar.components.BirdCard
import com.naveedali.collapsingtoolbar.components.InfoBanner
import com.naveedali.collapsingtoolbar.data.birdList

private val HeaderHeight = 280.dp

/**
 * Demonstrates a custom parallax collapsing header.
 *
 * Technique: Track [LazyListState.firstVisibleItemScrollOffset] to know how far the
 * header has scrolled. Apply [graphicsLayer { translationY = -scrollOffset * 0.4f }]
 * to the header so it moves at 40% of the list's scroll speed — creating the parallax
 * illusion of depth. A scrim gradient and the TopAppBar fade in as the header exits.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParallaxScreen() {
    val listState = rememberLazyListState()
    val density = LocalDensity.current
    val headerHeightPx = with(density) { HeaderHeight.toPx() }

    // How many px the first item has scrolled off the top
    val rawScrollOffset by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 0) {
                listState.firstVisibleItemScrollOffset.toFloat()
            } else {
                headerHeightPx
            }
        }
    }

    // 0f = header fully visible, 1f = header fully scrolled away
    val scrollFraction by remember {
        derivedStateOf { (rawScrollOffset / headerHeightPx).coerceIn(0f, 1f) }
    }

    // TopAppBar fades in during the last 25% of the header scroll
    val topBarAlpha by remember {
        derivedStateOf { ((scrollFraction - 0.75f) / 0.25f).coerceIn(0f, 1f) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // ── Parallax Header (behind the list) ──────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(HeaderHeight)
                .graphicsLayer {
                    // Moves at 40% of scroll speed → parallax depth
                    translationY = -rawScrollOffset * 0.4f
                },
        ) {
            ParallaxHeaderContent()

            // Gradient scrim that fades in as you scroll
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background.copy(alpha = scrollFraction),
                            ),
                        ),
                    ),
            )
        }

        // ── Scrollable Content ──────────────────────────────────────────────
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
        ) {
            // Spacer so list content starts below the header
            item { Spacer(modifier = Modifier.height(HeaderHeight)) }

            item {
                InfoBanner(
                    title = "Custom Parallax Header",
                    description = "The header moves at 40% of the list's scroll speed, " +
                        "creating a depth illusion. A scrim and TopAppBar fade in " +
                        "as the header scrolls away.",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }

            items(birdList) { bird ->
                BirdCard(bird = bird)
            }
        }

        // ── Fading TopAppBar ────────────────────────────────────────────────
        AnimatedVisibility(
            visible = topBarAlpha > 0f,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            TopAppBar(
                title = { Text("Birds of the World") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = topBarAlpha),
                ),
            )
        }
    }
}

@Composable
private fun ParallaxHeaderContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A237E),
                        Color(0xFF283593),
                        Color(0xFF1565C0),
                        Color(0xFF0288D1),
                    ),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        // Decorative background emojis at random positions
        Text(
            text = "\uD83E\uDD85",
            fontSize = 64.sp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 24.dp, top = 40.dp)
                .graphicsLayer { alpha = 0.3f },
        )
        Text(
            text = "\uD83E\uDD89",
            fontSize = 48.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 32.dp, top = 60.dp)
                .graphicsLayer { alpha = 0.25f },
        )
        Text(
            text = "\uD83E\uDDA2",
            fontSize = 56.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 48.dp, bottom = 48.dp)
                .graphicsLayer { alpha = 0.2f },
        )

        // Central content
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "\uD83E\uDD9A",
                modifier = Modifier.size(80.dp),
                fontSize = 72.sp,
                textAlign = TextAlign.Center,
            )
        }
        Text(
            text = "Birds of the World",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            textAlign = TextAlign.Center,
        )
    }
}
