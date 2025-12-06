package com.nikol.viewmodel.intentDsl

import com.nikol.viewmodel.UiIntent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.time.Duration

@IntentDslMarker
open class IntentHandlerBuilder<I>() {

    private var transforms: (Flow<I>) -> Flow<I> = { it }
    private var handler: (suspend (I) -> Unit)? = null
    private var catchAll: ((e: Exception) -> Unit) = { e -> throw e }
    private var collector: suspend (Flow<I>, suspend (I) -> Unit, CoroutineScope) -> Unit =
        { flow, handler, _ ->
            flow.collect { intent -> handler(intent) }
        }

    private fun setHandler(
        block: suspend (I) -> Unit,
        collect: (suspend (Flow<I>, suspend (I) -> Unit, CoroutineScope) -> Unit)? = null
    ) {
        handler?.let { throw IllegalStateException("`handle` может быть вызван только один раз для одного типа интента.") }
        handler = block
        collect?.let {
            collector = it
        }
    }

    fun handleConsistently(block: suspend (I) -> Unit) =
        setHandler(block)

    fun handleConcurrent(block: suspend (I) -> Unit) =
        setHandler(block) { f, h, s ->
            f.collect { intent ->
                s.launch { h(intent) }
            }
        }

    fun handleLatest(block: suspend (I) -> Unit) =
        setHandler(block) { f, h, s ->
            var currentJob: Job? = null
            f.collect { intent ->
                currentJob?.cancel()
                currentJob = s.launch { h(intent) }
            }
        }

    fun handleDropWhileBusy(block: suspend (I) -> Unit) =
        setHandler(block) { f, h, s ->
            var currentJob: Job? = null
            f.collect { intent ->
                if (currentJob?.isActive != true) {
                    currentJob = s.launch {
                        h(intent)
                    }
                }
            }
        }

    fun catch(errorHandler: (e: Exception) -> Unit) {
        catchAll = errorHandler
    }

    fun transform(operator: (Flow<I>) -> Flow<I>) {
        val old = transforms
        transforms = { flow -> operator(old(flow)) }
    }

    fun build(upstream: Flow<I>): RegisteredIntentHandler<I> {
        val finalHandler = handler
            ?: throw IllegalStateException("call handle{} before build()")

        val transformed = transforms(upstream)
        val safeFlow = transformed.catch { e -> catchAll(e as Exception) }

        return RegisteredIntentHandler { scope ->
            val safeHandler: suspend (I) -> Unit = { intent ->
                try {
                    finalHandler(intent)
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    catchAll(e)
                }
            }

            collector(safeFlow, safeHandler, scope)
        }
    }
}


@OptIn(FlowPreview::class)
fun <I : UiIntent> IntentHandlerBuilder<I>.debounce(timeMillis: Long) =
    transform { it.debounce(timeMillis) }

@OptIn(FlowPreview::class)
fun <I : UiIntent> IntentHandlerBuilder<I>.debounce(duration: Duration) =
    transform { it.debounce(duration) }

inline fun <I : UiIntent> IntentHandlerBuilder<I>.filter(
    crossinline predicate: suspend (I) -> Boolean
) = transform { flow -> flow.filter(predicate) }

fun <I : UiIntent> IntentHandlerBuilder<I>.distinct() =
    transform { flow -> flow.distinctUntilChanged() }

