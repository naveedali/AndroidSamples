package com.naveedali.collapsingtoolbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import com.naveedali.collapsingtoolbar.screens.CustomCollapsingScreen
import com.naveedali.collapsingtoolbar.screens.LargeTopBarScreen
import com.naveedali.collapsingtoolbar.screens.MediumTopBarScreen
import com.naveedali.collapsingtoolbar.screens.ParallaxScreen
import com.naveedali.collapsingtoolbar.screens.PinnedScreen
import com.naveedali.collapsingtoolbar.ui.theme.CollapsingToolBarTheme
import kotlinx.coroutines.launch

private val tabs = listOf(
    "Large Bar",
    "Medium Bar",
    "Pinned",
    "Parallax",
    "Custom",
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CollapsingToolBarTheme {
                val pagerState = rememberPagerState { tabs.size }
                val coroutineScope = rememberCoroutineScope()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.statusBars),
                ) {
                    ScrollableTabRow(selectedTabIndex = pagerState.currentPage) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                text = { Text(text = title) },
                            )
                        }
                    }

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.weight(1f),
                        beyondViewportPageCount = 1,
                    ) { page ->
                        when (page) {
                            0 -> LargeTopBarScreen()
                            1 -> MediumTopBarScreen()
                            2 -> PinnedScreen()
                            3 -> ParallaxScreen()
                            4 -> CustomCollapsingScreen()
                        }
                    }
                }
            }
        }
    }
}
