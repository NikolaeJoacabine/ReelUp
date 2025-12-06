package com.nikol.viewmodel.state

class StateMachine<STATE : Any, EVENT : Any>(
    initialState: STATE,
    private val uiBinder: (STATE) -> Unit,
    private val graph: Map<Matcher<STATE, EVENT>, (STATE, EVENT) -> STATE>
) {
    private var currentState: STATE = initialState

    fun process(event: EVENT) {
        val transition = graph.entries.find { (matcher, _) ->
            matcher.matches(currentState, event)
        }?.value

        if (transition != null) {
            val newState = transition(currentState, event)
            if (newState != currentState) {
                currentState = newState
                uiBinder(newState)
            }
        }
    }
}