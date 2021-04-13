package com.mayandro.domain.usecase

import com.mayandro.domain.mapper.CountrySummaryListToDbMapper
import com.mayandro.domain.repository.CoronaSummaryRepository
import com.mayandro.local.entity.CountrySummaryEntity
import com.mayandro.remote.model.CountrySummary
import com.mayandro.remote.model.SummaryResponse
import com.mayandro.utility.dataandtime.getTimeDifference
import com.mayandro.utility.dataandtime.getTimeLong
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.flow.*
import java.util.*

class GetCoronaSummaryUseCase(
    private val coronaSummaryRepository: CoronaSummaryRepository,
    private val countrySummaryListToDbMapper: CountrySummaryListToDbMapper
) {

    operator fun invoke(): Flow<NetworkStatus<List<CountrySummaryEntity>>> {
        return flow{
            emit(NetworkStatus.Loading<List<CountrySummaryEntity>>(null))

            //Get DB Data
            val localCountrySummaryList = getLocalDbData()

            //emit(NetworkStatus.Loading(localCountrySummaryList))

            val getFromServer = shouldFetchFromServer(localCountrySummaryList)

            val flow: Flow<NetworkStatus<List<CountrySummaryEntity>>>  = if(getFromServer) {
                handleServerResponse(localCountrySummaryList)
            } else {
                handleLocalSuccess(localCountrySummaryList)
            }

            emitAll(flow)
        }
    }

    private suspend fun getLocalDbData(): List<CountrySummaryEntity> {
        return coronaSummaryRepository.getCountrySummaryListFromDb()
    }

    private fun shouldFetchFromServer(dbList: List<CountrySummaryEntity>): Boolean {
        if(dbList.isEmpty()) return true

        val timeDifference = dbList.first().date.getTimeLong()?.getTimeDifference() ?: 0L
        return timeDifference == 0L || timeDifference > 120
    }

    private suspend fun handleLocalSuccess(localDbList: List<CountrySummaryEntity>): Flow<NetworkStatus<List<CountrySummaryEntity>>> {
        return flow { emit(NetworkStatus.Success(localDbList)) }
    }

    private suspend fun handleServerResponse(localDbList: List<CountrySummaryEntity>): Flow<NetworkStatus<List<CountrySummaryEntity>>> {
        return flow {
            val result = when(val remoteSummaryResponse = coronaSummaryRepository.getCoronaSummary()) {
                is NetworkStatus.Success -> {
                    handleServerSuccess(remoteSummaryResponse, localDbList)
                }
                is NetworkStatus.Error -> {
                    NetworkStatus.Error(errorMessage = remoteSummaryResponse.errorMessage, localDbList)
                }
                else -> {
                    NetworkStatus.Error(errorMessage = "Illegal State", localDbList)
                }
            }
            emit(result)
        }
    }

    private suspend fun handleServerSuccess(networkSummaryResponse: NetworkStatus<SummaryResponse>, localDbList: List<CountrySummaryEntity>): NetworkStatus<List<CountrySummaryEntity>> {
        return if(networkSummaryResponse.data != null) {
            NetworkStatus.Success(insertResponseInDb(networkSummaryResponse.data!!.countrySummaries))
        } else {
            NetworkStatus.Error(errorMessage = "No response found from server", localDbList)
        }
    }

    private suspend fun insertResponseInDb(serverList: List<CountrySummary>): List<CountrySummaryEntity> {
        val countrySummaryEntityList = countrySummaryListToDbMapper.mapFromOriginalObject(serverList)
        coronaSummaryRepository.insertCountrySummary(countrySummaryEntityList)
        return countrySummaryEntityList
    }
}