package com.naveedali.uiwithcompose.screens

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naveedali.uiwithcompose.model.FeedbackDemo
import com.naveedali.uiwithcompose.model.FeedbackDemoType
import com.naveedali.uiwithcompose.model.feedbackDemos
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme
import kotlinx.coroutines.launch

// ─────────────────────────────────────────────────────────────────────────────
// SnackbarsToastsScreen
//
// Entry-point composable for transient feedback patterns.
// Demonstrates Compose snackbars and Android toasts in one learning screen.
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnackbarsToastsScreen(onBack: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Snackbars & Toasts") },
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
                items = feedbackDemos,
                key = { _, demo -> demo.type.name }
            ) { index, demo ->
                FeedbackDemoCard(index = index + 1, demo = demo)
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// FeedbackDemoCard
//
// Shared card wrapper with a numbered header, description, and live demo.
// Each example owns its own SnackbarHostState or Toast trigger logic so the
// examples stay isolated and easier to understand.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun FeedbackDemoCard(index: Int, demo: FeedbackDemo) {
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
                FeedbackDemoType.SNACKBAR_BASIC -> SnackbarBasicDemo()
                FeedbackDemoType.SNACKBAR_ACTION -> SnackbarActionDemo()
                FeedbackDemoType.SNACKBAR_RESULT -> SnackbarResultDemo()
                FeedbackDemoType.SNACKBAR_CUSTOM_HOST -> SnackbarCustomHostDemo()
                FeedbackDemoType.TOAST_BASIC -> ToastBasicDemo()
                FeedbackDemoType.TOAST_DURATION -> ToastDurationDemo()
                FeedbackDemoType.TOAST_POSITIONED -> ToastPositionedDemo()
                FeedbackDemoType.FORM_FEEDBACK -> FormFeedbackDemo()
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Snackbar demos ───────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 01 · Basic snackbar ──────────────────────────────────────────────────────
// A Snackbar needs a SnackbarHostState plus a SnackbarHost that renders it.
// showSnackbar() is suspend, so we call it from a remembered coroutine scope.
@Composable
private fun SnackbarBasicDemo() {
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    DemoSnackbarHost(hostState = hostState) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = {
                    scope.launch {
                        hostState.showSnackbar("Profile saved successfully.")
                    }
                }
            ) {
                Text("Show basic snackbar")
            }
        }
    }
}

