package com.mayandro.domain.usecase

import com.mayandro.domain.repository.CoronaSummaryRepository
import com.mayandro.remote.model.CountryStatsResponse
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCountrySummaryUseCase(
    private val coronaSummaryRepository: CoronaSummaryRepository
) {

    data class Param(
        val country: String,
        val from: String?,
        val to: String?
    )

    operator fun invoke(param: Param): Flow<NetworkStatus<List<CountryStatsResponse>>> {
        return flow {
            emit(getCountryCoronaSummary(param))
        }
    }

    private suspend fun getCountryCoronaSummary(param: Param): NetworkStatus<List<CountryStatsResponse>> {
        return coronaSummaryRepository.getCountryCoronaSummary(
            countrySlug = param.country,
            from = param.from,
            to = param.to
        )
    }
}