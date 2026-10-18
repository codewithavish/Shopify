🛍️ Shopify — Android E-Commerce App

A modern Android e-commerce application built with Kotlin, Jetpack Compose, and Clean Architecture. The app provides a smooth shopping experience with authentication, product browsing, category navigation, search, filtering, cart access, and profile management.

GitHub Repository: https://github.com/codewithavish/Shopify

✨ Features

User Authentication: Register and log in.

Home Screen: Explore featured products, categories, and special offers.

Bottom Navigation: Navigate between Home, Categories, Cart, and Profile.

Category List: Browse available product categories.

Product Listing: Explore available products.

Product Details: View detailed product information.

Search: Search for products.

Sorting and Filtering: Organize products using available options.

Shopping Cart: Access the cart from the bottom navigation.

Profile: View user profile information.

Edit Profile: Update profile details.

🛠️ Tech Stack

Technology

Purpose

Kotlin

Programming language

Jetpack Compose

Modern declarative UI

Clean Architecture

Separation of application layers

Hilt

Dependency injection

Retrofit

REST API communication

OkHttp

HTTP client

Kotlin Coroutines

Asynchronous programming

Flow / StateFlow

Reactive data and UI state

Coil 3

Image loading

Navigation Compose

Screen navigation

🏗️ Architecture

The project follows Clean Architecture principles to separate presentation, business logic, and data operations.

Presentation Layer: Compose screens, ViewModels, and UI state management.

Domain Layer: Business logic, use cases, and repository interfaces.

Data Layer: API services, data models, and repository implementations.

Dependency Injection: Hilt modules for managing dependencies.

This architecture helps keep the code organized, maintainable, and easier to test.

🌐 API Integration

The application uses the EscuelaJS REST API for product and category data.

Base URL: https://api.escuelajs.co/api/v1/
