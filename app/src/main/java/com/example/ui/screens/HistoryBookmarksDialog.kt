package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Bookmark
import com.example.data.model.HistoryItem
import com.example.ui.theme.MinimalDarkAccent
import com.example.ui.theme.MinimalDarkBg
import com.example.ui.theme.MinimalDarkBorder
import com.example.ui.theme.MinimalDarkCard
import com.example.ui.theme.MinimalDarkMuted
import com.example.ui.theme.MinimalDarkText
import com.example.ui.theme.MinimalMilkAccent
import com.example.ui.theme.MinimalMilkBg
import com.example.ui.theme.MinimalMilkBorder
import com.example.ui.theme.MinimalMilkCard
import com.example.ui.theme.MinimalMilkMuted
import com.example.ui.theme.MinimalMilkText
import com.example.ui.theme.MinimalRed
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryBookmarksDialog(
    bookmarks: List<Bookmark>,
    history: List<HistoryItem>,
    currentUrl: String,
    isNightMode: Boolean = true,
    onSelectUrl: (String) -> Unit,
    onAddBookmark: (title: String, url: String) -> Unit,
    onDeleteBookmark: (Bookmark) -> Unit,
    onClearHistory: () -> Unit,
    onDismiss: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showAddBookmarkFields by remember { mutableStateOf(false) }
    var newTitle by remember { mutableStateOf("") }
    var newUrl by remember { mutableStateOf(currentUrl) }

    val dateFormat = SimpleDateFormat("dd.MM HH:mm", Locale.getDefault())

    val bg = if (isNightMode) MinimalDarkBg else MinimalMilkBg
    val card = if (isNightMode) MinimalDarkCard else MinimalMilkCard
    val border = if (isNightMode) MinimalDarkBorder else MinimalMilkBorder
    val textPrimary = if (isNightMode) MinimalDarkText else MinimalMilkText
    val textMuted = if (isNightMode) MinimalDarkMuted else MinimalMilkMuted
    val accent = if (isNightMode) MinimalDarkAccent else MinimalMilkAccent

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f)
                .clip(RoundedCornerShape(20.dp))
                .background(bg)
                .border(0.5.dp, border, RoundedCornerShape(20.dp))
                .padding(20.dp)
                .testTag("history_bookmarks_dialog")
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Top header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (selectedTab == 0) "Закладки" else "История",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = textPrimary
                    )

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Закрыть",
                            tint = textMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Tabs: Bookmarks vs History
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = card,
                    contentColor = accent,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = accent
                        )
                    }
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = {
                            Text("Закладки (${bookmarks.size})", color = if (selectedTab == 0) accent else textMuted, fontSize = 12.sp)
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = {
                            Text("История (${history.size})", color = if (selectedTab == 1) accent else textMuted, fontSize = 12.sp)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (selectedTab == 0) {
                    // Bookmarks View
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Сохраненные сайты",
                            fontSize = 12.sp,
                            color = textMuted
                        )

                        Text(
                            text = if (showAddBookmarkFields) "Отмена" else "+ Добавить",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = accent,
                            modifier = Modifier
                                .clickable { showAddBookmarkFields = !showAddBookmarkFields }
                                .padding(4.dp)
                        )
                    }

                    if (showAddBookmarkFields) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(card)
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = newTitle,
                                onValueChange = { newTitle = it },
                                placeholder = { Text("Название (напр. Яндекс Музыка)", color = textMuted, fontSize = 12.sp) },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = accent,
                                    unfocusedBorderColor = border,
                                    focusedTextColor = textPrimary,
                                    unfocusedTextColor = textPrimary
                                )
                            )
                            OutlinedTextField(
                                value = newUrl,
                                onValueChange = { newUrl = it },
                                placeholder = { Text("URL (https://...)", color = textMuted, fontSize = 12.sp) },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = accent,
                                    unfocusedBorderColor = border,
                                    focusedTextColor = textPrimary,
                                    unfocusedTextColor = textPrimary
                                )
                            )
                            Button(
                                onClick = {
                                    if (newTitle.isNotBlank() && newUrl.isNotBlank()) {
                                        onAddBookmark(newTitle, newUrl)
                                        newTitle = ""
                                        showAddBookmarkFields = false
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = accent),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.align(Alignment.End).height(34.dp)
                            ) {
                                Text("Сохранить", color = Color.White, fontSize = 11.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(bookmarks, key = { it.id }) { bookmark ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(card)
                                    .clickable {
                                        onSelectUrl(bookmark.url)
                                        onDismiss()
                                    }
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    com.example.ui.components.SiteFaviconImage(
                                        url = bookmark.url,
                                        title = bookmark.title,
                                        colorHex = "#3B82F6",
                                        size = 34.dp,
                                        iconSize = 20.dp,
                                        cornerRadius = 8.dp,
                                        isNightMode = isNightMode
                                    )
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = bookmark.title,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = textPrimary
                                        )
                                        Text(
                                            text = bookmark.url,
                                            fontSize = 11.sp,
                                            color = textMuted,
                                            maxLines = 1
                                        )
                                    }
                                }

                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Удалить",
                                    tint = textMuted,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clickable { onDeleteBookmark(bookmark) }
                                )
                            }
                        }
                    }
                } else {
                    // History View
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Недавние сайты",
                            fontSize = 12.sp,
                            color = textMuted
                        )

                        if (history.isNotEmpty()) {
                            Text(
                                text = "Очистить все",
                                fontSize = 12.sp,
                                color = MinimalRed,
                                modifier = Modifier
                                    .clickable { onClearHistory() }
                                    .padding(4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (history.isEmpty()) {
                        Box(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("История пуста", color = textMuted, fontSize = 13.sp)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(history, key = { it.id }) { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(card)
                                        .clickable {
                                            onSelectUrl(item.url)
                                            onDismiss()
                                        }
                                        .padding(horizontal = 14.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        com.example.ui.components.SiteFaviconImage(
                                            url = item.url,
                                            title = item.title.ifEmpty { item.url },
                                            colorHex = "#2563EB",
                                            size = 32.dp,
                                            iconSize = 18.dp,
                                            cornerRadius = 8.dp,
                                            isNightMode = isNightMode
                                        )
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = item.title.ifEmpty { item.url },
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Normal,
                                                color = textPrimary,
                                                maxLines = 1
                                            )
                                            Text(
                                                text = item.url,
                                                fontSize = 11.sp,
                                                color = textMuted,
                                                maxLines = 1
                                            )
                                        }
                                    }

                                    Text(
                                        text = dateFormat.format(Date(item.timestamp)),
                                        fontSize = 10.sp,
                                        color = textMuted
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
