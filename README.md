# 🩺 Pratham Chikitse — Emergency First Aid Android App

A bilingual (English + Kannada) AI-powered Android first-aid guide app designed for rural Karnataka.

The app provides instant emergency guidance, offline first-aid instructions, AI assistance, emergency helpline access, and nearby hospital support during critical situations.

---

## 🌟 Why Pratham Chikitse?

Pratham Chikitse is designed to help people in rural and low-connectivity areas access immediate first-aid guidance during emergencies.

The app focuses on:
- Offline accessibility
- Fast emergency response guidance
- Multilingual support
- AI-powered medical assistance
- Easy hospital access during emergencies

---

# ✨ Features

## 🚨 20 Emergency Cards
Step-by-step first-aid guidance for:
- Snake bite
- Choking
- Cardiac arrest
- Burns
- Fractures
- Asthma attack
- Allergic reactions
- Diabetic emergency
- And more

---

## 🇮🇳 Bilingual Support
- English
- Kannada
- Live language toggle

---

## 🤖 GROQ AI Assistant
Ask first-aid questions naturally in:
- English
- Kannada

Powered by:
- Llama 3.3 70B Versatile
- GROQ API

---

## 🏥 Hospital Finder
- Karnataka hospitals list
- One-tap call support
- Google Maps integration
- Nearby hospital access

---

## 🔍 Search Functionality
Search emergency topics instantly using keywords in:
- English
- Kannada

---

## 📦 Offline-First Architecture
- Uses Room Database
- Pre-seeded local JSON data
- Works without internet

---

## 🌙 Dark Mode Support

---

## 💉 DOs and DON'Ts
Every emergency includes:
- Safety precautions
- Important warnings
- Emergency response instructions

---

# 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Navigation | Compose Navigation |
| Database | Room (SQLite) |
| Dependency Injection | Hilt |
| AI | GROQ API (Llama 3.3 70B Versatile) |
| Preferences | DataStore |
| Build System | Gradle with Version Catalog |

---

# ⚙️ Setup Instructions

## 1️⃣ Open in Android Studio

```bash
File → Open → Select the PrathamChikitse folder
```

---

## 2️⃣ Add Your GROQ API Key

Get a free API key from:

https://console.groq.com

Open `local.properties` and add:

```properties
GROQ_API_KEY=gsk_your_actual_key_here
```

> ⚠️ The app works fully offline without the API key.  
> Only the AI Assistant requires internet access.

---

## 3️⃣ Set Android SDK Path

Android Studio usually auto-generates this automatically.

Example:

```properties
sdk.dir=/Users/your-name/Library/Android/sdk
```

---

## 4️⃣ Sync & Run

- Click **Sync Now**
- Run on:
  - Android Emulator (API 21+)
  - Physical Android Device

---

# 📂 Project Structure

```bash
app/src/main/java/com/pratham/chikitse/
├── data/
│   ├── dao/          # Room DAOs
│   ├── database/     # AppDatabase
│   ├── model/        # Emergency & Hospital entities
│   └── repository/   # Repositories
├── di/               # Hilt Dependency Injection
├── ui/
│   ├── ai/           # GROQ AI assistant screen
│   ├── emergency/    # Emergency detail screens
│   ├── home/         # Home dashboard
│   ├── hospital/     # Hospital finder
│   ├── search/       # Search functionality
│   ├── settings/     # Language & dark mode settings
│   └── theme/        # Material 3 theming
└── util/
    ├── GroqHelper.kt
    └── PreferencesHelper.kt

app/src/main/assets/
├── emergencies.json
└── hospitals.json
```

---

# 🚨 Emergency Helpline Numbers

| Service | Number |
|---|---|
| Ambulance | **108** |
| Police | 100 |
| Fire | 101 |
| Women Help | 1091 |

---

# 🤖 Note on AI

The AI assistant uses:
- Llama 3.3 70B Versatile
- GROQ API

If `GROQ_API_KEY` is missing:
- The AI Assistant shows a helpful message
- All other app features continue working completely offline

---

# 🎯 Project Goal

Pratham Chikitse aims to:
- Improve emergency awareness
- Provide accessible first-aid guidance
- Support rural healthcare accessibility
- Deliver multilingual emergency assistance
- Enable quick emergency response support

---

# 🚀 Future Improvements

- Voice-based emergency assistant
- SOS emergency contact alerts
- Offline Kannada voice narration
- Nearby pharmacy support
- Live ambulance tracking
- AI symptom analysis
- Emergency location sharing

---

# 👩‍💻 Developed With

- Kotlin
- Jetpack Compose
- Material 3
- Room Database
- Hilt
- GROQ AI
- Android Studio

Built to improve emergency awareness and accessibility in rural communities.

---

# 📱 App Screenshots

## 🏠 Home Screen
Emergency categories with quick access to first-aid guidance.

<p align="center">
  <img src="./screenshots/home_screen.png" width="280" alt="Home Screen"/>
</p>

---

## 🚑 Emergency Detail Screen
Step-by-step emergency instructions with DOs & DON'Ts.

<p align="center">
  <img src="./screenshots/emergency_detail.png" width="280" alt="Emergency Detail Screen"/>
</p>

---

## 🤖 AI First Aid Assistant
Ask first-aid questions in English or Kannada using GROQ AI.

<p align="center">
  <img src="./screenshots/ai_assistant.png" width="280" alt="AI Assistant"/>
</p>

---

## 🏥 Nearby Hospitals Screen
Find nearby Karnataka hospitals with quick call & map support.

<p align="center">
  <img src="./screenshots/hospital_list.png" width="280" alt="Hospital List"/>
</p>

---

## 🗺️ Google Maps Integration
Open hospitals directly in Google Maps for navigation.

<p align="center">
  <img src="./screenshots/maps_integration.png" width="280" alt="Maps Integration"/>
</p>

---

# ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub to support the project.
