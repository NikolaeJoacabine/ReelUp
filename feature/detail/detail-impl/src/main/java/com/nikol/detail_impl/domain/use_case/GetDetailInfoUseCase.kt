package com.nikol.detail_impl.domain.use_case

import com.nikol.domainutil.UseCase
import kotlinx.coroutines.Dispatchers

class GetDetailInfoUseCase : UseCase<Unit, Unit>(Dispatchers.IO) {
    override suspend fun run(params: Unit) {
        TODO("Not yet implemented")
    }
}