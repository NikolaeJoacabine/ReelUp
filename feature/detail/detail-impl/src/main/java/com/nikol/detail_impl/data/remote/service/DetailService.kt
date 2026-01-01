package com.nikol.detail_impl.data.remote.service

import arrow.core.Either
import com.nikol.detail_impl.data.remote.model.MovieDetailDTO
import com.nikol.detail_impl.data.remote.model.TvDetailDTO
import com.nikol.detail_impl.domain.errors.DetailsError
import com.nikol.security.model.SessionState

interface DetailService {
    suspend fun getDetailAboutMovie(
        id: Int,
        sessionState: SessionState
    ): Either<DetailsError, MovieDetailDTO>

    suspend fun getDetailAboutTv(
        id: Int,
        sessionState: SessionState
    ): Either<DetailsError, TvDetailDTO>

    suspend fun getDetailAboutPerson(
        id: Int, sessionState: SessionState
    )
}