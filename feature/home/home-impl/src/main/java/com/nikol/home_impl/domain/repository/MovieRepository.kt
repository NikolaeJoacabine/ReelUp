package com.nikol.home_impl.domain.repository

import arrow.core.Either
import com.nikol.domainutil.BaseResponseError
import com.nikol.home_impl.domain.model.MediaItem
import com.nikol.home_impl.domain.parameters.ContentParameter

typealias ResponseContent = Either<BaseResponseError, List<MediaItem>>

interface MovieRepository {
    suspend fun getTrendContent(contentParameter: ContentParameter): ResponseContent
    suspend fun getNowPlaying(contentParameter: ContentParameter): ResponseContent
}