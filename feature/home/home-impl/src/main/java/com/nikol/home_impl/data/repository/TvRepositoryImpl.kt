package com.nikol.home_impl.data.repository

import arrow.core.raise.either
import com.nikol.domainutil.BaseResponseError
import com.nikol.domainutil.ErrorMessage
import com.nikol.home_impl.data.remote.errors.CommonContentError
import com.nikol.home_impl.data.remote.ext.toDomain
import com.nikol.home_impl.data.remote.service.tv.TVService
import com.nikol.home_impl.domain.parameters.ContentParameter
import com.nikol.home_impl.domain.repository.ResponseContent
import com.nikol.home_impl.domain.repository.TvRepository
import com.nikol.ui.model.MediaType

class TvRepositoryImpl(
    private val tvService: TVService
) : TvRepository {
    override suspend fun getTrendTv(contentParameter: ContentParameter): ResponseContent =
        either {
            when (contentParameter.mediaType) {
                MediaType.MOVIE,
                MediaType.TV,
                MediaType.PERSON -> {
                    tvService.getTrendTv(contentParameter).mapLeft { error ->
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