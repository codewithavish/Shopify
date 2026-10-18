# 🛍️ Shopify — Android E-Commerce App

Shopify is an Android e-commerce application built with **Kotlin, Jetpack Compose, and Clean Architecture**. It provides a shopping experience with user authentication, product browsing, category navigation, search, filtering, cart management, and profile management.

**GitHub Repository:** [Shopify Android App](https://github.com/codewithavish/Shopify)


## ✨ Features

- **User Authentication:** User registration and login.
- **Home Screen:** Explore products and categories.
- **Bottom Navigation:** Navigate between Home, Categories, Cart, and Profile.
- **Category List:** Browse available product categories.
- **Product Listing:** Explore available products.
- **Product Details:** View detailed product information.
- **Search:** Search for products and categories.
- **Sorting and Filtering:** Organize products using available options.
- **Shopping Cart:** Manage cart items with local storage.
- **Profile:** View user profile information.
- **Edit Profile:** Update profile details.

---

## 🛠️ Tech Stack

- **Kotlin** — Programming language.
- **Jetpack Compose** — Modern declarative UI toolkit.
- **Clean Architecture** — Separation of application layers.
- **Hilt** — Dependency injection.
- **Retrofit** — REST API communication.
- **OkHttp** — HTTP client.
- **Kotlin Coroutines** — Asynchronous programming.
- **Flow / StateFlow** — Reactive data streams and UI state management.
- **Room Database** — Local persistence for shopping cart items.
- **Coil 3** — Image loading.
- **Navigation Compose** — Navigation between screens.

---

## 🏗️ Architecture

The application follows **Clean Architecture** principles to separate presentation, business logic, and data operations.

- **Presentation Layer:** Jetpack Compose screens, ViewModels, and UI state management.
- **Domain Layer:** Business logic, and repository interfaces.
- **Data Layer:** API services, data models, repository implementations, and local database operations.
- **Dependency Injection:** Hilt for managing dependencies.

This structure helps keep the code organized, maintainable, and easier to test.

---

## 🌐 API Integration

The application uses the EscuelaJS REST API for remote product and category data.

- **Base URL:** `https://api.escuelajs.co/api/v1/`

The API provides remote data, while Room Database is used for local shopping cart storage.

---

## 💾 Data Management

### Remote Data

Retrofit and OkHttp are used to communicate with the REST API.

### Local Cart Storage

Room Database stores shopping cart items locally, allowing them to remain available after the app is closed and reopened on the same device.

### Reactive UI Updates

Kotlin Flow and StateFlow help observe data changes and update the UI.

### Authentication

Registration and login use the available API functionality. Session persistence depends on the app's authentication and local session-management implementation.

---

## 🚀 Getting Started

### Prerequisites

- Android Studio
- Compatible JDK and Android SDK
- Internet connection

### Installation

**1. Clone the repository**

```bash
git clone https://github.com/codewithavish/Shopify.git
```

**2. Open the project**

Open the cloned project in Android Studio.

**3. Sync Gradle**

Allow Android Studio to download dependencies and complete the Gradle sync.

**4. Run the application**

Start an Android emulator or connect an Android device, then click **Run** in Android Studio.

---

## 📥 Download

**Source Code:** [Shopify on GitHub](https://github.com/codewithavish/Shopify)

An installable APK can be published in the repository's **Releases** section when available.

---

## 🎯 Key Highlights

- Modern Android UI built with Jetpack Compose.
- Clean Architecture for separation of concerns.
- REST API integration using Retrofit and OkHttp.
- Local cart persistence using Room Database.
- Reactive UI state management using Flow and StateFlow.
- Dependency injection using Hilt.
- Multi-screen navigation using Navigation Compose.

---

## 🔮 Future Improvements

Potential future enhancements include:

- Edit Profile functionality.
- Checkout and order management.
- Automated unit and UI testing.
- More UI Improvement

---

## 👨‍💻 Author

**Aviral**

- **GitHub Profile:** [@codewithavish](https://github.com/codewithavish)
- **Project Repository:** [Shopify](https://github.com/codewithavish/Shopify)


## 📸 Screenshots

Add screenshots of your actual application to showcase its interface.

<img width="1040" height="2120" alt="Screenshot_20261019_040153" src="https://github.com/user-attachments/assets/7d6cf358-4893-45d1-bf24-6aed22829d6e" />

