# Аудит проекта shopping-list-team-32-2

> Дата аудита: 23.06.2026 (обновлено после перехода на Compose-only тему)  
> Ветка: `2-theme-files`  
> Базовый commit: `bc79ff24c214eace221dbea094ef0491409dbfab`  
> Статус: **есть незакоммиченные изменения** (рефакторинг темы Compose без XML)  
> Задача: подготовка к «Добавление базовых зависимостей: Compose, Dagger, Retrofit, Room, Detekt»

---

## 1. Краткое резюме

Проект — **многомодульный Android-скелет** с рабочей сборкой. Реализованы **Detekt**, **Compose**, **MVI-инфраструктура** и **тема полностью в Compose** (по требованию заказчика — без XML attrs/colors/themes в `:core:design`).

**Не подключены и не реализованы:** Dagger, Room, Retrofit, Navigation Compose, экраны, БД, сеть, DI, кастомный `Application`.

**Готовность к задаче «Добавление базовых зависимостей»:** **~50%**

| Компонент задачи | Статус |
|------------------|--------|
| Compose (включая тему) | готово (~90%) |
| Detekt | почти готово (~90%) |
| Dagger | 0% |
| Room | 0% |
| Retrofit | 0% |

**Важно про ТЗ vs заказчик:**

| Источник | Требование к теме |
|----------|-------------------|
| ТЗ Практикума | `theme + attrs` (+2) или `values-night/colors` (+1) |
| Заказчик / команда | **Compose-only**, без XML-палитры |
| Текущая реализация | **Compose `MaterialTheme`** — соответствует заказчику, **не** формату `theme + attrs` из ТЗ |

С наставником стоит согласовать, какой критерий оценки приоритетнее.

---

## 2. Git / репозиторий

| Параметр | Значение |
|----------|----------|
| Название проекта | `shopping_list` (`rootProject.name` в `settings.gradle.kts`) |
| Репозиторий | `Practicum-Labs/shopping-list-team-32-2` |
| Текущая ветка | `2-theme-files` (tracking `origin/2-theme-files`) |
| Последний commit | `bc79ff2` — theme + attrs (устарел, переписан локально) |
| Рабочая директория | **modified** — рефакторинг на Compose-only тему не закоммичен |
| Remote | `origin` → `git@github.com:Practicum-Labs/shopping-list-team-32-2.git` |

**Локальные ветки:**

- `10-branch-rules`
- `2-theme-files` ← текущая
- `develop`

**Remote-ветки:**

- `origin/main`
- `origin/develop`
- `origin/10-branch-rules`
- `origin/2-theme-files`

**Незакоммиченные изменения (Compose-only тема):**

```
modified:   app/.../MainActivity.kt
modified:   app/.../themes.xml
modified:   core/design/build.gradle.kts
modified:   core/design/.../Theme.kt
deleted:    ColorFromAttr.kt, ColorSchemeFromAttrs.kt, ThemedContext.kt
deleted:    core/design/res/values/*, values-night/*
added:      Color.kt, ColorHex.kt
```

**Структура корня:**

```
shopping-list-team-32-2/
├── .github/          # CI, rulesets, PR template
├── app/
├── config/detekt/
├── core/
│   ├── common/       # только build.gradle.kts, без src/
│   ├── design/       # Compose theme (Kotlin only, без res/)
│   ├── mvi/          # MVI base
│   └── navigation/   # только build.gradle.kts, без src/
├── docs/             # BRANCHING.md, PROJECT_AUDIT.md
├── feature/
│   ├── main/         # только build.gradle.kts, без src/
│   └── product/      # только build.gradle.kts, без src/
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew / gradlew.bat
```

**Нет:** `README.md`, `proguard-rules.pro` (файл отсутствует, хотя указан в Gradle).

---

