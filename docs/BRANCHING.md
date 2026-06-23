# Правила работы с ветками

Документ описывает GitFlow команды для репозитория [shopping-list-team-32-2](https://github.com/Practicum-Labs/shopping-list-team-32-2).

## Основные ветки

| Ветка | Назначение |
|-------|------------|
| `develop` | Интеграционная ветка для feature-задач |
| `main` | Стабильная ветка для итераций и финального релиза |

## Именование веток

| Тип | Формат | Пример |
|-----|--------|--------|
| Feature | `номер_задачи-описание_до_6_слов` | `10-branch-rules` |
| Итерация | `iteration-номер_недели` | `iteration-1` |
| Релиз | `release-номер_версии` | `release-1.0.0` |

## Куда открывать PR

| Из ветки | В ветку | Когда |
|----------|---------|-------|
| `N-описание` | `develop` | Завершена задача из kanban |
| `iteration-N` | `main` | Пятничная проверка наставником |
| `release-X.Y.Z` | `main` | Финальная сдача проекта |

После одобрения итерации:

1. `iteration-N` → `main`
2. `main` → `iteration-N`
3. `iteration-N` → `develop`

## Защита веток на GitHub

В репозитории лежат готовые rulesets для импорта:

- `.github/rulesets/develop.json`
- `.github/rulesets/main.json`

### Как импортировать (нужны права администратора репозитория)

1. Откройте **Settings → Rules → Rulesets**.
2. Нажмите **New ruleset → Import a ruleset**.
3. Импортируйте `develop.json`, затем `main.json`.
4. Сохраните оба ruleset в режиме **Active**.

### Что настроено

**develop**

- Запрещён прямой push (только через PR)
- Запрещены force-push и удаление ветки
- Ревью не обязательно (достаточно PR от команды)

**main**

- Запрещён прямой push (только через PR)
- Требуется минимум 1 approving review
- Запрещены force-push и удаление ветки

## Автоматические проверки

Workflow `.github/workflows/branch_rules.yml` проверяет на PR:

- корректность имени source-ветки;
- соответствие пары source → target правилам GitFlow.

Workflow `.github/workflows/pr_checks.yml` запускает `detekt` и `assembleDebug`.