// ── 02 · Snackbar with action ────────────────────────────────────────────────
// Action snackbars work best for reversible events such as delete, archive, or
// dismissed notifications because the action gives the user a clear next step.
@Composable
private fun SnackbarActionDemo() {
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var status by rememberSaveable { mutableStateOf("No action yet.") }

    DemoSnackbarHost(hostState = hostState) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = {
                    scope.launch {
                        val result = hostState.showSnackbar(
                            message = "Conversation archived",
                            actionLabel = "Undo",
                            duration = SnackbarDuration.Short
                        )
                        status = if (result == SnackbarResult.ActionPerformed) {
                            "Action pressed: Undo"
                        } else {
                            "Snackbar dismissed without action"
                        }
                    }
                }
            ) {
                Text("Archive conversation")
            }

            Text(
                text = status,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ── 03 · Handling SnackbarResult ─────────────────────────────────────────────
// showSnackbar() returns SnackbarResult immediately after the snackbar finishes.
// That lets us branch application logic based on the user's response.
@Composable
private fun SnackbarResultDemo() {
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var resultText by rememberSaveable { mutableStateOf("Waiting for interaction.") }

    DemoSnackbarHost(hostState = hostState) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = {
                    scope.launch {
                        val result = hostState.showSnackbar(
                            message = "Message failed to send",
                            actionLabel = "Retry",
                            duration = SnackbarDuration.Long
                        )
                        resultText = when (result) {
                            SnackbarResult.ActionPerformed -> "Retry clicked by user"
                            SnackbarResult.Dismissed -> "Snackbar timed out or was dismissed"
                        }
                    }
                }
            ) {
                Text("Simulate send failure")
            }

            Text(
                text = resultText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ── 04 · Custom SnackbarHost styling ─────────────────────────────────────────
// SnackbarHost can render each queued SnackbarData with custom UI.
// This lets us keep the queue/behavior from SnackbarHostState while changing
// appearance to suit a brand or status-based design.
@Composable
private fun SnackbarCustomHostDemo() {
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(
            onClick = {
                scope.launch {
                    hostState.showSnackbar(
                        message = "Backup completed successfully",
                        actionLabel = "View"
                    )
                }
            }
        ) {
            Text("Show styled snackbar")
        }

        SnackbarHost(hostState = hostState) { data ->
            StyledSuccessSnackbar(data = data)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Toast demos ──────────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 05 · Basic toast ─────────────────────────────────────────────────────────
// Toast uses the Android Context instead of Compose Scaffold state.
// This makes it convenient in simple cases, but less integrated with Compose UI.
@Composable
private fun ToastBasicDemo() {
    val context = LocalContext.current

    Button(
        onClick = {
            Toast.makeText(context, "Draft saved locally", Toast.LENGTH_SHORT).show()
        }
    ) {
        Text("Show basic toast")
    }
}

// ── 06 · Toast duration examples ─────────────────────────────────────────────
// Android exposes only two toast durations. Keep the message concise either way.
@Composable
private fun ToastDurationDemo() {
    val context = LocalContext.current

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(
            onClick = {
                Toast.makeText(context, "Short toast example", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("SHORT")
        }
        OutlinedButton(
            onClick = {
                Toast.makeText(context, "Long toast example", Toast.LENGTH_LONG).show()
            }
        ) {
            Text("LONG")
        }
    }
}

// ── 07 · Positioned toast ────────────────────────────────────────────────────
// setGravity() changes where the toast appears on screen.
// Default positioning is usually preferable, but it is useful to know this API.
@Composable
private fun ToastPositionedDemo() {
    val context = LocalContext.current

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(
            onClick = {
                Toast.makeText(context, "Centered toast", Toast.LENGTH_SHORT).apply {
                    setGravity(Gravity.CENTER, 0, 0)
                }.show()
            }
        ) {
            Text("Center")
        }
        OutlinedButton(
            onClick = {
                Toast.makeText(context, "Top toast", Toast.LENGTH_SHORT).apply {
                    setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 120)
                }.show()
            }
        ) {
            Text("Top")
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Real-world example ───────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 08 · Form save feedback ──────────────────────────────────────────────────
// Snackbars are stronger when a follow-up action is available.
// Toasts are lighter when we only need passive confirmation.
@Composable
private fun FormFeedbackDemo() {
    val context = LocalContext.current
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var saveMode by rememberSaveable { mutableStateOf("snackbar") }

    DemoSnackbarHost(hostState = hostState) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Save feedback style",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Choose whether this save flow responds with a Snackbar or a Toast.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ChoiceChip(
                            label = "Snackbar",
                            selected = saveMode == "snackbar",
                            onClick = { saveMode = "snackbar" }
                        )
                        ChoiceChip(
                            label = "Toast",
                            selected = saveMode == "toast",
                            onClick = { saveMode = "toast" }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (saveMode == "snackbar") {
                        scope.launch {
                            hostState.showSnackbar(
                                message = "Settings updated",
                                actionLabel = "Undo"
                            )
                        }
                    } else {
                        Toast.makeText(context, "Settings updated", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Save settings")
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Private helpers
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun DemoSnackbarHost(
    hostState: SnackbarHostState,
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        content()
        SnackbarHost(hostState = hostState)
    }
}

@Composable
private fun StyledSuccessSnackbar(data: SnackbarData) {
    Snackbar(
        action = {
            data.visuals.actionLabel?.let { label ->
                TextButton(onClick = { data.performAction() }) {
                    Text(label, color = Color.White)
                }
            }
        },
        dismissAction = null,
        containerColor = Color(0xFF1B5E20),
        contentColor = Color.White
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            Text(data.visuals.message)
        }
    }
}

@Composable
private fun ChoiceChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        color = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
        else MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelLarge,
            color = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SnackbarsToastsScreenPreview() {
    UiWithComposeTheme {
        SnackbarsToastsScreen()
    }
}