## 3. Gradle / модули

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
- Version Catalog: yes (gradle/libs.versions.toml)
- Gradle Wrapper: yes
- Configuration Cache: enabled (gradle.properties)
```

| Параметр | Значение |
|----------|----------|
| Android Gradle Plugin | **8.7.3** |
| Kotlin | **2.1.0** |
| Compose Compiler | plugin `kotlin-compose` 2.1.0 |
| compileSdk | **35** |
| minSdk | **24** |
| targetSdk | **35** |
| JVM target | **21** |
| Compose enabled | **yes** |

**Замечание по namespace:** в Kotlin-коде — `ru.practicum.list.*`, в части `build.gradle.kts` — `ru.practicum.shopping_list.*`. Расхождение есть, сборка проходит.

---

## 4. Текущий стек

| Область | Ожидается по стеку | Сейчас есть? | Где найдено | Комментарий |
|---------|-------------------|--------------|-------------|-------------|
| **Jetpack Compose** | да | ✅ | app, :core:design, :feature:* | BOM 2024.11.00 |
| **Material 3** | да | ✅ | Compose Material3 | `ShoppingListTheme` + `MaterialTheme` |
| **Activity Compose** | да | ✅ | `MainActivity` | `setContent` |
| **Lifecycle Compose** | да | ⚠️ частично | :core:mvi, :feature:* | Не в :app |
| **Navigation Compose** | да | ❌ | — | Модуль пустой |
| **Compose Tooling** | желательно | ⚠️ | ui-tooling-preview в модулях | `@Preview` нет |
| **Coroutines / StateFlow** | да | ✅ | `MviViewModel.kt` | |
| **Room** | да | ❌ | — | |
| **KSP / KAPT** | да | ❌ | — | |
| **Dagger** | да | ❌ | — | |
| **Retrofit / OkHttp** | да | ❌ | — | |
| **Compose Theme (Kotlin)** | да (заказчик) | ✅ | `ColorHex.kt`, `Color.kt`, `Theme.kt`, `Type.kt` | Единственный источник цветов UI |
| **XML attrs / colors** | ТЗ (опционально) | ❌ убрано | — | По требованию заказчика |
| **Detekt** | да | ✅ | все модули + CI | |

---

## 5. AndroidManifest / Application

| Параметр | Значение |
|----------|----------|
| namespace | `com.practicum.list` |
| applicationId | `com.practicum.shopping_list` |
| Кастомный Application | ❌ |
| MainActivity | ✅ `ComponentActivity` |
| Theme (manifest) | `@style/Theme.ShoppingList` → `Theme.Material3.DayNight.NoActionBar` |

**Минимальный XML в `app`** — только системная тема окна до Compose. **Палитра UI не в XML.**

**Готовность к Dagger:** ❌ (нет Application, AppComponent, inject).

---

## 6. Структура пакетов

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

core/common/     — src/ отсутствует
core/navigation/ — src/ отсутствует
feature/main/    — src/ отсутствует
feature/product/ — src/ отсутствует
```

**`:core:design` не содержит `src/main/res/`** — тема полностью в Kotlin.

Пакеты `data`, `domain`, `presentation`, `di` — отсутствуют.

---

## 7. UI / Compose

| Элемент | Статус |
|---------|--------|
| `setContent` | ✅ |
| `ShoppingListTheme` | ✅ |
| `MaterialTheme.colorScheme` | ✅ light/dark через `isSystemInDarkTheme()` |
| Стартовый экран | ❌ заглушка |
| `@Preview` | ❌ |
| Navigation Compose | ❌ |
| Экраны приложения | ❌ |

---

## 8. MVI

**MVI-инфраструктура есть** (`MviViewModel`, State/Intent/Effect). Экранных ViewModel нет.

---

## 9. Room

**Не реализовано.** Рекомендуемый каркас — без изменений (entities, DAOs, `ShoppingDatabase`).

---

## 10. Dagger

**Не подключён.** Hilt не используется. Нужны: KSP, `AppComponent`, `Application`, modules.

---

## 11. Retrofit

**Не реализовано.** Обоснование для README: API популярных товаров → кэш в Room → подсказки offline.

---

## 12. Тема (Compose-only)

**Реализовано по требованию заказчика** — вся палитра в Kotlin, без XML в `:core:design`.

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
| `Theme.kt` | `ShoppingListTheme`, переключение day/night |
| `Type.kt` | `Typography` (titleLarge, bodyLarge, …) |

### Что удалено (бывший подход theme + attrs)

- `attrs.xml`, `colors.xml`, `themes.xml`, `values-night/*` в `:core:design`
- `ColorFromAttr.kt`, `ColorSchemeFromAttrs.kt`, `ThemedContext.kt`
- `libs.material` в `:core:design` (XML-тема не нужна)

### Минимальный XML (только app)

`app/src/main/res/values/themes.xml`:

```xml
<style name="Theme.ShoppingList" parent="Theme.Material3.DayNight.NoActionBar" />
```

Нужен для `AndroidManifest` и системного окна. **Не содержит цветов приложения.**

### Использование в UI

