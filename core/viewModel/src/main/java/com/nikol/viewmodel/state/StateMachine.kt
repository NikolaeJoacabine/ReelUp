package com.nikol.viewmodel.state

class StateMachine<STATE, EVENT>(
    initialState: STATE,
    private val uiBuilder: (STATE) -> Unit,
    buildGraph: GraphBuilder<STATE, EVENT>.() -> Unit
) {

    private var currentState: STATE = initialState
    private val graph: Map<Matcher<STATE, EVENT>, (STATE, EVENT) -> STATE>

    init {
        val builder = GraphBuilder<STATE, EVENT>()
        builder.buildGraph()
        graph = builder.transactions
    }

    fun process(event: EVENT) {
        val transition = graph.entries.find { (matcher, _) ->
            matcher.matches(currentState, event)
        }?.value

        transition?.let {
            val newState = transition(currentState, event)
            if (newState != currentState) {
                currentState = newState
                uiBuilder(newState)
            }
        }?.run {
            println("StateMachine: игнор события $event в состоянии $currentState")
        }

    }

    data class Matcher<S, E>(
        val stateClass: Class<out S>, val eventClass: Class<out E>
    ) {
        fun matches(state: S, event: E) =
            stateClass.isInstance(state) && eventClass.isInstance(event)
    }

    class GraphBuilder<S, E>() {
        val transactions = mutableMapOf<Matcher<S, E>, (S, E) -> S>()

        inline fun <reified STATE_T : S> inState(crossinline block: StateTransitionBuilder<STATE_T>.() -> Unit) {
            StateTransitionBuilder<STATE_T>().apply(block).transitions.forEach { (eventClass, action) ->
                transactions[Matcher(STATE_T::class.java, eventClass)] = { s, e ->
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
}


fun <S : Any, E : Any> stateMachine(
    initial: S,
    bindToUi: (S) -> Unit,
    init: StateMachine.GraphBuilder<S, E>.() -> Unit
) = StateMachine(initial, bindToUi, init)
