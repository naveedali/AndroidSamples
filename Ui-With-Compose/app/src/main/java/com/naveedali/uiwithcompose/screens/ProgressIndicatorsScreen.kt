package com.naveedali.uiwithcompose.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naveedali.uiwithcompose.model.ProgressDemo
import com.naveedali.uiwithcompose.model.ProgressDemoType
import com.naveedali.uiwithcompose.model.progressDemos
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

// ─────────────────────────────────────────────────────────────────────────────
// ProgressIndicatorsScreen
//
// Entry-point composable for CircularProgressIndicator and
// LinearProgressIndicator learning samples.
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressIndicatorsScreen(onBack: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Progress Indicators") },
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
                items = progressDemos,
                key = { _, demo -> demo.type.name }
            ) { index, demo ->
                ProgressDemoCard(index = index + 1, demo = demo)
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ProgressDemoCard
//
// Shared wrapper that renders the title, description, and live demo.
// The enum-driven dispatch keeps the screen consistent with the rest of
// this sample app's learning catalog.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun ProgressDemoCard(index: Int, demo: ProgressDemo) {
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
                ProgressDemoType.CIRCULAR_INDETERMINATE -> CircularIndeterminateDemo()
                ProgressDemoType.CIRCULAR_DETERMINATE -> CircularDeterminateDemo()
                ProgressDemoType.CIRCULAR_STYLES -> CircularStylesDemo()
                ProgressDemoType.LINEAR_INDETERMINATE -> LinearIndeterminateDemo()
                ProgressDemoType.LINEAR_DETERMINATE -> LinearDeterminateDemo()
                ProgressDemoType.LINEAR_BUFFERED -> LinearBufferedDemo()
                ProgressDemoType.LOADING_CARD -> LoadingCardDemo()
                ProgressDemoType.FILE_UPLOAD -> FileUploadDemo()
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Circular progress demos ──────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 01 · Circular indeterminate ──────────────────────────────────────────────
// Use this when we only know "work is happening", not "work is 42% complete".
@Composable
private fun CircularIndeterminateDemo() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
        Text(
            text = "Fetching latest updates...",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// ── 02 · Circular determinate ────────────────────────────────────────────────
// Determinate progress expects a lambda that returns a Float in the range 0f..1f.
// Here the button increments the value to show the ring filling over time.
@Composable
private fun CircularDeterminateDemo() {
    var progress by rememberSaveable { mutableFloatStateOf(0.65f) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                progress = { progress }
            )
            Text(
                text = "${(progress * 100).roundToInt()}%",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { progress = (progress + 0.10f).coerceAtMost(1f) }) {
                Text("Increase")
            }
            OutlinedButton(onClick = { progress = 0.15f }) {
                Text("Reset")
            }
        }
    }
}

// ── 03 · Circular styles ─────────────────────────────────────────────────────
// The same component can be tuned for different contexts by changing size,
// stroke width, and color.
@Composable
private fun CircularStylesDemo() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProgressStyleItem(
            label = "Small",
            content = {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            }
        )
        ProgressStyleItem(
            label = "Brand",
            content = {
                CircularProgressIndicator(
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.size(28.dp)
                )
            }
        )
        ProgressStyleItem(
            label = "Large",
            content = {
                CircularProgressIndicator(
                    progress = { 0.78f },
                    modifier = Modifier.size(48.dp),
                    strokeWidth = 5.dp
                )
            }
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Linear progress demos ────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 04 · Linear indeterminate ────────────────────────────────────────────────
// Linear indeterminate progress is good for page-level or section-level
// loading where a full circular spinner might feel too heavy.
@Composable
private fun LinearIndeterminateDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Refreshing feed...",
            style = MaterialTheme.typography.bodyMedium
        )
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}

// ── 05 · Linear determinate ──────────────────────────────────────────────────
// Linear determinate bars make long tasks feel more understandable because
// users can see remaining work at a glance.
@Composable
private fun LinearDeterminateDemo() {
    var progress by rememberSaveable { mutableFloatStateOf(0.42f) }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Completed ${(progress * 100).roundToInt()}%",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButtonSmall("Step") {
                    progress = (progress + 0.20f).coerceAtMost(1f)
                }
                TextButtonSmall("Reset") {
                    progress = 0f
                }
            }
        }
    }
}

// ── 06 · Buffered progress ───────────────────────────────────────────────────
// A common media pattern tracks two values:
//   • primary progress  -> how much has been consumed/played
//   • secondary progress -> how much has been buffered/downloaded
//
// Compose's LinearProgressIndicator only draws one bar, so we layer two bars
// to simulate buffered progress.
@Composable
private fun LinearBufferedDemo() {
    var played by rememberSaveable { mutableFloatStateOf(0.35f) }
    var buffered by rememberSaveable { mutableFloatStateOf(0.65f) }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            LinearProgressIndicator(
                progress = { buffered },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.45f),
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            LinearProgressIndicator(
                progress = { played },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.Transparent
            )
        }

        Text(
            text = "Played ${(played * 100).roundToInt()}% • Buffered ${(buffered * 100).roundToInt()}%",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    buffered = (buffered + 0.10f).coerceAtMost(1f)
                    played = (played + 0.08f).coerceAtMost(buffered)
                }
            ) {
                Text("Advance")
            }
            OutlinedButton(
                onClick = {
                    played = 0.15f
                    buffered = 0.35f
                }
            ) {
                Text("Restart")
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Real-world examples ──────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 07 · Loading card ────────────────────────────────────────────────────────
// This pattern keeps the loading indicator inside the content area being loaded
// instead of floating elsewhere on the screen.
@Composable
private fun LoadingCardDemo() {
    var loading by rememberSaveable { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedButton(onClick = { loading = !loading }) {
            Text(if (loading) "Show loaded state" else "Show loading state")
        }

        Surface(
            tonalElevation = 2.dp,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (loading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    Column {
                        Text(
                            text = "Loading dashboard metrics...",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Please wait while data is prepared.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Dashboard summary",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    HorizontalDivider()
                    Text("Revenue: PKR 124,000", style = MaterialTheme.typography.bodyMedium)
                    Text("Orders: 318", style = MaterialTheme.typography.bodyMedium)
                    Text("Conversion rate: 4.8%", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

// ── 08 · File upload demo ────────────────────────────────────────────────────
// Real tasks often update themselves over time rather than through manual taps.
// LaunchedEffect lets us run a small coroutine tied to the composable lifecycle.
@Composable
private fun FileUploadDemo() {
    var uploading by rememberSaveable { mutableStateOf(false) }
    var progress by rememberSaveable { mutableFloatStateOf(0f) }

    if (uploading) {
        LaunchedEffect(uploading) {
            while (progress < 1f) {
                delay(180)
                progress = (progress + 0.08f).coerceAtMost(1f)
            }
            uploading = false
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CloudUpload,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Column {
                Text(
                    text = "Quarterly_Report.pdf",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = if (progress >= 1f) "Upload completed"
                    else "${(progress * 100).roundToInt()}% uploaded",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    progress = 0f
                    uploading = true
                },
                enabled = !uploading
            ) {
                Text(if (uploading) "Uploading..." else "Start upload")
            }
            OutlinedButton(
                onClick = {
                    uploading = false
                    progress = 0f
                }
            ) {
                Text("Clear")
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Private helpers
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun ProgressStyleItem(
    label: String,
    content: @Composable () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.size(56.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun TextButtonSmall(
    text: String,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.height(36.dp)
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProgressIndicatorsScreenPreview() {
    UiWithComposeTheme {
        ProgressIndicatorsScreen()
    }
}
