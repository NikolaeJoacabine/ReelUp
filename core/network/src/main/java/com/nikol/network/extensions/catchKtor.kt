package com.nikol.network.extensions

import arrow.core.Either
import arrow.core.Either.Companion.catch
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

suspend inline fun <A, E> catchKtor(
    crossinline block: suspend () -> A,
    crossinline errorMapper: (ErrorResponse?, HttpStatusCode?, Throwable) -> E
): Either<E, A> =
    catch { block() }.mapLeft { throwable ->
        when (throwable) {
            is ClientRequestException,
            is ServerResponseException -> {
                val errorBody =  throwable.response.body<ErrorResponse>()
                errorMapper(errorBody, throwable.response.status, throwable)
            }
            else -> errorMapper(null, null, throwable)
        }
    }

@Serializable
data class ErrorResponse(
    @SerialName("success")
    val success: Boolean = false,
    @SerialName("status_code")
    val statusCode: Int,
    @SerialName("status_message")
    val statusMessage: String
)
