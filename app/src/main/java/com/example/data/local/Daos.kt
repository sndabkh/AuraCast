package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.Bookmark
import com.example.data.model.HistoryItem
import com.example.data.model.SiteShortcut
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY clickCount DESC, id ASC")
    fun getAllBookmarks(): Flow<List<Bookmark>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bookmarks: List<Bookmark>)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("DELETE FROM bookmarks WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE bookmarks SET clickCount = clickCount + 1 WHERE url = :url")
    suspend fun incrementClickCount(url: String)
}

@Dao
interface HistoryDao {
    @Query("SELECT * FROM browsing_history ORDER BY timestamp DESC LIMIT 100")
    fun getHistory(): Flow<List<HistoryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(item: HistoryItem): Long

    @Query("DELETE FROM browsing_history")
    suspend fun clearHistory()

    @Query("DELETE FROM browsing_history WHERE id = :id")
    suspend fun deleteById(id: Long)
}

@Dao
interface SiteShortcutDao {
    @Query("SELECT * FROM site_shortcuts ORDER BY orderIndex ASC, id ASC")
    fun getAllShortcuts(): Flow<List<SiteShortcut>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShortcut(shortcut: SiteShortcut): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shortcuts: List<SiteShortcut>)

    @Delete
    suspend fun deleteShortcut(shortcut: SiteShortcut)

    @Query("DELETE FROM site_shortcuts WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM site_shortcuts")
    suspend fun clearAll()
}
