# Документация проекта shopping-list-team-32-2

Справочник по архитектуре, структуре модулей и ключевым техническим решениям.

---

## 1. Репозиторий и структура проекта

| Параметр | Значение |
|----------|----------|
| Название проекта | `shopping_list` (`rootProject.name` в `settings.gradle.kts`) |
| Репозиторий | `Practicum-Labs/shopping-list-team-32-2` |

```
shopping-list-team-32-2/
├── .github/          # CI, rulesets, PR template
├── app/              # точка входа, MainActivity
├── config/detekt/    # detekt.yml
├── core/
│   ├── common/       # общие модели, утилиты
│   ├── design/       # Compose-тема (Kotlin only)
│   ├── mvi/          # MVI-база
│   └── navigation/ # NavGraph (Compose Navigation)
├── docs/             # BRANCHING.md, PROJECT_AUDIT.md
├── feature/
│   ├── main/         # главный экран
│   └── product/      # экран списка / товаров
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew / gradlew.bat
```

---

## 2. Gradle и модули

```text
Gradle modules:
- :app
- :core:common
- :core:mvi
- :core:design
- :core:navigation
- :feature:main
- :feature:product

Build system:
- Kotlin DSL (все *.gradle.kts)
- Version Catalog: gradle/libs.versions.toml
- Gradle Wrapper: yes
- Configuration Cache: enabled (gradle.properties)
```

| Параметр | Значение |
|----------|----------|
| Android Gradle Plugin | 8.7.3 |
| Kotlin | 2.1.0 |
| Compose Compiler | plugin `kotlin-compose` 2.1.0 |
| compileSdk | 35 |
| minSdk | 24 |
| targetSdk | 35 |
| JVM target | 21 |
| Compose | включён в app, core:design, core:navigation, feature:* |

**Namespace:** в Kotlin-коде используется `ru.practicum.list.*`, в части `build.gradle.kts` — `ru.practicum.shopping_list.*`. Сборка проходит, но именование стоит унифицировать.

---

## 3. Структура пакетов

```text
app/src/main/java/
└── com.practicum.list/
    └── MainActivity.kt

core/design/src/main/java/
└── ru.practicum.list.core.theme/
    ├── ColorHex.kt    — HEX-константы light/dark (Figma)
    ├── Color.kt       — androidx.compose.ui.graphics.Color
    ├── Theme.kt       — ShoppingListTheme, light/dark ColorScheme
    └── Type.kt        — Typography (M3)

core/mvi/src/main/java/
└── ru.practicum.list.core.mvi/
    ├── MviState.kt, MviIntent.kt, MviEffect.kt
    └── MviViewModel.kt

core/common/     — domain-модели, общий код
core/navigation/ — NavGraph, маршруты
feature/main/    — MainScreen, OnboardingScreen
feature/product/ — ListScreen
```

**`:core:design` не содержит `src/main/res/`** — палитра и типографика только в Kotlin.

Целевая раскладка feature-модулей: `presentation` (UI + ViewModel), `domain`, `data`, `di` — по мере роста проекта.

---

## 4. AndroidManifest и Application

| Параметр | Значение |
|----------|----------|
| namespace | `com.practicum.list` |
| applicationId | `com.practicum.shopping_list` |
| MainActivity | `ComponentActivity` + `setContent` |
| Theme (manifest) | `@style/Theme.ShoppingList` → `Theme.Material3.DayNight.NoActionBar` |

В `app` остаётся минимальный XML — системная тема окна до старта Compose. Цвета UI задаются в `ShoppingListTheme`, не в XML.

---

## 5. Стек технологий

| Область | Где подключено | Примечание |
|---------|----------------|------------|
| Jetpack Compose | app, :core:design, :core:navigation, :feature:* | BOM 2024.11.00 |
| Material 3 | :core:design | `ShoppingListTheme` + `MaterialTheme` |
| Activity Compose | app | `setContent` в MainActivity |
| Navigation Compose | :core:navigation | NavHost, composable-маршруты |
| Coroutines / StateFlow | :core:mvi | `MviViewModel` |
| Detekt | все модули + CI | `config/detekt/detekt.yml` |
| Room | — | планируется в :core или :data |
| Dagger | — | планируется, KSP |
| Retrofit / OkHttp | — | планируется для API товаров |

