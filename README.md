# Shopping List

Многомодульное Android-приложение «Список покупок».

## Стек

| Область | Технология | Зачем |
|---------|------------|-------|
| UI | Jetpack Compose + Material 3 | декларативный UI, единая тема через `ShoppingListTheme` |
| Архитектура UI | MVI | однонаправленный поток: Intent → State → UI, side-effects через Effect |
| Навигация | Navigation Compose | type-safe маршруты между feature-модулями |
| DI | Hilt + KSP | граф зависимостей, `@HiltViewModel` для ViewModel |
| БД | Room | офлайн-хранение списков и товаров |
| Сеть | Retrofit + OkHttp + Moshi | auth API (Railway) + заглушка ProductApi |
| Качество кода | Detekt | статический анализ в CI |

# Обоснование выбора технологического стека

| Компонент | Выбранный инструмент | Обоснование выбора |
|-----------|----------------------|--------------------|
| Архитектура проекта | Модульная архитектура | Используется в **Now in Android**. Позволяет разделить приложение на независимые feature-модули, улучшает масштабируемость и сопровождение проекта. |
| UI | Jetpack Compose + Material 3 | Используется в **Now in Android**. Современный декларативный UI, единая система темизации, меньше шаблонного кода. |
| Архитектура UI | MVI | Использует те же принципы однонаправленного потока данных (UDF), что и **Now in Android**. Обеспечивает предсказуемое управление состоянием и упрощает тестирование. |
| Навигация | Navigation Compose | Используется в **Now in Android**. Полностью интегрируется с Jetpack Compose и поддерживает type-safe навигацию. |
| Dependency Injection | Hilt | Используется в **Now in Android**. Кроме того, Hilt (Dagger) поощряется требованиями ТЗ по сравнению с Koin. |
| База данных | Room | Используется в **Now in Android**. Предоставляет типобезопасный доступ к данным, интеграцию с Kotlin Flow и соответствует требованиям ТЗ. |
| Сетевой слой | Retrofit | **Now in Android** использует Retrofit только как транспортный слой совместно с Ktor, однако для проекта выбран Retrofit как наиболее распространённая и простая библиотека для работы с REST API. Полностью соответствует требованиям ТЗ. |
| HTTP-клиент | OkHttp | Является стандартным HTTP-клиентом для Retrofit, предоставляет интерцепторы, логирование и гибкую настройку сетевого взаимодействия. |
| JSON-сериализация | Moshi | Выбран благодаря нативной интеграции с Retrofit, поддержке Kotlin и генерации адаптеров через KSP. |
| Статический анализ | Detekt | Не используется в **Now in Android**, однако прямо поощряется требованиями ТЗ. Позволяет автоматически контролировать качество кода и соблюдение единых правил оформления. |

## Модули

```
:app              — Application, MainActivity, NavHost, SessionExpiredHandler
:core:design      — тема (Compose) + shared components (TopBar, dialogs, FAB) + icon drawables
:core:mvi         — MviViewModel, State, Intent, Effect
:core:common      — domain-модели, ShoppingListRepository iface, ObserveListTitleUseCase
:core:data        — Room v6, Retrofit/auth resilient, SessionEvents, Hilt
:core:navigation  — type-safe маршруты + DefaultAnimations
:feature:main     — списки CRUD, category picker, logout (SignOutUseCase)
:feature:list     — товары, ProductBottomSheet, ListMenu, bulk delete (Epic 3)
:feature:auth     — login / register / recovery UI + VM (Epic 2)
```

**Ориентация:** **portrait** only (`MainActivity`). Landscape / tablet — [#79](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/79).

# Доска задач

Для организации процесса разработки использовалась **GitHub Projects** в формате Kanban-доски.

**Ссылка на доску задач:**

👉 https://github.com/orgs/Practicum-Labs/projects/8

## Auth (Epic 2)

Три экрана: **login → register / reset password**. Backend — [Railway REST API](https://practicumopbackend-production.up.railway.app/swagger-ui/index.html), не Firebase.

Согласованные расхождения UI-ТЗ ↔ Swagger (пароль ≥ 7, registration 200, `auth/check` boolean и т.д.) — в [`docs/AUTH.md`](docs/AUTH.md).

Списки покупок в Room привязаны к `user_id` из auth-сессии (см. `UserSession` в `:core:data`).

Точка входа: `RootScreenRoute` — splash, проверка токена, redirect на Login или Main ([#45](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/45)).

**Logout:** иконка профиля на Main → confirm → `SignOutUseCase`; failed refresh — через `TokenAuthenticator`. Оба пути → `SessionEvents` → Login ([#92](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/92)). Детали — [`docs/AUTH.md`](docs/AUTH.md).

## Products & List Screen (Epic 3)

Экран списка: товары, swipe edit/delete, `ProductBottomSheet` (add/edit), меню ⋮ (сортировка A→Я, удалить всё / купленные). Данные — Room; `ProductApi` — stub.

В работе: UI своей (DnD) сортировки; handler `ProductQuantityClicked`. Статус и соглашения — [`docs/PRODUCT.md`](docs/PRODUCT.md).

## Слои feature-модуля

```
feature/<name>/
├── presentation/   — ViewModel, State, Intent, Effect
├── domain/
│   ├── repository/ — интерфейсы репозиториев
│   └── usecase/    — UseCase-классы (не interactor)
├── data/           — реализация репозитория, DI-модули
└── ui/             — Compose-экраны и navigation extensions
```

## Тема

Палитра в Kotlin (`ColorHex` → `Color` → `MaterialTheme`). В UI используйте `MaterialTheme.colorScheme.*`, не хардкод цветов.

## Сборка

```bash
./gradlew assembleDebug
./gradlew detekt
```

## Команда

Проект: [shopping-list-team-32-2](https://github.com/Practicum-Labs/shopping-list-team-32-2)

**Figma:** [Практикум ОП — Список покупок](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=0-1). Deep-links по экранам — [`docs/PRODUCT.md`](docs/PRODUCT.md#figma).
