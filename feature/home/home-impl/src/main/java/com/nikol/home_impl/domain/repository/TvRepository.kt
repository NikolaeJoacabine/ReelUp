package com.nikol.home_impl.domain.repository

import com.nikol.home_impl.domain.parameters.ContentParameter

interface TvRepository {
    suspend fun getTrendTv(contentParameter: ContentParameter): ResponseContent
}