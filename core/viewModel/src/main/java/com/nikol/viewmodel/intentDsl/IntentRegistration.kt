package com.nikol.viewmodel.intentDsl

import androidx.lifecycle.viewModelScope
import com.nikol.viewmodel.BaseViewModel
import com.nikol.viewmodel.UiIntent
import kotlinx.coroutines.launch

fun <INTENT : UiIntent> BaseViewModel<INTENT, *, *, *>.intents(
    builder: IntentBuilder<INTENT>.() -> Unit
) {
    val scope = viewModelScope

    val intentBuilder = IntentBuilder(intentFlow).apply(builder)
    val handlers = intentBuilder.handlers
    handlers.forEach { registeredHandler ->
        scope.launch {
            registeredHandler.run(scope)
        }
    }
}
