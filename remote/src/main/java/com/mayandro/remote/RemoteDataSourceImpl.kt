package com.mayandro.remote

import com.mayandro.remote.model.SummaryResponse
import com.mayandro.remote.retrofit.RetrofitApi
import com.mayandro.remote.utils.safeApiCall
import com.mayandro.utility.network.NetworkStatus

class RemoteDataSourceImpl(
    private val retrofit: RetrofitApi
): RemoteDataSource {
    override suspend fun getSummaryResponse(): NetworkStatus<SummaryResponse> {
        return safeApiCall {
            retrofit.getSummary()
        }
    }
}