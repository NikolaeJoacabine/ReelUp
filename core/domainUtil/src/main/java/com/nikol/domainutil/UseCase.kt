package com.nikol.domainutil

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCase<in Params, out Result>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(params: Params): Result = withContext(coroutineDispatcher) {
        run(params)
    }

    protected abstract suspend fun run(params: Params): Result
}