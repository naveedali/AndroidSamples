package com.naveedali.collapsingtoolbar.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naveedali.collapsingtoolbar.components.BirdCard
import com.naveedali.collapsingtoolbar.components.InfoBanner
import com.naveedali.collapsingtoolbar.data.birdList

private val ExpandedHeaderHeight = 220.dp
private val CollapsedHeaderHeight = 56.dp

/**
 * Demonstrates a fully custom collapsing toolbar built with [NestedScrollConnection].
 *
 * Technique: A [NestedScrollConnection] intercepts upward scroll events in [onPreScroll]
 * and consumes them to shrink the header — the list does NOT scroll while the header collapses.
 * On downward scroll, [onPostScroll] catches unconsumed deltas (when the list is at the top)
 * to re-expand the header.
 *
 * The [LazyColumn] contentPadding top = [CollapsedHeaderHeight] so list items sit just below
 * the minimum toolbar height. As the header shrinks from [ExpandedHeaderHeight] to
 * [CollapsedHeaderHeight] the items it was covering are progressively revealed.
 *
 * The hero content inside the expanded header fades out via [animateFloatAsState] as it collapses.
 * The compact title simultaneously fades in, mimicking a real app bar transition.
 */
@Composable
fun CustomCollapsingScreen() {
    val density = LocalDensity.current

    val expandedHeightPx = with(density) { ExpandedHeaderHeight.toPx() }
    val collapsedHeightPx = with(density) { CollapsedHeaderHeight.toPx() }
    val collapseRangePx = expandedHeightPx - collapsedHeightPx

    // Tracks how many px the header has been collapsed (0 = fully expanded)
    val headerCollapsePx = remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Intercept upward scroll to collapse the header first
                val delta = available.y
                if (delta < 0) {
                    val oldCollapse = headerCollapsePx.floatValue
                    val newCollapse = (oldCollapse - delta).coerceIn(0f, collapseRangePx)
                    val consumed = newCollapse - oldCollapse
                    headerCollapsePx.floatValue = newCollapse
                    return Offset(0f, -consumed)
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                // Re-expand the header when the list reaches the top and there's leftover
                // downward delta the list could not consume
                val delta = available.y
                if (delta > 0) {
                    val oldCollapse = headerCollapsePx.floatValue
                    val newCollapse = (oldCollapse - delta).coerceIn(0f, collapseRangePx)
                    val expanded = oldCollapse - newCollapse
                    headerCollapsePx.floatValue = newCollapse
                    return Offset(0f, expanded)
                }
                return Offset.Zero
            }
        }
    }

    // 0f = fully expanded, 1f = fully collapsed
    val collapseFraction = headerCollapsePx.floatValue / collapseRangePx

    // Hero content fades out in the first 60% of collapse
    val heroAlpha by animateFloatAsState(
        targetValue = (1f - collapseFraction / 0.6f).coerceIn(0f, 1f),
        label = "heroAlpha",
    )

    // Compact title fades in during the last 40% of collapse
    val titleAlpha by animateFloatAsState(
        targetValue = ((collapseFraction - 0.6f) / 0.4f).coerceIn(0f, 1f),
        label = "titleAlpha",
    )

    val currentHeaderHeightDp = with(density) {
        (expandedHeightPx - headerCollapsePx.floatValue).toDp()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection),
    ) {
        // ── List — sits behind the header; top padding = collapsed bar height ──
        // Items in the range [CollapsedHeaderHeight..ExpandedHeaderHeight] are covered
        // by the expanding header and revealed as it collapses.
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = CollapsedHeaderHeight,
                bottom = 16.dp,
            ),
        ) {
            item {
                InfoBanner(
                    title = "Custom NestedScrollConnection",
                    description = "Upward scrolls collapse the header before the list moves. " +
                        "Downward scrolls re-expand the header once the list reaches the top. " +
                        "Items are revealed from behind the collapsing header.",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }
            items(birdList) { bird ->
                BirdCard(bird = bird)
            }
        }

        // ── Collapsing Header — drawn on top of the list ────────────────────
        CollapsingHeader(
            heightDp = currentHeaderHeightDp,
            heroAlpha = heroAlpha,
            titleAlpha = titleAlpha,
        )
    }
}

@Composable
private fun CollapsingHeader(
    heightDp: androidx.compose.ui.unit.Dp,
    heroAlpha: Float,
    titleAlpha: Float,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(heightDp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4A148C),
                        Color(0xFF6A1B9A),
                        Color(0xFF7B1FA2),
                        Color(0xFF8E24AA),
                    ),
                ),
            ),
    ) {
        // Compact title (fades in as toolbar collapses)
        Text(
            text = "Rare Birds",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White.copy(alpha = titleAlpha),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .graphicsLayer {
                    // Slides in from the left as it appears
                    translationX = -40f * (1f - titleAlpha)
                },
        )

        // Expanded hero content (fades out as toolbar collapses)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = heroAlpha },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "\uD83E\uDD9C",
                fontSize = 64.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Rare Birds",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Extraordinary features of the avian world",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp),
            )
        }
    }
}
