package com.naveedali.uiwithcompose.navigation

// ─────────────────────────────────────────────────────────────────────────────
// Screen — the single source of truth for every navigation destination.
//
// WHY a sealed class?
//   A sealed class restricts which classes can extend it to the same file.
//   This means the compiler knows every possible Screen at compile time,
//   so a `when` expression over a Screen is exhaustive — no runtime surprises.
//
// WHY store `route` as a String?
//   Navigation Compose identifies destinations by string routes (like URLs).
//   Centralising them here means you change a route in ONE place, not scattered
//   across every navigate() call.
//
// HOW to add a new screen:
//   1. Add a new `object` inside this sealed class with a unique route string.
//   2. Add a `composable(Screen.YourScreen.route) { ... }` entry in AppNavGraph.
//   3. Navigate to it with  navController.navigate(Screen.YourScreen.route)
// ─────────────────────────────────────────────────────────────────────────────
sealed class Screen(val route: String) {

    // The home screen — shows the full list of practice topics.
    object Home : Screen("home")

    // Demo screen for Scaffold: TopAppBar + BottomBar + FAB + Snackbar + Content.
    object ScaffoldDemo : Screen("scaffold_demo")

    // ── Future screens (add as you implement them) ────────────────────────────
    // object Labels           : Screen("labels")
    // object Buttons          : Screen("buttons")
    // object ImageViews       : Screen("image_views")
    // object TextFields       : Screen("text_fields")
    // object Toggles          : Screen("toggles")
    // object RatingBar        : Screen("rating_bar")
    // object Dialogs          : Screen("dialogs")
    // object ProgressIndicators : Screen("progress_indicators")
    // object Cards            : Screen("cards")
    // object Snackbars        : Screen("snackbars")
    // object HorizontalList   : Screen("horizontal_list")
    // object VerticalList     : Screen("vertical_list")
    // object Grid             : Screen("grid")
    // object LoginPage        : Screen("login_page")
    // object BottomNavigation : Screen("bottom_navigation")
    // object Animations       : Screen("animations")
}
