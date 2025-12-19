package com.nikol.home_impl.data.repository

import arrow.core.raise.either
import com.nikol.domainutil.BaseResponseError
import com.nikol.domainutil.ErrorMessage
import com.nikol.home_impl.data.remote.errors.CommonContentError
import com.nikol.home_impl.data.remote.ext.toDomain
import com.nikol.home_impl.data.remote.service.movie.MovieService
import com.nikol.home_impl.domain.parameters.ContentParameter
import com.nikol.home_impl.domain.repository.MovieRepository
import com.nikol.home_impl.domain.repository.ResponseContent
import com.nikol.ui.model.MediaType

class MovieRepositoryImpl(
    private val movieService: MovieService,
) : MovieRepository {
    override suspend fun getTrendContent(contentParameter: ContentParameter): ResponseContent =
        either {
            when (contentParameter.mediaType) {
                MediaType.MOVIE,
                MediaType.TV,
                MediaType.PERSON -> {
                    movieService.loadTrendingMovies(contentParameter).mapLeft { error ->
                        when (error) {
                            CommonContentError.InvalidApiKey,
                            CommonContentError.InvalidParams,
                            CommonContentError.NetworkError,
                            CommonContentError.RateLimitExceeded,
                            CommonContentError.ServerError,
                            is CommonContentError.Unknown -> BaseResponseError.Error(
                                ErrorMessage(
                                    "error"
                                )
                            )
                        }
                    }
                }
            }.bind().results.map { it.toDomain() }
        }

    override suspend fun getNowPlaying(contentParameter: ContentParameter): ResponseContent =
        either {
            when (contentParameter.mediaType) {
                MediaType.MOVIE,
                MediaType.TV,
                MediaType.PERSON -> {
                    movieService.loadNowPlaying().mapLeft { error ->
                        when (error) {
                            CommonContentError.InvalidApiKey,
                            CommonContentError.InvalidParams,
                            CommonContentError.NetworkError,
                            CommonContentError.RateLimitExceeded,
                            CommonContentError.ServerError,
                            is CommonContentError.Unknown -> BaseResponseError.Error(
                                ErrorMessage(
                                    "error"
                                )
                            )
                        }
                    }
                }
            }.bind().results.map { it.toDomain() }
        }
}