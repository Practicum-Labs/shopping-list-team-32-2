# Auth: ТЗ, backend и соглашения команды

Документ фиксирует **согласованные расхождения** между UI-ТЗ (MD «Экран регистрации»), [Swagger backend](https://practicumopbackend-production.up.railway.app/swagger-ui/index.html) и реализацией в репозитории.

Источники:
- UI-ТЗ: экраны login / register / recovery (внешний MD)
- OpenAPI: `https://practicumopbackend-production.up.railway.app/v3/api-docs`
- Epic 2 issues [#39](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/39)–[#46](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/46)
- Figma UI-kit: [Практикум ОП — Список покупок](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=0-1) (auth-экранов в файле нет; иконка `logout` — node [1:7294](https://www.figma.com/design/n84usOH28EjTrPXrfCzM3q/%D0%9F%D1%80%D0%B0%D0%BA%D1%82%D0%B8%D0%BA%D1%83%D0%BC-%D0%9E%D0%9F-%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA-%D0%BF%D0%BE%D0%BA%D1%83%D0%BF%D0%BE%D0%BA?node-id=1-7294))

**Ориентация приложения:** только portrait (`MainActivity` → `android:screenOrientation="portrait"`). Auth-экраны не поддерживают landscape отдельно. См. [`PROJECT_AUDIT.md`](PROJECT_AUDIT.md#4-androidmanifest-и-application).

---

## Backend

| Параметр | Значение |
|----------|----------|
| **Swagger UI** | https://practicumopbackend-production.up.railway.app/swagger-ui/index.html |
| **OpenAPI JSON** | https://practicumopbackend-production.up.railway.app/v3/api-docs |
| **Base URL (Retrofit)** | `https://practicumopbackend-production.up.railway.app/` |
| **Fallback proxy URL** | `https://api.techlabhub.ru/` ([Swagger](https://api.techlabhub.ru/swagger-ui/index.html)) |

При недоступности primary (сеть, timeout, 502/503/504) запросы auth автоматически повторяются через fallback proxy. После первого сбоя primary последующие запросы в сессии идут сразу на fallback; connect timeout primary — 3 с.

Trailing slash в `BASE_URL` обязателен для Retrofit. Пути эндпоинтов — **без** префикса `/api/` (например, `auth/login`, не `api/auth/login`).

Конфигурация в коде: `core/data/.../NetworkModule.kt`.

---

## Firebase

**Не используется.** В UI-ТЗ упоминаются «firebase или моковое API» — в проекте только **REST API** на Railway.

---

## Согласованные расхождения MD-ТЗ ↔ Swagger

| Тема | UI-ТЗ (MD) | Swagger / факт backend | **Принято в проекте** |
|------|------------|------------------------|------------------------|
| Минимальная длина пароля | «длиннее 6 символов» | 400 при пароле &lt; 7 | **≥ 7 символов** (клиент + сервер) |
| Успешная регистрация | HTTP **201** Created | HTTP **200** OK | **200** (ориентир — Swagger) |
| `auth/check` → `is_valid` | строка `"true"` в примере JSON | **boolean** | **boolean**; парсить как `Boolean` |
| `auth/check` доп. поля | не описаны | `success`, `refresh` (bool) | учитывать при маппинге DTO |
| `auth/recovery` | header `email` | параметры в Swagger не детализированы | **header `email`** (как в MD и issue #39) |
| `auth/check` Authorization | `Bearer {token}` | header `Authorization` | передавать **`Bearer ` + access_token** |
| Login при неверных данных | 400 email/пароль | чаще **401** Unauthorized | маппить 401 → `Unauthorized` / общая ошибка входа |
| Backend | Firebase / mock | Railway REST | **только Railway REST** |

---

## API endpoints

### POST `auth/registration`

**Request:**
```json
{ "email": "user@example.com", "password": "1234567" }
```

**Success (200):**
```json
{
  "access_token": "...",
  "refresh_token": "...",
  "user_id": 1
}
```

**Ошибки:** 400 (email / weak password), 409 (user exists), 401, 500.

DTO: snake_case в JSON → `@field:Json(name = "access_token")` и т.д.

---

### POST `auth/login`

**Request:** `{ "email", "password" }`  
**Success (200):** как registration (tokens + `user_id`).  
**Ошибки:** 400, **401** (неверные учётные данные), 500.

---

### POST `auth/refresh`

**Request:**
```json
{ "refresh_token": "..." }
```

**Success (200):**
```json
{
  "access_token": "...",
  "refresh_token": "..."
}
```

---

### GET `auth/check`

**Header:** `Authorization: Bearer {access_token}`

**Success (200):**
```json
{
  "is_valid": true,
  "success": true,
  "refresh": false
}
```

- `is_valid` — **boolean**, не строка.
- `success`, `refresh` — из OpenAPI; при необходимости добавить в `CheckResponse`.

Без префикса `Bearer` backend отвечает 400, а не 401.

---

### POST `auth/recovery`

**Header:** `email: user@example.com`  
**Body:** пустое.

**Success (200):** plain text (не JSON), например `"Letter send ..."`.  
В Retrofit использовать `Unit` / `ResponseBody` / `String`, не JSON-обёртку.

**Ошибки:** 400 (некорректный email).

---

## Auth flow в приложении

### Экраны (UI-ТЗ)

| Экран | Переходы |
|-------|----------|
| **Login** | → Register, → Reset password; успех → Main |
| **Register** | toolbar назад → Login |
| **Reset password** | toolbar назад → Login |

### Навигация (type-safe routes)

**Реализованы** в `core/navigation/Destinations.kt` ([#42](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/42)):

- `LoginRoute`
- `RegisterRoute`
- `ResetPasswordRoute`

Регистрация: `feature/auth/.../AuthNavigation.kt` → `authScreenNavigation()`.

**Поведение back stack:**
- Login → Register / Reset password: `navigate()` (экран остаётся в стеке, «назад» → Login)
- Register / Reset → Login: `popBackStack()`, не повторный `navigate(LoginRoute)`
- Успешный login / register → Main с `popUpTo<LoginRoute> { inclusive = true }`

Точка входа — `RootScreenRoute` + `RootViewModel` ([#45](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/45)): splash → `auth/check` / refresh → Login или Main.

Экран списка и товары (Epic 3) — [`PRODUCT.md`](PRODUCT.md).

### Сеть и отказоустойчивость

| Компонент | Роль |
|-----------|------|
| `AuthInterceptor` | `Authorization: Bearer` + `Content-Type` |
| `TokenAuthenticator` | 401 → refresh; при fail → `clearSession` + `SessionEvents` |
| `ResilientAuthApi` / `ApiHostSelector` | primary Railway → sticky fallback proxy при IO/502/503/504 |
| Отдельный refresh-клиент | без authenticator loop (`@RefreshResilientAuthApi`) |

Timeouts: primary connect ~3 с, fallback длиннее — см. `NetworkModule.kt`.

### Logout и SessionEvents ([#92](https://github.com/Practicum-Labs/shopping-list-team-32-2/issues/92))

| Путь | Цепочка |
|------|---------|
| Ручной | Main TopBar profile → `LogoutDialog` → `SignOutUseCase` → `clearSession()` + `notifySessionExpired()` |
| Авто | `TokenAuthenticator` после неудачного refresh — то же |
| UI | `MainActivity.SessionExpiredHandler` → `LoginRoute`, очистка back stack |

Диалог: `core/design/.../dialogs/LogoutDialog.kt` (на базе `CustomLayoutDialog`).

### Связь пользователя и списков покупок (Room)

Backend не хранит списки — только auth. Локально списки привязаны к `user_id` из API.

| Компонент | Роль |
|-----------|------|
| `shopping_lists.user_id` | владелец списка; товары наследуют через `list_id` |
| `UserSession` (`:core:common`) | **интерфейс** сессии |
| `UserSessionStore` (`:core:data`) | DataStore + **`CryptoHelper`** (Keystore AES) для токенов |
| `feature/auth/.../models/UserSession` | **DTO** ответа API (другой тип, то же имя) |
| `UserSessionDefaults.LEGACY_LOCAL_USER_ID` (`0`) | списки до первого логина / без сессии |

**Поведение:**
- CRUD списков фильтруется по текущему `userId` из `UserSession`
- При первом `saveSession` после логина списки с `user_id = 0` перепривязываются к `user_id` пользователя
- Logout очищает сессию; данные в Room остаются (мультиаккаунт на одном устройстве)
- `LoginUseCase` вызывает `UserSession.saveSession(userId, accessToken, refreshToken)` после успешного API

### Модули

| Модуль | Роль |
|--------|------|
| `:feature:auth` | UI (#43), экраны/VM (#44), `authScreenNavigation` (#42) |
| `:feature:main` | `SignOutUseCase`, logout dialog wiring |
| `:core:data` | `AuthApi`, resilient client, interceptor/authenticator, `SessionEvents`, crypto store |
| `:core:navigation` | type-safe routes + animations |
| `:app` | `NavHost`, `RootScreen`, `SessionExpiredHandler` (#45) |

### Design system (`:feature:auth`)

Компоненты (#43): `AuthOutlinedTextField`, `PasswordTextField`, `PasswordStrengthIndicator`, `PasswordRequirementsChecklist`, `PrimaryAuthButton`.

Валидация пароля в UI и на клиенте сети: **минимум 7 символов**.

---

## AuthError (domain)

Сеaled-тип в `:core:common`: `InvalidEmail`, `InvalidPassword`, `Unauthorized`, `WeakPassword`, `UserAlreadyExists`, `NetworkError`, `InternalServerError`, `Unknown`.

При login сервер чаще отдаёт **401**, а не отдельные 400 для email/пароля — UI показывает общую ошибку входа без уточнения поля.

---

## Чеклист для разработчиков

- [ ] Base URL Railway с trailing slash; учитывать fallback proxy
- [ ] Пароль ≥ 7, не 6
- [ ] Registration success = 200
- [ ] `auth/check`: `Bearer` + boolean `is_valid`
- [ ] `auth/recovery`: header `email`, ответ — text/plain
- [ ] Без Firebase
- [ ] DTO: snake_case через Moshi `@Json`
- [ ] Logout / expired session → только через `SessionEvents` (не дублировать navigate в VM)
- [ ] Не путать `core.common.UserSession` (iface) и `feature.auth...UserSession` (DTO)
- [ ] Списки покупок: `shopping_lists.user_id` + фильтрация через `UserSession`
