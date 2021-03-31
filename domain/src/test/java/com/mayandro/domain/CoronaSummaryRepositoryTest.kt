package com.mayandro.domain

import com.mayandro.datasource.factory.DataSourceFactory
import com.mayandro.datasource.utils.DataCacheHelper
import com.mayandro.domain.repository.CoronaSummaryRepository
import com.mayandro.domain.repository.CoronaSummaryRepositoryImpl
import com.mayandro.local.LocalDataSource
import com.mayandro.local.entity.CountrySummaryEntity
import com.mayandro.local.entity.GlobalSummaryEntity
import com.mayandro.remote.RemoteDataSource
import com.mayandro.remote.model.GlobalSummary
import com.mayandro.remote.model.SummaryResponse
import com.mayandro.utility.network.NetworkStatus
import io.mockk.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CoronaSummaryRepositoryTest {

    private val dataSourceFactory: DataSourceFactory = mockk()
    private val remoteDataSource: RemoteDataSource = mockk()
    private val localDataSource: LocalDataSource = mockk()

    private lateinit var coronaSummaryRepository: CoronaSummaryRepository

    private val summaryResponse = SummaryResponse(
        countrySummaries = listOf(),
        date = "",
        global = GlobalSummary(
            newConfirmed = 0,
            newDeaths = 0,
            newRecovered = 0,
            totalConfirmed = 0,
            totalDeaths = 0,
            totalRecovered = 0,
            date = ""
        )
    )

    private val globalSummaryEntity = GlobalSummaryEntity(
        newConfirmed = 0,
        newDeaths = 0,
        newRecovered = 0,
        totalConfirmed = 0,
        totalDeaths = 0,
        totalRecovered = 0,
        date = ""
    )

    private val countryListEntity: List<CountrySummaryEntity> = listOf()


    private val networkSummaryResponse = NetworkStatus.Success(summaryResponse)


    @Before
    fun setUp() {
        coronaSummaryRepository = CoronaSummaryRepositoryImpl(
            dataSourceFactory
        )
    }

    @Test
    fun getCoronaSummary() = runBlocking {
        mockkObject(DataCacheHelper)

        val flowResponse = flow { emit(networkSummaryResponse) }

        //STUB calls
        coEvery { remoteDataSource.getSummaryResponse() } returns networkSummaryResponse

        coEvery { localDataSource.getLastGlobalSummary() } returns globalSummaryEntity
        coEvery { localDataSource.getAllCountrySummary() } returns countryListEntity

        coEvery { dataSourceFactory.retrieveLocalDataStore() } returns localDataSource
        coEvery { dataSourceFactory.retrieveRemoteDataStore() } returns remoteDataSource

        //Execute the code
        val getCoronaSummary = coronaSummaryRepository.getCoronaSummary()

        //Verify
        coVerify {
            coronaSummaryRepository.getCoronaSummary()
        }

        assertEquals(getCoronaSummary, flowResponse)
    }
}