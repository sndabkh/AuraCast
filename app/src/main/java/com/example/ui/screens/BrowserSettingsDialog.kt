package com.example.ui.screens

import android.content.ComponentName
import android.content.Intent
import android.webkit.CookieManager
import android.webkit.WebStorage
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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

enum class SearchEngine(val title: String, val searchUrl: String) {
    YANDEX("Яндекс", "https://ya.ru/search/?text="),
    GOOGLE("Google", "https://www.google.com/search?q="),
    DUCKDUCKGO("DuckDuckGo", "https://duckduckgo.com/?q="),
    BING("Bing", "https://www.bing.com/search?q=")
}

@Composable
fun BrowserSettingsDialog(
    selectedEngine: SearchEngine,
    isDesktopMode: Boolean,
    saveHistory: Boolean,
    textScale: Int,
    isNightMode: Boolean,
    onSelectEngine: (SearchEngine) -> Unit,
    onToggleDesktopMode: () -> Unit,
    onToggleSaveHistory: () -> Unit,
    onSelectTextScale: (Int) -> Unit,
    onClearHistory: () -> Unit,
    onShowToast: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var showPrivacyPolicy by remember { mutableStateOf(false) }

    val bg = if (isNightMode) MinimalDarkBg else MinimalMilkBg
    val surface = if (isNightMode) MinimalDarkSurface else MinimalMilkSurface
    val border = if (isNightMode) MinimalDarkBorder else MinimalMilkBorder
    val card = if (isNightMode) MinimalDarkCard else MinimalMilkCard
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
                .testTag("browser_settings_dialog")
        ) {
            if (showPrivacyPolicy) {
                PrivacyPolicyScreen(
                    isNightMode = isNightMode,
                    onBack = { showPrivacyPolicy = false }
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Настройки",
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

                    // Section: Search Engine
                    Column {
                        Text(
                            text = "Поисковая система",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = textMuted,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            SearchEngine.values().forEach { engine ->
                                val isSelected = engine == selectedEngine
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (isSelected) accent else card)
                                        .clickable { onSelectEngine(engine) }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = engine.title,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (isSelected) Color.White else textPrimary
                                    )
                                }
                            }
                        }
                    }

                    // Section: Text Scale
                    Column {
                        Text(
                            text = "Размер текста",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = textMuted,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(100 to "100%", 115 to "115%", 130 to "130%").forEach { (scale, label) ->
                                val isSelected = textScale == scale
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (isSelected) accent else card)
                                        .clickable { onSelectTextScale(scale) }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = label,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (isSelected) Color.White else textPrimary
                                    )
                                }
                            }
                        }
                    }

                    // Section: Desktop mode
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(card)
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Версия для ПК",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                color = textPrimary
                            )
                            Text(
                                text = "Запрашивать десктопные страницы",
                                fontSize = 11.sp,
                                color = textMuted
                            )
                        }

                        Switch(
                            checked = isDesktopMode,
                            onCheckedChange = { onToggleDesktopMode() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = accent
                            )
                        )
                    }

                    // Section: Save History
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(card)
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Сохранять историю",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                color = textPrimary
                            )
                            Text(
                                text = "Локальная запись посещенных сайтов",
                                fontSize = 11.sp,
                                color = textMuted
                            )
                        }

                        Switch(
                            checked = saveHistory,
                            onCheckedChange = { onToggleSaveHistory() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = accent
                            )
                        )
                    }

                    // Clear actions
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(card)
                                .clickable {
                                    try {
                                        WebStorage.getInstance().deleteAllData()
                                        CookieManager.getInstance().removeAllCookies(null)
                                        onShowToast("Кэш очищен")
                                    } catch (_: Exception) {}
                                }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Очистить кэш", fontSize = 12.sp, color = textPrimary)
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(card)
                                .clickable {
                                    onClearHistory()
                                    onShowToast("История очищена")
                                }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Очистить историю", fontSize = 12.sp, color = MinimalRed)
                        }
                    }

                    // Android Auto Launcher Troubleshooting Guide
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(card)
                            .padding(14.dp)
                            .testTag("android_auto_guide_card")
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "Отображение в Android Auto",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = textPrimary
                            )
                            Text(
                                text = "Если AuraCast не видно в меню Android Auto на авто или в «Настройках панели запуска» телефона:",
                                fontSize = 11.5.sp,
                                color = textMuted
                            )
                            Text(
                                text = "1. Откройте «Настройки» телефона → «Android Auto»\n2. Прокрутите вниз до пункта «Версия» и нажмите на него 10 раз (включится режим разработчика)\n3. Нажмите меню (три точки) вверху → «Для разработчиков»\n4. Поставьте галочку «Неизвестные источники» (Unknown sources)\n5. Вернитесь в «Настройка панели запуска» — AuraCast появится в списке!",
                                fontSize = 11.sp,
                                color = textPrimary,
                                lineHeight = 16.sp
                            )
                            Button(
                                onClick = {
                                    try {
                                        val intent = Intent("com.google.android.gms.car.SETTINGS")
                                        context.startActivity(intent)
                                    } catch (_: Exception) {
                                        try {
                                            val intent = Intent().apply {
                                                component = ComponentName("com.google.android.projection.gearhead", "com.google.android.projection.gearhead.companion.settings.DefaultSettingsActivity")
                                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                            }
                                            context.startActivity(intent)
                                        } catch (_: Exception) {}
                                    }
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = accent),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Text("Открыть настройки Android Auto", fontSize = 11.5.sp, color = Color.White)
                            }
                        }
                    }

                    // Privacy Policy Link
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(card)
                            .clickable { showPrivacyPolicy = true }
                            .padding(horizontal = 14.dp, vertical = 12.dp)
                            .testTag("privacy_policy_button")
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Политика конфиденциальности",
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = textPrimary
                                )
                                Text(
                                    text = "Google Play, GDPR, защита данных и безопасность водителя",
                                    fontSize = 10.5.sp,
                                    color = textMuted
                                )
                            }
                            Text(
                                text = "→",
                                fontSize = 14.sp,
                                color = textMuted
                            )
                        }
                    }
                }
            }
        }
    }
}
