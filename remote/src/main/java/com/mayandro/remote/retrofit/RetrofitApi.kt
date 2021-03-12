package com.mayandro.remote.retrofit

import com.mayandro.remote.model.SummaryResponse
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitApi {

    @GET("/summary")
    suspend fun getSummary() : Response<SummaryResponse>
}