package com.naveedali.uiwithcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.naveedali.uiwithcompose.ui.theme.UiWithComposeTheme

// ─────────────────────────────────────────────────────────────────────────────
// LabelsScreen
//
// A comprehensive reference for the Text composable — the Compose equivalent
// of Android's TextView. Every section is self-contained so you can read one
// topic without needing the others.
//
// Sections:
//   1. Typography Scale        — all 15 Material 3 type roles
//   2. Font Weight             — Thin → Black (9 levels)
//   3. Font Style              — Normal vs Italic
//   4. Font Family             — Default, Serif, Monospace, Cursive
//   5. Font Size               — explicit sp values
//   6. Letter Spacing          — tight, normal, wide
//   7. Line Height             — compact vs relaxed
//   8. Text Alignment          — Start, Center, End, Justify
//   9. Text Color              — theme tokens + custom ARGB
//  10. Text Decoration         — Underline, Strikethrough, combined
//  11. Overflow & Max Lines    — Ellipsis, Clip, Visible
//  12. AnnotatedString         — inline bold, colored spans, superscript
//  13. Selectable Text         — SelectionContainer
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelsScreen(onBack: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Labels") },
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { Spacer(Modifier.height(4.dp)) }

            // ── 1. Typography Scale ───────────────────────────────────────────
            item { SectionHeader(number = "01", title = "Typography Scale") }
            item {
                SectionCard {
                    // ─────────────────────────────────────────────────────────
                    // Material 3 defines 15 type roles grouped into 5 families:
                    //   Display  → very large, expressive text (hero text)
                    //   Headline → screen titles
                    //   Title    → section headings, list items
                    //   Body     → paragraph / long-form text
                    //   Label    → captions, chips, button labels
                    //
                    // Use MaterialTheme.typography.<role> to apply them.
                    // Never hard-code font sizes — use these roles so your app
                    // respects the user's font size preference automatically.
                    // ─────────────────────────────────────────────────────────
                    SectionNote("Use MaterialTheme.typography roles instead of hard-coded sp values.")
                    Spacer(Modifier.height(8.dp))

                    TypographyRow("displayLarge",    MaterialTheme.typography.displayLarge)
                    TypographyRow("displayMedium",   MaterialTheme.typography.displayMedium)
                    TypographyRow("displaySmall",    MaterialTheme.typography.displaySmall)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                    TypographyRow("headlineLarge",   MaterialTheme.typography.headlineLarge)
                    TypographyRow("headlineMedium",  MaterialTheme.typography.headlineMedium)
                    TypographyRow("headlineSmall",   MaterialTheme.typography.headlineSmall)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                    TypographyRow("titleLarge",      MaterialTheme.typography.titleLarge)
                    TypographyRow("titleMedium",     MaterialTheme.typography.titleMedium)
                    TypographyRow("titleSmall",      MaterialTheme.typography.titleSmall)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                    TypographyRow("bodyLarge",       MaterialTheme.typography.bodyLarge)
                    TypographyRow("bodyMedium",      MaterialTheme.typography.bodyMedium)
                    TypographyRow("bodySmall",       MaterialTheme.typography.bodySmall)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                    TypographyRow("labelLarge",      MaterialTheme.typography.labelLarge)
                    TypographyRow("labelMedium",     MaterialTheme.typography.labelMedium)
                    TypographyRow("labelSmall",      MaterialTheme.typography.labelSmall)
                }
            }

            // ── 2. Font Weight ────────────────────────────────────────────────
            item { SectionHeader(number = "02", title = "Font Weight") }
            item {
                SectionCard {
                    // ─────────────────────────────────────────────────────────
                    // FontWeight maps to CSS numeric weights (100–900).
                    // Not all weights are available in the default system font —
                    // custom fonts loaded via downloadable fonts or assets can
                    // expose more weights.
                    // ─────────────────────────────────────────────────────────
                    SectionNote("FontWeight ranges from Thin (100) to Black (900).")
                    Spacer(Modifier.height(8.dp))
                    val weights = listOf(
                        "Thin (100)"       to FontWeight.Thin,
                        "ExtraLight (200)" to FontWeight.ExtraLight,
                        "Light (300)"      to FontWeight.Light,
                        "Normal (400)"     to FontWeight.Normal,
                        "Medium (500)"     to FontWeight.Medium,
                        "SemiBold (600)"   to FontWeight.SemiBold,
                        "Bold (700)"       to FontWeight.Bold,
                        "ExtraBold (800)"  to FontWeight.ExtraBold,
                        "Black (900)"      to FontWeight.Black
                    )
                    weights.forEach { (label, weight) ->
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            fontWeight = weight,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }

            // ── 3. Font Style ─────────────────────────────────────────────────
            item { SectionHeader(number = "03", title = "Font Style") }
            item {
                SectionCard {
                    // FontStyle.Normal is the default upright style.
                    // FontStyle.Italic renders the text in italic form.
                    Text(
                        text = "Normal — fontStyle = FontStyle.Normal",
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Italic — fontStyle = FontStyle.Italic",
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // ── 4. Font Family ────────────────────────────────────────────────
            item { SectionHeader(number = "04", title = "Font Family") }
            item {
                SectionCard {
                    // ─────────────────────────────────────────────────────────
                    // FontFamily selects which typeface to use.
                    // The built-in generic families below map to the system fonts.
                    //
                    // For custom fonts: place .ttf/.otf files in res/font/, then:
                    //   val myFont = FontFamily(Font(R.font.my_font, FontWeight.Normal))
                    // ─────────────────────────────────────────────────────────
                    SectionNote("Custom fonts: res/font/my_font.ttf → FontFamily(Font(R.font.my_font))")
                    Spacer(Modifier.height(8.dp))
                    val families = listOf(
                        "Default (system sans-serif)" to FontFamily.Default,
                        "Serif"                       to FontFamily.Serif,
                        "SansSerif"                   to FontFamily.SansSerif,
                        "Monospace — fixed width"     to FontFamily.Monospace,
                        "Cursive — script style"      to FontFamily.Cursive
                    )
                    families.forEach { (label, family) ->
                        Text(
                            text = label,
                            fontFamily = family,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(vertical = 3.dp)
                        )
                    }
                }
            }

            // ── 5. Font Size ──────────────────────────────────────────────────
            item { SectionHeader(number = "05", title = "Font Size (sp)") }
            item {
                SectionCard {
                    // ─────────────────────────────────────────────────────────
                    // `sp` = scale-independent pixels. It respects the user's
                    // system font size setting (Settings → Display → Font size).
                    // Always prefer `sp` over `dp` for text.
                    //
                    // Prefer typography roles (section 01) over raw sp values.
                    // Raw sp values are useful when you need exact visual control
                    // (e.g. a branded hero text, or a label inside a chart).
                    // ─────────────────────────────────────────────────────────
                    SectionNote("sp scales with the system font size. Use sp (not dp) for text.")
                    Spacer(Modifier.height(8.dp))
                    listOf(10, 12, 14, 16, 20, 24, 32, 40, 52).forEach { size ->
                        Text(
                            text = "${size}sp — Aa",
                            fontSize = size.sp,
                            modifier = Modifier.padding(vertical = 1.dp)
                        )
                    }
                }
            }

            // ── 6. Letter Spacing ─────────────────────────────────────────────
            item { SectionHeader(number = "06", title = "Letter Spacing") }
            item {
                SectionCard {
                    // ─────────────────────────────────────────────────────────
                    // letterSpacing adds space between individual characters.
                    // Unit: `em` (relative to font size) or `sp` (absolute).
                    // Negative values condense the text.
                    // ─────────────────────────────────────────────────────────
                    val spacings = listOf(
                        "(-0.05).em  — condensed"    to (-0.05).em,
                        "0.em        — default"       to 0.em,
                        "0.05.em     — slightly wide" to 0.05.em,
                        "0.15.em     — wide"          to 0.15.em,
                        "0.3.em      — very wide"     to 0.3.em
                    )
                    spacings.forEach { (label, spacing) ->
                        Text(
                            text = label,
                            fontSize = 14.sp,
                            letterSpacing = spacing,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }

            // ── 7. Line Height ────────────────────────────────────────────────
            item { SectionHeader(number = "07", title = "Line Height") }
            item {
                SectionCard {
                    // ─────────────────────────────────────────────────────────
                    // lineHeight controls the vertical space between lines.
                    // A common guideline: 1.2–1.5× the font size for body text.
                    // ─────────────────────────────────────────────────────────
                    val sample = "The quick brown fox jumps over the lazy dog. " +
                            "Pack my box with five dozen liquor jugs."
                    listOf(
                        "lineHeight = 14.sp (tight)"   to 14.sp,
                        "lineHeight = 20.sp (normal)"  to 20.sp,
                        "lineHeight = 28.sp (relaxed)" to 28.sp
                    ).forEach { (label, height) ->
                        Text(
                            text = "$label\n$sample",
                            fontSize = 13.sp,
                            lineHeight = height,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                        HorizontalDivider()
                    }
                }
            }

            // ── 8. Text Alignment ─────────────────────────────────────────────
            item { SectionHeader(number = "08", title = "Text Alignment (Gravity)") }
            item {
                SectionCard {
                    // ─────────────────────────────────────────────────────────
                    // textAlign works within the Text's measured width.
                    // The Text must fill its container — add fillMaxWidth() so
                    // alignment is actually visible.
                    //
                    // TextAlign.Start / End respect RTL layouts automatically.
                    // Use Start/End instead of Left/Right for RTL support.
                    // ─────────────────────────────────────────────────────────
                    val alignments = listOf(
                        "TextAlign.Start   (RTL-safe left)"   to TextAlign.Start,
                        "TextAlign.Center  (centred)"         to TextAlign.Center,
                        "TextAlign.End     (RTL-safe right)"  to TextAlign.End,
                        "TextAlign.Justify (spread to edges)" to TextAlign.Justify
                    )
                    alignments.forEach { (label, align) ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = label,
                                textAlign = align,
                                fontSize = 13.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            // ── 9. Text Color ─────────────────────────────────────────────────
            item { SectionHeader(number = "09", title = "Text Color") }
            item {
                SectionCard {
                    // ─────────────────────────────────────────────────────────
                    // Text color is set via the `color` parameter.
                    // Prefer MaterialTheme.colorScheme tokens so the color
                    // changes automatically in dark mode.
                    //
                    // For custom colors: Color(0xFFRRGGBB) uses ARGB hex.
                    // Alpha is the first byte: 0xFF = fully opaque, 0x88 = ~50%.
                    // ─────────────────────────────────────────────────────────
                    SectionNote("Use colorScheme tokens for automatic dark-mode support.")
                    Spacer(Modifier.height(8.dp))
                    ColorRow("colorScheme.primary",          MaterialTheme.colorScheme.primary)
                    ColorRow("colorScheme.secondary",        MaterialTheme.colorScheme.secondary)
                    ColorRow("colorScheme.tertiary",         MaterialTheme.colorScheme.tertiary)
                    ColorRow("colorScheme.error",            MaterialTheme.colorScheme.error)
                    ColorRow("colorScheme.onSurfaceVariant", MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(6.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(6.dp))
                    ColorRow("Color(0xFF6650A4) — custom purple", Color(0xFF6650A4))
                    ColorRow("Color.Red",                         Color.Red)
                    ColorRow("Color(0x88000000) — 50% black",     Color(0x88000000))
                }
            }

            // ── 10. Text Decoration ───────────────────────────────────────────
            item { SectionHeader(number = "10", title = "Text Decoration") }
            item {
                SectionCard {
                    // ─────────────────────────────────────────────────────────
                    // TextDecoration adds visual marks on the text.
                    // Combine multiple decorations with TextDecoration.combine().
                    // ─────────────────────────────────────────────────────────
                    Text(
                        text = "TextDecoration.None — plain text",
                        textDecoration = TextDecoration.None,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "TextDecoration.Underline",
                        textDecoration = TextDecoration.Underline,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "TextDecoration.LineThrough  (strikethrough)",
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "Underline + LineThrough combined",
                        textDecoration = TextDecoration.combine(
                            listOf(TextDecoration.Underline, TextDecoration.LineThrough)
                        ),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            // ── 11. Overflow & Max Lines ──────────────────────────────────────
            item { SectionHeader(number = "11", title = "Overflow & Max Lines") }
            item {
                SectionCard {
                    // ─────────────────────────────────────────────────────────
                    // maxLines caps how many lines of text are shown.
                    // overflow decides what happens to text that doesn't fit:
                    //
                    //   TextOverflow.Clip      — cuts off abruptly (default)
                    //   TextOverflow.Ellipsis  — appends "…" at the cut point
                    //   TextOverflow.Visible   — lets text overflow its bounds
                    //
                    // softWrap = false disables word-wrapping entirely.
                    // ─────────────────────────────────────────────────────────
                    val longText = "This is a very long sentence that will definitely " +
                            "not fit on a single line in most screen sizes and needs " +
                            "to be truncated according to the overflow setting chosen."

                    OverflowRow(label = "maxLines=1, Ellipsis", text = longText,
                        maxLines = 1, overflow = TextOverflow.Ellipsis)
                    OverflowRow(label = "maxLines=1, Clip", text = longText,
                        maxLines = 1, overflow = TextOverflow.Clip)
                    OverflowRow(label = "maxLines=2, Ellipsis", text = longText,
                        maxLines = 2, overflow = TextOverflow.Ellipsis)
                    OverflowRow(label = "softWrap=false, Ellipsis", text = longText,
                        maxLines = 1, overflow = TextOverflow.Ellipsis, softWrap = false)
                }
            }

            // ── 12. AnnotatedString ───────────────────────────────────────────
            item { SectionHeader(number = "12", title = "AnnotatedString (Inline Spans)") }
            item {
                SectionCard {
                    // ─────────────────────────────────────────────────────────
                    // AnnotatedString lets you apply different SpanStyle rules
                    // to ranges within a single Text — similar to HTML <span>
                    // or Android's SpannableString.
                    //
                    // buildAnnotatedString { } is the builder DSL.
                    // withStyle(SpanStyle(...)) { append("...") } scopes a style
                    // to only the text appended inside that block.
                    // ─────────────────────────────────────────────────────────
                    SectionNote("buildAnnotatedString applies different styles within one Text.")
                    Spacer(Modifier.height(10.dp))

                    // Example 1 — mixed weight and style in one line
                    Text(
                        text = buildAnnotatedString {
                            append("Regular  ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Bold  ")
                            }
                            withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                                append("Italic  ")
                            }
                            withStyle(SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic
                            )) {
                                append("Bold-Italic")
                            }
                        },
                        fontSize = 15.sp
                    )

                    Spacer(Modifier.height(10.dp))

                    // Example 2 — price with strikethrough + discount highlight
                    Text(
                        text = buildAnnotatedString {
                            append("Price: ")
                            withStyle(SpanStyle(
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.LineThrough
                            )) {
                                append("\$99")
                            }
                            append("  ")
                            withStyle(SpanStyle(
                                color = Color(0xFF2E7D32),   // green
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )) {
                                append("\$59")
                            }
                            withStyle(SpanStyle(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )) {
                                append("  (40% off)")
                            }
                        },
                        fontSize = 15.sp
                    )

                    Spacer(Modifier.height(10.dp))

                    // Example 3 — superscript and subscript via BaselineShift
                    Text(
                        text = buildAnnotatedString {
                            append("H")
                            withStyle(SpanStyle(
                                baselineShift = BaselineShift.Subscript,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.primary
                            )) { append("2") }
                            append("O   |   E = mc")
                            withStyle(SpanStyle(
                                baselineShift = BaselineShift.Superscript,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )) { append("2") }
                            append("   |   Brand")
                            withStyle(SpanStyle(
                                baselineShift = BaselineShift.Superscript,
                                fontSize = 9.sp
                            )) { append("™") }
                        },
                        fontSize = 16.sp
                    )

                    Spacer(Modifier.height(10.dp))

                    // Example 4 — background highlight (search match simulation)
                    Text(
                        text = buildAnnotatedString {
                            append("Search result: ")
                            withStyle(SpanStyle(
                                background = Color(0xFFFFEB3B),  // yellow highlight
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )) {
                                append("Jetpack Compose")
                            }
                            append(" is a modern UI toolkit.")
                        },
                        fontSize = 15.sp
                    )
                }
            }

            // ── 13. Selectable Text ───────────────────────────────────────────
            item { SectionHeader(number = "13", title = "Selectable Text") }
            item {
                SectionCard {
                    // ─────────────────────────────────────────────────────────
                    // By default, Text in Compose is NOT selectable — long-press
                    // does nothing.
                    //
                    // Wrap one or more Text composables in SelectionContainer to
                    // enable long-press text selection and the system copy action.
                    // A single SelectionContainer allows selection across all
                    // Texts nested inside it.
                    // ─────────────────────────────────────────────────────────
                    SectionNote("Long-press the text below to select and copy it.")
                    Spacer(Modifier.height(8.dp))
                    SelectionContainer {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "This text is selectable. Long-press to highlight.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "API key: A1B2-C3D4-E5F6-G7H8",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "⬆ Inside SelectionContainer — long-press to select",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Private helper composables used only within this file
// ─────────────────────────────────────────────────────────────────────────────

/** Numbered section header with a colour-coded badge. */
@Composable
private fun SectionHeader(number: String, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(top = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 8.dp, vertical = 3.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

/** Outlined card container wrapping each section's demo content. */
@Composable
private fun SectionCard(content: @Composable ColumnScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Column(content = content)
    }
}

/** Subtle informational tip displayed at the top of a section. */
@Composable
private fun SectionNote(text: String) {
    Text(
        text = "ℹ  $text",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 6.dp)
    )
}

/**
 * One row in the Typography Scale section.
 * Shows the role name on the left and a live sample on the right.
 */
@Composable
private fun TypographyRow(roleName: String, style: TextStyle) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = roleName,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.45f)
        )
        Text(
            text = "Aa — Hello World",
            style = style,
            modifier = Modifier.weight(0.55f)
        )
    }
}

/** One row in the Color section — swatch + coloured label. */
@Composable
private fun ColorRow(label: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .height(18.dp)
                .weight(0.08f)
                .background(color, RoundedCornerShape(4.dp))
        )
        Text(
            text = label,
            color = color,
            fontSize = 13.sp,
            modifier = Modifier.weight(0.92f)
        )
    }
}

/** One row in the Overflow section with a labelled constrained Text sample. */
@Composable
private fun OverflowRow(
    label: String,
    text: String,
    maxLines: Int,
    overflow: TextOverflow,
    softWrap: Boolean = true
) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = text,
            fontSize = 13.sp,
            maxLines = maxLines,
            overflow = overflow,
            softWrap = softWrap,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    RoundedCornerShape(4.dp)
                )
                .padding(6.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LabelsScreenPreview() {
    UiWithComposeTheme {
        LabelsScreen()
    }
}
