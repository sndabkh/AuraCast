package com.example.ui.screens

import android.annotation.SuppressLint
import android.graphics.Color as AndroidColor
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.browser.AutoWebChromeClient
import com.example.browser.AutoWebViewClient
import com.example.data.model.Bookmark
import com.example.ui.BrowserAction
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
import kotlinx.coroutines.flow.SharedFlow

private const val DESKTOP_USER_AGENT =
    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun BrowserScreen(
    currentUrl: String,
    bookmarks: List<Bookmark>,
    isNightMode: Boolean,
    isDesktopMode: Boolean,
    textScale: Int,
    searchEngine: SearchEngine,
    browserAction: SharedFlow<BrowserAction>,
    onUrlChange: (String) -> Unit,
    onRecordHistory: (String, String) -> Unit,
    onAddCurrentBookmark: (String, String) -> Unit,
    onToggleNightMode: () -> Unit,
    onOpenBookmarksDialog: () -> Unit,
    onOpenSettingsDialog: () -> Unit,
    onNavigateHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    var customVideoView by remember { mutableStateOf<View?>(null) }
    var customVideoCallback by remember { mutableStateOf<android.webkit.WebChromeClient.CustomViewCallback?>(null) }

    var pageTitle by remember { mutableStateOf("AuraCast") }
    var isLoading by remember { mutableStateOf(false) }
    var loadProgress by remember { mutableIntStateOf(0) }
    var showMenu by remember { mutableStateOf(false) }

    // Minimalist theme tokens
    val barBg = if (isNightMode) MinimalDarkSurface else MinimalMilkSurface
    val barBorder = if (isNightMode) MinimalDarkBorder else MinimalMilkBorder
    val badgeBg = if (isNightMode) MinimalDarkCard else MinimalMilkCard
    val badgeBorder = if (isNightMode) MinimalDarkBorder else MinimalMilkBorder
    val textPrimary = if (isNightMode) MinimalDarkText else MinimalMilkText
    val textMuted = if (isNightMode) MinimalDarkMuted else MinimalMilkMuted
    val accent = if (isNightMode) MinimalDarkAccent else MinimalMilkAccent

    // Sync external URL changes
    LaunchedEffect(currentUrl) {
        if (currentUrl.isNotEmpty() && currentUrl != webViewRef?.url) {
            webViewRef?.loadUrl(currentUrl)
        }
    }

    // Update Desktop mode User-Agent and Text Zoom dynamically
    LaunchedEffect(isDesktopMode, textScale) {
        webViewRef?.settings?.apply {
            userAgentString = if (isDesktopMode) DESKTOP_USER_AGENT else userAgentString.replace("wv", "")
            textZoom = textScale
        }
    }

    // Handle remote browser actions (e.g., from steering wheel buttons)
    LaunchedEffect(Unit) {
        browserAction.collect { action ->
            when (action) {
                BrowserAction.GoBack -> if (webViewRef?.canGoBack() == true) webViewRef?.goBack()
                BrowserAction.GoForward -> if (webViewRef?.canGoForward() == true) webViewRef?.goForward()
                BrowserAction.Reload -> webViewRef?.reload()
                BrowserAction.Stop -> webViewRef?.stopLoading()
                BrowserAction.OpenBookmarks -> onOpenBookmarksDialog()
            }
        }
    }

    // Hardware back button support
    BackHandler(enabled = true) {
        if (customVideoView != null) {
            customVideoCallback?.onCustomViewHidden()
            customVideoView = null
        } else if (webViewRef?.canGoBack() == true) {
            webViewRef?.goBack()
        } else {
            onNavigateHome()
        }
    }

    fun navigateHome() {
        onNavigateHome()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(if (isNightMode) MinimalDarkBg else MinimalMilkBg)
            .testTag("browser_screen")
    ) {
        // Ultra-Minimalist Top Bar (strictly navigation & controls: no search bar, no mic)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(barBg)
                .border(width = 0.5.dp, color = barBorder)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // 1. Кнопка «Домой»
            IconButton(
                onClick = { navigateHome() },
                modifier = Modifier
                    .size(44.dp)
                    .testTag("home_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Домой",
                    tint = textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            // 2. Кнопка «Назад»
            IconButton(
                onClick = { webViewRef?.goBack() },
                enabled = webViewRef?.canGoBack() == true,
                modifier = Modifier
                    .size(44.dp)
                    .testTag("back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = if (webViewRef?.canGoBack() == true) textPrimary else textMuted.copy(alpha = 0.35f),
                    modifier = Modifier.size(20.dp)
                )
            }

            // 3. Кнопка «Вперед»
            IconButton(
                onClick = { webViewRef?.goForward() },
                enabled = webViewRef?.canGoForward() == true,
                modifier = Modifier
                    .size(44.dp)
                    .testTag("forward_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Вперед",
                    tint = if (webViewRef?.canGoForward() == true) textPrimary else textMuted.copy(alpha = 0.35f),
                    modifier = Modifier.size(20.dp)
                )
            }

            // 4. Кнопка «Обновить»
            IconButton(
                onClick = { webViewRef?.reload() },
                modifier = Modifier
                    .size(44.dp)
                    .testTag("reload_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Обновить страницу",
                    tint = textPrimary,
                    modifier = Modifier.size(19.dp)
                )
            }

            // Minimalist Central Web Indicator Capsule (clean, non-editable, distraction-free)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(38.dp)
                    .clip(RoundedCornerShape(19.dp))
                    .background(badgeBg)
                    .border(0.5.dp, badgeBorder, RoundedCornerShape(19.dp))
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val activeUrl = webViewRef?.url ?: currentUrl
                    if (activeUrl.isNotBlank()) {
                        com.example.ui.components.SiteFaviconImage(
                            url = activeUrl,
                            title = pageTitle,
                            colorHex = "#2563EB",
                            size = 20.dp,
                            iconSize = 13.dp,
                            cornerRadius = 5.dp,
                            isNightMode = isNightMode
                        )
                    }
                    Text(
                        text = pageTitle.ifEmpty { "AuraCast" },
                        color = textPrimary,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // 5. Переключатель на темный / молочный режим
            IconButton(
                onClick = onToggleNightMode,
                modifier = Modifier
                    .size(44.dp)
                    .testTag("night_mode_button")
            ) {
                Icon(
                    imageVector = if (isNightMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                    contentDescription = "Режим день/ночь",
                    tint = textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            // 6. Кнопка «Закладки»
            IconButton(
                onClick = onOpenBookmarksDialog,
                modifier = Modifier
                    .size(44.dp)
                    .testTag("bookmarks_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = "Закладки",
                    tint = textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            // 7. Кнопка «Меню»
            Box {
                IconButton(
                    onClick = { showMenu = true },
                    modifier = Modifier
                        .size(44.dp)
                        .testTag("browser_menu_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Меню",
                        tint = textPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.background(barBg)
                ) {
                    DropdownMenuItem(
                        text = { Text("Главная страница (Сайты)", color = textPrimary) },
                        leadingIcon = {
                            Icon(Icons.Default.Home, contentDescription = null, tint = accent)
                        },
                        onClick = {
                            showMenu = false
                            onNavigateHome()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Закладки и история", color = textPrimary) },
                        leadingIcon = {
                            Icon(Icons.Default.Bookmark, contentDescription = null, tint = textPrimary)
                        },
                        onClick = {
                            showMenu = false
                            onOpenBookmarksDialog()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Настройки", color = textPrimary) },
                        leadingIcon = {
                            Icon(Icons.Default.Settings, contentDescription = null, tint = textPrimary)
                        },
                        onClick = {
                            showMenu = false
                            onOpenSettingsDialog()
                        }
                    )
                }
            }

            // 8. Кнопка «Настройки»
            IconButton(
                onClick = onOpenSettingsDialog,
                modifier = Modifier
                    .size(44.dp)
                    .testTag("settings_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Настройки",
                    tint = textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Hairline loading indicator
        if (isLoading) {
            LinearProgressIndicator(
                progress = { loadProgress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp),
                color = accent,
                trackColor = Color.Transparent
            )
        }

        // Full-screen WebView Container with HTML5 Video
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    WebView(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setBackgroundColor(AndroidColor.BLACK)

                        settings.apply {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            databaseEnabled = true
                            setSupportZoom(true)
                            builtInZoomControls = true
                            displayZoomControls = false
                            mediaPlaybackRequiresUserGesture = false
                            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                            userAgentString = if (isDesktopMode) DESKTOP_USER_AGENT else userAgentString.replace("wv", "")
                            textZoom = textScale
                        }

                        webViewClient = AutoWebViewClient(
                            context = ctx,
                            onPageTitleChanged = { pageTitle = it },
                            onPageUrlChanged = { onUrlChange(it) },
                            onLoadingStateChanged = { isLoading = it },
                            onHistoryRecorded = onRecordHistory
                        )

                        webChromeClient = AutoWebChromeClient(
                            onProgressChanged = { loadProgress = it },
                            onTitleReceived = { pageTitle = it },
                            onCustomViewShow = { view, callback ->
                                customVideoView = view
                                customVideoCallback = callback
                            },
                            onCustomViewHide = {
                                customVideoView = null
                                customVideoCallback = null
                            }
                        )

                        loadUrl(currentUrl.ifEmpty { "https://ya.ru" })
                        webViewRef = this
                    }
                },
                update = { webView ->
                    webViewRef = webView
                }
            )

            // Fullscreen Custom Video View (HTML5)
            customVideoView?.let { videoView ->
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = {
                        FrameLayout(it).apply {
                            setBackgroundColor(AndroidColor.BLACK)
                            addView(
                                videoView,
                                ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                            )
                        }
                    }
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            webViewRef?.destroy()
        }
    }
}
