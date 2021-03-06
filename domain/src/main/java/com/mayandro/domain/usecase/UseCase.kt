package com.mayandro.domain.usecase

import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

abstract class UseCase<in P, O: Any> {
    abstract suspend fun run(param: P): Flow<NetworkStatus<O>>

    suspend operator fun invoke(
        param: P,
    ): Flow<NetworkStatus<O>> {
        return run(param)
    }
}