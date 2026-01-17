# Ktlint Rules Configuration Guide

> Comprehensive guide to ktlint and Compose rules configuration for the Kurrent project

##  Table of Contents

- [Overview](#overview)
- [Disabled Standard Rules](#disabled-standard-rules)
- [Enabled Compose Rules](#enabled-compose-rules)
- [Custom Configuration](#custom-configuration)
- [Examples](#examples)
- [Resources](#resources)

---

##  Overview

This project uses **ktlint** (v1.4.1) with **Compose Rules** (v0.4.15) to enforce code quality and Jetpack Compose best practices.

**Configuration File:** `.editorconfig`

**Compose Rules:** [mrmans0n/compose-rules](https://github.com/mrmans0n/compose-rules)

### Why These Rules?

Jetpack Compose introduces new conventions that conflict with standard Kotlin rules:
- Composable functions use **PascalCase** (not camelCase)
- Compose encourages specific **parameter ordering**
- **CompositionLocals** are used for theming
- Lambda parameters use **present tense** naming

Our configuration balances strict code quality with Compose-friendly patterns.

---

##  Disabled Standard Rules

These standard ktlint rules are disabled because they conflict with Compose conventions:

### 1. `ktlint_standard_function-naming`

**Status:**  Disabled

**Reason:** Composable functions must use PascalCase (capitalized), which conflicts with standard Kotlin function naming (camelCase).

**Standard Kotlin:**
```kotlin
//  Standard Kotlin - camelCase
fun calculateTotal(): Int { }
```

**Jetpack Compose:**
```kotlin
//  Composable - PascalCase
@Composable
fun CalculatorScreen() { }

@Composable
fun CurrencyItem() { }
```

**Why Disabled:** If this rule is enabled, every Composable function would trigger a violation. Compose conventions require PascalCase for UI-emitting composables.

---

### 2. `ktlint_standard_filename`

**Status:**  Disabled

**Reason:** Compose files often contain multiple related composables or preview functions, making strict filename matching impractical.

**Example:**
```kotlin
// File: CalculatorScreen.kt
@Composable
fun CalculatorScreen() { }

@Composable
private fun AmountInputCard() { }

@Composable
private fun ConversionResultCard() { }

@Preview
@Composable
private fun CalculatorScreenPreview() { }
```

**Why Disabled:** Standard ktlint would require the file to be named after a single class. In Compose, it's common to have multiple composables in one file for organization.

---

### 3. `ktlint_standard_backing-property-naming`

**Status:**  Disabled

**Reason:** ViewModel pattern uses backing properties for state management, which is a standard Android practice.

**Example:**
```kotlin
class CalculatorViewModel : ViewModel() {
    // Private mutable state
    private val _uiState = MutableStateFlow(CalculatorUiState())
    
    // Public immutable state
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()
    
    // Internal flow (no public property needed)
    private val _amountFlow = MutableStateFlow("") //  Valid pattern
}
```

**Why Disabled:** Standard rule requires every `_property` to have a matching public `property`. In ViewModels, internal flows like `_amountFlow` don't always need public exposure.

---

### 4. `ktlint_standard_no-wildcard-imports`

**Status:**  Disabled

**Reason:** Compose commonly uses wildcard imports for cleaner, more readable code.

**Example:**
```kotlin
//  Compose wildcard imports
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
```

**Why Disabled:** Compose APIs have many small functions (e.g., `Row`, `Column`, `Text`, `Spacer`). Wildcard imports improve readability and are recommended by Google for Compose projects.

---

### 5. `ktlint_standard_multiline-expression-starts-on-new-line`

**Status:**  Disabled

**Reason:** Compose modifier chains are more readable when starting on the same line.

**With Rule (Less Readable):**
```kotlin
Text(
    text = "Hello",
    modifier = 
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
)
```

**Without Rule (More Readable):**
```kotlin
Text(
    text = "Hello",
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
)
```

**Why Disabled:** Compose code is more readable with modifiers starting inline with the parameter.

---

##  Enabled Compose Rules

These rules enforce Jetpack Compose best practices from [compose-rules](https://mrmans0n.github.io/compose-rules/rules/):

### 1. Parameter Naming (Present Tense)

**Rule:** `compose:parameter-naming`

**Enforcement:** Lambda parameters in composables must use **present tense**, not past tense.

**Examples:**

|  Wrong (Past Tense) |  Correct (Present Tense) |
|----------------------|---------------------------|
| `onClicked`          | `onClick`                  |
| `onTextChanged`      | `onTextChange`             |
| `onAmountChanged`    | `onAmountChange`           |
| `onValueUpdated`     | `onValueUpdate`            |
| `onItemSelected`     | `onItemSelect`             |

**Code Example:**
```kotlin
//  Wrong
@Composable
fun MyButton(
    onClicked: () -> Unit,
    onTextChanged: (String) -> Unit,
) { }

//  Correct
@Composable
fun MyButton(
    onClick: () -> Unit,
    onTextChange: (String) -> Unit,
) { }
```

**Why:** Present tense is clearer and more action-oriented. It indicates what will happen, not what happened.

---

### 2. Parameter Order

**Rule:** `compose_check_composable_parameter_order`

**Enforcement:** Parameters must follow this order:
1. Required parameters (no defaults)
2. Optional parameters with defaults
3. `modifier: Modifier = Modifier`
4. Trailing lambda (content)

**Example:**
```kotlin
//  Wrong order
@Composable
fun MyCard(
    modifier: Modifier = Modifier,  // Should be 3rd
    title: String,                   // Should be 1st
    subtitle: String = "",           // Should be 2nd
) { }

//  Correct order
@Composable
fun MyCard(
    title: String,                   // 1. Required
    subtitle: String = "",           // 2. Optional with default
    modifier: Modifier = Modifier,   // 3. Modifier
) { }

//  With trailing lambda
@Composable
fun MyCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit, // 4. Trailing lambda
) { }
```

**Why:** Consistent parameter ordering makes APIs predictable and easier to use. This matches Material 3 conventions.

---

### 3. Composable Naming

**Rule:** `compose_check_composable_naming`

**Enforcement:** UI-emitting composables use PascalCase. Helper functions use camelCase.

**Examples:**
```kotlin
//  PascalCase for UI composables
@Composable
fun CalculatorScreen() { }

@Composable
fun CurrencyListItem() { }

//  camelCase for helper/state functions
@Composable
fun rememberCalculatorState(): CalculatorState { }

@Composable
fun useThemeManager(): ThemeManager { }
```

**Allowed Patterns:**
```editorconfig
compose_allowed_composable_function_names = .*Theme,.*Presenter,use.*,remember.*
```

This allows:
- `KurrentTheme` - Theme functions
- `MyPresenter` - Presenter functions
- `useMyEffect` - React-style hooks
- `rememberMyState` - State remember functions

---

### 4. Preview Naming

**Rule:** `compose_check_preview_naming`

**Enforcement:** Preview functions should follow naming conventions.

**Configuration:**
```editorconfig
compose_preview_public_only_if_params = true
```

**Examples:**
```kotlin
//  Private preview (no parameters)
@Preview
@Composable
private fun CalculatorScreenPreview() { }

//  Public preview (with parameters)
@Preview
@Composable
fun CalculatorScreenPreview(
    @PreviewParameter(CurrencyProvider::class) currency: Currency
) { }
```

**Why:** Previews without parameters should be private to avoid cluttering the public API.

---

### 5. Composable Metrics

**Rule:** `compose_check_composable_metrics`

**Enforcement:** Warns about composables with too many parameters (>10) or too complex.

**Example:**
```kotlin
//  Warning - Too many parameters
@Composable
fun ComplexComponent(
    param1: String,
    param2: String,
    param3: String,
    // ... 10+ parameters
    param11: String,
) { }

//  Better - Use data class
data class ComponentState(
    val param1: String,
    val param2: String,
    // ... bundle related params
)

@Composable
fun ComplexComponent(
    state: ComponentState,
    modifier: Modifier = Modifier,
) { }
```

**Why:** Composables with many parameters are hard to test and maintain. Group related parameters into data classes.

---

##  Custom Configuration

### Content Emitters

**Configuration:**
```editorconfig
compose_content_emitters = KurrentCardView,QuickLookCard,KurrentFloatingActionButton
```

**Purpose:** Identifies custom composables that act as containers (like `Box`, `Column`, `Card`).

**Example:**
```kotlin
@Composable
fun KurrentCardView(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit, //  Recognized as content emitter
) {
    Card(modifier = modifier) {
        content()
    }
}
```

**Why:** Compose rules treat these composables like standard layout containers, allowing trailing lambda content.

---

### Allowed CompositionLocals

**Configuration:**
```editorconfig
compose_allowed_composition_locals = LocalDimensions
```

**Purpose:** Allowlist for creating new `CompositionLocal` instances.

**Example:**
```kotlin
//  Allowed CompositionLocal
val LocalDimensions = compositionLocalOf { Dimensions() }

@Composable
fun KurrentTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalDimensions provides Dimensions()) {
        content()
    }
}
```

**Why:** Creating `CompositionLocal` is generally discouraged because it introduces implicit dependencies. However, for theme systems (like Material's `LocalContentColor`), it's a valid pattern.

**Disallowed Example:**
```kotlin
//  Not allowed (not in allowlist)
val LocalMyCustomData = compositionLocalOf { MyData() }
```

**Learn More:** [Compose Rules - CompositionLocals](https://mrmans0n.github.io/compose-rules/rules/#compositionlocals)

---

##  Examples

### Complete Component Example

```kotlin
//  Follows all rules
@Composable
fun CurrencyCard(
    currencyCode: String,              // 1. Required param
    exchangeRate: String,              // 1. Required param
    currencyName: String = "",         // 2. Optional with default
    modifier: Modifier = Modifier,     // 3. Modifier last
    onClick: () -> Unit = {},          // 2. Optional callback (present tense)
) {
    KurrentCardView(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = currencyCode)
            Text(text = exchangeRate)
            if (currencyName.isNotEmpty()) {
                Text(text = currencyName)
            }
        }
    }
}

//  Preview follows naming convention
@Preview(showBackground = true)
@Composable
private fun CurrencyCardPreview() {
    KurrentTheme {
        CurrencyCard(
            currencyCode = "USD",
            exchangeRate = "1.00",
            currencyName = "US Dollar"
        )
    }
}
```

---

##  Running Ktlint

### Check for Violations
```bash
# All modules
./gradlew ktlintCheck

# Specific module
./gradlew :feature:calculator:ktlintCheck
```

### Auto-Fix
```bash
# Auto-fix all violations
./gradlew ktlintFormat

# Auto-fix specific module
./gradlew :app:ktlintFormat
```
##  Resources

### Official Documentation
- [Ktlint](https://pinterest.github.io/ktlint/) - Kotlin linter
- [Compose Rules GitHub](https://github.com/mrmans0n/compose-rules) - Compose-specific rules
- [Compose Rules Docs](https://mrmans0n.github.io/compose-rules/rules/) - Rule documentation

### Compose Guidelines
- [Compose API Guidelines](https://github.com/androidx/androidx/blob/androidx-main/compose/docs/compose-api-guidelines.md)
- [Compose Layout Basics](https://developer.android.com/jetpack/compose/layouts/basics)
- [Compose State Management](https://developer.android.com/jetpack/compose/state)

### Related Documentation
- [KTLINT_QUICK_REFERENCE.md](KTLINT_QUICK_REFERENCE.md) - Quick reference
---

##  Summary

### Rules to Remember

**Do:**
- Use PascalCase for UI-emitting composables
- Use present tense for lambda parameters (`onClick`, not `onClicked`)
- Order parameters: Required  Optional  Modifier  Trailing lambda
- Keep composables focused (< 10 parameters)
- Use `private` for previews without parameters

**Don't:**
- Use camelCase for main composables
- Use past tense for callbacks
- Put modifier first in parameter list
- Create new `CompositionLocal` without adding to allowlist
- Make preview functions public unless they have parameters

---
