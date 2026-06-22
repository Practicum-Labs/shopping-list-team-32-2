package ru.practicum.shoppinglist.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Базовый ViewModel для архитектуры MVI.
 *
 * Поток данных однонаправленный:
 *   View ──dispatch(Intent)──▶ ViewModel ──reduce──▶ State ──collect──▶ View
 *                                       └─emitEffect─▶ Effect ──collect──▶ View
 *
 *  - [state] — горячий [StateFlow], всегда содержит текущее состояние экрана.
 *  - [effects] — поток одноразовых событий через [Channel]. Channel выбран
 *    специально: после rotation подписчик пересоздаётся, и SharedFlow с replay
 *    повторно доставил бы старые эффекты, а Channel — нет.
 *  - [reduce] — чистая функция перехода `(Intent, State) → State`.
 *  - [handleIntent] — место для side-effects (запросы к репозиторию,
 *    отправка эффектов, и т.п.).
 */
abstract class MviViewModel<I : MviIntent, S : MviState, E : MviEffect>(
    initialState: S,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effects = Channel<E>(Channel.BUFFERED)
    val effects: Flow<E> = _effects.receiveAsFlow()

    /** Точка входа из View: любое пользовательское намерение приходит сюда. */
    fun dispatch(intent: I) {
        _state.value = reduce(intent, _state.value)
        viewModelScope.launch { handleIntent(intent) }
    }

    /** Чистая функция: на основании текущего state и intent возвращает новый state. */
    protected abstract fun reduce(intent: I, current: S): S

    /** Side-effects: сетевые запросы, эмит [Effect], запуск таймеров и т.д. */
    protected open suspend fun handleIntent(intent: I) = Unit

    /** Помощник для асинхронного апдейта state из [handleIntent]. */
    protected fun updateState(transform: (S) -> S) {
        _state.value = transform(_state.value)
    }

    /** Помощник для отправки эффекта во View. */
    protected suspend fun emitEffect(effect: E) {
        _effects.send(effect)
    }
}
