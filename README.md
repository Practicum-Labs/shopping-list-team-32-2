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

## Модули

```
:app              — Application, MainActivity, NavHost
:core:design      — тема (Compose-only)
:core:mvi         — MviViewModel, State, Intent, Effect
:core:common      — domain-модели
:core:data        — Room, Retrofit, Hilt modules
:core:navigation  — type-safe маршруты
:feature:main     — главный экран (presentation / domain / data)
:feature:list  — экран списка / товаров
:feature:auth     — auth design system, экраны login / register / recovery (Epic 2)
```

## Auth (Epic 2)

Три экрана: **login → register / reset password**. Backend — [Railway REST API](https://practicumopbackend-production.up.railway.app/swagger-ui/index.html), не Firebase.

Согласованные расхождения UI-ТЗ ↔ Swagger (пароль ≥ 7, registration 200, `auth/check` boolean и т.д.) — в [`docs/AUTH.md`](docs/AUTH.md).

Списки покупок в Room привязаны к `user_id` из auth-сессии (см. `UserSession` в `:core:data`).

Точка входа в приложение и проверка сессии — в работе ([#45](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/45)); после логина — `MainScreenRoute`.

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
