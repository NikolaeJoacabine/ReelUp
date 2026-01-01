package com.nikol.detail_impl.data.remote.ext

import com.nikol.security.model.SessionState
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter

internal fun HttpRequestBuilder.sessionQueryParameter(sessionState: SessionState) {
    when (sessionState) {
        is SessionState.Guest -> parameter("session_id", sessionState.sessionId)
        is SessionState.User -> parameter("guest_session_id", sessionState.sessionId)
        is SessionState.None -> Unit
    }
}