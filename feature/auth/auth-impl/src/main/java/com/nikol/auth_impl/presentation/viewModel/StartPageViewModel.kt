package com.nikol.auth_impl.presentation.viewModel

import com.nikol.auth_impl.domain.model.UserLogin
import com.nikol.auth_impl.domain.model.UserPassword
import com.nikol.auth_impl.domain.parameters.UserCredential
import com.nikol.auth_impl.domain.useCase.CreateGuestSessionUseCase
import com.nikol.auth_impl.domain.useCase.CreateSessionUseCase
import com.nikol.auth_impl.presentation.mvi.effect.StartPageEffect
import com.nikol.auth_impl.presentation.mvi.intent.StartPageIntent
import com.nikol.auth_impl.presentation.mvi.state.CreateSessionState
import com.nikol.auth_impl.presentation.mvi.state.StartPageState
import com.nikol.nav_api.Router
import com.nikol.viewmodel.BaseViewModel
import com.nikol.viewmodel.intentDsl.intents
import com.nikol.viewmodel.state.stateMachine

interface StartPageRouter : Router {
    fun main()
}

typealias StartPageComponent = BaseViewModel<StartPageIntent, StartPageState, StartPageEffect, StartPageRouter>


sealed interface SessionEvent {
    data object Start : SessionEvent
    data object Success : SessionEvent
    data object Failure : SessionEvent
}

class StartPageViewModel(
    private val createGuestSessionUseCase: CreateGuestSessionUseCase,
    private val createSessionUseCase: CreateSessionUseCase
) :
    StartPageComponent() {
    override fun createInitialState() = StartPageState(
        guestButtonState = CreateSessionState.Initial,
        sessionState = CreateSessionState.Initial,
        login = "",
        password = "",
        showPassword = false
    )

    private val sessionMachine = stateMachine<CreateSessionState, SessionEvent>(
        initial = CreateSessionState.Initial,
        bindToUi = { s -> setState { copy(sessionState = s) } }
    ) {
        inState<CreateSessionState.Initial> {
            goto<SessionEvent.Start>(CreateSessionState.Loading)
        }

        inState<CreateSessionState.Loading> {
            goto<SessionEvent.Success>(CreateSessionState.Initial)
            goto<SessionEvent.Failure>(CreateSessionState.Error)
        }

        inState<CreateSessionState.Loading> {
            goto<SessionEvent.Success>(CreateSessionState.Initial)
            goto<SessionEvent.Failure>(CreateSessionState.Error)
        }
    }

    override fun handleIntents() = intents {
        on<StartPageIntent.ContinueWithGuestAccount> {
            handleDropWhileBusy {
                setState { copy(guestButtonState = CreateSessionState.Loading) }
                createGuestSessionUseCase(Unit).fold(
                    ifLeft = {
                        setState { copy(guestButtonState = CreateSessionState.Error) }
                    },
                    ifRight = {
                        setState { copy(guestButtonState = CreateSessionState.Initial) }
                        navigate { main() }
                    }
                )
            }
        }

        on<StartPageIntent.LogIn> {
            handleDropWhileBusy {
                sessionMachine.process(SessionEvent.Start)
                val userCredential = UserCredential(
                    login = UserLogin(uiState.value.login),
                    password = UserPassword(uiState.value.password)
                )
                createSessionUseCase(userCredential).fold(
                    ifLeft = {
                        sessionMachine.process(SessionEvent.Failure)
                    },
                    ifRight = {
                        sessionMachine.process(SessionEvent.Success)
                        navigate { main() }
                    }
                )
            }
        }

        on<StartPageIntent.CreateAccount> {
            handleLatest {
                setEffect { StartPageEffect.GoToBrowser }
            }
        }
        on<StartPageIntent.ChangeLogin> {
            handleConsistently { intent ->
                setState { copy(login = intent.login) }
            }
        }
        on<StartPageIntent.ChangePassword> {
            handleConsistently { intent ->
                setState { copy(password = intent.password) }
            }
        }

        on<StartPageIntent.SwitchPasswordVisibility> {
            handleConsistently {
                setState { copy(showPassword = !showPassword) }
            }
        }
    }
}