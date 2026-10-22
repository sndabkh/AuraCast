package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.ui.util.FaviconUtils

@Composable
fun SiteFaviconImage(
    url: String,
    title: String,
    colorHex: String,
    size: Dp = 46.dp,
    iconSize: Dp = 28.dp,
    cornerRadius: Dp = 12.dp,
    isNightMode: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val parsedColor = remember(colorHex) {
        try {
            Color(android.graphics.Color.parseColor(colorHex))
        } catch (_: Exception) {
            Color(0xFF3B82F6)
        }
    }

    val faviconUrl = remember(url) {
        FaviconUtils.getGoogleFaviconUrl(url)
    }

    val monogram = remember(title) {
        FaviconUtils.getMonogram(title)
    }

    val containerBg = if (isNightMode) {
        Color(0xFF222634)
    } else {
        Color(0xFFFAF6EE)
    }

    val containerBorder = if (isNightMode) {
        Color(0xFF333A4D)
    } else {
        Color(0xFFE6DECF)
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .background(containerBg)
            .border(0.8.dp, containerBorder, RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        if (faviconUrl.isNotBlank()) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(faviconUrl)
                    .crossfade(250)
                    .build(),
                contentDescription = "$title logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(iconSize)
                    .padding(2.dp),
                loading = {
                    FallbackMonogram(monogram = monogram, color = parsedColor, size = size)
                },
                error = {
                    FallbackMonogram(monogram = monogram, color = parsedColor, size = size)
                }
            )
        } else {
            FallbackMonogram(monogram = monogram, color = parsedColor, size = size)
        }
    }
}

@Composable
private fun FallbackMonogram(
    monogram: String,
    color: Color,
    size: Dp
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        color,
                        color.copy(alpha = 0.8f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = monogram,
            color = Color.White,
            fontSize = (size.value * 0.32f).sp,
            fontWeight = FontWeight.Bold
        )
    }
}
