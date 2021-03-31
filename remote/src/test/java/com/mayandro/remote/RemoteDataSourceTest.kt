package com.mayandro.remote

import com.mayandro.remote.model.GlobalSummary
import com.mayandro.remote.model.SummaryResponse
import com.mayandro.remote.utils.ApiResponseHandler
import com.mayandro.remote.utils.UNKNOWN_NETWORK_EXCEPTION
import com.mayandro.utility.network.NetworkStatus
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class RemoteDataSourceTest {

    private val remoteDataSource = mockk<RemoteDataSourceImpl>()

    @Test
    fun remoteDataSource_getSummaryResponse_resultSuccess() {
        val summaryResponse = SummaryResponse(
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

        val networkSummaryResponse = NetworkStatus.Success(summaryResponse)

        //STUB calls
        coEvery { remoteDataSource.getSummaryResponse() } returns networkSummaryResponse

        //Execute the code
        val result = runBlocking { remoteDataSource.getSummaryResponse() }

        //Verify
        coVerify { remoteDataSource.getSummaryResponse() }

        assertEquals(networkSummaryResponse, result)
    }

    @Test
    fun remoteDataSource_getSummaryResponse_resultFailed() {
        val summaryResponse = SummaryResponse(
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

        val networkSummaryResponse = NetworkStatus.Error(errorMessage = UNKNOWN_NETWORK_EXCEPTION, summaryResponse)

        //STUB calls
        coEvery { remoteDataSource.getSummaryResponse() } returns networkSummaryResponse

        //Execute the code
        val result = runBlocking { remoteDataSource.getSummaryResponse() }

        //Verify
        coVerify { remoteDataSource.getSummaryResponse() }

        assertEquals(networkSummaryResponse, result)
    }
}