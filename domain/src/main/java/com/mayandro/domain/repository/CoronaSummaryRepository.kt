package com.mayandro.domain.repository

import com.mayandro.remote.model.SummaryResponse
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface CoronaSummaryRepository {
    suspend fun getCoronaSummary(): Flow<NetworkStatus<SummaryResponse>>
}