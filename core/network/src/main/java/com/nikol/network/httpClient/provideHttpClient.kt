package com.nikol.network.httpClient

import com.nikol.network.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import kotlin.math.pow
import kotlin.time.Duration.Companion.seconds

internal fun provideHttpClient() =
    HttpClient(OkHttp) {

        engine {

            config {
                retryOnConnectionFailure(true)
                followRedirects(true)
                connectTimeout(10.seconds)
                callTimeout(15.seconds)
                readTimeout(15.seconds)
            }
        }

        install(plugin = ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
                isLenient = true
                explicitNulls = false
                prettyPrint = true
            })
        }

        install(Resources)

        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }

        install(HttpRequestRetry) {
            maxRetries = 3
            retryIf { _, response ->
                response.status.value in 500..599
            }
            retryOnExceptionIf { _, cause -> cause is IOException || cause is TimeoutCancellationException }


            delayMillis { retry ->
                1000L * 2.0.pow(retry.toDouble()).toLong()
            }
        }

        defaultRequest {
            url("https://api.themoviedb.org/3/")
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
        }
        expectSuccess = true
    }
