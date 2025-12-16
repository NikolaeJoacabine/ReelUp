package com.nikol.home_impl.presentation.viewModel

import com.nikol.home_impl.presentation.mvi.effect.MovieEffect
import com.nikol.home_impl.presentation.mvi.intent.MovieIntent
import com.nikol.home_impl.presentation.mvi.state.MovieState
import com.nikol.nav_api.Router
import com.nikol.viewmodel.BaseViewModel
import com.nikol.viewmodel.intentDsl.intents
import com.nikol.viewmodel.intentDsl.listen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.seconds

fun interface MovieRouter : Router {
    fun toDetail(id: String)
}

class MovieViewModel : BaseViewModel<MovieIntent, MovieState, MovieEffect, MovieRouter>() {
    override fun createInitialState() = MovieState(isLoading = false)

    override fun handleIntents() = intents {

    }
}