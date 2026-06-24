# Shopping List

Многомодульное Android-приложение «Список покупок».

## Стек

| Область | Технология | Зачем |
|---------|------------|-------|
| UI | Jetpack Compose + Material 3 | декларативный UI, единая тема через `ShoppingListTheme` |
| Архитектура UI | MVI | однонаправленный поток: Intent → State → UI |
| Навигация | Navigation Compose | type-safe маршруты между feature-модулями |
| DI | Dagger 2 + KSP | явный граф зависимостей без runtime reflection |
| БД | Room | офлайн-хранение списков и товаров |
| Сеть | Retrofit + OkHttp + Moshi | API популярных товаров для подсказок |
| Качество кода | Detekt | статический анализ в CI |

## Модули

```
:app              — Application, DI graph, MainActivity
:core:design      — тема (Compose-only)
:core:mvi         — MviViewModel, State, Intent, Effect
:core:common      — domain-модели
:core:data        — Room, Retrofit, Dagger modules
:core:navigation  — NavGraph
:feature:main     — главный экран
:feature:product  — экран списка
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
