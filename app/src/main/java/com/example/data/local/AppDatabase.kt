package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.Bookmark
import com.example.data.model.HistoryItem
import com.example.data.model.SiteShortcut
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Bookmark::class, HistoryItem::class, SiteShortcut::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun historyDao(): HistoryDao
    abstract fun siteShortcutDao(): SiteShortcutDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "auto_browser_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateInitialBookmarks(database.bookmarkDao())
                        populateInitialShortcuts(database.siteShortcutDao())
                    }
                }
            }
        }

        suspend fun populateInitialBookmarks(dao: BookmarkDao) {
            val defaultBookmarks = listOf(
                Bookmark(title = "YouTube", url = "https://m.youtube.com", category = "video", clickCount = 10),
                Bookmark(title = "Яндекс Карты", url = "https://yandex.ru/maps", category = "maps", clickCount = 8),
                Bookmark(title = "Яндекс Поиск", url = "https://ya.ru", category = "search", clickCount = 7),
                Bookmark(title = "Auto.ru", url = "https://auto.ru", category = "auto", clickCount = 6),
                Bookmark(title = "Drive2", url = "https://www.drive2.ru", category = "auto", clickCount = 5),
                Bookmark(title = "Google", url = "https://www.google.com", category = "search", clickCount = 4),
                Bookmark(title = "Онлайн Радио", url = "https://radiopotok.ru", category = "music", clickCount = 3),
                Bookmark(title = "Погода Gismeteo", url = "https://www.gismeteo.ru", category = "general", clickCount = 2),
                Bookmark(title = "Новости Авто", url = "https://ria.ru/auto/", category = "news", clickCount = 1)
            )
            dao.insertAll(defaultBookmarks)
        }

        suspend fun populateInitialShortcuts(dao: SiteShortcutDao) {
            val defaultShortcuts = listOf(
                SiteShortcut(title = "YouTube", url = "https://m.youtube.com", category = "video", iconColorHex = "#EF4444", orderIndex = 0),
                SiteShortcut(title = "Rutube", url = "https://rutube.ru", category = "video", iconColorHex = "#0284C7", orderIndex = 1),
                SiteShortcut(title = "VK Видео", url = "https://vk.com/video", category = "video", iconColorHex = "#2563EB", orderIndex = 2),
                SiteShortcut(title = "Кинопоиск", url = "https://www.kinopoisk.ru", category = "video", iconColorHex = "#F97316", orderIndex = 3),
                SiteShortcut(title = "Яндекс", url = "https://ya.ru", category = "search", iconColorHex = "#E11D48", orderIndex = 4),
                SiteShortcut(title = "Яндекс Музыка", url = "https://music.yandex.ru", category = "music", iconColorHex = "#EAB308", orderIndex = 5),
                SiteShortcut(title = "Drive2", url = "https://www.drive2.ru", category = "auto", iconColorHex = "#DC2626", orderIndex = 6),
                SiteShortcut(title = "Auto.ru", url = "https://auto.ru", category = "auto", iconColorHex = "#B91C1C", orderIndex = 7),
                SiteShortcut(title = "Новости", url = "https://dzen.ru/news", category = "news", iconColorHex = "#6366F1", orderIndex = 8),
                SiteShortcut(title = "Онлайн Радио", url = "https://radiopotok.ru", category = "music", iconColorHex = "#10B981", orderIndex = 9),
                SiteShortcut(title = "Погода", url = "https://yandex.ru/pogoda", category = "general", iconColorHex = "#06B6D4", orderIndex = 10),
                SiteShortcut(title = "Google", url = "https://www.google.com", category = "search", iconColorHex = "#4285F4", orderIndex = 11)
            )
            dao.insertAll(defaultShortcuts)
        }
    }
}
