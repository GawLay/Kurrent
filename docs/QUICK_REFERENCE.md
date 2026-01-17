# Kurrent - Quick Reference Card

> Quick access to important information

##  Quick Start

```bash
# Clone
git clone <your-repo-url>

# Add API key to somewhere secure
CURRENCY_API_KEY=your_key_here

# Build
./gradlew build

# Run tests
./gradlew test

# Check code style
./gradlew ktlintCheck

# Format code
./gradlew ktlintFormat
```

---

##  Project Structure

```
kurrent/
 app/              # Main app module
 core/
    data/        # Repositories, API, DB
    domain/      # UseCases, Models
    ui/          # Shared components
    utility/     # Utils & extensions
 feature/
     calculator/  # Conversion calculator
     currency-list/ # Currency list & rates
```

---

##  Tech Stack Quick List

**UI:** Jetpack Compose, Material 3  
**DI:** Hilt  
**Database:** Room  
**Network:** Retrofit, OkHttp  
**Images:** Coil 3  
**Async:** Coroutines, Flow  
**Testing:** JUnit, MockK, Turbine  
**Quality:** Ktlint + Compose Rules

---

##  Compose Rules Cheat Sheet

### Parameter Naming
`onClick`  `onClicked`  
`onTextChange`  `onTextChanged`  
`onAmountChange`  `onAmountChanged`

### Parameter Order
1. Required params
2. Optional params with defaults
3. `modifier: Modifier = Modifier`
4. Trailing lambda

### Function Naming
`@Composable fun MyScreen()` - PascalCase for UI  
`@Composable fun rememberState()` - camelCase for helpers

---

##  API Information

**Provider:** CurrencyFreaks  
**Base URL:** `https://api.currencyfreaks.com/v2.0/`  
**Endpoint:** `/rates/latest`  
**Base Currency:** USD  
**Update Frequency:** 5 minutes (auto-refresh)

---

##  Documentation Links

- [README.md](../README.md) - Full project documentation
- [KTLINT_RULES_GUIDE.md](KTLINT_RULES_GUIDE.md) - Detailed rules
- [QUICK_REFERENCE.md](KTLINT_QUICK_REFERENCE.md) - Rules cheat sheet
---

##  Common Issues

### Build Fails

### Ktlint Errors
- Run `./gradlew ktlintFormat` to auto-fix
- Check [KTLINT_RULES_GUIDE.md](KTLINT_RULES_GUIDE.md)

### Test Failures
- Check if ViewModels use test dispatcher
- Verify MockK setup is correct

---
