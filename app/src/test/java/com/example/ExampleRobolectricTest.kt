package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.car.CarMediaBrowserService
import com.example.data.model.SiteShortcut
import com.example.ui.AppScreen
import com.example.ui.screens.SearchEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("AuraCast", appName)
  }

  @Test
  fun `search engines have valid query urls`() {
    val yandex = SearchEngine.YANDEX
    assertTrue(yandex.searchUrl.startsWith("https://"))
  }

  @Test
  fun `site shortcut model creates properly`() {
    val shortcut = SiteShortcut(
      title = "Rutube",
      url = "https://rutube.ru",
      category = "video",
      iconColorHex = "#0284C7"
    )
    assertEquals("Rutube", shortcut.title)
    assertEquals("https://rutube.ru", shortcut.url)
    assertEquals("video", shortcut.category)
  }

  @Test
  fun `app navigation screens defined`() {
    val screens = AppScreen.values()
    assertTrue(screens.contains(AppScreen.HOME))
    assertTrue(screens.contains(AppScreen.BROWSER))
  }

  @Test
  fun `car media browser service returns valid root`() {
    val service = Robolectric.buildService(CarMediaBrowserService::class.java).create().get()
    assertNotNull(service)
    val root = service.onGetRoot("com.google.android.projection.gearhead", 1000, null)
    assertNotNull(root)
    assertEquals("auracast_root", root?.rootId)
  }

  @Test
  fun `favicon utils extracts domain and generates google favicon url`() {
    val domain = com.example.ui.util.FaviconUtils.extractDomain("https://m.youtube.com/watch?v=123")
    assertEquals("youtube.com", domain)

    val faviconUrl = com.example.ui.util.FaviconUtils.getGoogleFaviconUrl("https://rutube.ru")
    assertTrue(faviconUrl.contains("rutube.ru"))
    assertTrue(faviconUrl.contains("sz=128"))

    val guessed = com.example.ui.util.FaviconUtils.guessSiteTitle("kinopoisk.ru")
    assertEquals("Кинопоиск", guessed)
  }
}