**Тема:** Compose-only по требованию заказчика — без XML attrs/colors в `:core:design`.

В ТЗ Практикума для баллов предусмотрены варианты `theme + attrs` (+2) или `values-night/colors` (+1). Команда сознательно выбрала Compose `MaterialTheme`; с наставником стоит согласовать, какой критерий оценки приоритетнее.

---

## 6. MVI

Базовый контракт в `:core:mvi`:

- `MviState` — неизменяемое состояние экрана
- `MviIntent` — действия пользователя / UI
- `MviEffect` — одноразовые события (навигация, snackbar)
- `MviViewModel` — `StateFlow` для state, `Channel` для effects

Экранные ViewModel наследуют `MviViewModel` и обрабатывают intent'ы в `handleIntent`.

---

## 7. Тема (Compose-only)

Вся палитра в Kotlin, без XML в `:core:design`.

### Архитектура темы

```
ColorHex.kt (HEX из Figma)
      ↓
Color.kt (Compose Color)
      ↓
Theme.kt (lightColorScheme / darkColorScheme)
      ↓
ShoppingListTheme { MaterialTheme(...) }
      ↓
UI: MaterialTheme.colorScheme.surface, .secondary, ...
```

### Файлы

| Файл | Роль |
|------|------|
| `ColorHex.kt` | Константы `0xAARRGGBB` для light и dark |
| `Color.kt` | Обёртки `Color(...)` |
| `Theme.kt` | `ShoppingListTheme`, переключение day/night через `isSystemInDarkTheme()` |
| `Type.kt` | `Typography` (titleLarge, bodyLarge, …) |

### Минимальный XML (только app)

`app/src/main/res/values/themes.xml`:

```xml
<style name="Theme.ShoppingList" parent="Theme.Material3.DayNight.NoActionBar" />
```

Нужен для `AndroidManifest` и системного окна. Не содержит цветов приложения.

### Использование в UI

```kotlin
ShoppingListTheme {
    Box(Modifier.background(MaterialTheme.colorScheme.surface))
    Text("...", color = MaterialTheme.colorScheme.onSurface)
}
```

Переключение light/dark — автоматически по системной теме устройства.

---

## 8. Detekt

| Элемент | Описание |
|---------|----------|
| Plugin | подключён во всех модулях |
| Конфиг | `config/detekt/detekt.yml` |
| Правила ТЗ | LongMethod ≤ 50 строк, LongParameterList ≤ 6, LargeClass ≤ 350 |
| CI | `.github/workflows/pr_checks.yml` |

В `detekt.yml` задано `maxIssues: 100500` — при стабилизации проекта порог можно ужесточить.

---

## 9. Release build

| Параметр | Значение |
|----------|----------|
| release buildType | объявлен |
| minifyEnabled | false |
| proguard-rules.pro | указан в Gradle, файл может отсутствовать |
| signing config | не настроен |

---

## 10. Навигация

`:core:navigation` содержит `NavGraph` на Compose Navigation:

- маршруты объявляются в `NavHost`
- feature-экраны подключаются как composable destination
- передача аргументов — через `navArgument` (рекомендуется `listId: Long`, не JSON в path)

Целевая схема зависимостей: `:app` связывает feature-модули с navigation; `:core:navigation` не должен тянуть feature напрямую.

---

## 11. Планируемые слои (Room, Dagger, Retrofit)

Кратко о задуманной интеграции — без привязки к доске задач:

- **Room** — entities, DAOs, `ShoppingDatabase`; кэш списков и товаров
- **Dagger** — `Application`, `AppComponent`, модули по слоям (network, database, repository)
- **Retrofit** — API популярных товаров → кэш в Room → подсказки offline
