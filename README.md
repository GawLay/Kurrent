# Kurrent

> A modern currency conversion Android app built with Jetpack Compose and Clean Architecture principles.

##  About

**Kurrent** is a currency converter app that provides real-time exchange rates for multiple currencies. The app features an offline-first architecture, ensuring users can view cached currency rates even without an internet connection.

### Key Features

-  **Offline-First** - Access cached currency data without internet
-  **Material Design 3** - Modern UI with dynamic theming (Light/Dark mode)
-  **Currency Calculator** - Convert between currencies with live updates
-  **Quick Look Card** - View your last conversion at a glance
-  **Fast & Responsive** - Built with Jetpack Compose for smooth performance
-  **Auto-Refresh** - Currency rates update automatically (5-minute cache)

---

##  Architecture

This project follows **Clean Architecture** principles with a **feature-modular** structure:

```
Kurrent/
 app/                          # Application module (navigation, MainActivity)
 core/                         # Shared core modules
    data/                    # Data layer (API, Database, Repositories)
    domain/                  # Domain layer (UseCases, Models)
    ui/                      # Shared UI components & theming
    utility/                 # Utilities & extensions
 feature/                      # Feature modules
     calculator/              # Currency calculator feature
     currency-list/           # Currency list & rates feature
```

### Architecture Layers

####  Feature Modules
Each feature is isolated with its own UI, ViewModel, and models:
- **Calculator** - Currency conversion calculator
- **Currency List** - Display all available currencies with rates

####  Core Modules
Shared functionality across features:

**Core Data**
- Repository implementations
- API service (Retrofit)
- Local database (Room)
- Data sources (Remote & Local)

**Core Domain**
- Use cases (business logic)
- Domain models
- Repository interfaces

**Core UI**
- Shared composable components
- Theme system (Material 3)
- Custom dimensions & typography
- Reusable UI elements

**Core Utility**
- Extension functions
- Constants
- Utility classes

> **Note**: While this project is structured for feature-modular Clean Architecture, the domain and data layers are currently centralized in `core/` modules. In larger projects, each feature can have its own isolated `data` and `domain` modules for complete independence.

---

##  API Integration

### CurrencyFreaks API

This app uses **[CurrencyFreaks API](https://currencyfreaks.com/)** to fetch real-time exchange rates.

**Features:**
-  Supports 180+ currencies
-  Real-time exchange rates
-  Historical data support
-  Free tier available

**Base Currency:** USD (United States Dollar)

**API Endpoint:**
```
GET https://api.currencyfreaks.com/v2.0/rates/latest
```

**Setup:**
1. Sign up at [CurrencyFreaks](https://currencyfreaks.com/)
2. Get your API key
3. Add to `local.properties`:
   ```properties
   CURRENCY_API_KEY=your_api_key_here
   ```

---

##  Tech Stack

### Android & Kotlin
- **Kotlin** - 100% Kotlin codebase
- **Jetpack Compose** - Modern declarative UI
- **Material Design 3** - Latest Material theming
- **Compose Navigation** - Type-safe navigation

### Architecture Components
- **ViewModel** - UI state management
- **Coroutines & Flow** - Asynchronous programming
- **StateFlow** - Reactive state management
- **Hilt** - Dependency injection

### Data & Networking
- **Room Database** - Local data persistence
- **Retrofit** - REST API client
- **OkHttp** - HTTP client with logging
- **Gson** - JSON serialization

### Image Loading
- **Coil 3** - Async image loading for Compose

### Code Quality
- **Ktlint** - Kotlin linter with Compose rules
- **Compose Rules** - Jetpack Compose best practices
- **EditorConfig** - Consistent code style

### Testing
- **JUnit 4** - Unit testing framework
- **MockK** - Mocking library for Kotlin
- **Turbine** - Flow testing utilities
- **Coroutines Test** - Testing coroutines

---

##  Code Quality & Linting

This project enforces strict code quality standards using **ktlint** with **Compose Rules** from [mrmans0n/compose-rules](https://github.com/mrmans0n/compose-rules).

### Quick Commands
```bash
# Check code style
./gradlew ktlintCheck

# Auto-format code
./gradlew ktlintFormat

# Run tests
./gradlew test
```

### Compose Rules Applied
-  **Parameter Naming** - Present tense for lambda parameters (`onClick` not `onClicked`)
-  **Parameter Order** - Required params  Modifiers  Optional params  Trailing lambdas
-  **Composable Naming** - PascalCase for UI-emitting composables
-  **CompositionLocal Usage** - Allowlist for theme system locals

**Detailed Documentation:** See [Ktlint Rules Configuration](docs/KTLINT_RULES_GUIDE.md) for complete rule explanations and examples.

---

##  Getting Started

### Prerequisites
- JDK 17 or later
- Android SDK 34 (API level 34)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/kurrent.git
   cd kurrent
   ```

2. **Add API Key**
   The API key is **not stored in plain text**.  
   It is embedded in the **NDK layer** and retrieved via a **JNI call** using a **basic XOR obfuscation** strategy to avoid direct exposure in the Kotlin codebase. **Please set your API key accordingly in the native (C/C++) implementation.**

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the app**
    - Open in Android Studio
    - Select a device/emulator
    - Click Run

---

##  Module Structure

### App Module
- Application class with Hilt setup
- Navigation graph
- MainActivity

### Feature Modules

#### Calculator
- Currency conversion calculator
- Amount input with validation
- Real-time conversion display
- Saves conversion history

#### Currency List
- List of all available currencies
- Quick Look card (last conversion)
- Manual refresh functionality
- Theme toggle

### Core Modules

#### Data
- `CurrencyRepository` - Data operations
- `CurrencyDao` - Room database access
- `CurrencyApiService` - API endpoints
- Network & Database data sources

#### Domain
- `ObserveLocalCurrenciesUseCase` - Fetch currencies
- `ConversionCurrencyUseCase` - Handle conversions
- Domain models

#### UI
- `KurrentCardView` - Custom card component
- `QuickLookCard` - Conversion preview card
- `CurrencyItem` - Currency list item
- Theme system with Material 3

---

##  Design System

### Theme
- **Material Design 3** dynamic color scheme
- **Dark/Light mode** support with theme toggle
- **Custom dimensions** for consistent spacing
- **Typography** with custom text styles

### Custom Components
- `KurrentCardView` - Elevated card with gradient
- `KurrentFloatingActionButton` - Styled FAB
- `QuickLookCard` - Conversion summary card
- `SectionHeader` - Section divider with title

---

##  Testing Strategy

### Unit Tests
-  ViewModel logic testing
-  UseCase business logic
-  Repository operations
-  Mocked dependencies with MockK

### Example Test
```kotlin
@Test
fun `currency conversion calculates correctly`() = runTest {
    viewModel.onAmountChange("100")
    advanceUntilIdle()
    
    val state = viewModel.uiState.value
    assertEquals("117.65", state.convertedAmount)
}
```

##  Acknowledgments

- [CurrencyFreaks API](https://currencyfreaks.com/) - Currency exchange rate data
- [Compose Rules](https://github.com/mrmans0n/compose-rules) - Jetpack Compose linting rules
- [Material Design 3](https://m3.material.io/) - Design system
- [Android Jetpack](https://developer.android.com/jetpack) - Android libraries

**Made with Jetpack Compose**
