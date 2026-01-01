package com.nikol.detail_impl.domain.use_case

import arrow.core.Either
import com.nikol.detail_api.ContentType
import com.nikol.detail_impl.domain.errors.DetailsError
import com.nikol.detail_impl.domain.model.ContentDetailDomain
import com.nikol.detail_impl.domain.parameter.DetailParameter
import com.nikol.detail_impl.domain.repository.DetailRepository
import com.nikol.domainutil.UseCase
import kotlinx.coroutines.Dispatchers

class GetDetailInfoUseCase(
    private val getDetailRepository: DetailRepository
) : UseCase<DetailParameter, Either<DetailsError, ContentDetailDomain>>(Dispatchers.IO) {
    override suspend fun run(params: DetailParameter): Either<DetailsError, ContentDetailDomain> {
        return when (params.contentType) {
            ContentType.MOVIE -> getDetailRepository.getMovie(params)
            ContentType.TV -> getDetailRepository.getTv(params)
            ContentType.PERSON -> throw IllegalArgumentException()
        }
    }
}