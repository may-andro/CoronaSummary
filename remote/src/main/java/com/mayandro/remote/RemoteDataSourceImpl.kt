package com.mayandro.remote

import com.mayandro.remote.model.CountryStatsResponse
import com.mayandro.remote.model.GlobalSummary
import com.mayandro.remote.model.SummaryResponse
import com.mayandro.remote.retrofit.RetrofitApi
import com.mayandro.remote.utils.ApiResponseHandler.safeApiCall
import com.mayandro.utility.network.NetworkStatus

class RemoteDataSourceImpl(
    private val retrofit: RetrofitApi
): RemoteDataSource {
    override suspend fun getSummaryResponse(): NetworkStatus<SummaryResponse> {
        return safeApiCall {
            retrofit.getSummary()
        }
    }

    override suspend fun getWorldCoronaSummary(
        from: String?,
        to: String?
    ): NetworkStatus<List<GlobalSummary>> {
        return safeApiCall {
            retrofit.getWorldCoronaSummary(from, to)
        }
    }

    override suspend fun getCountryCoronaSummary(
        countrySlug: String,
        from: String?,
        to: String?
    ): NetworkStatus<List<CountryStatsResponse>> {
        return safeApiCall {
            retrofit.getCountryCoronaSummary(countrySlug, from, to)
        }
    }
}