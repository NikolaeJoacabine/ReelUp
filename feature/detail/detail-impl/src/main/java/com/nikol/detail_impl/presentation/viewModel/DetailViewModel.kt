package com.nikol.detail_impl.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.nikol.detail_api.ContentType
import com.nikol.detail_api.DetailScreen
import com.nikol.detail_impl.domain.errors.DetailsError
import com.nikol.detail_impl.domain.parameter.DetailParameter
import com.nikol.detail_impl.domain.use_case.GetDetailInfoUseCase
import com.nikol.detail_impl.presentation.mvi.effect.DetailEffect
import com.nikol.detail_impl.presentation.mvi.intent.DetailIntent
import com.nikol.detail_impl.presentation.mvi.state.DetailState
import com.nikol.detail_impl.presentation.ui.ext.toUi
import com.nikol.detail_impl.presentation.ui.model.DetailContent
import com.nikol.ui.state.SingleState
import com.nikol.viewmodel.BaseViewModel
import com.nikol.viewmodel.Router
import com.nikol.viewmodel.intentDsl.filter
import com.nikol.viewmodel.intentDsl.intents
import com.nikol.viewmodel.intentDsl.on
import com.nikol.viewmodel.intentDsl.onLatest
import com.nikol.viewmodel.intentDsl.onSingle

interface DetailRouter : Router {
    fun onBack()
    fun toContent(detailScreen: DetailScreen)
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle, private val getDetailInfoUseCase: GetDetailInfoUseCase
) : BaseViewModel<DetailIntent, DetailState, DetailEffect, DetailRouter>() {
    private val routeArg = savedStateHandle.toRoute<DetailScreen>()

    private val type: ContentType = routeArg.contentType
    private val id: Int = routeArg.id

    init {
        setIntent(DetailIntent.LoadAllData(type))
    }

    override fun createInitialState() = DetailState(
        contentType = type,
        state = SingleState.Loading,
        isLoading = false,
        showBottomSheet = false
    )

    override fun handleIntents() = intents {
        onSingle<DetailIntent.NavigateBack> {
            navigate { onBack() }
        }

        onLatest<DetailIntent.LoadAllData> { intent ->
            getDetailInfoUseCase(
                params = DetailParameter(contentType = intent.contentType, id = id)
            ).fold(ifLeft = { error ->
                when (error) {
                    DetailsError.Network,
                    DetailsError.NotFound,
                    DetailsError.ServerError,
                    is DetailsError.Unknown -> setState { copy(state = SingleState.Error) }

                    DetailsError.UserNotAuth -> TODO()
                }
            }, ifRight = {
                val model = it.toUi()
                setState { copy(state = SingleState.Success(model)) }
            })
        }

        onLatest<DetailIntent.NavigateOtherDetail> {
            navigate { toContent(it.contentType) }
        }

        setup<DetailIntent.ToggleDescription> {
            filter { uiState.value.state is SingleState.Success }
            handleConsistently {
                val state = uiState.value.state as SingleState.Success<DetailContent>
                setState {
                    copy(
                        state = SingleState.Success(
                            content = state.content.copy(
                                showAllDescription = !state.content.showAllDescription
                            )
                        )
                    )
                }
            }
        }
        on<DetailIntent.Update> {
            setState { copy(isLoading = true) }
            getDetailInfoUseCase(
                params = DetailParameter(contentType = type, id = id)
            ).fold(ifLeft = {
                setState { copy(state = SingleState.Error) }
            }, ifRight = {
                val model = it.toUi()
                setState { copy(state = SingleState.Success(model), isLoading = false) }
            }
            )
        }

        setup<DetailIntent.ToggleAction> {
            filter { uiState.value.state is SingleState.Success }
            handleConsistently {
                setState { copy(showBottomSheet = !showBottomSheet) }
            }
        }

        setup<DetailIntent.SeeTriller> {
            filter { uiState.value.state is SingleState.Success }
            handleConsistently {
                val content = (uiState.value.state as SingleState.Success).content
                content.trailers.firstOrNull()?.let { video ->
                    setEffect { DetailEffect.SeeTrailerOnYouTube(video.key) }
                }
            }
        }
    }
}