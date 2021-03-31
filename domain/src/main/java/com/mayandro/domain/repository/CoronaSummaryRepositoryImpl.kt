package com.mayandro.domain.repository

import com.mayandro.datasource.factory.DataSourceFactory
import com.mayandro.datasource.utils.*
import com.mayandro.remote.model.SummaryResponse
import com.mayandro.utility.dataandtime.getTimeDifference
import com.mayandro.utility.dataandtime.getTimeLong
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.NullPointerException
import java.util.*

class CoronaSummaryRepositoryImpl (
    private val dataSourceFactory: DataSourceFactory
): CoronaSummaryRepository {

    override suspend fun getCoronaSummary(): Flow<NetworkStatus<SummaryResponse>> {
        return DataCacheHelper.getDataAndCacheIt(
            query = {
                querySummaryResponseFromDb()
            },
            shouldFetch = { summaryResponse ->
                val timeDifference = summaryResponse.date.getTimeLong(Locale.getDefault())?.getTimeDifference() ?: 0L
                timeDifference == 0L || timeDifference > 120
            },
            fetch = {
                fetchSummaryResponseFromServer()
            },
            saveFetchResult = {
                insertGlobalSummaryInDb(it.data)
            },clearData = {
                dataSourceFactory.retrieveLocalDataStore().deleteAllCountrySummary()
            },shouldClear = { _, _ ->
                true
            }
        )
    }

    private suspend fun fetchSummaryResponseFromServer(): NetworkStatus<SummaryResponse> {
        return dataSourceFactory.retrieveRemoteDataStore().getSummaryResponse()
    }

    private fun querySummaryResponseFromDb() = flow {
        val lastGlobalSummary = dataSourceFactory.retrieveLocalDataStore().getLastGlobalSummary()
        val allCountrySummary = dataSourceFactory.retrieveLocalDataStore().getAllCountrySummary()

        if(lastGlobalSummary == null) {
            throw NullPointerException()
        } else {
            val summaryResponse = SummaryResponse(
                global = lastGlobalSummary.toGlobalSummary(),
                countrySummaries = allCountrySummary.toCountryList(),
                date = lastGlobalSummary.date
            )

            emit(summaryResponse)
        }
    }

    private suspend fun insertGlobalSummaryInDb(summaryResponse: SummaryResponse?) {
        summaryResponse?.let { data ->
            dataSourceFactory.retrieveLocalDataStore().insertGlobalSummary(data.global.toGlobalSummary())
            dataSourceFactory.retrieveLocalDataStore().insertCountrySummary(data.countrySummaries.toCountrySummaryEntityList())
        }
    }
}