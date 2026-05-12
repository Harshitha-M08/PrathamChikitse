# 🩺 Pratham Chikitse — Emergency First Aid Android App

A bilingual (English + Kannada) AI-powered Android first-aid guide app for rural Karnataka.

## Features

- 🚨 **20 Emergency Cards** — step-by-step first-aid for snake bite, choking, cardiac arrest, burns, fractures, and more
- 🇮🇳 **Bilingual** — full English & Kannada support with live toggle
- 🤖 **Gemini AI Assistant** — ask any first-aid question in English or Kannada
- 🏥 **Hospital Finder** — 20 Karnataka hospitals with call & map buttons
- 🔍 **Search** — find emergencies by keyword in both languages
- 📦 **Room Database** — offline-first, pre-seeded from bundled JSON
- 🌙 **Dark Mode** support
- 💉 **DOs and DON'Ts** for every emergency

---

## Tech Stack

| Layer       | Technology |
|-------------|-----------|
| Language    | Kotlin |
| UI          | Jetpack Compose + Material 3 |
| Navigation  | Compose Navigation |
| Database    | Room (SQLite) |
| DI          | Hilt |
| AI          | Google Generative AI (Gemini 1.5 Flash) |
| Preferences | DataStore |
| Build       | Gradle with Version Catalog |

---

## Setup Instructions

### 1. Open in Android Studio
File → Open → Select the `PrathamChikitse` folder.

### 2. Add Your Gemini API Key

Get a **free** API key from https://aistudio.google.com

Open `local.properties` and replace the placeholder:
```
GEMINI_API_KEY=AIza...your-actual-key...
```

> ⚠️ The app works fully offline without the key — only the AI chat tab requires it.

### 3. Set SDK Path in local.properties
Android Studio auto-fills this. If not:
```
sdk.dir=/Users/your-name/Library/Android/sdk
```

### 4. Sync & Run
- Click **Sync Now** in Android Studio
- Run on an emulator (API 21+) or physical device

---

## App Structure

```
app/src/main/java/com/pratham/chikitse/
├── data/
│   ├── dao/          # Room DAOs (EmergencyDao, HospitalDao)
│   ├── database/     # AppDatabase
│   ├── model/        # Emergency, Hospital entities
│   └── repository/   # EmergencyRepository, HospitalRepository
├── di/               # Hilt AppModule
├── ui/
│   ├── ai/           # Gemini AI chat screen
│   ├── emergency/    # Detail screen for each emergency
│   ├── home/         # Home grid screen
│   ├── hospital/     # Hospital list with call/map
│   ├── search/       # Full-text search
│   ├── settings/     # Language & dark mode toggle
│   └── theme/        # Material 3 color scheme
└── util/
    ├── GeminiHelper.kt
    └── PreferencesHelper.kt
app/src/main/assets/
├── emergencies.json  # 20 emergencies (EN + KN)
└── hospitals.json    # 20 Karnataka hospitals
```

---

## Emergency Call Numbers

| Service     | Number |
|-------------|--------|
| Ambulance   | **108** |
| Police      | 100 |
| Fire        | 101 |
| Women Help  | 1091 |

---

## Note on AI
The AI assistant uses `gemini-1.5-flash` model. If `GEMINI_API_KEY` is not set, it shows a helpful error message. The rest of the app functions completely offline.
