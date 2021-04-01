package com.mayandro.domain.usecase

import com.mayandro.domain.mapper.GlobalSummaryListToDbMapper
import com.mayandro.domain.repository.CoronaSummaryRepository
import com.mayandro.local.entity.GlobalSummaryEntity
import com.mayandro.remote.model.GlobalSummary
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetGlobalSummaryListUseCase(
    private val coronaSummaryRepository: CoronaSummaryRepository,
    private val globalSummaryListToDbMapper: GlobalSummaryListToDbMapper,
) {

    data class Param(
        val from: String?,
        val to: String?
    )

    operator fun invoke(param: Param): Flow<NetworkStatus<List<GlobalSummaryEntity>>> {
        return flow {
            emit(NetworkStatus.Loading<List<GlobalSummaryEntity>>(null))

            val localList = getLocalDbData()

            emit(NetworkStatus.Loading(localList))

            val getFromServer = true

            val flow: Flow<NetworkStatus<List<GlobalSummaryEntity>>>  = if(getFromServer) {
                handleServerResponse(localList, param)
            } else {
                handleLocalSuccess(localList)
            }

            emitAll(flow)
        }
    }

    private fun handleLocalSuccess(localList: List<GlobalSummaryEntity>): Flow<NetworkStatus<List<GlobalSummaryEntity>>> {
        return flow { emit(NetworkStatus.Success(localList)) }
    }

    private fun handleServerResponse(localList: List<GlobalSummaryEntity>, param: Param): Flow<NetworkStatus<List<GlobalSummaryEntity>>> {
        return flow {
            val result = when(val remoteSummaryResponse = getGlobalCoronaSummary(param)) {
                is NetworkStatus.Success -> {
                    handleServerSuccess(remoteSummaryResponse, localList)
                }
                is NetworkStatus.Error -> {
                    NetworkStatus.Error(errorMessage = remoteSummaryResponse.errorMessage, localList)
                }
                else -> {
                    NetworkStatus.Error(errorMessage = "Illegal State", localList)
                }
            }
            emit(result)
        }
    }

    private suspend fun handleServerSuccess(networkSummaryResponse: NetworkStatus<List<GlobalSummary>>, localDbList: List<GlobalSummaryEntity>): NetworkStatus<List<GlobalSummaryEntity>> {
        return if(networkSummaryResponse.data != null) {
            NetworkStatus.Success(insertResponseInDb(networkSummaryResponse.data!!))
        } else {
            NetworkStatus.Error(errorMessage = "No response found from server", localDbList)
        }
    }

    private suspend fun insertResponseInDb(serverList: List<GlobalSummary>): List<GlobalSummaryEntity> {
        val globalSummaryEntityList = globalSummaryListToDbMapper.mapFromOriginalObject(serverList)
        coronaSummaryRepository.insertGlobalSummary(globalSummaryEntityList)
        return globalSummaryEntityList
    }

    private suspend fun getLocalDbData(): List<GlobalSummaryEntity> {
        return coronaSummaryRepository.getGlobalSummaryFromDb()
    }

    private suspend fun getGlobalCoronaSummary(param: Param): NetworkStatus<List<GlobalSummary>> {
        return coronaSummaryRepository.getGlobalCoronaSummary(
            from = param.from,
            to = param.to
        )
    }
}