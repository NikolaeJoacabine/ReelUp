package com.nikol.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<INTENT : UiIntent, STATE : UiState, EFFECT : UiEffect, ROUTER : Router> :
    ViewModel() {

    private val _uiState: MutableStateFlow<STATE> by lazy {
        MutableStateFlow(createInitialState())
    }

    val uiState: StateFlow<STATE> by lazy { _uiState.asStateFlow() }


    private val _effect = Channel<EFFECT>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    protected open fun onIntent(intent: INTENT) {
        Log.d("MVI", "Intent: $intent")
    }

    protected open fun onStateChanged(oldState: STATE, newState: STATE) {
        Log.d("MVI", "State change: $oldState -> $newState")
    }

    fun setIntent(intent: INTENT) {
        onIntent(intent)
        viewModelScope.launch { _intent.emit(intent) }
    }

    var router: ROUTER? = null
        private set

    fun setRouter(router: ROUTER?) {
        this.router = router
    }

    protected abstract fun createInitialState(): STATE

    protected fun setState(reducer: STATE.() -> STATE) {
        _uiState.update { old ->
            val new = old.reducer()
            if (old != new) onStateChanged(old, new)
            new
        }
    }

    protected fun setEffect(builder: () -> EFFECT) {
        viewModelScope.launch { _effect.send(builder()) }
    }

    protected open suspend fun onLaunch() {}

    protected open fun onDispose() {
        router = null
    }

    protected abstract fun handleIntents()

    protected inline fun navigate(block: ROUTER.() -> Unit) {
        router?.run(block)
    }

    private val _intent = MutableSharedFlow<INTENT>()


    val intentFlow: SharedFlow<INTENT> = _intent.asSharedFlow()

    init {
        handleIntents()
        viewModelScope.launch { onLaunch() }
    }

    override fun onCleared() {
        onDispose()
        super.onCleared()
    }
}
