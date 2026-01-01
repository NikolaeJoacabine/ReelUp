package com.nikol.detail_impl.data.remote.service

import arrow.core.Either
import com.nikol.detail_impl.data.remote.ext.sessionQueryParameter
import com.nikol.detail_impl.data.remote.model.MovieDetailDTO
import com.nikol.detail_impl.data.remote.model.TvDetailDTO
import com.nikol.detail_impl.data.remote.service.route.Movie
import com.nikol.detail_impl.data.remote.service.route.Tv
import com.nikol.detail_impl.domain.errors.DetailsError
import com.nikol.network.extensions.catchKtor
import com.nikol.security.model.SessionState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class DetailServiceImpl(
    private val httpClient: HttpClient
) : DetailService {
    override suspend fun getDetailAboutMovie(
        id: Int,
        sessionState: SessionState
    ): Either<DetailsError, MovieDetailDTO> = catchKtor(
        block = {
            httpClient.get(Movie.Id(id = id)) {
                sessionQueryParameter(sessionState)
                parameter("language", "ru-Ru")
            }.body()
        },
        errorMapper = { response, status, throwable ->
            if (status != null) {
                when (status) {
                    HttpStatusCode.NotFound -> DetailsError.NotFound

                    HttpStatusCode.Unauthorized,
                    HttpStatusCode.Forbidden,
                    HttpStatusCode.InternalServerError,
                    HttpStatusCode.BadGateway,
                    HttpStatusCode.ServiceUnavailable -> DetailsError.ServerError

                    else -> DetailsError.Unknown("HTTP ${status.value}: ${response?.statusMessage}")
                }
            } else {
                when (throwable) {
                    is HttpRequestTimeoutException,
                    is ConnectTimeoutException,
                    is SocketTimeoutException,
                    is UnknownHostException,
                    is IOException -> DetailsError.Network

                    else -> DetailsError.Unknown(throwable.message)
                }
            }

        }
    )

    override suspend fun getDetailAboutTv(
        id: Int,
        sessionState: SessionState
    ): Either<DetailsError, TvDetailDTO> = catchKtor(
        block = {
            httpClient.get(Tv.Id(id = id)) {
                sessionQueryParameter(sessionState)
                parameter("language", "ru-Ru")
            }.body()
        },
        errorMapper = { response, status, throwable ->
            if (status != null) {
                when (status) {
                    HttpStatusCode.NotFound -> DetailsError.NotFound

                    HttpStatusCode.Unauthorized,
                    HttpStatusCode.Forbidden,
                    HttpStatusCode.InternalServerError,
                    HttpStatusCode.BadGateway,
                    HttpStatusCode.ServiceUnavailable -> DetailsError.ServerError

                    else -> DetailsError.Unknown("HTTP ${status.value}: ${response?.statusMessage}")
                }
            } else {
                when (throwable) {
                    is HttpRequestTimeoutException,
                    is ConnectTimeoutException,
                    is SocketTimeoutException,
                    is UnknownHostException,
                    is IOException -> DetailsError.Network

                    else -> DetailsError.Unknown(throwable.message)
                }
            }

        }
    )

    override suspend fun getDetailAboutPerson(
        id: Int,
        sessionState: SessionState
    ) {
        TODO("Not yet implemented")
    }
}


