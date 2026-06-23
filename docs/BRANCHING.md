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

## Защита веток

**develop** — только через PR, без force-push и удаления ветки.

**main** — только через PR, минимум 1 approving review, без force-push и удаления ветки.

## Автоматические проверки

Workflow `.github/workflows/branch_rules.yml` проверяет на PR:

- корректность имени source-ветки;
- соответствие пары source → target правилам GitFlow.

Workflow `.github/workflows/pr_checks.yml` запускает `detekt` и `assembleDebug`.
