package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val url: String,
    val category: String = "general", // "video", "search", "music", "news", "auto"
    val isFavorite: Boolean = true,
    val clickCount: Int = 0
)

@Entity(tableName = "browsing_history")
data class HistoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val url: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "site_shortcuts")
data class SiteShortcut(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val url: String,
    val category: String = "general", // "video", "music", "auto", "news", "search", "general"
    val iconColorHex: String = "#3B82F6",
    val isCustom: Boolean = false,
    val orderIndex: Int = 0
)
