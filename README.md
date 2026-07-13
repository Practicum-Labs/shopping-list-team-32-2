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
:feature:list  — экран списка / товаров (Epic 3)
:feature:auth     — auth design system, экраны login / register / recovery (Epic 2)
```

## Auth (Epic 2)

Три экрана: **login → register / reset password**. Backend — [Railway REST API](https://practicumopbackend-production.up.railway.app/swagger-ui/index.html), не Firebase.

Согласованные расхождения UI-ТЗ ↔ Swagger (пароль ≥ 7, registration 200, `auth/check` boolean и т.д.) — в [`docs/AUTH.md`](docs/AUTH.md).

Списки покупок в Room привязаны к `user_id` из auth-сессии (см. `UserSession` в `:core:data`).

Точка входа: `RootScreenRoute` — splash, проверка токена, redirect на Login или Main ([#45](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/45)).

## Products & List Screen (Epic 3)

Экран списка покупок: товары, сортировка, swipe-действия, FAB «добавить». Данные — только Room, backend не хранит товары.

Архитектура, MVI, `MeasureUnit`, duplicate, статус реализации — в [`docs/PRODUCT.md`](docs/PRODUCT.md).

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
