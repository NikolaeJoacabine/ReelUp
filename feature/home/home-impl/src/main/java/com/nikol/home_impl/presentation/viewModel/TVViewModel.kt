package com.nikol.home_impl.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.nikol.home_impl.domain.parameters.ContentParameter
import com.nikol.home_impl.domain.parameters.Period
import com.nikol.home_impl.domain.useCase.GetTrendTvUseCase
import com.nikol.home_impl.presentation.mvi.intent.MovieIntent
import com.nikol.home_impl.presentation.mvi.intent.TVIntent
import com.nikol.home_impl.presentation.mvi.state.TVState
import com.nikol.home_impl.presentation.mvi.state.TrendContent
import com.nikol.home_impl.presentation.ui.ext.toUi
import com.nikol.home_impl.presentation.viewModel.MovieViewModel.Companion.LOAD_NOW_PLAYING
import com.nikol.ui.model.MediaType
import com.nikol.ui.state.ListState
import com.nikol.viewmodel.BaseViewModel
import com.nikol.viewmodel.Router
import com.nikol.viewmodel.UiEffect
import com.nikol.viewmodel.asyncWithoutOld
import com.nikol.viewmodel.intentDsl.filter
import com.nikol.viewmodel.intentDsl.intents
import com.nikol.viewmodel.intentDsl.onLatest
import com.nikol.viewmodel.intentDsl.onSingle
import com.nikol.viewmodel.launchWithoutOld
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

fun interface TVRouter : Router {
    fun toDetail(id: String)
}

class TVViewModel(
    private val getTrendTvUseCase: GetTrendTvUseCase
) : BaseViewModel<TVIntent, TVState, UiEffect, TVRouter>() {

    init {
        setIntent(TVIntent.LoadAllData)
    }

    override fun createInitialState() =
        TVState(
            isLoading = false,
            trend = TrendContent(
                state = ListState.Loading,
                period = Period.Day
            )
        )

    override fun handleIntents() = intents {
        onSingle<TVIntent.LoadAllData> {
            loadTrending(ContentParameter(mediaType = MediaType.TV, period = Period.Day))
        }

        setup<TVIntent.ChangePeriodForTrend> {
            filter { intent ->
                val currentBlock = uiState.value.trend
                intent.period != currentBlock.period || currentBlock.state is ListState.Error
            }
            handleLatest { intent ->
                setState {
                    copy(
                        trend = trend.copy(
                            period = intent.period,
                            state = ListState.Loading
                        )
                    )
                }
                loadTrending(
                    ContentParameter(
                        mediaType = MediaType.MOVIE,
                        period = intent.period
                    )
                ).join()
            }
        }

        onSingle<TVIntent.NavigateToDetail> { intent ->
            navigate { toDetail(intent.id) }
        }

        onLatest<TVIntent.RefreshData> {
            setState { copy(isLoading = true) }
            val timer = viewModelScope.launch {
                delay(1.seconds)
            }
            val trendResult = asyncWithoutOld(LOAD_POPULAR) {
                getTrendTvUseCase(
                    ContentParameter(
                        mediaType = MediaType.TV,
                        period = uiState.value.trend.period
                    )
                )
            }

            val movieTrend = trendResult.await()
            timer.join()
            setState {
                copy(
                    isLoading = false,
                    trend = trend.copy(
                        state = movieTrend.fold(
                            ifLeft = { ListState.Error },
                            ifRight = { content ->
                                val uiList = content.map { it.toUi() }.toImmutableList()
                                ListState.Success(uiList)
                            }
                        )
                    )
                )
            }
        }
    }

    private fun loadTrending(contentParameter: ContentParameter): Job =
        launchWithoutOld(LOAD_POPULAR) {
            getTrendTvUseCase(contentParameter).fold(
                ifLeft = { setState { copy(trend = trend.copy(state = ListState.Error)) } },
                ifRight = { content ->
                    val tv = content.map { it.toUi() }.toImmutableList()
                    setState { copy(trend = trend.copy(state = ListState.Success(tv))) }
                }
            )
        }


    companion object {
        const val LOAD_POPULAR = "load_popular_job"
    }
}