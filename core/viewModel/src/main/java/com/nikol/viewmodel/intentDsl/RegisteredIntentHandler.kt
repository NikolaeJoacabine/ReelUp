package com.nikol.viewmodel.intentDsl

import com.nikol.viewmodel.UiIntent
import kotlinx.coroutines.CoroutineScope


fun interface RegisteredIntentHandler<INTENT : UiIntent> {
    suspend fun run(scope: CoroutineScope)
}
