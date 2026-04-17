package com.naveedali.uiwithcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import com.naveedali.uiwithcompose.model.DialogDemo
import com.naveedali.uiwithcompose.model.DialogDemoType
import com.naveedali.uiwithcompose.model.dialogDemos
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// ─────────────────────────────────────────────────────────────────────────────
// DialogsScreen
//
// Entry-point composable for alerts and dialogs learning samples.
// Shows both Material 3 AlertDialog usage and lower-level custom Dialog
// patterns so learners understand when each approach fits.
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsScreen(onBack: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dialogs & Alerts") },
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
                items = dialogDemos,
                key = { _, demo -> demo.type.name }
            ) { index, demo ->
                DialogDemoCard(index = index + 1, demo = demo)
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// DialogDemoCard
//
// Shared wrapper that renders the header, explanation, and live demo for one
// dialog sample. The `when` dispatch below mirrors the screen architecture used
// elsewhere in the project.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun DialogDemoCard(index: Int, demo: DialogDemo) {
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
                DialogDemoType.ALERT_BASIC -> BasicAlertDialogDemo()
                DialogDemoType.ALERT_CONFIRMATION -> ConfirmationAlertDialogDemo()
                DialogDemoType.ALERT_DESTRUCTIVE -> DestructiveAlertDialogDemo()
                DialogDemoType.ALERT_SINGLE_CHOICE -> SingleChoiceAlertDialogDemo()
                DialogDemoType.DIALOG_CUSTOM_CARD -> CustomCardDialogDemo()
                DialogDemoType.DIALOG_FORM -> FormDialogDemo()
                DialogDemoType.DIALOG_PROGRESS -> ProgressDialogDemo()
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── AlertDialog demos ────────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 01 · Basic AlertDialog ───────────────────────────────────────────────────
// AlertDialog is the quickest way to show a Material-styled decision dialog.
// Core idea:
//   • local `showDialog` Boolean controls visibility
//   • when true, include AlertDialog in the composition
//   • `onDismissRequest` must turn that Boolean back to false
@Composable
private fun BasicAlertDialogDemo() {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var resultText by rememberSaveable { mutableStateOf("No action selected yet.") }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { showDialog = true }) {
            Text("Show basic alert")
        }

        Text(
            text = resultText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            icon = {
                Icon(Icons.Default.Info, contentDescription = null)
            },
            title = { Text("Enable sync?") },
            text = {
                Text("This will keep your notes updated across devices using the same account.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        resultText = "User chose: Enable"
                        showDialog = false
                    }
                ) {
                    Text("Enable")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        resultText = "User chose: Cancel"
                        showDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

// ── 02 · Confirmation AlertDialog ────────────────────────────────────────────
// A confirmation dialog is useful when the tap triggers a meaningful change.
// The actual setting update happens ONLY after the confirm action is pressed.
@Composable
private fun ConfirmationAlertDialogDemo() {
    var notificationsEnabled by rememberSaveable { mutableStateOf(false) }
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Marketing alerts", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = if (notificationsEnabled) "Currently enabled" else "Currently disabled",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            OutlinedButton(onClick = { showDialog = true }) {
                Text(if (notificationsEnabled) "Disable" else "Enable")
            }
        }
    }

    if (showDialog) {
        val newValue = !notificationsEnabled

        AlertDialog(
            onDismissRequest = { showDialog = false },
            icon = {
                Icon(Icons.Default.Notifications, contentDescription = null)
            },
            title = {
                Text(if (newValue) "Enable alerts?" else "Disable alerts?")
            },
            text = {
                Text(
                    if (newValue) "You will receive occasional product updates and announcements."
                    else "You will stop receiving product announcements until you enable them again."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        notificationsEnabled = newValue
                        showDialog = false
                    }
                ) {
                    Text(if (newValue) "Confirm" else "Disable")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Keep current")
                }
            }
        )
    }
}

