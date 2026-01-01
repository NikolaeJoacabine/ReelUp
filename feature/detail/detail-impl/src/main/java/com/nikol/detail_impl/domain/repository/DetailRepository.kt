package com.nikol.detail_impl.domain.repository

import arrow.core.Either
import com.nikol.detail_impl.domain.errors.DetailsError
import com.nikol.detail_impl.domain.model.ContentDetailDomain
import com.nikol.detail_impl.domain.parameter.DetailParameter

interface DetailRepository {
    suspend fun getMovie(detailParameter: DetailParameter): Either<DetailsError, ContentDetailDomain>
    suspend fun getTv(detailParameter: DetailParameter): Either<DetailsError, ContentDetailDomain>
    suspend fun getPerson(detailParameter: DetailParameter): Either<DetailsError, ContentDetailDomain>
}