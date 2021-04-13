package com.mayandro.remote

import com.mayandro.remote.model.CountryStatsResponse
import com.mayandro.remote.model.GlobalSummary
import com.mayandro.remote.model.SummaryResponse
import com.mayandro.utility.network.NetworkStatus

interface RemoteDataSource {
    suspend fun getSummaryResponse(): NetworkStatus<SummaryResponse>

    suspend fun getWorldCoronaSummary(
        from: String?,
        to: String?
    ): NetworkStatus<List<GlobalSummary>>

    suspend fun getCountryCoronaSummary(
        countrySlug: String,
        from: String?,
        to: String?
    ): NetworkStatus<List<CountryStatsResponse>>
}