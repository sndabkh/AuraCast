# AuraCast — In-Car Browser & Media Streamer for Android & Android Auto

[![English](https://img.shields.io/badge/Language-English-blue.svg)](README_EN.md)
[![Russian](https://img.shields.io/badge/Language-Russian-lightgrey.svg)](README.md)
[![Android](https://img.shields.io/badge/Platform-Android%207.0%2B-green.svg)](https://developer.android.com)
[![Android Auto](https://img.shields.io/badge/Android%20Auto-Compatible-blue.svg)](https://www.android.com/auto/)
[![License](https://img.shields.io/badge/License-MIT-orange.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2-purple.svg)](https://kotlinlang.org/)

**AuraCast** is an elegant in-car web browser and media hub engineered to comfortably launch your favorite websites, video streams, and audio services on smartphone displays and within the **Android Auto** multimedia environment.

---

### 📥 [DOWNLOAD READY-TO-INSTALL APK (DIRECT LINK)](releases/AuraCast-v1.0.apk?raw=true)

> **[👉 Click here to download AuraCast-v1.0.apk directly](releases/AuraCast-v1.0.apk?raw=true)**  
> *The precompiled APK installation package is hosted right in this repository. In addition, automatic continuous builds are powered by GitHub Actions.*

---

## ⚠️ CRITICAL LEGAL NOTICE & DISCLAIMER OF LIABILITY

> **WARNING! PLEASE READ CAREFULLY PRIOR TO INSTALLATION AND USE.**
>
> 1. **Exclusive User Responsibility**: This software is provided exclusively for educational, demonstrational purposes, and for safe use by vehicle passengers or by the driver when the motor vehicle is completely stationary (in Park "P" / parked state).
> 2. **Road Traffic Safety**: Operating a motor vehicle demands undivided attention. Watching video streams, reading text, browsing websites, or interacting with touch screens while driving presents an immediate threat to the life and physical health of yourself, your passengers, and all other road users.
> 3. **Complete Assumption of Liability**: By downloading, compiling, installing, or executing **AuraCast**, **YOU EXPRESSLY AND FULLY ASSUME ALL CIVIL, ADMINISTRATIVE, FINANCIAL, AND CRIMINAL LIABILITY** for any and all consequences, including but not limited to:
>    - Motor vehicle collisions and traffic accidents (MVA / RTA);
>    - Personal injuries, bodily damage, disability, or loss of life;
>    - Traffic violation fines, legal penalties, or driver's license revocations;
>    - Damage to the vehicle, dashboard electronics, or head unit hardware;
>    - Voiding of vehicle manufacturer warranties or insurance policies.
> 4. **Waiver of Claims Against Developers**: The authors, contributors, and copyright holders of this repository shall not, under any legal theory (contract, tort, negligence, or otherwise), be held liable for the actions of any end-user, nor for the operation of this application during vehicle motion, nor for any direct, indirect, incidental, or consequential damages.
> 5. **If you do not agree unconditionally with these terms, you must immediately terminate use and uninstall this software.**

---

## 🌟 Key Features

- 🚀 **Uninterrupted In-Car Operation**: Keeps the display continuously awake (`FLAG_KEEP_SCREEN_ON`), preventing automatic screen lockouts, dimming, or audio stream interruptions while on the road.
- 🎨 **Distraction-Free Minimalist UI**: All clutter, search banners, and nested directories have been eliminated. The home screen solely displays tactile website cards with official high-resolution logos, ending with a quick "+ Add Website" tile.
- 🏷️ **Automatic Official Logos (128px HD Favicons)**: Integrated real-time favicon resolution. Typing any URL displays an instant live preview with automatic domain parsing and title suggestion.
- 🥛 **Dual Adaptive Themes**:
  - **Daytime Milk Luxury Theme**: Warm, gentle porcelain tones designed to eliminate bright white glare inside the cabin.
  - **Nighttime OLED Theme**: True deep black background with minimal power consumption and glare protection at night.
- 🚗 **Android Auto Integration**: Declares automotive metadata and `MediaBrowserService`, enabling seamless recognition in Android Auto companion launcher settings.
- 🔘 **Steering Wheel Key Controls**: Intercepts physical steering wheel buttons (Next track/site, Previous track/site, Play/Pause, Back).
- ⚡ **Preloaded Top Services**: Fast access to *YouTube, Rutube, VK Video, Kinopoisk, Yandex Music, Drive2, Auto.ru, News, Online Radio*, and custom user-defined shortcuts.

---

## 📲 Step-by-Step Installation Guide

### Step 1. Compile the APK (Optional if downloading directly)

Clone this repository and assemble the debug APK using Gradle:

```bash
git clone https://github.com/your-username/AuraCast.git
cd AuraCast
gradle assembleDebug
```

The compiled APK will be generated at:
`app/build/outputs/apk/debug/app-debug.apk`

---

### Step 2. Install APK on Smartphone

Install the APK onto your Android device via ADB or by transferring the file directly to your smartphone's storage:

```bash
adb install -r releases/AuraCast-v1.0.apk
```

---

### Step 3. Activate Display in Android Auto

Because this is a standalone sideloaded APK (installed outside Google Play to remove driving lockouts), Android Auto hides unofficial applications by default. To make AuraCast appear on your vehicle's head unit screen:

1. On your smartphone, open **Settings** → search for **"Android Auto"** (or find it under *Connected Devices*).
2. Scroll to the bottom of the Android Auto settings page to find the **"Version"** row.
3. **Tap the "Version" field 10 times consecutively** until a toast notification appears stating: *"Developer mode enabled"*.
4. In the top-right corner, tap the **overflow menu (three vertical dots)** → select **"Developer settings"**.
5. Locate the **"Unknown sources"** checkbox and **turn it ON (checked)**.
6. Return to the main Android Auto settings menu and open **"Customize launcher"**.
7. Scroll down to find **AuraCast** and ensure its checkbox is marked.
8. Connect your phone to your car using a USB cable or wireless projection (Wi-Fi / Bluetooth).
9. The **AuraCast** icon is now active and ready in your vehicle's dashboard launcher!

---

## 🛠️ Technology Stack

- **Language**: Kotlin 2.2
- **UI Framework**: Jetpack Compose, Material Design 3
- **Architecture**: MVVM (Model-View-ViewModel), Coroutines, StateFlow
- **Image Pipeline**: Coil 2.7.0 (asynchronous favicon loading with monogram fallbacks)
- **Persistence**: Room Database (storing custom site shortcuts and browsing history locally)
- **Automotive Components**: Android Auto `MediaBrowserService`, `SteeringWheelHandler`, `KeyEvent` interception

---

## ⚖️ License

Distributed under the **MIT License** with strict Driver Liability & Road Safety Waiver terms. See [LICENSE](LICENSE) for details.
