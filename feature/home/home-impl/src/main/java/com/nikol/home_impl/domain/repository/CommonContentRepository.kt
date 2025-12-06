package com.nikol.home_impl.domain.repository

import arrow.core.Either
import com.nikol.domainutil.BaseResponseError
import com.nikol.home_impl.domain.model.Content
import com.nikol.home_impl.domain.parameters.ContentParameter

typealias ResponseContent = Either<BaseResponseError, Content>

interface CommonContentRepository {
    suspend fun getPopularContent(contentParameter: ContentParameter): ResponseContent
    suspend fun getChangedContent(contentParameter: ContentParameter): ResponseContent
}