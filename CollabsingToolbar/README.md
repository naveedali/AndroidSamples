# Collapsing Toolbar — Jetpack Compose Samples

A multi-tab sample app demonstrating five different collapsing toolbar behaviors in Jetpack Compose using Material 3.

---

## Screens Overview

The app is organized as a `ScrollableTabRow` + `HorizontalPager`. Each tab is a self-contained screen showcasing a distinct toolbar pattern.

---

## Collapsing Toolbar Behavior Types

### 1. `enterAlwaysScrollBehavior` — Large Top Bar

Used with `LargeTopAppBar`.

The large title collapses into a compact title when you scroll **up**. As soon as you scroll **down** even slightly — regardless of list position — the toolbar immediately slides back in from the top.

> Best for: Home/feed screens where users frequently scroll up and down and benefit from reclaiming screen space quickly.

---

### 2. `exitUntilCollapsedScrollBehavior` — Medium Top Bar

Used with `MediumTopAppBar`.

The medium title collapses into a compact title when you scroll **up**. Unlike `enterAlways`, the toolbar does **not** re-expand until the list is scrolled all the way back to the very top.

> Best for: Detail screens where re-expanding the header mid-scroll would feel disruptive.

---

### 3. `pinnedScrollBehavior` — Pinned

Used with `TopAppBar`.

The toolbar **never collapses**. It stays pinned at the top at all times. The only visual change on scroll is the container color and shadow elevation, signaling that content is scrolling behind the bar.

> Best for: Screens where the title must always remain visible for context (e.g., a settings or profile page).

---

### 4. Custom Parallax Header

Custom implementation using `LazyListState` + `graphicsLayer`.

A large decorative header moves at **40% of the list's scroll speed**, creating a depth/parallax illusion. A gradient scrim and a `TopAppBar` both fade in progressively as the header scrolls off screen.

Key technique:
```kotlin
Modifier.graphicsLayer {
    translationY = -scrollOffset * 0.4f  // 40% parallax speed
}
```

> Best for: Visual landing screens, photo galleries, or article headers where depth and immersion matter.

---

### 5. Custom `NestedScrollConnection` — Custom Collapsing

Fully custom implementation using `NestedScrollConnection`.

A hero header collapses from **220 dp → 56 dp** by intercepting scroll events before the list receives them. The hero content (emoji, title, subtitle) fades out and a compact toolbar title fades in as it collapses.

Key technique:
```kotlin
override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
    // Consume upward scroll to collapse header; list stays still
    if (available.y < 0) { /* shrink header, return consumed offset */ }
    return Offset.Zero
}

override fun onPostScroll(...): Offset {
    // Re-expand header using unconsumed downward delta when list is at top
}
```

> Best for: Fully custom branded headers that need precise control over collapse animation and threshold.

---

## Bird Data

Each screen uses a list of **15 real bird species**, each with:

| Field | Description |
|-------|-------------|
| `name` | Common name |
| `scientificName` | Latin binomial |
| `specialty` | The bird's most remarkable trait |
| `habitat` | Natural environment |
| `funFact` | A surprising fact |
| `emoji` | Visual avatar |
| `color` | Accent color for the card |

Species included: Peregrine Falcon, Ruby-throated Hummingbird, Arctic Tern, Wandering Albatross, Great Horned Owl, Greater Flamingo, Superb Lyrebird, Emperor Penguin, Common Swift, Indian Peacock, Pileated Woodpecker, Kiwi, Toco Toucan, African Grey Parrot, Magnificent Frigatebird.

---

## Project Structure

```
app/src/main/java/com/naveedali/collapsingtoolbar/
├── MainActivity.kt                  # Tab row + HorizontalPager host
├── data/
│   └── BirdData.kt                  # Bird data class and list
├── components/
│   ├── BirdCard.kt                  # Reusable bird list item card
│   └── InfoBanner.kt                # Behavior description banner
└── screens/
    ├── LargeTopBarScreen.kt         # enterAlwaysScrollBehavior
    ├── MediumTopBarScreen.kt        # exitUntilCollapsedScrollBehavior
    ├── PinnedScreen.kt              # pinnedScrollBehavior
    ├── ParallaxScreen.kt            # Custom parallax header
    └── CustomCollapsingScreen.kt    # Custom NestedScrollConnection
```

---

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose (BOM 2024.09.00)
- **Design system:** Material 3
- **Pager:** `androidx.compose.foundation.pager.HorizontalPager`
- **Min SDK:** 24
- **Target SDK:** 36
