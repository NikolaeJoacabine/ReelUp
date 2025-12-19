package com.nikol.home_impl.domain.useCase

import com.nikol.domainutil.UseCase
import com.nikol.home_impl.domain.parameters.ContentParameter
import com.nikol.home_impl.domain.repository.MovieRepository
import com.nikol.home_impl.domain.repository.ResponseContent
import kotlinx.coroutines.Dispatchers

class GetTrendMoviesUseCase(
    private val movieRepository: MovieRepository
) : UseCase<ContentParameter, ResponseContent>(Dispatchers.IO) {
    override suspend fun run(params: ContentParameter): ResponseContent {
        return movieRepository.getTrendContent(params)
    }
}