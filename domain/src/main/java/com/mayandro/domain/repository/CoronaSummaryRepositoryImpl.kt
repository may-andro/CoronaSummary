package com.mayandro.domain.repository

import com.mayandro.datasource.factory.DataSourceFactory
import com.mayandro.local.entity.CountrySummaryEntity
import com.mayandro.local.entity.GlobalSummaryEntity
import com.mayandro.remote.model.CountryStatsResponse
import com.mayandro.remote.model.GlobalSummary
import com.mayandro.remote.model.SummaryResponse
import com.mayandro.utility.network.NetworkStatus

class CoronaSummaryRepositoryImpl (
    private val dataSourceFactory: DataSourceFactory
): CoronaSummaryRepository {

    override suspend fun getCoronaSummary(): NetworkStatus<SummaryResponse> {
        return dataSourceFactory.retrieveRemoteDataStore().getSummaryResponse()
    }

    override suspend fun getGlobalCoronaSummary(
        from: String?,
        to: String?
    ): NetworkStatus<List<GlobalSummary>> {
        return dataSourceFactory.retrieveRemoteDataStore().getWorldCoronaSummary(from, to)
    }

    override suspend fun getCountryCoronaSummary(
        countrySlug: String,
        from: String?,
        to: String?
    ): NetworkStatus<List<CountryStatsResponse>> {
        return dataSourceFactory.retrieveRemoteDataStore().getCountryCoronaSummary(countrySlug, from, to)
    }

    override suspend fun getGlobalSummaryFromDb():  List<GlobalSummaryEntity> {
        return dataSourceFactory.retrieveLocalDataStore().getAllGlobalSummary()
    }

    override suspend fun getCountrySummaryListFromDb(): List<CountrySummaryEntity> {
        return dataSourceFactory.retrieveLocalDataStore().getAllCountrySummary()
    }

    override suspend fun insertCountrySummary(countrySummaryEntity: List<CountrySummaryEntity>) {
        dataSourceFactory.retrieveLocalDataStore().insertCountrySummary(countrySummaryEntity)
    }

    override suspend fun insertGlobalSummary(list: List<GlobalSummaryEntity>) {
        dataSourceFactory.retrieveLocalDataStore().insertGlobalSummary(list)
    }

}