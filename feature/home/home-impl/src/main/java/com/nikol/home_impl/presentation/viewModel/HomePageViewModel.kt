package com.nikol.home_impl.presentation.viewModel

import com.nikol.home_impl.domain.parameters.TypeContent
import com.nikol.home_impl.presentation.mvi.effect.HomeEffect
import com.nikol.home_impl.presentation.mvi.intent.HomeIntent
import com.nikol.home_impl.presentation.mvi.state.HomeState
import com.nikol.nav_api.Router
import com.nikol.viewmodel.BaseViewModel
import com.nikol.viewmodel.intentDsl.filter
import com.nikol.viewmodel.intentDsl.intents
import com.nikol.viewmodel.state.stateMachine

interface TypeContentRouter : Router {
    fun navigateToMovie()
    fun navigateToTV()
}

class HomePageViewModel : BaseViewModel<HomeIntent, HomeState, HomeEffect, TypeContentRouter>() {
    override fun createInitialState() = HomeState(
        typeContent = TypeContent.Movie
    )

    override fun handleIntents() = intents {
        on<HomeIntent.ChangeTypeContent> {
            filter { intent -> intent.typeContent != uiState.value.typeContent }
            handleConsistently { intent ->
                setState { copy(typeContent = intent.typeContent) }
                navigate {
                    when (intent.typeContent) {
                        TypeContent.Movie -> navigateToMovie()
                        TypeContent.TV -> navigateToTV()
                    }
                }
            }
        }
    }
}