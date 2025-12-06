package com.nikol.viewmodel.intentDsl

import com.nikol.viewmodel.UiIntent
import kotlinx.coroutines.CoroutineScope


fun interface RegisteredIntentHandler<INTENT> {
    suspend fun run(scope: CoroutineScope)
}
