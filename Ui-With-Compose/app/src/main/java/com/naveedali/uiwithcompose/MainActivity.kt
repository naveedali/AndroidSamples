package com.naveedali.uiwithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.naveedali.uiwithcompose.navigation.AppNavGraph
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme

// ─────────────────────────────────────────────────────────────────────────────
// ComponentActivity vs AppCompatActivity — which one should I extend?
//
// AppCompatActivity  (from the older AndroidX AppCompat library)
//   • Was the standard base class before Jetpack Compose existed.
//   • Brings backward-compatible support for Action Bar, Toolbar, night-mode,
//     and View-based theme features (all things tied to the classic XML UI).
//   • Required when you mix traditional Views / Fragments with the older
//     support library APIs.
//
// ComponentActivity  (from androidx.activity)
//   • A lighter, modern base class that strips out everything AppCompat adds
//     for the XML View system.
//   • RECOMMENDED for pure Jetpack Compose projects because Compose handles
//     its own theming, navigation, and lifecycle.
//   • Still provides ViewModel support, result contracts,
//     permission launchers, and lifecycle events.
//
// Rule of thumb:
//   → Building with Jetpack Compose only?  ✅ use ComponentActivity
//   → Mixing Compose with legacy XML Views? ✅ use AppCompatActivity
// ─────────────────────────────────────────────────────────────────────────────
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enableEdgeToEdge() allows the app to draw behind the system status
        // bar and navigation bar. Each screen's Scaffold receives innerPadding
        // that keeps content visible above those system bars.
        enableEdgeToEdge()

        setContent {

            // ─────────────────────────────────────────────────────────────────
            // UiWithComposeTheme
            //
            // Wraps the entire app so every Composable inherits the Material 3
            // color scheme, typography, and shapes. Also handles light/dark mode
            // automatically based on the device setting.
            // ─────────────────────────────────────────────────────────────────
            UiWithComposeTheme {

                // ─────────────────────────────────────────────────────────────
                // rememberNavController()
                //
                // Creates and remembers a NavHostController for the lifetime of
                // this composable. The controller is the object you call
                // navigate(), popBackStack(), etc. on.
                //
                // WHY create it here (in MainActivity) and pass it down?
                //   • Only one NavController should exist per navigation graph.
                //   • Creating it at the top level makes it easy to share across
                //     the whole app without threading it through many layers.
                // ─────────────────────────────────────────────────────────────
                val navController = rememberNavController()

                // AppNavGraph wires up the NavHost and every screen destination.
                // MainActivity's only job is to provide the theme and the
                // NavController — all navigation logic lives in AppNavGraph.
                AppNavGraph(navController = navController)
            }
        }
    }
}
