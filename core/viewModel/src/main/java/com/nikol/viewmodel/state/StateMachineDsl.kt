package com.nikol.viewmodel.state

data class Matcher<S, E>(
    val stateClass: Class<out S>,
    val eventClass: Class<out E>
) {
    fun matches(state: S, event: E) =
        stateClass.isInstance(state) && eventClass.isInstance(event)
}

class GraphBuilder<S : Any, E : Any> {
    val transitions = mutableMapOf<Matcher<S, E>, (S, E) -> S>()

    inline fun <reified STATE_T : S> inState(block: StateTransitionBuilder<STATE_T>.() -> Unit) {
        StateTransitionBuilder<STATE_T>().apply(block).transitions.forEach { (eventClass, action) ->
            transitions[Matcher(STATE_T::class.java, eventClass)] = { s, e ->
                action(s as STATE_T, e)
            }
        }
    }

    inner class StateTransitionBuilder<FROM : S> {
        val transitions = mutableMapOf<Class<out E>, (FROM, E) -> S>()

        inline fun <reified EVENT_T : E> on(crossinline block: (EVENT_T) -> S) {
            transitions[EVENT_T::class.java] = { s, e -> block(e as EVENT_T) }
        }

        inline fun <reified EVENT_T : E> goto(newState: S) {
            on<EVENT_T> { newState }
        }
    }
}

fun <S : Any, E : Any> stateMachine(
    initial: S,
    bindToUi: (S) -> Unit,
    init: GraphBuilder<S, E>.() -> Unit
): StateMachine<S, E> {
    val builder = GraphBuilder<S, E>()
    builder.init()
    return StateMachine(initial, bindToUi, builder.transitions)
}
