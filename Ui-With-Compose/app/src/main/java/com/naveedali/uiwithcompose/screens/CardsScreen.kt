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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naveedali.uiwithcompose.model.CardDemo
import com.naveedali.uiwithcompose.model.CardDemoType
import com.naveedali.uiwithcompose.model.cardDemos
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme

// ─────────────────────────────────────────────────────────────────────────────
// CardsScreen
//
// Entry-point composable for Material 3 card learning samples.
// Shows the core card families plus practical layouts you would build in a
// real app.
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsScreen(onBack: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cards") },
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
                items = cardDemos,
                key = { _, demo -> demo.type.name }
            ) { index, demo ->
                CardDemoCard(index = index + 1, demo = demo)
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// CardDemoCard
//
// Shared wrapper with a numbered heading, description, and a live example.
// The enum-driven `when` keeps the screen structure consistent with the rest
// of the sample project.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun CardDemoCard(index: Int, demo: CardDemo) {
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
                CardDemoType.BASIC -> BasicCardDemo()
                CardDemoType.ELEVATED -> ElevatedCardDemo()
                CardDemoType.OUTLINED -> OutlinedCardDemoSample()
                CardDemoType.CLICKABLE -> ClickableCardDemo()
                CardDemoType.CUSTOM_COLORS -> CustomColorsCardDemo()
                CardDemoType.CUSTOM_SHAPES -> CustomShapesCardDemo()
                CardDemoType.MEDIA -> MediaCardDemo()
                CardDemoType.ACTIONS -> ActionsCardDemo()
                CardDemoType.SETTINGS_SUMMARY -> SettingsSummaryCardDemo()
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Core card-family demos ───────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 01 · Basic Card ──────────────────────────────────────────────────────────
// `Card` is the default Material 3 card surface.
// It is best when we want light grouping without strong emphasis.
@Composable
private fun BasicCardDemo() {
    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Meeting Notes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Capture discussion points, next steps, and open questions in one grouped surface.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ── 02 · ElevatedCard ────────────────────────────────────────────────────────
// ElevatedCard adds more depth, so it feels more prominent than a standard Card.
@Composable
private fun ElevatedCardDemo() {
    ElevatedCard {
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
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CloudDone,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Column {
                Text(
                    text = "Sync Complete",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "All project files have been uploaded successfully.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// ── 03 · OutlinedCard ────────────────────────────────────────────────────────
// OutlinedCard replaces strong elevation with a visible border.
// This is useful in flatter layouts or dense content lists.
@Composable
private fun OutlinedCardDemoSample() {
    OutlinedCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Draft Proposal",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Bordered cards communicate structure without adding much visual weight.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Interaction & styling demos ──────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 04 · Clickable Card ──────────────────────────────────────────────────────
// Cards can be large touch targets by using the `onClick` overload.
// This creates one tappable semantic surface instead of wiring clicks to
// individual child composables.
@Composable
private fun ClickableCardDemo() {
    var tapCount by rememberSaveable { mutableIntStateOf(0) }

    Card(
        onClick = { tapCount++ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Open article preview",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Tapped $tapCount ${if (tapCount == 1) "time" else "times"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ── 05 · Custom colors ───────────────────────────────────────────────────────
// CardDefaults.cardColors() makes it easy to build featured or contextual cards.
@Composable
private fun CustomColorsCardDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Text(
                text = "Featured update card",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF3E0),
                contentColor = Color(0xFF8A4B00)
            )
        ) {
            Text(
                text = "Reminder card with warm accent styling",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

// ── 06 · Custom shapes ───────────────────────────────────────────────────────
// Shape changes affect the whole surface outline and help create a distinct UI style.
@Composable
private fun CustomShapesCardDemo() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Rounded",
                modifier = Modifier.padding(18.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Card(
            modifier = Modifier.weight(1f),
            shape = CutCornerShape(topStart = 20.dp, bottomEnd = 20.dp)
        ) {
            Text(
                text = "Cut corner",
                modifier = Modifier.padding(18.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Real-world layout demos ──────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 07 · Media card ──────────────────────────────────────────────────────────
// Many cards start with a media/header area. Here we fake that visual block
// with a gradient Box so the layout can be learned without extra image assets.
@Composable
private fun MediaCardDemo() {
    Card {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF4FC3F7),
                                Color(0xFF1565C0)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Evening Lo-fi Mix",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "32 tracks • Curated for focus and deep work sessions.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// ── 08 · Card with actions ───────────────────────────────────────────────────
// Actions usually live at the bottom of the card and should feel secondary to
// the main information. This keeps the card readable and scan-friendly.
@Composable
private fun ActionsCardDemo() {
    var saved by rememberSaveable { mutableStateOf(false) }

    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column {
                    Text(
                        text = "Project Brief",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Last edited 2 hours ago",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider()

            Text(
                text = "Cards with actions are useful when users may want to open, bookmark, " +
                        "share, or retry directly from the preview surface.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = { saved = !saved }) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                    Spacer(Modifier.size(6.dp))
                    Text(if (saved) "Saved" else "Save")
                }
                OutlinedButton(onClick = { }) {
                    Icon(Icons.Default.Share, contentDescription = null)
                    Spacer(Modifier.size(6.dp))
                    Text("Share")
                }
            }
        }
    }
}

// ── 09 · Settings summary card ───────────────────────────────────────────────
// A summary card groups several related data points in one scannable surface.
// This is common in dashboards, profile pages, and subscription screens.
@Composable
private fun SettingsSummaryCardDemo() {
    ElevatedCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Workspace Summary",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            SummaryRow(
                icon = Icons.Default.Storage,
                label = "Storage",
                value = "18.4 GB of 50 GB used"
            )
            SummaryRow(
                icon = Icons.Default.Notifications,
                label = "Alerts",
                value = "Daily digest enabled"
            )
            SummaryRow(
                icon = Icons.Default.CloudDone,
                label = "Backup",
                value = "Last sync 12 minutes ago"
            )

            Surface(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Tip: summary cards work well near the top of screens where users " +
                            "need a quick account or system overview.",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Private helpers
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun SummaryRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardsScreenPreview() {
    UiWithComposeTheme {
        CardsScreen()
    }
}
