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
    private val summaryResponse = SummaryResponse(
        countries = listOf(),
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

    private val networkSummaryResponse = NetworkStatus.Success(summaryResponse)

    @Test
    fun getSummaryResponse() {
        val remoteDataSource = mockk<RemoteDataSourceImpl>()
        //STUB calls
        coEvery { remoteDataSource.getSummaryResponse() } returns networkSummaryResponse

        //Execute the code
        val result = runBlocking { remoteDataSource.getSummaryResponse() }

        //Verify
        coVerify { remoteDataSource.getSummaryResponse() }

        assertEquals(networkSummaryResponse, result)
    }

    @Test
    fun testSafeApiCallSuccessfull() {
        mockkObject(ApiResponseHandler)

        val apiRequest = mockk<Response<SummaryResponse>>()

        val expectedResponse =  NetworkStatus.Success(summaryResponse)

        //STUB calls
        every { apiRequest.body() } returns summaryResponse
        every { apiRequest.isSuccessful } returns true
        coEvery { ApiResponseHandler.safeApiCall{ apiRequest } } returns expectedResponse

        val result = runBlocking { ApiResponseHandler.safeApiCall{ apiRequest } }

        assertEquals(expectedResponse.data, result.data)
    }

    @Test
    fun testSafeApiCallFailed() {
        mockkObject(ApiResponseHandler)

        val apiRequest = mockk<Response<SummaryResponse>>()

        val expectedResponse =  NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION, data = summaryResponse)

        //STUB calls
        every { apiRequest.isSuccessful } returns false
        coEvery { ApiResponseHandler.safeApiCall{ apiRequest } } returns expectedResponse

        //Execute the code
        val result = runBlocking { ApiResponseHandler.safeApiCall{ apiRequest } }

        //Verify
        assertEquals(expectedResponse.errorMessage, result.errorMessage)
    }
}