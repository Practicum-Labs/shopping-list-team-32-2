package com.practicum.list.presentation

import androidx.lifecycle.viewModelScope
import com.practicum.list.core.data.session.UserSessionStore
import com.practicum.list.core.mvi.MviViewModel
import com.practicum.list.domain.CheckTokenUseCase
import com.practicum.list.domain.RefreshTokenUseCase
import com.practicum.list.feature.auth.domain.models.RefreshResult
import com.practicum.list.feature.auth.domain.models.TokenValidResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val userSessionStore: UserSessionStore,
    private val checkTokenUseCase: CheckTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
) :
    MviViewModel<RootIntent, RootState, RootEffect>(RootState()) {
    private var isAnimationFinished = false
    private var pendingNavigationEffect: RootEffect? = null

    init {
        viewModelScope.launch {
            initScreen()
        }
    }

    override fun reduce(
        intent: RootIntent,
        current: RootState
    ): RootState {
        return when (intent) {
            is RootIntent.Renew -> current.copy(isLoading = true, error = null)
            RootIntent.AnimationFinished -> current
        }
    }

    override suspend fun handleIntent(intent: RootIntent) {
        when (intent) {
            RootIntent.Renew -> initScreen()
            RootIntent.AnimationFinished -> {
                isAnimationFinished = true
                pendingNavigationEffect?.let {
                    emitEffect(it)
                    pendingNavigationEffect = null
                }
            }
        }
    }

    private suspend fun initScreen() {
        val accessToken = userSessionStore.getAccessToken()
        if (accessToken == null) {
            finishLoadingAndNavigate(RootEffect.NavigateToLogin)
        } else {
            when (val res = checkTokenUseCase.invoke(accessToken)) {
                is TokenValidResult.Error -> updateErrorState(
                    RootError.UNDEFINED_ERROR
                )

                is TokenValidResult.NoInternet -> updateErrorState(
                    RootError.NO_INTERNET
                )

                is TokenValidResult.ServerError -> updateErrorState(
                    RootError.SERVER_ERROR
                )

                is TokenValidResult.Success -> handleSuccess(res.isValid)
            }
        }
    }

    private suspend fun handleSuccess(isValid: Boolean) {
        if (isValid) {
            finishLoadingAndNavigate(RootEffect.NavigateToMain)
        } else {
            when (refreshTokenUseCase.invoke()) {
                is RefreshResult.Error -> updateErrorState(
                    RootError.UNDEFINED_ERROR
                )

                is RefreshResult.NoInternet -> updateErrorState(
                    RootError.NO_INTERNET
                )

                is RefreshResult.ServerError -> updateErrorState(
                    RootError.SERVER_ERROR
                )

                is RefreshResult.Success -> finishLoadingAndNavigate(RootEffect.NavigateToMain)
            }
        }
    }

    private suspend fun finishLoadingAndNavigate(effect: RootEffect) {
        updateState { it.copy(isLoading = false) }

        if (isAnimationFinished) {
            emitEffect(effect)
        } else {
            pendingNavigationEffect = effect
        }
    }

    private fun updateErrorState(
        error: RootError
    ) {
        updateState { it.copy(isLoading = false, error = error) }
    }
}
