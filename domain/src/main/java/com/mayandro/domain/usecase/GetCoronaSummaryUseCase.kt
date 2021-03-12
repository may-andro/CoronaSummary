package com.mayandro.domain.usecase

import com.mayandro.domain.repository.CoronaSummaryRepository
import com.mayandro.remote.model.SummaryResponse
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.flow.Flow

class GetCoronaSummaryUseCase(
    private val coronaSummaryRepository: CoronaSummaryRepository
): UseCase<Any, SummaryResponse>() {

    override suspend fun run(param: Any): Flow<NetworkStatus<SummaryResponse>> {
        return coronaSummaryRepository.getCoronaSummary()
    }
}