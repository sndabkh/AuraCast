package com.example.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MinimalDarkAccent
import com.example.ui.theme.MinimalDarkBorder
import com.example.ui.theme.MinimalDarkCard
import com.example.ui.theme.MinimalDarkMuted
import com.example.ui.theme.MinimalDarkSurface
import com.example.ui.theme.MinimalDarkText
import com.example.ui.theme.MinimalMilkAccent
import com.example.ui.theme.MinimalMilkBorder
import com.example.ui.theme.MinimalMilkCard
import com.example.ui.theme.MinimalMilkMuted
import com.example.ui.theme.MinimalMilkSurface
import com.example.ui.theme.MinimalMilkText

@Composable
fun PrivacyPolicyScreen(
    isNightMode: Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textPrimary = if (isNightMode) MinimalDarkText else MinimalMilkText
    val textMuted = if (isNightMode) MinimalDarkMuted else MinimalMilkMuted
    val cardBg = if (isNightMode) MinimalDarkCard else MinimalMilkCard
    val borderCol = if (isNightMode) MinimalDarkBorder else MinimalMilkBorder
    val accent = if (isNightMode) MinimalDarkAccent else MinimalMilkAccent

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("privacy_policy_screen")
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .testTag("privacy_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад к настройкам",
                    tint = textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = "Политика конфиденциальности",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textPrimary
                )
                Text(
                    text = "AuraCast • com.aistudio.smartcast.xrvkp • Google Play Compliant",
                    fontSize = 11.sp,
                    color = accent
                )
            }
        }

        HorizontalDivider(color = borderCol, thickness = 0.5.dp)

        Spacer(modifier = Modifier.height(12.dp))

        // Policy Content Scrollable
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Overview Badge Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardBg)
                    .border(0.5.dp, borderCol, RoundedCornerShape(12.dp))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Безопасность",
                        tint = accent,
                        modifier = Modifier
                            .size(22.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Принцип нулевого сбора данных (Zero-Data)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textPrimary
                        )
                        Text(
                            text = "Приложение AuraCast создано с приоритетом приватности. Мы не собираем, не передаем третьим лицам и не продаем персональные данные пользователей. Все данные хранятся исключительно на вашем устройстве.",
                            fontSize = 11.5.sp,
                            color = textMuted,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // Section 1
            PolicySectionCard(
                title = "1. Общие положения и назначение",
                content = "Настоящая Политика конфиденциальности описывает порядок обработки информации в мобильном приложении AuraCast (пакет: com.aistudio.smartcast.xrvkp) для Android и Android Auto. Приложение представляет собой специализированный минималистичный веб-браузер, ориентированный на комфортную работу в автомобильной среде. Используя приложение, вы соглашаетесь с условиями настоящей Политики.",
                textPrimary = textPrimary,
                textMuted = textMuted,
                cardBg = cardBg,
                borderCol = borderCol
            )

            // Section 2
            PolicySectionCard(
                title = "2. Сбор и обработка персональных данных",
                content = "Разработчик приложения AuraCast категорически НЕ осуществляет:\n" +
                        "• Сбор персональных идентификаторов (ФИО, email, телефон, аккаунты).\n" +
                        "• Сбор уникальных идентификаторов оборудования (IMEI, MAC-адреса, IMSI).\n" +
                        "• Использование рекламных идентификаторов Android Advertising ID (AAID).\n" +
                        "• Профилирование поведения пользователей или поведенческий таргетинг.\n" +
                        "• Продажу, аренду или передачу любых пользовательских данных третьим лицам.",
                textPrimary = textPrimary,
                textMuted = textMuted,
                cardBg = cardBg,
                borderCol = borderCol
            )

            // Section 3
            PolicySectionCard(
                title = "3. Отсутствие скрытой аналитики и трекеров",
                content = "В исходном коде приложения AuraCast отсутствуют сторонние трекинговые SDK (такие как Firebase Analytics, AppsFlyer, Facebook SDK, Adjust и аналоги). Никакая скрытая фоновая телеметрия с устройства не передается.",
                textPrimary = textPrimary,
                textMuted = textMuted,
                cardBg = cardBg,
                borderCol = borderCol
            )

            // Section 4
            PolicySectionCard(
                title = "4. Локальное хранение (Offline & Local-Only)",
                content = "Все пользовательские данные сохраняются исключительно локально на устройстве:\n" +
                        "• Закладки (сохраненные сайты) и история посещений хранятся во внутренней изолированной базе данных SQLite / Room приложения.\n" +
                        "• Пользователь в любой момент может отключить сохранение истории в настройках либо очистить историю нажатием кнопки «Очистить историю».\n" +
                        "• Данные никогда не передаются на внешние серверы и полностью удаляются при деинсталляции приложения.",
                textPrimary = textPrimary,
                textMuted = textMuted,
                cardBg = cardBg,
                borderCol = borderCol
            )

            // Section 5
            PolicySectionCard(
                title = "5. Движок WebView, Кэш и Cookie-файлы",
                content = "Для отображения страниц AuraCast задействует стандартный системный защищенный компонент Android System WebView.\n" +
                        "• Сторонние веб-сайты, открываемые пользователем, могут устанавливать собственные сессионные cookie и сохранять кэш в песочнице WebView.\n" +
                        "• В приложении предусмотрена кнопка мгновенной полной очистки кэша и cookie (WebStorage.deleteAllData() и CookieManager.removeAllCookies()).",
                textPrimary = textPrimary,
                textMuted = textMuted,
                cardBg = cardBg,
                borderCol = borderCol
            )

            // Section 6
            PolicySectionCard(
                title = "6. Системные разрешения Android (Политика наименьших привилегий)",
                content = "Приложение запрашивает исключительно необходимые разрешения для работы веб-браузера:\n" +
                        "• android.permission.INTERNET — требуется для загрузки веб-страниц по протоколам HTTP/HTTPS.\n" +
                        "• android.permission.ACCESS_NETWORK_STATE — требуется для проверки доступности сети и предотвращения ошибок загрузки.\n\n" +
                        "Приложение НЕ запрашивает и не использует конфиденциальные разрешения: доступ к микрофону (RECORD_AUDIO), доступ к геопозиции (ACCESS_FINE_LOCATION), чтение контактов или файлов накопителя.",
                textPrimary = textPrimary,
                textMuted = textMuted,
                cardBg = cardBg,
                borderCol = borderCol
            )

            // Section 7
            PolicySectionCard(
                title = "7. Безопасность вождения (Google Play Car Guidelines)",
                content = "Приложение разработано в строгом соответствии с требованиями Google Play Developer Policy для автомобилей:\n" +
                        "• Интерфейс оптимизирован для быстрого восприятия с крупными элементами управления.\n" +
                        "• Отсутствуют навязчивые всплывающие баннеры и блокирующие диалоги.\n" +
                        "• Водитель обязан уделять главное внимание контролю за дорожной обстановкой. Использование браузера рекомендуется во время безопасной стоянки или пассажирами.",
                textPrimary = textPrimary,
                textMuted = textMuted,
                cardBg = cardBg,
                borderCol = borderCol
            )

            // Section 8
            PolicySectionCard(
                title = "8. Права пользователей (GDPR, CCPA, Закон о персональных данных)",
                content = "Пользователи обладают полными правами в отношении своих данных, включая «право на забвение»:\n" +
                        "• Доступ и удаление: полное удаление истории и кэша доступно в меню «Настройки» в один клик.\n" +
                        "• Отзыв согласия: удаление приложения с устройства приводит к безвозвратному удалению всех локально сохраненных данных.",
                textPrimary = textPrimary,
                textMuted = textMuted,
                cardBg = cardBg,
                borderCol = borderCol
            )

            // Section 9
            PolicySectionCard(
                title = "9. Изменения в Политике конфиденциальности",
                content = "Мы можем периодически обновлять настоящую Политику для соответствия изменениям законодательства или требованиям Google Play. Актуальная редакция всегда доступна в меню «Настройки» приложения.",
                textPrimary = textPrimary,
                textMuted = textMuted,
                cardBg = cardBg,
                borderCol = borderCol
            )

            // Section 10 - Contacts
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardBg)
                    .border(0.5.dp, borderCol, RoundedCornerShape(12.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Mail,
                            contentDescription = "Контакты",
                            tint = accent,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "10. Контактные данные разработчика",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textPrimary
                        )
                    }

                    Text(
                        text = "По любым вопросам, касающимся настоящей Политики конфиденциальности или работы приложения AuraCast, вы можете связаться с нами напрямую:\n\n" +
                                "Электронная почта: abdulkhaev535@gmail.com\n" +
                                "Идентификатор приложения: com.aistudio.smartcast.xrvkp\n" +
                                "Дата редакции: Октябрь 2026 г.",
                        fontSize = 11.5.sp,
                        color = textPrimary,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun PolicySectionCard(
    title: String,
    content: String,
    textPrimary: androidx.compose.ui.graphics.Color,
    textMuted: androidx.compose.ui.graphics.Color,
    cardBg: androidx.compose.ui.graphics.Color,
    borderCol: androidx.compose.ui.graphics.Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(cardBg)
            .border(0.5.dp, borderCol, RoundedCornerShape(12.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = textPrimary
            )
            Text(
                text = content,
                fontSize = 11.5.sp,
                color = textMuted,
                lineHeight = 16.sp
            )
        }
    }
}
