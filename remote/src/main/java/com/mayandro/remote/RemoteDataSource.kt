package com.mayandro.remote

import com.mayandro.remote.model.SummaryResponse
import com.mayandro.utility.network.NetworkStatus

interface RemoteDataSource {
    suspend fun getSummaryResponse(): NetworkStatus<SummaryResponse>
}