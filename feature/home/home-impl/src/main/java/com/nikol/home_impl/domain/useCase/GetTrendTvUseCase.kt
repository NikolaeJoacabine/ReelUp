package com.nikol.home_impl.domain.useCase

import com.nikol.domainutil.UseCase
import com.nikol.home_impl.domain.parameters.ContentParameter
import com.nikol.home_impl.domain.repository.ResponseContent
import com.nikol.home_impl.domain.repository.TvRepository
import kotlinx.coroutines.Dispatchers

class GetTrendTvUseCase(
    private val tvRepository: TvRepository
) : UseCase<ContentParameter, ResponseContent>(Dispatchers.IO) {
    override suspend fun run(params: ContentParameter): ResponseContent {
        return tvRepository.getTrendTv(params)
    }
}