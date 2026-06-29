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
| Сеть | Retrofit + OkHttp + Moshi | API популярных товаров для подсказок |
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
:feature:product  — экран списка / товаров
```

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