```kotlin
ShoppingListTheme {
    Box(Modifier.background(MaterialTheme.colorScheme.surface))
    Text("...", color = MaterialTheme.colorScheme.onSurface)
}
```

### Соответствие ТЗ

| Критерий ТЗ | Статус |
|-------------|--------|
| `theme + attrs` (+2) | ❌ не реализовано (сознательно) |
| `values-night/colors` (+1) | ❌ не реализовано |
| Тёмная/светлая тема работает | ✅ через `isSystemInDarkTheme()` + два ColorScheme |

**Для задачи «базовые зависимости»:** тему **не нужно** переделывать — она готова в формате Compose.

---

## 13. Detekt

| Элемент | Статус |
|---------|--------|
| Plugin | ✅ все модули |
| `config/detekt/detekt.yml` | ✅ |
| Правила ТЗ (50 строк / 6 args / 350 класс) | ✅ |
| CI | ✅ `pr_checks.yml` |
| `detektAll` | ❌ |

Замечания: `maxIssues: 100500`; `:app` без явного `detekt { config.setFrom(...) }`.

---

## 14. Small phone support

UI-экранов нет. Рекомендации: `LazyColumn`, `safeDrawingPadding`, без фиксированных высот, preview 320×640.

---

## 15. Release build

| Элемент | Статус |
|---------|--------|
| release buildType | ✅ |
| minifyEnabled | ❌ false |
| proguard-rules.pro | ❌ файл отсутствует |
| signing config | ❌ |

---

## 16. Проверки сборки

После рефакторинга Compose-only (локально, незакоммичено):

```text
assembleDebug: success
detekt:          success
detektAll:       not configured
```

---

## 17. Что делать в задаче «Добавление базовых зависимостей»

### Что уже есть

- Многомодульная структура (7 модулей)
- Compose BOM, Material3, Activity Compose
- **Compose Theme** (`ColorHex` → `Color` → `ShoppingListTheme`)
- MVI base (`MviViewModel`)
- Detekt + CI
- `ComponentActivity` + `setContent`

### Что отсутствует

- Dagger, Room, Retrofit, Navigation Compose
- Application, DI graph
- Feature source code, экраны, ViewModels, repositories
- README

### Что сделать (порядок)

1. **Закоммитить** Compose-only тему в `2-theme-files`
2. **Version Catalog** — Dagger, Room, Retrofit, Navigation, KSP
3. **Dagger** — Application, AppComponent, modules
4. **Room** — Database, entities, DAOs
5. **Retrofit** — API каркас + NetworkModule
6. **Navigation Compose** — NavGraph в `:core:navigation`
7. **Feature stubs** — MainScreen, ListScreen + ViewModels
8. **README** — стек; указать: тема в Compose по требованию заказчика
9. Detekt / namespace cleanup

### Готовность

```text
Готовность к задаче "Добавление базовых зависимостей": ~50%

Что уже есть:
- Compose + Material3 + ShoppingListTheme (Kotlin-only)
- Detekt + CI
- MVI infrastructure
- Multimodule skeleton
- assembleDebug проходит

Что отсутствует:
- Dagger, Room, Retrofit, Navigation Compose
- Application, DI, экраны, README

Критичные риски:
- Незакоммиченный рефакторинг темы
- ТЗ (theme+attrs) vs заказчик (Compose-only) — согласовать с наставником
- PR #18 (navigation) — черновик
- namespace mismatch
- proguard-rules.pro отсутствует

Рекомендованный порядок:
1. Commit Compose-only theme → merge #2 в develop
2. KSP + Dagger
3. Room
4. Retrofit
5. Navigation Compose
6. README + detekt cleanup
```

---

## 18. Риски и рекомендации

1. **Закоммитить Compose-only изменения** перед merge PR #2.
2. **ТЗ vs заказчик** — зафиксировать в README: «тема в Compose по требованию заказчика; формат theme+attrs из ТЗ не используется».
3. **PR #18** — черновик навигации; согласовать с h0mepunk.
4. **MVI готов** — новые ViewModels наследовать от `MviViewModel`.
5. **Тему не трогать** в задаче базовых зависимостей — она закрыта.
6. **Detekt** — подключить config к `:app`, обсудить снижение `maxIssues`.

---

**Итог:** фундамент готов (модули, Compose, MVI, Detekt, **Compose-only тема**). Задача «базовые зависимости» — это **Dagger + Room + Retrofit + Navigation + feature-каркас**. Тема и Compose закрыты; XML attrs больше не используются.
