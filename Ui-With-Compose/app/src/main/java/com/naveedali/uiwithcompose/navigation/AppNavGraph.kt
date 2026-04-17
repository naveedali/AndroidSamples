package com.naveedali.uiwithcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.naveedali.uiwithcompose.screens.ButtonsScreen
import com.naveedali.uiwithcompose.screens.CardsScreen
import com.naveedali.uiwithcompose.screens.DialogsScreen
import com.naveedali.uiwithcompose.screens.TogglesScreen
import com.naveedali.uiwithcompose.screens.HorizontalListScreen
import com.naveedali.uiwithcompose.screens.ImageViewsScreen
import com.naveedali.uiwithcompose.screens.LabelsScreen
import com.naveedali.uiwithcompose.screens.LearningHomeScreen
import com.naveedali.uiwithcompose.screens.ProgressIndicatorsScreen
import com.naveedali.uiwithcompose.screens.RatingBarScreen
import com.naveedali.uiwithcompose.screens.ScaffoldDemoScreen
import com.naveedali.uiwithcompose.screens.SnackbarsToastsScreen
import com.naveedali.uiwithcompose.screens.TextFieldsScreen
import com.naveedali.uiwithcompose.screens.VerticalListScreen

// ─────────────────────────────────────────────────────────────────────────────
// AppNavGraph — the navigation map for the whole app.
//
// NavHost is the container that swaps composables in and out based on the
// current destination. Think of it as the frame inside which each "screen"
// is rendered.
//
// Key parameters:
//   navController     — the object that drives navigation (go to, go back, etc.)
//   startDestination  — the route shown when the app first opens
//
// Each `composable(route) { ... }` block declares one screen destination.
// The lambda receives a NavBackStackEntry (ignored here with `_`) which can
// carry navigation arguments when needed.
//
// HOW TO ADD A NEW SCREEN:
//   1. Add its object to Screen.kt
//   2. Add a composable { } block below, passing the right callbacks
//   3. Map its index in the `onItemClick` when expression inside Home
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        // ── Home screen ───────────────────────────────────────────────────────
        composable(route = Screen.Home.route) {
            LearningHomeScreen(
                // onItemClick delivers the 0-based index of the tapped row.
                // Map each index to the matching Screen destination.
                onItemClick = { index ->
                    when (index) {
                        0 -> navController.navigate(Screen.ScaffoldDemo.route)
                        // Uncomment as each screen is implemented:
                        1  -> navController.navigate(Screen.Labels.route)
                        2  -> navController.navigate(Screen.Buttons.route)
                        3  -> navController.navigate(Screen.ImageViews.route)
                        4  -> navController.navigate(Screen.TextFields.route)
                        5  -> navController.navigate(Screen.Toggles.route)
                        6  -> navController.navigate(Screen.RatingBar.route)
                        7  -> navController.navigate(Screen.Dialogs.route)
                        8  -> navController.navigate(Screen.ProgressIndicators.route)
                        9  -> navController.navigate(Screen.Cards.route)
                        10 -> navController.navigate(Screen.Snackbars.route)
                        11 -> navController.navigate(Screen.HorizontalList.route)
                        12 -> navController.navigate(Screen.VerticalList.route)
                        // 13 -> navController.navigate(Screen.Grid.route)
                        // 14 -> navController.navigate(Screen.LoginPage.route)
                        // 15 -> navController.navigate(Screen.BottomNavigation.route)
                        // 16 -> navController.navigate(Screen.Animations.route)
                        else -> { /* screen not yet implemented — do nothing */ }
                    }
                }
            )
        }

        // ── TextFields screen ─────────────────────────────────────────────────
        composable(route = Screen.TextFields.route) {
            TextFieldsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Labels screen ─────────────────────────────────────────────────────
        composable(route = Screen.Labels.route) {
            LabelsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Toggles screen ────────────────────────────────────────────────────
        composable(route = Screen.Toggles.route) {
            TogglesScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Buttons screen ────────────────────────────────────────────────────
        composable(route = Screen.Buttons.route) {
            ButtonsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── RatingBar screen ─────────────────────────────────────────────────
        composable(route = Screen.RatingBar.route) {
            RatingBarScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Dialogs screen ───────────────────────────────────────────────────
        composable(route = Screen.Dialogs.route) {
            DialogsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Progress indicators screen ──────────────────────────────────────
        composable(route = Screen.ProgressIndicators.route) {
            ProgressIndicatorsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Cards screen ────────────────────────────────────────────────────
        composable(route = Screen.Cards.route) {
            CardsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Snackbars & Toasts screen ───────────────────────────────────────
        composable(route = Screen.Snackbars.route) {
            SnackbarsToastsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Horizontal List screen ──────────────────────────────────────────
        composable(route = Screen.HorizontalList.route) {
            HorizontalListScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Vertical List screen ────────────────────────────────────────────
        composable(route = Screen.VerticalList.route) {
            VerticalListScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Image Views screen ────────────────────────────────────────────────
        composable(route = Screen.ImageViews.route) {
            ImageViewsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Scaffold demo screen ──────────────────────────────────────────────
        composable(route = Screen.ScaffoldDemo.route) {
            ScaffoldDemoScreen(
                // navController.popBackStack() removes the current destination
                // from the back stack, effectively going back to the previous screen.
                onBack = { navController.popBackStack() }
            )
        }
    }
}
