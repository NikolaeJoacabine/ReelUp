package com.nikol.viewmodel.intentDsl

import com.nikol.viewmodel.UiIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance

@IntentDslMarker
class IntentBuilder<INTENT : UiIntent>(
    val upstream: Flow<INTENT>
) {
    val handlers = mutableListOf<RegisteredIntentHandler<INTENT>>()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified I : INTENT> on(
        configure: IntentHandlerBuilder<I>.() -> Unit
    ) {
        val builder = IntentHandlerBuilder<I>().apply(configure)
        val intentFlow = upstream.filterIsInstance<I>()
        val registered = builder.build(intentFlow)

        handlers.add(
            registered as RegisteredIntentHandler<INTENT>
        )
    }
}