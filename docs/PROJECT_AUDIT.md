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
│   ├── common/       # domain-модели, общий код
│   ├── data/         # Room, Retrofit, DI modules
│   ├── design/       # Compose-тема (Kotlin only)
│   ├── mvi/          # MVI-база
│   └── navigation/ # NavGraph (Compose Navigation)
├── docs/             # BRANCHING.md, PROJECT_AUDIT.md, AUTH.md
├── feature/
│   ├── main/         # главный экран
│   ├── list/      # экран списка / товаров
│   └── auth/         # auth UI-компоненты и экраны (Epic 2)
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
- :core:data
- :core:design
- :core:navigation
- :feature:main
- :feature:list
- :feature:auth

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

**Namespace:** во всех модулях используется единая схема `com.practicum.list.*`.

---

## 3. Структура пакетов

```text
app/src/main/java/
└── com.practicum.list/
    ├── MainActivity.kt
    ├── ShoppingListApplication.kt
    └── di/
        └── AppModule.kt

core/design/src/main/java/
└── com.practicum.list.core.theme/
    ├── ColorHex.kt    — HEX-константы light/dark (Figma)
    ├── Color.kt       — androidx.compose.ui.graphics.Color
    ├── Theme.kt       — ShoppingListTheme, light/dark ColorScheme
    ├── Type.kt        — Typography (M3)
    └── ListIcons.kt   — DEFAULT_LIST_ICON и др.

core/mvi/src/main/java/
└── com.practicum.list.core.mvi/
    ├── MviState.kt, MviIntent.kt, MviEffect.kt
    └── MviViewModel.kt

core/data/       — Room, Retrofit, DatabaseModule, NetworkModule
core/common/     — domain-модели (ShoppingList, Product, …)
core/navigation/ — type-safe маршруты (MainScreenRoute, ListScreenRoute; auth: LoginRoute, RegisterRoute, ResetPasswordRoute — #42)

feature/main/
├── presentation/  — MainState, MainIntent, MainEffect, MainViewModel
├── domain/
│   ├── repository/ — ShoppingListRepository
│   └── usecase/    — *UseCase (ObserveShoppingLists, Upsert, …)
├── data/
│   ├── impl/       — ShoppingListRepositoryImpl
│   └── di/         — ShoppingListRepositoryModule
└── ui/screens/    — MainScreen, mainScreenNavigation

feature/list/   — ListScreen, listScreenNavigation

feature/auth/
└── ui/components/ — AuthOutlinedTextField, PasswordTextField, PrimaryAuthButton, … (#43)
    ui/screens/    — authScreenNavigation (#42, в merge)
```

**`:core:design` не содержит `src/main/res/`** — палитра и типографика только в Kotlin (drawable иконок списков — в `:core:design`).

Раскладка feature-модулей: `presentation`, `domain/usecase`, `data`, `ui` — см. README.

---

## 4. AndroidManifest и Application

| Параметр | Значение |
|----------|----------|
| namespace | `com.practicum.list` |
| applicationId | `com.practicum.shopping_list` |
| MainActivity | `ComponentActivity` + `setContent` |
| Application | `ShoppingListApplication` + `@HiltAndroidApp` |
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
| Room | :core:data | ShoppingDatabase, DAOs, миграции |
| Hilt | :app, :core:data, :feature:main | `@InstallIn(SingletonComponent)`, `@HiltViewModel` |
| Retrofit / OkHttp | :core:data | `AuthApi` (Railway), `ProductApi` (заглушка) |

**Тема:** Compose-only по требованию заказчика — без XML attrs/colors в `:core:design`.

В ТЗ Практикума для баллов предусмотрены варианты `theme + attrs` (+2) или `values-night/colors` (+1). Команда сознательно выбрала Compose `MaterialTheme`; с наставником стоит согласовать, какой критерий оценки приоритетнее.

---

## 6. MVI

Базовый контракт в `:core:mvi`:

- `MviState` — неизменяемое состояние экрана
- `MviIntent` — действия пользователя / UI
- `MviEffect` — одноразовые события (навигация, snackbar)
- `MviViewModel` — `StateFlow` для state, `Channel` для effects; View вызывает `dispatch(intent)`

Экранные ViewModel наследуют `MviViewModel`: синхронные изменения state — в `reduce`, side-effects (UseCase, `emitEffect`) — в `handleIntent`.

Пример: `:feature:main` — `MainViewModel`, `MainState`, `MainIntent`, `MainEffect`.

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

Type-safe маршруты в `:core:navigation` (`@Serializable`):

- `MainScreenRoute` — главный экран
- `ListScreenRoute(id: Long)` — экран списка / товаров
- `LoginRoute`, `RegisterRoute`, `ResetPasswordRoute` — auth-флоу (#42)

Feature-модули регистрируют destination через extension (`mainScreenNavigation`, `listScreenNavigation`, `authScreenNavigation`). `:app` собирает `NavHost`.

Auth back stack: login → register/reset через `navigate()`; обратно на login — `popBackStack()`. Подробнее — [`AUTH.md`](AUTH.md).

Передача аргументов — через type-safe routes (`ListScreenRoute(id = …)`), не hardcoded path.

---

## 11. Data-слой и DI

| Компонент | Где | Статус |
|-----------|-----|--------|
| **Room** | `:core:data` | `ShoppingDatabase` v3, `ShoppingListDao`, `ProductDao`; `shopping_lists.user_id` |
| **UserSession** | `:core:data` | DataStore: `user_id`, токены; миграция legacy-списков при логине |
| **Hilt** | `:app`, `:core:data`, `:feature:*` | `DatabaseModule`, `NetworkModule`, `AppModule`, feature `@Binds`-модули |
| **Retrofit** | `:core:data` | `AuthApi` → Railway; `ProductApi` — заглушка |
| **Auth** | `:core:data`, `:feature:auth` | DTO, `AuthError`, UI-компоненты; см. [`AUTH.md`](AUTH.md) |
| **Repository** | `:feature:main/data` | `ShoppingListRepository` + impl, маппер в `:core:data` |
| **UseCase** | `:feature:main/domain/usecase` | Observe / Upsert / Delete / Duplicate |

Domain-слой feature-модулей использует пакет **`domain/usecase`**, не `interactor`.
