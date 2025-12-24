package com.nikol.detail_impl.presentation.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.nikol.detail_api.ContentType
import com.nikol.detail_api.DetailScreen
import com.nikol.detail_impl.presentation.mvi.intent.DetailIntent
import com.nikol.detail_impl.presentation.mvi.state.DetailState
import com.nikol.ui.state.SingleState
import com.nikol.viewmodel.BaseViewModel
import com.nikol.viewmodel.Router
import com.nikol.viewmodel.UiEffect
import com.nikol.viewmodel.intentDsl.intents
import com.nikol.viewmodel.intentDsl.on
import com.nikol.viewmodel.intentDsl.onSingle

interface DetailRouter : Router {
    fun onBack()
    fun toContent(contentType: ContentType, id: Int)
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailIntent, DetailState, UiEffect, DetailRouter>() {
    private val routeArg = savedStateHandle.toRoute<DetailScreen>()

    private val type: ContentType = routeArg.contentType
    private val id: Int = routeArg.id

    override fun createInitialState() = DetailState(
        contentType = type,
        state = SingleState.Loading,
        isLoading = false
    )

    override fun handleIntents() = intents {
        Log.d(
            "TEST_DEBUG",
            "handleIntents called for VM: ${System.identityHashCode(this@DetailViewModel)}"
        )
        onSingle<DetailIntent.NavigateBack> {
            Log.d(
                "TEST_DEBUG",
                "Processing NavigateBack in VM: ${System.identityHashCode(this@DetailViewModel)}"
            )
            navigate { onBack() }
        }

        on<DetailIntent.Update> {
            Log.d("TEST_VIEW_MODEL", "вот он тест")
        }
    }
}