package com.nikol.viewmodel.intentDsl

import com.nikol.viewmodel.UiIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance

@IntentDslMarker
class IntentBuilder<INTENT : UiIntent>(
    val upstream: Flow<INTENT>
) {
    val handlers = mutableListOf<RegisteredIntentHandler<*>>()

    inline fun <reified I : INTENT> setup(
        configure: IntentHandlerBuilder<I>.() -> Unit
    ) {
        val builder = IntentHandlerBuilder<I>().apply(configure)
        val intentFlow = upstream.filterIsInstance<I>()
        val registered = builder.build(intentFlow)

        handlers.add(registered)
    }

    inline fun <reified T> setupListener(
        source: Flow<T>,
        configure: IntentHandlerBuilder<T>.() -> Unit
    ) {
        val builder = IntentHandlerBuilder<T>().apply(configure)
        val registered = builder.build(source)

        handlers.add(registered)
    }
}

inline fun <reified I : UiIntent> IntentBuilder<in I>.on(
    noinline block: suspend (I) -> Unit
) = setup<I> { handleConsistently(block) }

inline fun <reified I : UiIntent> IntentBuilder<in I>.onLatest(
    noinline block: suspend (I) -> Unit
) = setup<I> { handleLatest(block) }

inline fun <reified I : UiIntent> IntentBuilder<in I>.onParallel(
    noinline block: suspend (I) -> Unit
) = setup<I> { handleConcurrent(block) }

inline fun <reified I : UiIntent> IntentBuilder<in I>.onSingle(
    noinline block: suspend (I) -> Unit
) = setup<I> { handleDropWhileBusy(block) }

inline fun <reified T> IntentBuilder<*>.listen(
    source: Flow<T>,
    noinline block: suspend (T) -> Unit
) = setupListener(source) { handleConsistently(block) }

inline fun <reified T> IntentBuilder<*>.listenLatest(
    source: Flow<T>,
    noinline block: suspend (T) -> Unit
) = setupListener(source) { handleLatest(block) }

inline fun <reified T> IntentBuilder<*>.listenParallel(
    source: Flow<T>,
    noinline block: suspend (T) -> Unit
) = setupListener(source) { handleConcurrent(block) }

inline fun <reified T> IntentBuilder<*>.listenSingle(
    source: Flow<T>,
    noinline block: suspend (T) -> Unit
) = setupListener(source) { handleDropWhileBusy(block) }