// ── 03 · Destructive AlertDialog ─────────────────────────────────────────────
// Risky actions should be explicit and visually stronger so the user pauses
// before confirming. Material 3 makes it easy to emphasize the dangerous path
// by using the error color on the confirm button.
@Composable
private fun DestructiveAlertDialogDemo() {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var archiveState by rememberSaveable { mutableStateOf("Project is still available.") }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedButton(onClick = { showDialog = true }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.size(8.dp))
            Text("Delete project")
        }

        Text(
            text = archiveState,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            },
            title = { Text("Delete this project?") },
            text = {
                Text("This action permanently removes tasks, attachments, and activity history.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        archiveState = "Project deleted. This demo only updates local UI state."
                        showDialog = false
                    }
                ) {
                    Text(
                        text = "Delete",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// ── 04 · Single-choice AlertDialog ───────────────────────────────────────────
// AlertDialog's `text` slot can host any composable tree, not just a Text node.
// That means we can place a mini radio-group inside it for single-selection
// flows like choosing a theme, language, or sort order.
@Composable
private fun SingleChoiceAlertDialogDemo() {
    val options = listOf("Sort by newest", "Sort by oldest", "Sort by priority")
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    var pendingIndex by rememberSaveable { mutableIntStateOf(selectedIndex) }
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedButton(
            onClick = {
                pendingIndex = selectedIndex
                showDialog = true
            }
        ) {
            Text("Choose sort order")
        }

        Text(
            text = "Selected: ${options[selectedIndex]}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Sort tasks") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    options.forEachIndexed { index, label ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(role = Role.RadioButton) { pendingIndex = index }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = pendingIndex == index,
                                onClick = null
                            )
                            Text(
                                text = label,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedIndex = pendingIndex
                        showDialog = false
                    }
                ) {
                    Text("Apply")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ── Custom Dialog demos ──────────────────────────────────────────────────────
// ─────────────────────────────────────────────────────────────────────────────

// ── 05 · Custom card dialog ──────────────────────────────────────────────────
// `Dialog` is lower-level than AlertDialog. It only provides the popup window
// and scrim; you provide the actual surface, shape, padding, and content.
// This is the right tool when you need richer layout than the fixed M3 alert.
@Composable
private fun CustomCardDialogDemo() {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Button(onClick = { showDialog = true }) {
        Text("Show custom dialog")
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                tonalElevation = 6.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Profile completed",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Your profile is now 100% complete. Rich custom dialogs work well " +
                                    "for success states, illustrations, or branded layouts.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    HorizontalDivider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = { showDialog = false }) {
                            Text("Nice")
                        }
                    }
                }
            }
        }
    }
}

// ── 06 · Small form dialog ───────────────────────────────────────────────────
// Compose dialogs can safely host short-form content. Keep the fields focused
// and the interaction brief; if the task grows complex, prefer a full screen.
@Composable
private fun FormDialogDemo() {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var savedName by rememberSaveable { mutableStateOf("Sprint Planning") }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedButton(onClick = { showDialog = true }) {
            Text("Rename workspace")
        }

        Text(
            text = "Current name: $savedName",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    if (showDialog) {
        var draftName by rememberSaveable { mutableStateOf(savedName) }

        Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                tonalElevation = 6.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Rename workspace",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Short forms like rename, add tag, or invite member can fit well in a dialog.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    OutlinedTextField(
                        value = draftName,
                        onValueChange = { draftName = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = { Text("Workspace name") }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                savedName = draftName.ifBlank { savedName }
                                showDialog = false
                            }
                        ) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}

// ── 07 · Blocking progress dialog ────────────────────────────────────────────
// Sometimes we must prevent dismissal while a critical action is in progress.
// `dismissOnBackPress = false` and `dismissOnClickOutside = false` make the
// popup modal until the work finishes.
@Composable
private fun ProgressDialogDemo() {
    val scope = rememberCoroutineScope()
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var completedMessage by rememberSaveable { mutableStateOf("No upload in progress.") }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(
            onClick = {
                showDialog = true
                completedMessage = "Uploading report..."
                scope.launch {
                    delay(1800)
                    showDialog = false
                    completedMessage = "Upload complete. The dialog dismissed after the work finished."
                }
            }
        ) {
            Text("Simulate upload")
        }

        Text(
            text = completedMessage,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                tonalElevation = 6.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(28.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Uploading...",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Please wait while we send your file.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogsScreenPreview() {
    UiWithComposeTheme {
        DialogsScreen()
    }
}
