package com.nikol.auth_impl.presentation.viewModel

import android.util.Log
import com.nikol.auth_impl.domain.model.UserType
import com.nikol.auth_impl.domain.useCase.CheckLogInUseCase
import com.nikol.auth_impl.presentation.mvi.intent.CheckPageIntent
import com.nikol.auth_impl.presentation.mvi.state.CheckPageState
import com.nikol.viewmodel.Router
import com.nikol.viewmodel.BaseViewModel
import com.nikol.viewmodel.UiEffect
import com.nikol.viewmodel.intentDsl.debounce
import com.nikol.viewmodel.intentDsl.intents
import com.nikol.viewmodel.intentDsl.on

interface CheckRouter : Router {
    fun toStart()
    fun toHome()
}

typealias CheckStore = BaseViewModel<CheckPageIntent, CheckPageState, UiEffect, CheckRouter>

class CheckLogInPageViewModel(
    private val checkLogInUseCase: CheckLogInUseCase
) : CheckStore() {
    override fun createInitialState() = CheckPageState.Loading

    init {
        setIntent(CheckPageIntent.StartCheck)
    }

    override fun handleIntents() = intents {
        setup<CheckPageIntent.StartCheck> {
            debounce(600)
            handleDropWhileBusy {
                checkLogInUseCase(Unit).fold(
                    ifLeft = {
                        navigate { toStart() }
                    },
                    ifRight = {
                        Log.d("Auth", it.toString())
                        navigate { toHome() }
                    }
                )
            }
            catch {
                Log.d("Auth", "error")
                navigate { toStart() }
            }
        }
        on<CheckPageIntent.NavigateToSart> {
            navigate { toStart() }
        }
        on<CheckPageIntent.NavigateToHome> {
            navigate { toStart() }
        }
    }
}