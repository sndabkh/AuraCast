package com.example

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.car.SteeringWheelHandler
import com.example.ui.AppScreen
import com.example.ui.AutoMainViewModel
import com.example.ui.screens.BrowserScreen
import com.example.ui.screens.BrowserSettingsDialog
import com.example.ui.screens.HistoryBookmarksDialog
import com.example.ui.screens.HomeScreen
import com.example.ui.theme.AutoBrowserTheme
import android.view.WindowManager
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private val viewModel: AutoMainViewModel by viewModels()
    private val steeringWheelHandler = SteeringWheelHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Keep screen awake continuously while driving (no screen lock, no dimming)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        enableEdgeToEdge()

        setContent {
            val currentScreen by viewModel.currentScreen.collectAsState()
            val siteShortcuts by viewModel.siteShortcuts.collectAsState()

            val isNightMode by viewModel.isNightMode.collectAsState()
            val isDesktopMode by viewModel.isDesktopMode.collectAsState()
            val textScale by viewModel.textScale.collectAsState()
            val searchEngine by viewModel.searchEngine.collectAsState()
            val saveHistory by viewModel.saveHistory.collectAsState()

            val currentUrl by viewModel.currentUrl.collectAsState()
            val bookmarks by viewModel.bookmarks.collectAsState()
            val history by viewModel.history.collectAsState()
            val toastMessage by viewModel.toastMessage.collectAsState()

            var showBookmarksDialog by remember { mutableStateOf(false) }
            var showSettingsDialog by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                // Handle steering wheel buttons
                steeringWheelHandler.actions.collect { action ->
                    viewModel.handleSteeringWheelAction(action)
                }
            }

            // Auto-dismiss in-car notifications
            LaunchedEffect(toastMessage) {
                if (toastMessage != null) {
                    delay(2500)
                    viewModel.clearToast()
                }
            }

            AutoBrowserTheme(nightMode = isNightMode) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (currentScreen) {
                            AppScreen.HOME -> {
                                HomeScreen(
                                    sites = siteShortcuts,
                                    isNightMode = isNightMode,
                                    onOpenSite = { url -> viewModel.openSite(url) },
                                    onAddSite = { title, url, category, colorHex ->
                                        viewModel.addSiteShortcut(title, url, category, colorHex)
                                    },
                                    onDeleteSite = { site -> viewModel.deleteSiteShortcut(site) },
                                    onResetDefaults = { viewModel.resetSiteShortcutsToDefault() },
                                    onOpenBrowser = { viewModel.navigateToBrowser() },
                                    onOpenBookmarks = { showBookmarksDialog = true },
                                    onOpenSettings = { showSettingsDialog = true },
                                    onToggleNightMode = { viewModel.toggleNightMode() }
                                )
                            }
                            AppScreen.BROWSER -> {
                                BrowserScreen(
                                    currentUrl = currentUrl,
                                    bookmarks = bookmarks,
                                    isNightMode = isNightMode,
                                    isDesktopMode = isDesktopMode,
                                    textScale = textScale,
                                    searchEngine = searchEngine,
                                    browserAction = viewModel.browserAction,
                                    onUrlChange = { viewModel.setUrl(it) },
                                    onRecordHistory = { title, url ->
                                        viewModel.recordHistory(title, url)
                                    },
                                    onAddCurrentBookmark = { title, url ->
                                        viewModel.addBookmark(title, url)
                                    },
                                    onToggleNightMode = { viewModel.toggleNightMode() },
                                    onOpenBookmarksDialog = { showBookmarksDialog = true },
                                    onOpenSettingsDialog = { showSettingsDialog = true },
                                    onNavigateHome = { viewModel.navigateToHome() }
                                )
                            }
                        }

                        // Floating In-Car Toast Pill
                        AnimatedVisibility(
                            visible = toastMessage != null,
                            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 12.dp)
                        ) {
                            toastMessage?.let { msg ->
                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFF1B1E2B), RoundedCornerShape(20.dp))
                                        .border(0.5.dp, Color(0xFF33384D), RoundedCornerShape(20.dp))
                                        .padding(horizontal = 16.dp, vertical = 7.dp)
                                ) {
                                    Text(
                                        text = msg,
                                        color = Color(0xFFF1F3F7),
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }

                        // Bookmarks and History Dialog
                        if (showBookmarksDialog) {
                            HistoryBookmarksDialog(
                                bookmarks = bookmarks,
                                history = history,
                                currentUrl = currentUrl,
                                isNightMode = isNightMode,
                                onSelectUrl = { url ->
                                    viewModel.setUrl(url)
                                    showBookmarksDialog = false
                                },
                                onAddBookmark = { title, url ->
                                    viewModel.addBookmark(title, url)
                                },
                                onDeleteBookmark = { b ->
                                    viewModel.deleteBookmark(b)
                                },
                                onClearHistory = {
                                    viewModel.clearHistory()
                                },
                                onDismiss = { showBookmarksDialog = false }
                            )
                        }

                        // Settings Dialog
                        if (showSettingsDialog) {
                            BrowserSettingsDialog(
                                selectedEngine = searchEngine,
                                isDesktopMode = isDesktopMode,
                                saveHistory = saveHistory,
                                textScale = textScale,
                                isNightMode = isNightMode,
                                onSelectEngine = { viewModel.setSearchEngine(it) },
                                onToggleDesktopMode = { viewModel.toggleDesktopMode() },
                                onToggleSaveHistory = { viewModel.toggleSaveHistory() },
                                onSelectTextScale = { viewModel.setTextScale(it) },
                                onClearHistory = { viewModel.clearHistory() },
                                onShowToast = { viewModel.showToast(it) },
                                onDismiss = { showSettingsDialog = false }
                            )
                        }
                    }
                }
            }
        }
    }

    // Intercept physical buttons from car steering wheel and console rotary knob
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event != null && steeringWheelHandler.handleKeyEvent(keyCode, event)) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
