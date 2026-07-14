# Документация проекта shopping-list-team-32-2

Справочник по архитектуре, структуре модулей и ключевым техническим решениям.

| Документ | Содержание |
|----------|------------|
| [`AUTH.md`](AUTH.md) | Auth API, сессия, Epic 2 |
| [`PRODUCT.md`](PRODUCT.md) | Экран списка, товары, Room, Epic 3, **Figma deep-links** |
| [`BRANCHING.md`](BRANCHING.md) | Правила веток и PR |

---

## 1. Репозиторий и структура проекта

| Параметр | Значение |
|----------|----------|
| Название проекта | `shopping_list` (`rootProject.name` в `settings.gradle.kts`) |
| Репозиторий | `Practicum-Labs/shopping-list-team-32-2` |
| **Figma (UI-kit)** | [Практикум ОП — Список покупок](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=0-1) |

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
├── docs/             # BRANCHING.md, PROJECT_AUDIT.md, AUTH.md, PRODUCT.md
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
| Compose BOM | `2025.08.01` (`gradle/libs.versions.toml`) |

**Namespace:** во всех модулях используется единая схема `com.practicum.list.*`.

---

## 3. Структура пакетов

```text
app/src/main/java/
└── com.practicum.list/
    ├── ui/
    │   ├── MainActivity.kt          — NavHost + SessionExpiredHandler
    │   ├── RootScreen.kt            — splash / проверка сессии (#45)
    │   └── RootScreenNavigation.kt
    ├── presentation/
    │   └── RootViewModel.kt
    ├── domain/
    │   └── RootUseCases.kt          — CheckToken, RefreshToken
    ├── appliction/                  — пакет (typo в коде): ShoppingListApplication
    └── di/
        └── AppModule.kt

core/design/src/main/java/
├── com.practicum.list.core.theme/
│   ├── ColorHex.kt, Color.kt, Theme.kt, Type.kt, Shape.kt, Dimens.kt
│   └── ListIcons.kt
└── com.practicum.list.core.components/
    ├── topbar/TopBar.kt
    ├── dialogs/ — CustomLayoutDialog, Create/Rename/DeleteListDialog, LogoutDialog
    ├── bottomsheet/CategoryPickerBottomSheet.kt
    ├── cards/ShoppingListItem.kt (SwipeableListItem)
    ├── fab/, placeholder/, …

core/design/src/main/res/ — drawable иконок + strings/arrays (цвета темы — только Kotlin)

core/mvi/        — MviState / Intent / Effect / MviViewModel
core/data/       — Room v6, Retrofit, SessionEvents, TokenAuthenticator, CryptoHelper
core/common/     — Product, MeasureUnit, ShoppingList, UserSession (interface),
                   ShoppingListRepository (interface), ObserveListTitleUseCase
core/navigation/ — Destinations + anim/DefaultAnimations.kt

feature/main/
├── presentation/  — MainViewModel (+ ProfileClicked / logout dialog state)
├── domain/usecase — Observe/Upsert/Delete/Duplicate + SignOutUseCase
├── data/impl      — ShoppingListRepositoryImpl
└── ui/screens/    — MainScreen, mainScreenNavigation (TopBar → profile)

feature/list/
├── presentation/  — ListViewModel (sheet, ListMenu, bulk delete)
├── domain/        — ListRepository + product UseCases
├── data/          — ListRepositoryImpl
└── ui/            — ListScreen, ProductBottomSheet, ListMenu, Delete*Dialog, …

feature/auth/
├── presentation/  — Login / Register / ResetPassword MVI
├── domain/        — AuthRepository, AuthValidation, AuthUseCases
├── data/          — AuthRepositoryImpl
└── ui/            — components + authScreenNavigation
```

Палитра/типографика — Compose-only; XML в `:core:design` — иконки и строки, не color attrs.

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
| **Ориентация** | `android:screenOrientation="portrait"` на `MainActivity` |

В `app` остаётся минимальный XML — системная тема окна до старта Compose. Цвета UI задаются в `ShoppingListTheme`, не в XML.

