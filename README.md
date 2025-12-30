# AgroSmart: Your Smart Farming Companion

AgroSmart is a comprehensive Android application designed to empower farmers and agriculture enthusiasts. It provides a suite of modern tools and vital information to help manage farming activities, gain knowledge, and connect with the agricultural market.

## ✨ Key Features

*   **👤 User Authentication:** Secure and easy sign-up and login functionality.
*   **🌦️ Real-time Weather:** Get current weather updates (temperature, humidity, wind speed) based on your device's location, powered by the OpenWeatherMap API.
*   **📚 Rich Knowledge Base:** An extensive library of articles covering essential farming topics:
    *   **Plants:** Detailed information on various crops and plants.
    *   **Diseases:** Guides to identify common crop diseases and find effective solutions.
    *   **Methods:** Best practices and modern farming methods to improve yield.
*   **🛒 E-commerce Marketplace:** An in-app platform to buy and sell agricultural products with secure payment processing.
*   **🤖 AI Chat Assistant:** An integrated AI-powered chatbot (accessible via a Floating Action Button) to get instant answers to farming-related questions.
*   **📜 Government Schemes:** A dedicated section to browse and learn about relevant government agricultural schemes and subsidies.
*   **🏠 Modern Dashboard:** A central, intuitive dashboard to access all the app's features seamlessly.

## 🛠️ Tech Stack & Architecture

This project is built with modern Android development practices and a robust, scalable architecture.

*   **Language:** **Kotlin**
*   **Architecture:** **MVVM (Model-View-ViewModel)** to ensure a clean separation of concerns between the UI, business logic, and data layers.
*   **UI:**
    *   **XML Layouts** with **ViewBinding** for type-safe access to views.
    *   **Fragments & Navigation Component** for a streamlined single-activity architecture.
    *   **RecyclerView** for displaying dynamic lists of articles, products, and more.
    *   **Material Components for Android** for a modern and consistent user interface.
*   **Asynchronous Programming:** **Kotlin Coroutines** and **LiveData** for managing background tasks and observing UI state changes efficiently.
*   **Networking:** **Retrofit** and **Gson** for making RESTful API calls to external services.
*   **Dependency Injection:** Implicitly handled by Android Jetpack components like `viewModels`.
*   **Image Loading:** **Glide** for high-performance loading, caching, and display of images.
*   **Payment Gateway:** **Razorpay** for secure and reliable in-app payment processing.
*   **Location Services:** **Google Play Services** for fetching the user's location for weather data.


    
