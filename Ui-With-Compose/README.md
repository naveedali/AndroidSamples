# UI With Compose

A hands-on learning sample for **Jetpack Compose** — Android's modern, declarative UI toolkit. Every screen in this app demonstrates a specific set of UI components with detailed inline comments explaining **what** each API does and **why** it works that way.

---

## Screenshots

<p align="center">
  <img src="[screenshots/home_screen_top.png](https://drive.google.com/file/d/14R7u9q0oGfmDhoWUyPIXfbuZ7DUqbQ9l/view?usp=sharing)" width="270" alt="Home screen — top"/>
  &nbsp;&nbsp;&nbsp;
  <img src="[screenshots/home_screen_bottom.png](https://drive.google.com/file/d/1VglWl0tSrBcT-K3BmEutpDuLP2MkUt1Q/view?usp=sharing)" width="270" alt="Home screen — bottom"/>
</p>
<p align="center">
  <em>Home screen — the full catalogue of UI practice topics</em>
</p>

---

## What This Project Is

Most Compose tutorials show isolated snippets. This project instead builds a **navigable app** where each item on the home list opens a dedicated screen that live-demonstrates the component — with every concept labelled and explained directly in the code.

Goals:
- See every component rendered on a real device, not just in a preview
- Read the "why" alongside the "what" through thorough inline comments
- Follow a consistent, production-like architecture from the very first screen

---

## Tech Stack

| Layer | Library | Version |
|---|---|---|
| Language | Kotlin | 2.0.21 |
| UI toolkit | Jetpack Compose (BOM) | 2024.09.00 |
| Material design | Material 3 | via BOM |
| Navigation | Navigation Compose | 2.8.4 |
| Min SDK | — | 24 (Android 7.0) |
| Target / Compile SDK | — | 36 |

---

## Project Structure

```
app/src/main/java/com/naveedali/uiwithcompose/
│
├── MainActivity.kt              ← Entry point. Creates NavController, applies theme.
│
├── navigation/
│   ├── Screen.kt                ← Sealed class — single source of truth for all routes.
│   └── AppNavGraph.kt           ← NavHost wiring every screen to its route.
│
├── screens/
│   ├── LearningHomeScreen.kt    ← Scrollable index of all practice topics.
│   ├── ScaffoldDemoScreen.kt    ← 01 · TopBar, BottomBar & Content
│   └── LabelsScreen.kt          ← 02 · Labels (Text composable)
│
└── ui/theme/
    ├── Color.kt                 ← Material 3 colour tokens
    ├── Theme.kt                 ← UiWithComposeTheme wrapper
    └── Type.kt                  ← Typography scale
```

---

## Architecture Pattern

```
MainActivity
    └── UiWithComposeTheme
            └── AppNavGraph (NavHost)
                    ├── LearningHomeScreen  ──(tap row)──►  navigate(Screen.X.route)
                    ├── ScaffoldDemoScreen  ──(back)──────►  popBackStack()
                    └── LabelsScreen        ──(back)──────►  popBackStack()
```

Key decisions:
- **`ComponentActivity`** over `AppCompatActivity` — Compose doesn't need AppCompat's View-system baggage.
- **`NavController` created in `MainActivity`** and passed into `AppNavGraph`, so a single controller manages the whole back-stack.
- **Event hoisting** — screens don't navigate themselves; they call `onItemClick(index)` or `onBack()` and the parent (`AppNavGraph`) decides the destination.

---

## Screen Catalogue

### ✅ Implemented

#### 01 — TopBar, BottomBar & Content
`screens/ScaffoldDemoScreen.kt`

Demonstrates all five Scaffold slots at once:

| Slot | Component | Highlights |
|---|---|---|
| `topBar` | `TopAppBar` | navigationIcon, title, actions, colour customisation |
| `bottomBar` | `NavigationBar` + `NavigationBarItem` | selection state with `remember { mutableIntStateOf(0) }` |
| `floatingActionButton` | `FloatingActionButton` | triggers Snackbar via coroutine scope |
| `snackbarHost` | `SnackbarHost` | `SnackbarHostState`, suspend `showSnackbar()` |
| `content` | `LazyColumn` | `innerPadding` applied correctly to avoid content hidden under bars |

---

#### 02 — Labels
`screens/LabelsScreen.kt`

13 sections covering every `Text` composable capability:

