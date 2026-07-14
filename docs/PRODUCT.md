# Products & List Screen: соглашения команды

Документ фиксирует **архитектуру и согласованное поведение** экрана списка покупок (товары внутри одного `ShoppingList`) — по образцу [`AUTH.md`](AUTH.md).

Источники:
- Epic 3 issues [#63](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/63)–[#75](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/75)
- [Figma — Практикум ОП, Список покупок](#figma) (экраны `shopping lists`, `list - …`)
- Реализация в `:feature:list` (актуально для текущего `develop`)

---

## Figma

**Файл:** [Практикум ОП — Список покупок](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=0-1)

Для deep-link в URL замените `:` на `-` (например `1:8208` → `node-id=1-8208`).

### Секции файла

| Node | Раздел | Содержимое |
|------|--------|------------|
| [0:1](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=0-1) | Page 1 | весь файл |
| [1:7480](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-7480) | **Android light** | все light-экраны |
| [1:8266](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8266) | **Android dark** | те же экраны, тёмная тема |
| [1:7294](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-7294) | **Icons** | Material Icons, чекбоксы, swipe-кнопки, `logout` |
| [1:9049](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-9049) | **components** | карточки списков, product row, UI-kit |

Auth-экраны (login / register) **в этом файле нет** — только иконки; макеты auth — в MD-ТЗ, см. [`AUTH.md`](AUTH.md).

### Экран списка — макеты (Android light)

| Экран Figma | Node | Код / поведение |
|-------------|------|-----------------|
| [shopping lists](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8208) | `1:8208` | основной экран: TopBar, LazyColumn, FAB |
| [lists are empty](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-7971) | `1:7971` | `ListEmptyPlaceholder` |
| [shopping list - swipe an item](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8118) | `1:8118` | `ProductListItem` + `ProductListActions` |
| [shopping list - menu](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8044) | `1:8044` | TopBar ⋮ → sort / delete |
| [shopping list - sort alphabetically](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8068) | `1:8068` | `SortProductsAlphabeticallyUseCase` |
| [shopping list - manual sorting](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8093) | `1:8093` | drag-and-drop, `isBeingSorted` |
| [shopping list - manually dragging…](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8130) | `1:8130` | DnD в процессе |
| [shopping list - moved](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8147) | `1:8147` | результат custom sort |
| [shopping list - sorted](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8201) | `1:8201` | после A→Я сортировки |
| [shopping list - delete all items](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8222) | `1:8222` | confirmation → `DeleteAllProductsUseCase` |
| [list - edit product](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8170) | `1:8170` | bottom sheet: quantity + unit |

### Добавление товара (flow FAB)

| Экран Figma | Node | Шаг |
|-------------|------|-----|
| [list - add items](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8161) | `1:8161` | FAB нажат |
| [list - product input](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-7962) | `1:7962` | ввод названия |
| [list - quantity input](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-7953) | `1:7953` | ввод количества |
| [list - enter unit of measure](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-7927) | `1:7927` | выбор единицы |
| [list - unit selected](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-7936) | `1:7936` | единица выбрана |
| [list - when all is entered](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-7945) | `1:7945` | форма заполнена, save enabled |

### Dark theme (те же экраны)

| Light | Dark |
|-------|------|
| [shopping lists `1:8208`](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8208) | [shopping lists `1:8946`](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8946) |
| [lists are empty `1:7971`](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-7971) | [lists are empty `1:8713`](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8713) |
| [swipe `1:8118`](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8118) | [swipe `1:8869`](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8869) |
| [edit product `1:8170`](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8170) | [edit product `1:8912`](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-8912) |

Палитра light/dark сверена с `ColorHex.kt` / `ShoppingListTheme` — см. [`PROJECT_AUDIT.md`](PROJECT_AUDIT.md#7-тема-compose-only).

---

## Scope

| Параметр | Значение |
|----------|----------|
| **Epic** | 3 — экран списка и CRUD товаров |
| **Feature-модуль** | `:feature:list` (не `:feature:product`) |
| **Связанный модуль** | `:feature:main` — дублирование списка **вместе с товарами** |
| **Domain-модели** | `:core:common` — `Product`, `MeasureUnit` |
| **Persistence** | `:core:data` — Room (`ProductEntity`, `ProductDao`) |
| **Ориентация** | только **portrait** (`MainActivity` в манифесте); landscape / tablet — [#79](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/79) |

Экран списка включает: TopBar, список товаров, FAB «добавить», swipe-действия, сортировку, массовое удаление, редактирование количества/единицы — см. раздел «Статус реализации».

---

## Backend

Backend ([Swagger](https://practicumopbackend-production.up.railway.app/swagger-ui/index.html)) **не хранит** списки и товары — только auth.

| Компонент | Статус |
|-----------|--------|
| `ProductApi.getPopularProducts()` | Заглушка в `:core:data`, endpoint `GET products` **не используется** в feature-слое |
| Синхронизация товаров с сервером | **Нет** — offline-first, только Room |

Конфигурация Retrofit: `core/data/.../NetworkModule.kt`.

---

## Хранение данных (Room)

### Таблица `products`

| Колонка | Тип | Описание |
|---------|-----|----------|
| `id` | `INTEGER PK AUTOINCREMENT` | идентификатор товара |
| `listId` | `INTEGER FK` | → `shopping_lists.id`, **ON DELETE CASCADE** |
| `name` | `TEXT` | название |
| `quantity` | `REAL` | количество (default `1`) |
| `isChecked` | `INTEGER` | куплен / отмечен (0/1) |
| `unit` | `TEXT` | код единицы измерения (`pcs`, `kg`, …) |
| `sortPosition` | `INTEGER` | порядок в списке |

Entity: `core/data/.../entity/ProductEntity.kt`  
DAO: `core/data/.../dao/ProductDao.kt` — сортировка в UI: `ORDER BY sortPosition, id`.

### Версия БД

`ShoppingDatabase` **v6** (миграции `MIGRATION_1_2` … `MIGRATION_5_6` в `ShoppingDatabaseMigrations.kt`):
- v2 — `icon_res_id` у списков
- v3 — `user_id` у списков
- v4 — `unit`, `sortPosition`, `quantity` как `REAL` у товаров
- v5 — нормализация пустых `unit` → `'pcs'`
- v6 — `icon_res_id = 0` → `ListIcons.DEFAULT_LIST_ICON`

### Domain-модель

```kotlin
data class Product(
    val id: Long,
    val name: String,
    val isChecked: Boolean,
    val listId: Long,
    val quantity: Float,
    val unit: MeasureUnit,
    val sortPosition: Int,
)
```

---

## Связь с пользователем

Товары **не имеют** собственного `user_id`. Владелец определяется через родительский список:

```
UserSession.userId → shopping_lists.user_id → products.listId
```

| Операция | Фильтрация |
|----------|------------|
| Заголовок экрана (`observeListTitle`) | `ShoppingListDao.observeById(listId, userId)` |
| Товары (`observeProductsByListId`) | только по `listId` (список уже принадлежит пользователю через навигацию с Main) |

Подробнее про `user_id` и legacy-списки — [`AUTH.md`](AUTH.md#связь-пользователя-и-списков-покупок-room).

**Канонический путь заголовка:** `ObserveListTitleUseCase` в `:core:common` → `ShoppingListRepository` (интерфейс в `:core:common`, impl в `:feature:main`).  
В `feature/list/.../ListUseCases.kt` может оставаться legacy-дубликат — `ListViewModel` берёт UseCase из `:core:common`.

---

## Навигация

Type-safe маршруты (`:core:navigation`):

```
RootScreenRoute → MainScreenRoute → ListScreenRoute(listId) → popBackStack → Main
```

| Переход | Как |
|---------|-----|
| Main → List | `navController.navigate(ListScreenRoute(listId))` |
| List → Main (back) | `ListIntent.BackClicked` → `ListEffect.NavigateToMain` → `popBackStack()` |
| `listId` | из `SavedStateHandle.toRoute<ListScreenRoute>()` в `ListViewModel` |

Extension: `feature/list/.../ListScreenNavigation.kt` → `listScreenNavigation()`.

---

## MVI (`ListViewModel`)

Базовый контракт — `:core:mvi` (`dispatch` → `reduce` + `handleIntent`).

### State (`ListState`)

| Поле | Назначение |
|------|------------|
| `listId`, `listTitle` | контекст экрана |
| `products` | товары из Room (Flow) |
| `isLoading` | первичная загрузка |
| `productBottomSheetOpened` | единый bottom sheet add/edit |
| `productBottomSheetState` | поля формы (`id`, `name`, `quantity`, `unit`, …) |
| `addProductError` | ошибка формы (если нужна) |
| `contextMenuOpened` | видимость `ListMenu` (⋮ → ModalBottomSheet) |
| `contextMenuState` | метаданные сортировки (`sortType`), не visibility меню |
| `confirmationDialogState` | подтверждение «удалить все / купленные» |
| `isBeingSorted` | задел под drag-and-drop (UI режима пока нет) |

### Effect (`ListEffect`)

| Effect | Действие UI |
|--------|-------------|
| `NavigateToMain` | `popBackStack()` |
| `ShowError(message)` | Snackbar |

### UseCases (`:feature:list/domain/usecase`)

| UseCase | Репозиторий |
|---------|-------------|
| `ObserveProductsByListIdUseCase` | `ListRepository` |
| `UpsertProductUseCase` | create / update / toggle checked |
| `DeleteProductUseCase` | удаление одного товара |
| `DeleteAllProductsUseCase` | очистка списка |
| `DeleteBoughtProductsUseCase` | удаление `isChecked = true` |
| `SortProductsAlphabeticallyUseCase` | пересчёт `sortPosition` по имени |
| `SortProductsCustomUseCase` | сохранение порядка после drag |

Заголовок: `ObserveListTitleUseCase` — `:core:common` (см. выше).

---

## UI экрана списка

### Структура

```
Scaffold (ListScreenNavigation)
├── TopBar — title, back, options (⋮)
├── SnackbarHost — ошибки UseCase
└── ListScreen
    ├── Loading — CircularProgressIndicator
    ├── Empty — ListEmptyPlaceholder
    ├── LazyColumn — ProductListItem × N
    └── AddFab — «+» (добавить товар)
```

### `ProductListItem`

Компонент: `feature/list/.../ui/components/listitem/ProductListItem.kt`

| Элемент | Intent | Статус |
|---------|--------|--------|
| Checkbox | `ToggleProductChecked` → upsert | ✅ |
| Tap на quantity | `ProductQuantityClicked` | ⚠️ UI шлёт intent, VM **не обрабатывает** |
| Swipe → Edit | `ProductMenuEditClicked` → `ProductBottomSheet` | ✅ |
| Swipe → Delete | `DeleteProductClicked` | ✅ |

Swipe через `AnchoredDraggable` (`ProductListActions`: edit + delete).

Формат количества: `@Composable Product.formatQuantityLabel()` в `ProductQuantityFormatter.kt` — `"1 кг"`, целые без `.0`.

### Меню списка (TopBar ⋮)

UI: `ListMenu` — `ModalBottomSheet` (`feature/list/.../bottomsheet/ListMenu.kt`), флаг `contextMenuOpened`.  
Открытие: `OptionsMenuClicked` из `ListScreenNavigation` / TopBar options.

| Пункт в UI | Intent | Side-effect | Статус |
|------------|--------|-------------|--------|
| Сортировать по алфавиту | `ListMenuAlphabeticalSortClicked` | `SortProductsAlphabeticallyUseCase` | ✅ |
| Удалить купленные | `ListMenuDeleteCheckedClicked` → `DeleteBoughtDialog` → `DeleteDialogConfirmed(Checked)` | `DeleteBoughtProductsUseCase` | ✅ |
| Удалить всё | `ListMenuDeleteAllClicked` → `DeleteAllDialog` → `DeleteDialogConfirmed(All)` | `DeleteAllProductsUseCase` | ✅ |
| Своя сортировка (DnD) | `ListMenuCustomSortClicked` / `ListMenuCustomSortConfirmed` | `SortProductsCustomUseCase` | ⚠️ только VM; **пункта в `ListMenu` нет**, DnD UI нет |

На пустом списке пункты меню закрывают sheet и показывают `ShowError("Список товаров пуст")`.

### Добавление и редактирование товара

Единый `ProductBottomSheet` (`feature/list/.../bottomsheet/ProductBottomSheet.kt`):

| Flow | Как открывается | State | Статус |
|------|-----------------|-------|--------|
| FAB → add | `AddProductClicked` | `productBottomSheetOpened` + пустой `ProductBottomSheetState` | ✅ |
| Edit | swipe → `ProductMenuEditClicked(product)` | sheet с полями товара | ✅ |
| Apply | `ProductApplyClicked` → `applyProductForm()` → `UpsertProductUseCase` | sheet закрывается | ✅ |

**Принято:** upsert — `UpsertProductUseCase`; `id = 0` → insert, иначе update (Room `REPLACE`).

---

## Единицы измерения (`MeasureUnit`)

Sealed class в `:core:common`. Код хранится в Room как `TEXT`, конвертер — `MeasureUnitConverter`.

| Код | Enum | Строка (R.string) |
|-----|------|-------------------|
| `pcs` | `Piece` | шт |
| `kg` | `Kilogram` | кг |
| `g` | `Gram` | г |
| `mg` | `Milligram` | мг |
| `l` | `Liter` | л |
| `ml` | `Milliliter` | мл |
| `pkg` | `Package` | уп |

```kotlin
fun fromCode(code: String?): MeasureUnit =
    all.firstOrNull { it.code == code } ?: Piece
```

Nullable `fromCode` + fallback `Piece` — обязательны после ProGuard/minify ([#98](https://github.com/Practicum-Labs/shopping-list-team-32-2/pull/98)).

---

## Дублирование списка (#66)

Реализовано в `:feature:main`, не в `:feature:list`:

1. `DuplicateShoppingListUseCase` → `ShoppingListRepository.duplicateShoppingList`
2. Новый список: имя `"Копия " + original.name`
3. Товары копируются через `ProductDao.insertAll` с новыми `id` и `listId`, поля **1:1** (`isChecked`, `quantity`, `unit`, `sortPosition`)

Файл: `feature/main/.../ShoppingListRepositoryImpl.kt`.

---

## Модули и файлы

```
:feature:list/
├── presentation/
│   ├── ListViewModel.kt
│   ├── ListState.kt
│   ├── ListIntent.kt
│   └── ListEffect.kt
├── domain/
│   ├── repository/ListRepository.kt
│   └── usecase/… (Observe, Upsert, Delete*, Sort*)
├── data/
│   ├── impl/ListRepositoryImpl.kt
│   └── di/ListRepositoryModule.kt
└── ui/
    ├── screens/
    │   ├── ListScreen.kt
    │   └── ListScreenNavigation.kt
    └── components/
        ├── bottomsheet/ProductBottomSheet.kt
        ├── bottomsheet/ListMenu.kt
        ├── dialogs/DeleteAllDialog.kt, DeleteBoughtDialog.kt
        ├── listitem/ProductListItem.kt
        ├── ProductListActions.kt
        ├── checkbox/ProductRoundCheckbox.kt
        ├── ProductQuantityFormatter.kt
        └── ListEmptyPlaceholder.kt

:core:data/      — ProductEntity, ProductDao, ProductApi (stub), Room v6
:core:common/    — Product, MeasureUnit, ShoppingListRepository (interface), ObserveListTitleUseCase
:feature:main/   — ShoppingListRepositoryImpl, duplicate, Main CRUD, SignOutUseCase
```

---

## Статус реализации (develop)

| Блок | Data / Domain | Presentation | UI |
|------|---------------|--------------|-----|
| Observe products + title | ✅ | ✅ | ✅ |
| Toggle checked | ✅ | ✅ | ✅ |
| Swipe delete / edit | ✅ | ✅ `DeleteProductClicked` / `ProductMenuEditClicked` | ✅ |
| Sort A→Я | ✅ | ✅ | ✅ `ListMenu` |
| Custom sort (DnD) | ✅ UseCase | ✅ intents | ⚠️ нет пункта меню и DnD UI |
| Delete all / checked | ✅ | ✅ | ✅ `DeleteAllDialog` / `DeleteBoughtDialog` |
| Add / edit product | ✅ upsert | ✅ единый sheet | ✅ `ProductBottomSheet` |
| Tap quantity → edit | — | ⚠️ `ProductQuantityClicked` не handled | UI шлёт intent |
| Duplicate list + products | ✅ | ✅ (`:feature:main`) | ✅ |

---

## Вне scope Epic 3 (или реализовано в других эпиках)

| Тема | Статус |
|------|--------|
| `ProductApi` / популярные товары | Stub; нет endpoint в Swagger |
| Auth / сессия / login–register | ✅ Epic 2 — [`AUTH.md`](AUTH.md) |
| Logout / профиль | ✅ [#92](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/92) — `LogoutDialog` + `SignOutUseCase` |
| `CategoryPickerBottomSheet` на Main | ✅ [#25](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/25) |
| Rename / delete списка на Main | ✅ [#24](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/24) |
| Онбординг / splash | ✅ / частично [#61](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/61), [#45](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/45) |
| Landscape / tablet | Follow-up [#79](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/79); сейчас portrait-only |
| Отдельный flow только «переименовать товар» | Нет; имя правится в том же `ProductBottomSheet` |

---

## Чеклист для разработчиков

- [ ] Feature-модуль — `:feature:list`, не `product`
- [ ] Новый UseCase → `domain/usecase`, не `interactor`
- [ ] CRUD товаров — через `ListRepository` / соответствующий UseCase
- [ ] Upsert: `Product(id = 0, …)` для insert; `sortPosition` задаёт порядок в DAO
- [ ] `MeasureUnit.fromCode(null)` → `Piece`; не использовать `first { }` без fallback
- [ ] Не подключать `ProductApi` без endpoint в Swagger
- [ ] Swipe: `DeleteProductClicked`, `ProductMenuEditClicked`; меню — `contextMenuOpened` + `ListMenu`
- [ ] Tap quantity: доработать handler для `ProductQuantityClicked` (сейчас noop)
- [ ] Ошибки UseCase → `ListEffect.ShowError`, не silent fail
- [ ] Duplicate — только через `DuplicateShoppingListUseCase` в `:feature:main`
