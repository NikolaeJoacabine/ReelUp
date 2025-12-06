package com.nikol.home_impl.domain.useCase

import com.nikol.domainutil.UseCase
import com.nikol.home_impl.domain.parameters.ContentParameter
import com.nikol.home_impl.domain.repository.CommonContentRepository
import com.nikol.home_impl.domain.repository.ResponseContent
import kotlinx.coroutines.Dispatchers

class GetPopularUseCase(
    //private val commonContentRepository: CommonContentRepository
) : UseCase<ContentParameter, ResponseContent>(Dispatchers.IO) {
    override suspend fun run(params: ContentParameter): ResponseContent {
        TODO("Not yet implemented")
    }
}