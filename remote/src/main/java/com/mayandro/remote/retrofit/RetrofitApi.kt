package com.mayandro.remote.retrofit

import com.mayandro.remote.model.CountrySummary
import com.mayandro.remote.model.GlobalSummary
import com.mayandro.remote.model.SummaryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {

    @GET("/summary")
    suspend fun getSummary() : Response<SummaryResponse>

    @GET("/world")
    suspend fun getWorldCoronaSummary(
        @Query("from") from: String?,
        @Query("to") to: String?,
    ) : Response<List<GlobalSummary>>

    @GET("/country/{countrySlug}")
    suspend fun getCountryCoronaSummary(
        @Path("countrySlug") countrySlug: String,
        @Query("from") from: String?,
        @Query("to") to: String?,
    ) : Response<List<CountrySummary>>
}