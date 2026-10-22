package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.car.SteeringWheelAction
import com.example.data.local.AppDatabase
import com.example.data.model.Bookmark
import com.example.data.model.HistoryItem
import com.example.data.model.SiteShortcut
import com.example.ui.screens.SearchEngine
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.URLEncoder

enum class AppScreen {
    HOME,
    BROWSER
}

sealed class BrowserAction {
    object GoBack : BrowserAction()
    object GoForward : BrowserAction()
    object Reload : BrowserAction()
    object Stop : BrowserAction()
    object OpenBookmarks : BrowserAction()
}

class AutoMainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val bookmarkDao = database.bookmarkDao()
    private val historyDao = database.historyDao()
    private val siteShortcutDao = database.siteShortcutDao()

    // Screen State: Starts on HOME page with sites collection
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Current Web URL (Default to ya.ru)
    private val _currentUrl = MutableStateFlow("https://ya.ru")
    val currentUrl: StateFlow<String> = _currentUrl.asStateFlow()

    // Automotive Night Mode (defaults to dark OLED theme)
    private val _isNightMode = MutableStateFlow(true)
    val isNightMode: StateFlow<Boolean> = _isNightMode.asStateFlow()

    // Settings
    private val _searchEngine = MutableStateFlow(SearchEngine.YANDEX)
    val searchEngine: StateFlow<SearchEngine> = _searchEngine.asStateFlow()

    private val _isDesktopMode = MutableStateFlow(false)
    val isDesktopMode: StateFlow<Boolean> = _isDesktopMode.asStateFlow()

    private val _saveHistory = MutableStateFlow(true)
    val saveHistory: StateFlow<Boolean> = _saveHistory.asStateFlow()

    private val _textScale = MutableStateFlow(100)
    val textScale: StateFlow<Int> = _textScale.asStateFlow()

    // Toast message
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    // Navigation events dispatched to WebView
    private val _browserAction = MutableSharedFlow<BrowserAction>(extraBufferCapacity = 4)
    val browserAction: SharedFlow<BrowserAction> = _browserAction.asSharedFlow()

    // Bookmarks and History Flow from Room
    val bookmarks: StateFlow<List<Bookmark>> = bookmarkDao.getAllBookmarks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val history: StateFlow<List<HistoryItem>> = historyDao.getHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Customizable Sites on Home Page
    val siteShortcuts: StateFlow<List<SiteShortcut>> = siteShortcutDao.getAllShortcuts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            // Seed defaults if empty
            val current = siteShortcutDao.getAllShortcuts().firstOrNull() ?: emptyList()
            if (current.isEmpty()) {
                AppDatabase.populateInitialShortcuts(siteShortcutDao)
            }
        }
    }

    fun navigateToHome() {
        _currentScreen.value = AppScreen.HOME
    }

    fun navigateToBrowser() {
        _currentScreen.value = AppScreen.BROWSER
    }

    fun openSite(url: String) {
        setUrl(url)
        _currentScreen.value = AppScreen.BROWSER
    }

    fun setUrl(url: String) {
        val trimmed = url.trim()
        val formatted = when {
            trimmed.startsWith("http://") || trimmed.startsWith("https://") -> trimmed
            trimmed.contains(".") && !trimmed.contains(" ") -> "https://$trimmed"
            else -> "${_searchEngine.value.searchUrl}${URLEncoder.encode(trimmed, "UTF-8")}"
        }
        _currentUrl.value = formatted
    }

    fun addSiteShortcut(title: String, rawUrl: String, category: String = "general", colorHex: String = "#3B82F6") {
        viewModelScope.launch {
            val cleanTitle = title.trim().ifEmpty { "Сайт" }
            val cleanUrl = rawUrl.trim().let {
                if (it.startsWith("http://") || it.startsWith("https://")) it else "https://$it"
            }
            val newShortcut = SiteShortcut(
                title = cleanTitle,
                url = cleanUrl,
                category = category,
                iconColorHex = colorHex,
                isCustom = true,
                orderIndex = (siteShortcuts.value.maxOfOrNull { it.orderIndex } ?: 0) + 1
            )
            siteShortcutDao.insertShortcut(newShortcut)
            _toastMessage.value = "Сайт «$cleanTitle» добавлен"
        }
    }

    fun deleteSiteShortcut(shortcut: SiteShortcut) {
        viewModelScope.launch {
            siteShortcutDao.deleteShortcut(shortcut)
            _toastMessage.value = "Сайт «${shortcut.title}» удален"
        }
    }

    fun resetSiteShortcutsToDefault() {
        viewModelScope.launch {
            siteShortcutDao.clearAll()
            AppDatabase.populateInitialShortcuts(siteShortcutDao)
            _toastMessage.value = "Сайты сброшены к начальным"
        }
    }

    fun toggleNightMode() {
        _isNightMode.value = !_isNightMode.value
        _toastMessage.value = if (_isNightMode.value) "Включен ночной режим" else "Включен молочный режим"
    }

    fun setSearchEngine(engine: SearchEngine) {
        _searchEngine.value = engine
        _toastMessage.value = "Поиск: ${engine.title}"
    }

    fun toggleDesktopMode() {
        _isDesktopMode.value = !_isDesktopMode.value
        _toastMessage.value = if (_isDesktopMode.value) "Включена версия для ПК" else "Включена мобильная версия"
    }

    fun toggleSaveHistory() {
        _saveHistory.value = !_saveHistory.value
    }

    fun setTextScale(scale: Int) {
        _textScale.value = scale
        _toastMessage.value = "Масштаб: $scale%"
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
    }

    fun handleSteeringWheelAction(action: SteeringWheelAction) {
        when (action) {
            SteeringWheelAction.NEXT_ITEM -> {
                val list = siteShortcuts.value
                if (list.isNotEmpty()) {
                    val currentIndex = list.indexOfFirst { it.url == _currentUrl.value }
                    val nextIndex = (currentIndex + 1) % list.size
                    openSite(list[nextIndex].url)
                    _toastMessage.value = "Руль: ${list[nextIndex].title}"
                } else {
                    _browserAction.tryEmit(BrowserAction.GoForward)
                }
            }
            SteeringWheelAction.PREVIOUS_ITEM -> {
                val list = siteShortcuts.value
                if (list.isNotEmpty()) {
                    val currentIndex = list.indexOfFirst { it.url == _currentUrl.value }
                    val prevIndex = if (currentIndex <= 0) list.size - 1 else currentIndex - 1
                    openSite(list[prevIndex].url)
                    _toastMessage.value = "Руль: ${list[prevIndex].title}"
                } else {
                    _browserAction.tryEmit(BrowserAction.GoBack)
                }
            }
            SteeringWheelAction.TRIGGER_VOICE -> {
                _browserAction.tryEmit(BrowserAction.Reload)
                _toastMessage.value = "Руль: Обновление страницы"
            }
            SteeringWheelAction.PLAY_PAUSE -> {
                _browserAction.tryEmit(BrowserAction.Stop)
                _toastMessage.value = "Руль: Пауза / Стоп"
            }
            SteeringWheelAction.BACK -> {
                if (_currentScreen.value == AppScreen.BROWSER) {
                    navigateToHome()
                } else {
                    _browserAction.tryEmit(BrowserAction.GoBack)
                }
            }
            else -> {}
        }
    }

    fun addBookmark(title: String, url: String) {
        viewModelScope.launch {
            val category = when {
                url.contains("youtube", ignoreCase = true) -> "video"
                url.contains("radio", ignoreCase = true) -> "music"
                url.contains("auto", ignoreCase = true) -> "auto"
                else -> "general"
            }
            bookmarkDao.insertBookmark(Bookmark(title = title, url = url, category = category))
            _toastMessage.value = "Добавлено в закладки"
        }
    }

    fun deleteBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            bookmarkDao.deleteBookmark(bookmark)
            _toastMessage.value = "Закладка удалена"
        }
    }

    fun recordHistory(title: String, url: String) {
        if (!_saveHistory.value) return
        viewModelScope.launch {
            historyDao.insertHistory(HistoryItem(title = title, url = url))
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            historyDao.clearHistory()
            _toastMessage.value = "История очищена"
        }
    }

    fun clearToast() {
        _toastMessage.value = null
    }
}
