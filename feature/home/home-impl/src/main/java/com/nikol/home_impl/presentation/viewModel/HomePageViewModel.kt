package com.nikol.home_impl.presentation.viewModel

import com.nikol.home_impl.presentation.mvi.effect.HomeEffect
import com.nikol.home_impl.presentation.mvi.intent.HomeIntent
import com.nikol.home_impl.presentation.mvi.state.HomeState
import com.nikol.viewmodel.Router
import com.nikol.ui.model.MediaType
import com.nikol.viewmodel.BaseViewModel
import com.nikol.viewmodel.intentDsl.filter
import com.nikol.viewmodel.intentDsl.intents

interface TypeContentRouter : Router {
    fun navigateToMovie()
    fun navigateToTV()
}

class HomePageViewModel : BaseViewModel<HomeIntent, HomeState, HomeEffect, TypeContentRouter>() {
    override fun createInitialState() = HomeState(
        mediaType = MediaType.MOVIE
    )

    override fun handleIntents() = intents {
        setup<HomeIntent.ChangeTypeContent> {
            filter { intent -> intent.mediaType != uiState.value.mediaType }
            handleConsistently { intent ->
                setState { copy(mediaType = intent.mediaType) }
                navigate {
                    when (intent.mediaType) {
                        MediaType.MOVIE -> navigateToMovie()
                        MediaType.TV -> navigateToTV()
                        else -> {}
                    }
                }
            }
        }
    }
}