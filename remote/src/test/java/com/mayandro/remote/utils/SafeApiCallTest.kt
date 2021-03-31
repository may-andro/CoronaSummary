package com.mayandro.remote.utils

import com.mayandro.remote.model.GlobalSummary
import com.mayandro.remote.model.SummaryResponse
import com.mayandro.utility.network.NetworkStatus
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class SafeApiCallTest {


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

    private val networkSummaryResponse = NetworkStatus.Success(summaryResponse)

    private val apiRequest: Response<SummaryResponse> = mockk()

    @Before
    fun setUp() {
        mockkObject(ApiResponseHandler)
    }

    @Test
    fun apiResponseHandler_safeApiCall_resultSuccessful() {
        val expectedResponse =  NetworkStatus.Success(summaryResponse)

        //STUB calls
        every { apiRequest.body() } returns summaryResponse
        every { apiRequest.isSuccessful } returns true
        coEvery { ApiResponseHandler.safeApiCall{ apiRequest } } returns expectedResponse

        val result = runBlocking { ApiResponseHandler.safeApiCall{ apiRequest } }

        Assert.assertEquals(expectedResponse.data, result.data)
    }

    @Test
    fun apiResponseHandler_safeApiCall_resultRequestFailed() {
        val expectedResponse =  NetworkStatus.Error(errorMessage = "Is not successful", data = summaryResponse)

        //STUB calls
        every { apiRequest.isSuccessful } returns false
        every { apiRequest.message() } returns "Is not successful"
        coEvery { ApiResponseHandler.safeApiCall{ apiRequest } } returns expectedResponse

        //Execute the code
        val result = runBlocking { ApiResponseHandler.safeApiCall{ apiRequest } }

        //Verify
        Assert.assertEquals(expectedResponse.errorMessage, result.errorMessage)
    }

    @Test
    fun apiResponseHandler_safeApiCall_resultNullBody() {
        val expectedResponse =  NetworkStatus.Error(errorMessage = NULL_RESPONSE_BODY, data = summaryResponse)

        //STUB calls
        every { apiRequest.isSuccessful } returns true
        every { apiRequest.body() } returns null
        coEvery { ApiResponseHandler.safeApiCall{ apiRequest } } returns expectedResponse

        //Execute the code
        val result = runBlocking { ApiResponseHandler.safeApiCall{ apiRequest } }

        //Verify
        Assert.assertEquals(expectedResponse.errorMessage, result.errorMessage)
    }
}