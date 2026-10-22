# AuraCast — In-Car Browser & Media Streamer for Android & Android Auto

[![English](https://img.shields.io/badge/Language-English-blue.svg)](#-english-documentation)
[![Russian](https://img.shields.io/badge/Language-Русский-red.svg)](#-русская-документация)
[![Android](https://img.shields.io/badge/Platform-Android%207.0%2B-green.svg)](https://developer.android.com)
[![Android Auto](https://img.shields.io/badge/Android%20Auto-Compatible-blue.svg)](https://www.android.com/auto/)
[![License](https://img.shields.io/badge/License-MIT-orange.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2-purple.svg)](https://kotlinlang.org/)

---

## 📥 DOWNLOAD APK / СКАЧАТЬ APK

Choose any convenient download method below / Выберите любой удобный способ загрузки:

| Method / Способ | Download Link / Ссылка | Description / Описание |
| :--- | :--- | :--- |
| **⭐ GitHub Releases (Latest)** | [📦 **Download from Releases / Скачать из релизов**](../../releases/latest) | Official precompiled APK releases / Официальные стабильные сборки |
| **📁 Repository File (Direct)** | [⬇️ **Download `AuraCast-v1.0.apk`**](releases/AuraCast-v1.0.apk) | Direct repository download. On GitHub page, click **Download** or **View Raw** |
| **⚙️ GitHub Actions CI/CD** | [🛠️ **Workflow Artifacts / Сборки из Actions**](../../actions) | Automatically built APK from the latest commit |

> 💡 **Mobile Tip / Совет для смартфонов:** When opening `releases/AuraCast-v1.0.apk` on GitHub mobile browser, tap the **"View Raw"** or **"Download"** button to start downloading immediately. / При открытии файла `releases/AuraCast-v1.0.apk` в мобильном браузере нажмите кнопку **"Download"** (или **"View Raw"**) для прямого скачивания.

---

# 🇬🇧 English Documentation

## ⚠️ CRITICAL LEGAL NOTICE & DISCLAIMER OF LIABILITY

> **WARNING! READ CAREFULLY PRIOR TO INSTALLING OR USING THIS APPLICATION.**
>
> 1. **Exclusive User Responsibility**: This software is provided strictly for educational, experimental purposes, and for safe passenger entertainment or driver use when the vehicle is stationary (in Park "P" / parked state).
> 2. **Road Safety**: Operating a motor vehicle demands full, undivided attention. Looking at video streams, reading websites, or interacting with touch screens while driving creates a severe threat of collision, injury, or death.
> 3. **Complete Assumption of Liability**: By compiling, installing, or executing **AuraCast**, **YOU EXPRESSLY AND FULLY ASSUME ALL CIVIL, ADMINISTRATIVE, FINANCIAL, AND CRIMINAL LIABILITY** for any and all outcomes, including but not limited to:
>    - Road traffic accidents (RTA / collisions);
>    - Physical injuries, disability, or loss of life;
>    - Traffic violation fines or revocation of driver's license;
>    - Damage to the vehicle or infotainment hardware;
>    - Voiding of vehicle warranties or insurance coverage.
> 4. **No Developer Liability**: Authors, developers, and contributors of this project bear ZERO liability under any legal theory for the conduct of any end-user, nor for the operation of the app while driving, nor for any direct or consequential damages.
> 5. **If you do not agree unconditionally with these terms, do not install or use this software.**

---

## 🌟 Key Features

- 🚀 **Uninterrupted In-Car Operation**: Keeps the display continuously awake (`FLAG_KEEP_SCREEN_ON`), preventing automatic screen lockouts, dimming, or audio stream interruptions while on the road.
- 🎨 **Minimalist Distraction-Free UI**: Completely removed search bars, promotional banners, and nested category chips. The screen solely presents clean website cards with official high-resolution logos, ending with a quick "+ Add Website" tile.
- 🏷️ **Automatic Official Logos (128px HD Favicons)**: Integrated real-time favicon resolution. Typing any URL displays an instant live preview with automatic domain parsing and title suggestion.
- 🥛 **Dual Adaptive Themes**:
  - **Daytime Milk Luxury Theme**: Warm, gentle porcelain tones designed to eliminate bright white glare inside the cabin.
  - **Nighttime OLED Theme**: True deep black background with minimal power consumption and glare protection at night.
- 🚗 **Android Auto Integration**: Declares automotive metadata and `MediaBrowserService`, enabling seamless recognition in Android Auto companion launcher settings.
- 🔘 **Steering Wheel Key Controls**: Intercepts physical steering wheel buttons (Next track/site, Previous track/site, Play/Pause, Back).
- ⚡ **Preloaded Top Services**: Fast access to *YouTube, Rutube, VK Video, Kinopoisk, Yandex Music, Drive2, Auto.ru, News, Online Radio*, and custom user-defined shortcuts.

---

## 📲 Step-by-Step Installation Guide

### Step 1. Compile APK with Gradle (Optional)
```bash
git clone https://github.com/sndabkh/AuraCast.git
cd AuraCast
gradle assembleDebug
```
The output APK is located at: `app/build/outputs/apk/debug/app-debug.apk`

### Step 2. Install APK onto Smartphone
Install via ADB or transfer the file to your Android smartphone:
```bash
adb install -r releases/AuraCast-v1.0.apk
```

### Step 3. Activate Display in Android Auto
Because this is a standalone sideloaded APK (installed outside Google Play to bypass driving lockouts), Android Auto hides unofficial applications by default. To enable it on your vehicle dashboard:
1. On your phone, go to **Settings** → search for **"Android Auto"**.
2. Scroll to the bottom and locate the **"Version"** row.
3. **Tap "Version" 10 times consecutively** until a message announces: *"Developer mode enabled"*.
4. Tap the **three-dot menu** in the top-right corner → choose **"Developer settings"**.
5. Find **"Unknown sources"** and **enable the checkbox**.
6. Return to main Android Auto settings and tap **"Customize launcher"**.
7. Find **AuraCast** in the app list and ensure its checkbox is marked.
8. Connect your phone to your vehicle via USB or wireless projection.
9. **AuraCast** will appear on your vehicle's touchscreen launcher!

---

## 🛠️ Technology Stack
- **Language**: Kotlin 2.2
- **UI Framework**: Jetpack Compose, Material Design 3
- **Architecture**: MVVM (Model-View-ViewModel), Coroutines, StateFlow
- **Image Pipeline**: Coil 2.7.0 (asynchronous favicon loading with monogram fallbacks)
- **Persistence**: Room Database (local storage for sites and history)
- **Automotive Components**: Android Auto `MediaBrowserService`, `SteeringWheelHandler`, `KeyEvent` interception

---

<br/>

---

# 🇷🇺 Русская документация

## ⚠️ ВАЖНОЕ ПРЕДУПРЕЖДЕНИЕ И ПОЛНЫЙ ОТКАЗ ОТ ОТВЕТСТВЕННОСТИ (DISCLAIMER OF LIABILITY)

> **ВНИМАНИЕ! ПРОЧИТАЙТЕ ПЕРЕД УСТАНОВКОЙ И ИСПОЛЬЗОВАНИЕМ ПРОГРАММЫ.**
>
> 1. **Исключительная ответственность пользователя**: Данное программное обеспечение создано исключительно в образовательных, демонстрационных целях, а также для использования пассажирами транспортного средства либо водителем во время полной остановки (в режиме парковки «P» / на стоянке).
> 2. **Безопасность дорожного движения**: Управление транспортным средством требует максимальной концентрации внимания. Просмотр видео, веб-страниц или взаимодействие с интерактивными элементами экрана во время вождения представляет прямую угрозу безопасности жизни и здоровья вас, ваших пассажиров и других участников дорожного движения.
> 3. **Переход ответственности**: Скачивая, компилируя, устанавливая или запуская приложение **AuraCast**, **ВЫ В ПОЛНОМ ОБЪЕМЕ БЕРЕТЕ НА СЕБЯ ВСЮ ЮРИДИЧЕСКУЮ, АДМИНИСТРАТИВНУЮ, МАТЕРИАЛЬНУЮ И УГОЛОВНУЮ ОТВЕТСТВЕННОСТЬ** за любые возможные последствия, включая, но не ограничиваясь:
>    - Дорожно-транспортные происшествия (ДТП);
>    - Травмы, ущерб здоровью или гибель людей;
>    - Административные штрафы и лишение права управления транспортными средствами за нарушение ПДД;
>    - Повреждение транспортного средства или бортовой электроники;
>    - Аннулирование гарантии автопроизводителя.
> 4. **Отказ разработчиков от претензий**: Разработчики, авторы и контрибьюторы репозитория ни при каких обстоятельствах не несут ответственности за действия конечного пользователя, за использование приложения во время движения автомобиля, а также за любой прямой или косвенный ущерб.
> 5. **Если вы не согласны с данными условиями, вы обязаны немедленно прекратить использование и удалить данное программное обеспечение.**

---

## 🌟 Основные возможности

- 🚀 **Беспрепятственная работа за рулем**: Приложение удерживает экран включенным (`FLAG_KEEP_SCREEN_ON`), предотвращая автоматическую блокировку дисплея, затемнение экрана и прерывание воспроизведения аудио на ходу.
- 🎨 **Минималистичный дизайн без визуального шума**: Полностью убраны громоздкие поисковые строки, баннеры и каталоги — на экране отображаются исключительно эстетичные карточки сайтов и плитка добавления в конце.
- 🏷️ **Автоматические логотипы (128px HD Favicon)**: Интегрирован сервис мгновенного получения официальных логотипов сайтов. При вводе URL в реальном времени работает живой предпросмотр логотипа с определением домена.
- 🥛 **Две адаптивные темы**: 
  - **Дневная молочная тема** — мягкие, приятные для глаз кремовые тона без слепящего белого света.
  - **Ночная OLED-тема** — глубокий черный фон с минимальным энергопотреблением и защитой зрения ночью.
- 🚗 **Интеграция с Android Auto**: Поддержка протокола `MediaBrowserService` и метаданных `automotiveApp`, благодаря чему приложение отображается в меню Android Auto.
- 🔘 **Управление с руля**: Поддержка перехвата мультимедийных клавиш руля (Переключение треков/сайтов, Пауза/Плей, Назад).
- ⚡ **Встроенные популярные сервисы**: Предустановлены ссылки на *VK Видео, Rutube, Кинопоиск, Яндекс Музыку, YouTube, Drive2, Auto.ru, Дзен, Онлайн Радио* и др.

---

## 📲 Пошаговая инструкция по установке

### Шаг 1. Сборка APK-файла (Опционально)
```bash
git clone https://github.com/sndabkh/AuraCast.git
cd AuraCast
gradle assembleDebug
```
Собранный файл будет находиться по пути: `app/build/outputs/apk/debug/app-debug.apk`

### Шаг 2. Установка APK на смартфон
Установите полученный APK-файл на ваш Android-смартфон через ADB или перенеся файл в память телефона:
```bash
adb install -r releases/AuraCast-v1.0.apk
```

### Шаг 3. Активация отображения в Android Auto
Так как приложение собрано вручную (сторонний APK вне Google Play для снятия ограничений на ходу), система Android Auto по умолчанию скрывает неофициальные приложения. Чтобы приложение появилось на дисплее автомобиля:
1. На смартфоне откройте **«Настройки»** → введите в поиске **«Android Auto»**.
2. Прокрутите экран настроек Android Auto в самый низ до пункта **«Версия»**.
3. **Нажмите на пункт «Версия» 10 раз подряд**, пока на экране не появится уведомление: *«Режим разработчика включен»*.
4. В правом верхнем углу нажмите **меню (три вертикальные точки)** → выберите **«Для разработчиков»**.
5. Найдите пункт **«Неизвестные источники» (Unknown Sources)** и **поставьте напротив него галочку**.
6. Вернитесь в основное меню настроек Android Auto и откройте **«Настройка панели запуска»**.
7. В списке приложений найдите **AuraCast** и убедитесь, что напротив него установлена галочка.
8. Подключите смартфон к автомобилю по USB-кабелю или по беспроводной связи (Wi-Fi / Bluetooth).
9. Иконка **AuraCast** появится в списке приложений на экране магнитолы!

---

## ⚖️ Лицензия

Проект распространяется под свободной лицензией **MIT License** с обязательным соблюдением условий отказа от ответственности. Подробнее см. в файле [LICENSE](LICENSE).