| # | Topic | What you learn |
|---|---|---|
| 01 | Typography Scale | All 15 Material 3 roles — `displayLarge` → `labelSmall` |
| 02 | Font Weight | Thin (100) → Black (900) — all 9 levels rendered side-by-side |
| 03 | Font Style | `FontStyle.Normal` vs `FontStyle.Italic` |
| 04 | Font Family | Default, Serif, SansSerif, Monospace, Cursive + how to load custom `.ttf` |
| 05 | Font Size | Explicit `sp` values — why `sp` beats `dp` for text |
| 06 | Letter Spacing | Condensed (`-0.05.em`) to very wide (`0.3.em`) |
| 07 | Line Height | Tight / normal / relaxed — same paragraph, three heights |
| 08 | Text Alignment | `Start`, `Center`, `End`, `Justify` — RTL-safe alternatives to Left/Right |
| 09 | Text Color | `colorScheme` tokens for dark-mode + custom `Color(0xFFRRGGBB)` |
| 10 | Text Decoration | `Underline`, `LineThrough`, combined via `TextDecoration.combine()` |
| 11 | Overflow & Max Lines | `Ellipsis`, `Clip`, `softWrap = false` |
| 12 | AnnotatedString | Inline bold/italic · price spans · H₂O subscript · search-match highlight |
| 13 | Selectable Text | `SelectionContainer` — long-press to copy |

---

### 🔜 Coming Next

| # | Screen | Key components |
|---|---|---|
| 03 | Buttons | `Button`, `OutlinedButton`, `TextButton`, `IconButton`, `FAB` |
| 04 | Image Views | `Image`, `AsyncImage` (Coil), `contentScale`, clipping |
| 05 | Text Fields | `TextField`, `OutlinedTextField`, keyboard options, validation |
| 06 | Switches, RadioButtons & Checkboxes | State-driven toggle components |
| 07 | Rating Bar | Custom star rating with `Row` + `Icon` |
| 08 | Dialogs & Alerts | `AlertDialog`, `BottomSheet`, `DatePickerDialog` |
| 09 | Progress Indicators | `CircularProgressIndicator`, `LinearProgressIndicator` |
| 10 | Cards | `Card`, `ElevatedCard`, `OutlinedCard`, clickable cards |
| 11 | Snackbars & Toasts | `SnackbarHost`, `SnackbarResult`, coroutine scope |
| 12 | Horizontal List | `LazyRow`, item keys, content padding |
| 13 | Vertical List | `LazyColumn`, `stickyHeader`, pull-to-refresh |
| 14 | Grid | `LazyVerticalGrid`, `LazyHorizontalGrid`, adaptive columns |
| 15 | Login Page | Form layout combining `TextField`, `Button`, validation |
| 16 | Bottom Navigation | `NavigationBar`, `NavHost`, back-stack management |
| 17 | Animations | `AnimatedVisibility`, `animate*AsState`, Transition API |

---

## How to Add a New Screen

Follow these four steps every time:

**1. Declare the route** — `navigation/Screen.kt`
```kotlin
object MyScreen : Screen("my_screen")
```

**2. Register the destination** — `navigation/AppNavGraph.kt`
```kotlin
composable(route = Screen.MyScreen.route) {
    MyScreen(onBack = { navController.popBackStack() })
}
```

**3. Wire up navigation** — still in `AppNavGraph.kt`, inside the `onItemClick` `when` block
```kotlin
N -> navController.navigate(Screen.MyScreen.route)
```

**4. Create the screen** — `screens/MyScreen.kt`
```kotlin
@Composable
fun MyScreen(onBack: () -> Unit = {}) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("My Screen") }, navigationIcon = { /* back */ }) }
    ) { innerPadding ->
        // your content here, using Modifier.padding(innerPadding)
    }
}
```

---

## Key Concepts Reference

### ComponentActivity vs AppCompatActivity

```
AppCompatActivity   → Use when mixing Compose with legacy XML Views / Fragments
ComponentActivity   → Use for pure Compose projects (lighter, no View-system overhead)
```

### Scaffold innerPadding — why it matters

```
Scaffold { innerPadding ->
    YourContent(
        modifier = Modifier.padding(innerPadding)   // ← NEVER skip this
    )
}
```

`innerPadding` = height of TopAppBar + height of NavigationBar + system status/nav bar insets.
Skipping it causes content to render hidden behind bars.

### State hoisting pattern

```kotlin
// ❌ state buried inside — not reusable, not testable
@Composable fun Counter() {
    var count by remember { mutableIntStateOf(0) }
    Button(onClick = { count++ }) { Text("$count") }
}

// ✅ state hoisted — caller controls the value and the event
@Composable fun Counter(count: Int, onIncrement: () -> Unit) {
    Button(onClick = onIncrement) { Text("$count") }
}
```

### Navigation event hoisting

Screens never call `navController.navigate()` directly. They fire a lambda (`onItemClick`, `onBack`) and the parent — `AppNavGraph` — decides where to go. This keeps screens decoupled from the navigation graph and makes them independently previewable.

---

## Getting Started

```bash
# Clone
git clone https://github.com/your-username/ui-with-compose.git

# Open in Android Studio Hedgehog or later
# Run on an emulator or device (minSdk 24)
```

No API keys, no backend — the app is entirely self-contained.

---

## License

```
Copyright 2024 Naveed Ali

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0
```
