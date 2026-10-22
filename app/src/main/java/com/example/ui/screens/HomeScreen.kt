package com.example.ui.screens

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.SiteShortcut
import com.example.ui.components.SiteFaviconImage
import com.example.ui.theme.MinimalDarkAccent
import com.example.ui.theme.MinimalDarkBg
import com.example.ui.theme.MinimalDarkBorder
import com.example.ui.theme.MinimalDarkCard
import com.example.ui.theme.MinimalDarkMuted
import com.example.ui.theme.MinimalDarkSurface
import com.example.ui.theme.MinimalDarkText
import com.example.ui.theme.MinimalMilkAccent
import com.example.ui.theme.MinimalMilkBg
import com.example.ui.theme.MinimalMilkBorder
import com.example.ui.theme.MinimalMilkCard
import com.example.ui.theme.MinimalMilkMuted
import com.example.ui.theme.MinimalMilkSurface
import com.example.ui.theme.MinimalMilkText
import com.example.ui.theme.MinimalRed
import com.example.ui.util.FaviconUtils

@Composable
fun HomeScreen(
    sites: List<SiteShortcut>,
    isNightMode: Boolean,
    onOpenSite: (String) -> Unit,
    onAddSite: (title: String, url: String, category: String, colorHex: String) -> Unit,
    onDeleteSite: (SiteShortcut) -> Unit,
    onResetDefaults: () -> Unit,
    onOpenBrowser: () -> Unit,
    onOpenBookmarks: () -> Unit,
    onOpenSettings: () -> Unit,
    onToggleNightMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    var showAutoHelpDialog by remember { mutableStateOf(false) }

    // Minimal palette tokens
    val bg = if (isNightMode) MinimalDarkBg else MinimalMilkBg
    val surface = if (isNightMode) MinimalDarkSurface else MinimalMilkSurface
    val border = if (isNightMode) MinimalDarkBorder else MinimalMilkBorder
    val textPrimary = if (isNightMode) MinimalDarkText else MinimalMilkText
    val textMuted = if (isNightMode) MinimalDarkMuted else MinimalMilkMuted
    val accent = if (isNightMode) MinimalDarkAccent else MinimalMilkAccent

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bg)
            .testTag("home_screen")
    ) {
        // Ultra-minimal Top Header: App Identity & Quick Navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(surface)
                .border(0.5.dp, border)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // App Branding Pill with Car Icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(accent),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = "AuraCast Car",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "AuraCast",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = textPrimary,
                            letterSpacing = 0.2.sp
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isNightMode) Color(0xFF1E293B) else Color(0xFFE2E8F0))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Auto",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = accent
                            )
                        }
                    }
                    Text(
                        text = "Сайты для автомобиля",
                        fontSize = 11.sp,
                        color = textMuted
                    )
                }
            }

            // Quick Actions: Menu, Theme Switch, Settings
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Dropdown Menu
                Box {
                    IconButton(
                        onClick = { showMenu = true },
                        modifier = Modifier
                            .size(44.dp)
                            .testTag("home_menu_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Меню",
                            tint = textPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier.background(surface)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Открыть веб-браузер", color = textPrimary) },
                            leadingIcon = {
                                Icon(Icons.Default.OpenInBrowser, contentDescription = null, tint = accent)
                            },
                            onClick = {
                                showMenu = false
                                onOpenBrowser()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Закладки и история", color = textPrimary) },
                            leadingIcon = {
                                Icon(Icons.Default.Bookmark, contentDescription = null, tint = textPrimary)
                            },
                            onClick = {
                                showMenu = false
                                onOpenBookmarks()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Добавить сайт", color = textPrimary) },
                            leadingIcon = {
                                Icon(Icons.Default.Add, contentDescription = null, tint = accent)
                            },
                            onClick = {
                                showMenu = false
                                showAddDialog = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Инструкция Android Auto", color = textPrimary) },
                            leadingIcon = {
                                Icon(Icons.Default.Info, contentDescription = null, tint = accent)
                            },
                            onClick = {
                                showMenu = false
                                showAutoHelpDialog = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Сбросить сайты к начальным", color = textMuted) },
                            leadingIcon = {
                                Icon(Icons.Default.Refresh, contentDescription = null, tint = textMuted)
                            },
                            onClick = {
                                showMenu = false
                                onResetDefaults()
                            }
                        )
                    }
                }

                // Day / Night Switch
                IconButton(
                    onClick = onToggleNightMode,
                    modifier = Modifier
                        .size(44.dp)
                        .testTag("home_theme_button")
                ) {
                    Icon(
                        imageVector = if (isNightMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                        contentDescription = "Сменить тему",
                        tint = textPrimary,
                        modifier = Modifier.size(21.dp)
                    )
                }

                // Settings
                IconButton(
                    onClick = onOpenSettings,
                    modifier = Modifier
                        .size(44.dp)
                        .testTag("home_settings_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Настройки",
                        tint = textPrimary,
                        modifier = Modifier.size(21.dp)
                    )
                }
            }
        }

        // Clean Grid: ONLY site cards with official logos, ending with "+ Добавить свой сайт" tile
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .testTag("sites_grid")
        ) {
            items(sites, key = { it.id }) { site ->
                SiteCard(
                    site = site,
                    isNightMode = isNightMode,
                    onOpen = { onOpenSite(site.url) },
                    onDelete = { onDeleteSite(site) }
                )
            }

            // The "+ Добавить свой сайт" Card Tile at the end of the list
            item {
                AddSiteTile(
                    isNightMode = isNightMode,
                    onClick = { showAddDialog = true }
                )
            }
        }
    }

    // Add Site Dialog with Real-time Logo Preview
    if (showAddDialog) {
        AddSiteDialog(
            isNightMode = isNightMode,
            onAdd = { title, url, category, colorHex ->
                onAddSite(title, url, category, colorHex)
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }

    // Android Auto Help Dialog
    if (showAutoHelpDialog) {
        AndroidAutoHelpDialog(
            isNightMode = isNightMode,
            onDismiss = { showAutoHelpDialog = false }
        )
    }
}

@Composable
private fun SiteCard(
    site: SiteShortcut,
    isNightMode: Boolean,
    onOpen: () -> Unit,
    onDelete: () -> Unit
) {
    val cardBg = if (isNightMode) MinimalDarkCard else Color.White
    val border = if (isNightMode) MinimalDarkBorder else MinimalMilkBorder
    val textPrimary = if (isNightMode) MinimalDarkText else MinimalMilkText
    val textMuted = if (isNightMode) MinimalDarkMuted else MinimalMilkMuted

    val domain = remember(site.url) {
        FaviconUtils.extractDomain(site.url)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)
            .shadow(
                elevation = if (isNightMode) 0.dp else 2.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .clip(RoundedCornerShape(16.dp))
            .background(cardBg)
            .border(0.7.dp, border, RoundedCornerShape(16.dp))
            .clickable { onOpen() }
            .padding(12.dp)
            .testTag("site_card_${site.id}")
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Site Favicon Logo Capsule
            SiteFaviconImage(
                url = site.url,
                title = site.title,
                colorHex = site.iconColorHex,
                size = 48.dp,
                iconSize = 30.dp,
                cornerRadius = 13.dp,
                isNightMode = isNightMode
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Site Details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = site.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = domain.ifBlank { site.url },
                    fontSize = 11.5.sp,
                    color = textMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Delete Action Button (min 48dp touch target)
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .size(44.dp)
                    .testTag("delete_site_${site.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Удалить ${site.title}",
                    tint = MinimalRed.copy(alpha = 0.8f),
                    modifier = Modifier.size(19.dp)
                )
            }
        }
    }
}

@Composable
private fun AddSiteTile(
    isNightMode: Boolean,
    onClick: () -> Unit
) {
    val cardBg = if (isNightMode) MinimalDarkCard.copy(alpha = 0.45f) else Color.White.copy(alpha = 0.7f)
    val border = if (isNightMode) MinimalDarkBorder else MinimalMilkBorder
    val accent = if (isNightMode) MinimalDarkAccent else MinimalMilkAccent

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(cardBg)
            .border(1.dp, border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(12.dp)
            .testTag("add_site_card_tile"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(accent.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить сайт",
                    tint = accent,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = "Добавить свой сайт",
                fontSize = 12.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = accent
            )
        }
    }
}

@Composable
private fun AddSiteDialog(
    isNightMode: Boolean,
    onAdd: (title: String, url: String, category: String, colorHex: String) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf("#3B82F6") }

    val bg = if (isNightMode) MinimalDarkBg else MinimalMilkBg
    val border = if (isNightMode) MinimalDarkBorder else MinimalMilkBorder
    val surface = if (isNightMode) MinimalDarkSurface else Color.White
    val card = if (isNightMode) MinimalDarkCard else MinimalMilkCard
    val textPrimary = if (isNightMode) MinimalDarkText else MinimalMilkText
    val textMuted = if (isNightMode) MinimalDarkMuted else MinimalMilkMuted
    val accent = if (isNightMode) MinimalDarkAccent else MinimalMilkAccent

    val colors = listOf(
        "#EF4444" to "Красный",
        "#0284C7" to "Голубой",
        "#2563EB" to "Синий",
        "#F97316" to "Оранжевый",
        "#10B981" to "Зеленый",
        "#8B5CF6" to "Фиолетовый"
    )

    // Popular Quick Suggestions for 1-tap fill
    val popularSuggestions = listOf(
        Triple("Кинопоиск", "kinopoisk.ru", "video"),
        Triple("Rutube", "rutube.ru", "video"),
        Triple("VK Видео", "vk.com/video", "video"),
        Triple("Яндекс Музыка", "music.yandex.ru", "music"),
        Triple("Drive2", "drive2.ru", "auto"),
        Triple("Telegram Web", "web.telegram.org", "general"),
        Triple("Twitch", "twitch.tv", "video"),
        Triple("Хабр", "habr.com", "news")
    )

    // Automatically guess title when user types URL if title is still empty
    LaunchedEffect(url) {
        if (title.isBlank() && url.contains(".")) {
            val guessed = FaviconUtils.guessSiteTitle(url)
            if (guessed.isNotBlank()) {
                title = guessed
            }
        }
    }

    val extractedDomain = remember(url) {
        FaviconUtils.extractDomain(url)
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(24.dp))
                .background(bg)
                .border(0.8.dp, border, RoundedCornerShape(24.dp))
                .padding(20.dp)
                .testTag("add_site_dialog")
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Добавление сайта",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = textPrimary
                        )
                        Text(
                            text = "Логотип определится автоматически",
                            fontSize = 11.5.sp,
                            color = textMuted
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Закрыть",
                            tint = textMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // LIVE LOGO PREVIEW CARD (Displays live site logo as user types!)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(surface)
                        .border(1.dp, border, RoundedCornerShape(16.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        SiteFaviconImage(
                            url = url.ifBlank { "https://google.com" },
                            title = title.ifBlank { "Сайт" },
                            colorHex = selectedColor,
                            size = 52.dp,
                            iconSize = 32.dp,
                            cornerRadius = 14.dp,
                            isNightMode = isNightMode
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = title.ifBlank { "Название сайта" },
                                    fontSize = 14.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textPrimary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if (extractedDomain.isNotBlank()) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(accent.copy(alpha = 0.12f))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "Логотип активен",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = accent
                                        )
                                    }
                                }
                            }

                            Text(
                                text = if (extractedDomain.isNotBlank()) "Домен: $extractedDomain"
                                else "Введите адрес сайта ниже для загрузки логотипа",
                                fontSize = 11.5.sp,
                                color = textMuted,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                // Quick Popular Suggestions Chips
                Column {
                    Text(
                        text = "Быстрый выбор:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = textMuted,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(popularSuggestions) { (presetTitle, presetUrl, _) ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(card)
                                    .border(0.5.dp, border, RoundedCornerShape(8.dp))
                                    .clickable {
                                        title = presetTitle
                                        url = presetUrl
                                    }
                                    .padding(horizontal = 9.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = presetTitle,
                                    fontSize = 11.sp,
                                    color = textPrimary
                                )
                            }
                        }
                    }
                }

                // URL Input
                Column {
                    Text(
                        text = "Адрес сайта (URL)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = textPrimary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = url,
                        onValueChange = { url = it },
                        placeholder = { Text("kinopoisk.ru или rutube.ru", color = textMuted.copy(alpha = 0.6f)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textPrimary,
                            unfocusedTextColor = textPrimary,
                            focusedBorderColor = accent,
                            unfocusedBorderColor = border,
                            focusedContainerColor = surface,
                            unfocusedContainerColor = surface
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("add_site_url_input")
                    )
                }

                // Title Input
                Column {
                    Text(
                        text = "Название",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = textPrimary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = { Text("Например: Кинопоиск", color = textMuted.copy(alpha = 0.6f)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textPrimary,
                            unfocusedTextColor = textPrimary,
                            focusedBorderColor = accent,
                            unfocusedBorderColor = border,
                            focusedContainerColor = surface,
                            unfocusedContainerColor = surface
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("add_site_title_input")
                    )
                }

                // Color Selector for fallback monogram
                Column {
                    Text(
                        text = "Цвет оформления",
                        fontSize = 12.sp,
                        color = textMuted,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        colors.forEach { (hex, _) ->
                            val color = Color(android.graphics.Color.parseColor(hex))
                            val isSelected = selectedColor == hex
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(
                                        width = if (isSelected) 2.5.dp else 0.dp,
                                        color = if (isSelected) textPrimary else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable { selectedColor = hex }
                            )
                        }
                    }
                }

                // Dialog Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Отмена", color = textMuted)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (url.isNotBlank()) {
                                val finalTitle = title.ifBlank {
                                    FaviconUtils.guessSiteTitle(url).ifBlank { "Сайт" }
                                }
                                onAdd(finalTitle, url, "general", selectedColor)
                            }
                        },
                        enabled = url.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(containerColor = accent),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("add_site_confirm_button")
                    ) {
                        Text("Добавить сайт", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun AndroidAutoHelpDialog(
    isNightMode: Boolean,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val bg = if (isNightMode) MinimalDarkBg else MinimalMilkBg
    val border = if (isNightMode) MinimalDarkBorder else MinimalMilkBorder
    val card = if (isNightMode) MinimalDarkCard else MinimalMilkCard
    val textPrimary = if (isNightMode) MinimalDarkText else MinimalMilkText
    val textMuted = if (isNightMode) MinimalDarkMuted else MinimalMilkMuted
    val accent = if (isNightMode) MinimalDarkAccent else MinimalMilkAccent

    fun openAndroidAutoSettings(ctx: Context) {
        try {
            val intent = Intent("com.google.android.gms.car.SETTINGS")
            ctx.startActivity(intent)
        } catch (_: Exception) {
            try {
                val intent = Intent().apply {
                    component = ComponentName("com.google.android.projection.gearhead", "com.google.android.projection.gearhead.companion.settings.DefaultSettingsActivity")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                ctx.startActivity(intent)
            } catch (_: Exception) {
                try {
                    val intent = Intent(android.provider.Settings.ACTION_SETTINGS)
                    ctx.startActivity(intent)
                } catch (_: Exception) {}
            }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(22.dp))
                .background(bg)
                .border(0.8.dp, border, RoundedCornerShape(22.dp))
                .padding(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Отображение в Android Auto",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = textPrimary
                    )

                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Закрыть", tint = textMuted, modifier = Modifier.size(18.dp))
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(card)
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Как включить отображение приложения в автомобиле:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textPrimary
                        )
                        Text(
                            text = "Так как приложение устанавливается напрямую через APK (для снятия блокировок во время движения), система Android Auto скрывает его по умолчанию. Для активации:",
                            fontSize = 11.5.sp,
                            color = textMuted
                        )
                        Text(
                            text = "1. Откройте «Настройки» телефона → «Android Auto»\n2. Прокрутите вниз до пункта «Версия» и нажмите на него 10 раз, пока не включится «Режим разработчика»\n3. В правом верхнем углу нажмите меню (три точки) → «Для разработчиков»\n4. Поставьте галочку «Неизвестные источники» (Unknown sources)\n5. Вернитесь в «Настройка панели запуска» — AuraCast появится в списке с возможностью отметить его галочкой!",
                            fontSize = 11.5.sp,
                            color = textPrimary,
                            lineHeight = 17.sp
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { openAndroidAutoSettings(context) },
                        colors = ButtonDefaults.buttonColors(containerColor = accent),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Настройки Android Auto", fontSize = 11.5.sp, color = Color.White)
                    }

                    TextButton(onClick = onDismiss) {
                        Text("Понятно", color = textPrimary)
                    }
                }
            }
        }
    }
}
