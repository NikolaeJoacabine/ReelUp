package com.nikol.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewModel<INTENT : UiIntent, STATE : UiState, EFFECT : UiEffect, ROUTER : Router> :
    ViewModel() {

    private val isInitialized = AtomicBoolean(false)
    private val _uiState: MutableStateFlow<STATE> by lazy {
        MutableStateFlow(createInitialState())
    }

    private fun ensureInitialized() {
        if (isInitialized.compareAndSet(false, true)) {
            Log.d("MVI_DEBUG", "Initializing VM: ${System.identityHashCode(this)}")
            viewModelScope.launch {
                handleIntents()
                onLaunch()
            }
        }
    }

    val uiState: StateFlow<STATE> by lazy {
        ensureInitialized()
        _uiState.asStateFlow()
    }

    private val _effect = Channel<EFFECT>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val _intent = MutableSharedFlow<INTENT>()
    val intentFlow: SharedFlow<INTENT> by lazy {
        ensureInitialized()
        _intent.asSharedFlow()
    }

    private val _isUiVisible = MutableStateFlow(false)
    val isUiVisible = _isUiVisible.asStateFlow()

    fun onUiStart() {
        ensureInitialized()
        _isUiVisible.value = true
    }

    fun onUiStop() {
        _isUiVisible.value = false
    }

    var router: ROUTER? = null
        private set

    fun setRouter(router: ROUTER?) {
        this.router = router
    }

    protected inline fun navigate(block: ROUTER.() -> Unit) {
        router?.run(block)
    }

    /**
     * Точка входа для UI событий
     */
    fun setIntent(intent: INTENT) {
        ensureInitialized()
        onIntent(intent)
        viewModelScope.launch { _intent.emit(intent) }
    }

    /**
     * Обновление стейта. Потокобезопасно.
     */
    protected fun setState(reducer: STATE.() -> STATE) {
        _uiState.update { old ->
            val new = old.reducer()
            if (old != new) onStateChanged(old, new)
            new
        }
    }

    /**
     * Отправка эффекта (One-shot event)
     */
    protected fun setEffect(builder: () -> EFFECT) {
        viewModelScope.launch { _effect.send(builder()) }
    }


    protected abstract fun createInitialState(): STATE

    protected abstract fun handleIntents()

    protected open suspend fun onLaunch() {}

    protected open fun onIntent(intent: INTENT) {
        Log.d("MVI", "Intent: $intent")
    }

    protected open fun onStateChanged(oldState: STATE, newState: STATE) {
        Log.d("MVI", "State change: $oldState -> $newState")
    }

    protected open fun onDispose() {
        router = null
    }

    override fun onCleared() {
        onDispose()
        super.onCleared()
    }
}