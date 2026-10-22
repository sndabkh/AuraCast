package com.example.car

import android.media.browse.MediaBrowser.MediaItem
import android.media.session.MediaSession
import android.os.Bundle
import android.service.media.MediaBrowserService

/**
 * Automotive MediaBrowserService that enables Android Auto
 * to recognize AuraCast as an automotive-compatible media and content app,
 * allowing it to appear in the Android Auto launcher customization settings.
 */
class CarMediaBrowserService : MediaBrowserService() {

    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSession(this, "AuraCastMediaSession").apply {
            isActive = true
        }
        sessionToken = mediaSession?.sessionToken
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        // Return valid root for Android Auto companion querying
        return BrowserRoot("auracast_root", null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaItem>>
    ) {
        // Provide empty media list; primary interaction happens through web content
        result.sendResult(mutableListOf())
    }

    override fun onDestroy() {
        mediaSession?.release()
        super.onDestroy()
    }
}
