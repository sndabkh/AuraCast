package com.example.ui.util

import java.net.URI

object FaviconUtils {

    /**
     * Extracts a clean domain from any URL string, e.g.:
     * - "https://m.youtube.com/watch" -> "youtube.com" (or "m.youtube.com")
     * - "rutube.ru" -> "rutube.ru"
     * - "https://vk.com/video" -> "vk.com"
     */
    fun extractDomain(rawUrl: String): String {
        if (rawUrl.isBlank()) return ""
        val trimmed = rawUrl.trim()
        val withoutProtocol = trimmed
            .removePrefix("https://")
            .removePrefix("http://")
        val withoutPath = withoutProtocol.substringBefore("/").substringBefore("?").substringBefore(":")
        val cleanDomain = withoutPath
            .removePrefix("www.")
            .removePrefix("m.")
            .removePrefix("mobile.")
        return cleanDomain
    }

    /**
     * Generates a high-resolution 128px favicon URL using Google's Favicon Service.
     */
    fun getGoogleFaviconUrl(rawUrl: String): String {
        val domain = extractDomain(rawUrl)
        if (domain.isBlank() || !domain.contains(".")) return ""
        return "https://www.google.com/s2/favicons?domain=$domain&sz=128"
    }

    /**
     * Fallback favicon provider via DuckDuckGo.
     */
    fun getDuckDuckGoFaviconUrl(rawUrl: String): String {
        val domain = extractDomain(rawUrl)
        if (domain.isBlank() || !domain.contains(".")) return ""
        return "https://icons.duckduckgo.com/ip3/$domain.ico"
    }

    /**
     * Generates a two-letter monogram for fallback display.
     */
    fun getMonogram(title: String): String {
        val trimmed = title.trim()
        if (trimmed.isEmpty()) return "W"
        val words = trimmed.split(Regex("\\s+")).filter { it.isNotBlank() }
        return if (words.size >= 2) {
            "${words[0].take(1)}${words[1].take(1)}".uppercase()
        } else {
            trimmed.take(2).uppercase()
        }
    }

    /**
     * Auto-detects likely site title from domain name if user didn't type one.
     */
    fun guessSiteTitle(rawUrl: String): String {
        val domain = extractDomain(rawUrl)
        if (domain.isBlank()) return ""
        val namePart = domain.substringBeforeLast(".")
        return when (namePart.lowercase()) {
            "youtube" -> "YouTube"
            "rutube" -> "Rutube"
            "vk" -> "VK Видео"
            "kinopoisk" -> "Кинопоиск"
            "yandex", "ya" -> "Яндекс"
            "music.yandex" -> "Яндекс Музыка"
            "drive2" -> "Drive2"
            "auto" -> "Auto.ru"
            "dzen" -> "Дзен"
            "gismeteo" -> "Gismeteo"
            "twitch" -> "Twitch"
            "reddit" -> "Reddit"
            "telegram", "t" -> "Telegram"
            "spotify" -> "Spotify"
            "soundcloud" -> "SoundCloud"
            "2gis" -> "2ГИС"
            "avito" -> "Авито"
            "habr" -> "Хабр"
            else -> namePart.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    }
}