**Portrait only:** макеты Figma и UI-ТЗ рассчитаны на телефон в портрете. Landscape и tablet master-detail ([#79](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/79)) — отдельно, когда появится layout.

---

## 5. Стек технологий

| Область | Где подключено | Примечание |
|---------|----------------|------------|
| Jetpack Compose | app, :core:design, :core:navigation, :feature:* | BOM **2025.08.01** |
| Material 3 | :core:design | `ShoppingListTheme` + `MaterialTheme` |
| Activity Compose | app | `setContent` в MainActivity |
| Navigation Compose | :core:navigation | NavHost, type-safe routes, `DefaultAnimations` |
| Coroutines / StateFlow | :core:mvi | `MviViewModel` |
| Detekt | все модули + CI | `config/detekt/detekt.yml` |
| Room | :core:data | `ShoppingDatabase` **v6**, DAOs, миграции 1→6 |
| Hilt | :app, :core:data, :feature:* | `@InstallIn(SingletonComponent)`, `@HiltViewModel` |
| Retrofit / OkHttp | :core:data | `AuthApi` (Railway + fallback), `ProductApi` (заглушка) |
| DataStore + Keystore | :core:data | `UserSessionStore` + `CryptoHelper` |

**Тема:** цвета/типографика — Compose-only; drawable/strings иконок — в `res/` модуля design.

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

Токены сверены с Figma (`Android light` [1:7480](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-7480) / `Android dark` [1:8266](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8266)). Экранные макеты — [`PRODUCT.md`](PRODUCT.md#figma).

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
| minifyEnabled | **true** (`isShrinkResources = true`) |
| proguard-rules.pro | `app/proguard-rules.pro` (Room, Hilt, Moshi, MeasureUnit) |
| signing config | `local.properties` / env: `STORE_FILE`, `STORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD` |
| CI | `assembleDebug` only; release — ручная проверка `assembleRelease` |

---

## 10. Навигация

Type-safe маршруты в `:core:navigation` (`Destinations.kt`, `@Serializable`):

- `RootScreenRoute` — splash + проверка токена (#45), **startDestination**
- `MainScreenRoute` — главный экран (списки, logout)
- `ListScreenRoute(listId: Long)` — экран списка / товаров
- `LoginRoute`, `RegisterRoute`, `ResetPasswordRoute` — auth (#42), **реализованы**

Extensions: `rootScreenNavigation`, `mainScreenNavigation`, `listScreenNavigation`, `authScreenNavigation`.  
Общие transitions: `core/navigation/anim/DefaultAnimations.kt`.

Auth back stack: login → register/reset через `navigate()`; назад — `popBackStack()`.  
Сессия истекла / logout → `SessionEvents` → `MainActivity` чистит стек и открывает `LoginRoute`.  
Подробнее — [`AUTH.md`](AUTH.md). Экран списка — [`PRODUCT.md`](PRODUCT.md).

---

## 11. Data-слой и DI

| Компонент | Где | Статус |
|-----------|-----|--------|
| **Room** | `:core:data` | `ShoppingDatabase` **v6**, DAOs; `user_id` у списков; миграции **1→6** |
| **UserSession** | `:core:common` (iface) + `:core:data` | DataStore + `CryptoHelper`; legacy `user_id=0` при логине |
| **SessionEvents** | `:core:data` | `sessionExpired` Flow; ручной logout + failed refresh |
| **Hilt** | `:app`, `:core:data`, `:feature:*` | `DatabaseModule`, `NetworkModule`, feature `@Binds` |
| **Retrofit** | `:core:data` | `AuthApi` + `ResilientAuthApi` / fallback; `ProductApi` stub |
| **Auth** | `:feature:auth`, `:app` | экраны, validation, `RootViewModel`; [`AUTH.md`](AUTH.md) |
| **Lists** | `:feature:main` | CRUD, rename/delete dialogs, category picker, duplicate, `SignOutUseCase` |
| **Products** | `:feature:list` | CRUD, `ProductBottomSheet`, `ListMenu`, bulk delete; [`PRODUCT.md`](PRODUCT.md) |
| **UseCase** | `:feature:*/domain/usecase` + часть в `:core:common` | `*UseCase`, не `interactor` |

### Lifecycle сессии

1. **Ручной logout:** TopBar profile → `LogoutDialog` → `SignOutUseCase` → `clearSession()` + `SessionEvents.notifySessionExpired()`.
2. **Авто:** `TokenAuthenticator` — refresh fail → то же уведомление.
3. **UI:** `MainActivity.SessionExpiredHandler` собирает `sessionExpired` и `navigate(LoginRoute) { popUpTo(0) { inclusive = true } }`.

Domain-слой feature-модулей использует пакет **`domain/usecase`**, не `interactor`.
