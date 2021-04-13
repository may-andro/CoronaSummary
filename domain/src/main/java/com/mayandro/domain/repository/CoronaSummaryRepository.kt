package com.mayandro.domain.repository

import com.mayandro.local.entity.CountrySummaryEntity
import com.mayandro.local.entity.GlobalSummaryEntity
import com.mayandro.remote.model.CountryStatsResponse
import com.mayandro.remote.model.GlobalSummary
import com.mayandro.remote.model.SummaryResponse
import com.mayandro.utility.network.NetworkStatus

interface CoronaSummaryRepository {
    suspend fun getCoronaSummary(): NetworkStatus<SummaryResponse>

    suspend fun getGlobalCoronaSummary(
        from: String?,
        to: String?
    ): NetworkStatus<List<GlobalSummary>>

    suspend fun getCountryCoronaSummary(
        countrySlug: String,
        from: String?,
        to: String?
    ): NetworkStatus<List<CountryStatsResponse>>

    suspend fun getGlobalSummaryFromDb(): List<GlobalSummaryEntity>

    suspend fun getCountrySummaryListFromDb(): List<CountrySummaryEntity>

    suspend fun insertCountrySummary(countrySummaryEntity: List<CountrySummaryEntity>)

    suspend fun insertGlobalSummary(list: List<GlobalSummaryEntity>)
}