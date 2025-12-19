package com.nikol.home_impl.data.remote.service.movie

import arrow.core.Either
import com.nikol.home_impl.data.remote.errors.CommonContentError
import com.nikol.home_impl.data.remote.models.TrendingResponse
import com.nikol.home_impl.data.remote.service.rote.Movie
import com.nikol.home_impl.data.remote.service.rote.Trending
import com.nikol.home_impl.domain.parameters.ContentParameter
import com.nikol.home_impl.domain.parameters.Period
import com.nikol.network.extensions.catchKtor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.accept
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters
import java.io.IOException
import java.net.UnknownHostException

interface MovieService {
    suspend fun loadTrendingMovies(contentParameter: ContentParameter): Either<CommonContentError, TrendingResponse>

    suspend fun loadNowPlaying(): Either<CommonContentError, TrendingResponse>
}

class MovieServiceImpl(
    private val httpClient: HttpClient
) : MovieService {
    override suspend fun loadTrendingMovies(contentParameter: ContentParameter) =
        catchKtor<TrendingResponse, CommonContentError>(
            block = {
                val path = Trending.MediaType.TimeWindow(
                    parent = Trending.MediaType(media_type = "movie"),
                    time_window = when (contentParameter.period) {
                        Period.Day -> "day"
                        Period.Week -> "week"
                    }
                )
                httpClient.get(path) {
                    accept(ContentType.Application.Json)
                    parameter("language", "ru-RU")
                }.body()
            },
            errorMapper = { _, status, throwable ->
                if (status != null) {
                    when (status) {
                        HttpStatusCode.Unauthorized -> CommonContentError.InvalidApiKey

                        HttpStatusCode.NotFound,
                        HttpStatusCode.UnprocessableEntity -> CommonContentError.InvalidParams

                        HttpStatusCode.TooManyRequests -> CommonContentError.RateLimitExceeded
                        else -> {
                            if (status.value in 500..599) {
                                CommonContentError.ServerError
                            } else {
                                CommonContentError.Unknown("Http error: $status")
                            }
                        }
                    }
                } else {
                    when (throwable) {
                        is HttpRequestTimeoutException,
                        is ConnectTimeoutException,
                        is SocketTimeoutException,
                        is UnknownHostException -> CommonContentError.NetworkError

                        is IOException -> CommonContentError.NetworkError
                        else -> CommonContentError.Unknown(throwable.message)
                    }
                }
            }
        )

    override suspend fun loadNowPlaying(): Either<CommonContentError, TrendingResponse> =
        catchKtor(
            block = {
                val path = Movie.NowPlaying()
                httpClient.get(path) {
                    accept(ContentType.Application.Json)
                    parameter("language", "ru-RU")
                }.body()
            },
            errorMapper = { _, status, throwable ->
                if (status != null) {
                    when (status) {
                        HttpStatusCode.Unauthorized -> CommonContentError.InvalidApiKey

                        HttpStatusCode.NotFound,
                        HttpStatusCode.UnprocessableEntity -> CommonContentError.InvalidParams

                        HttpStatusCode.TooManyRequests -> CommonContentError.RateLimitExceeded
                        else -> {
                            if (status.value in 500..599) {
                                CommonContentError.ServerError
                            } else {
                                CommonContentError.Unknown("Http error: $status")
                            }
                        }
                    }
                } else {
                    when (throwable) {
                        is HttpRequestTimeoutException,
                        is ConnectTimeoutException,
                        is SocketTimeoutException,
                        is UnknownHostException -> CommonContentError.NetworkError

                        is IOException -> CommonContentError.NetworkError
                        else -> CommonContentError.Unknown(throwable.message)
                    }
                }
            }
        )
}