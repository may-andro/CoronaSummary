package com.mayandro.remote.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("ID") val id: String,
    @SerializedName("Country") val country: String,
    @SerializedName("CountryCode") val countryCode: String,
    @SerializedName("Slug") val slug: String,
    @SerializedName("NewConfirmed") val newConfirmed: Long,
    @SerializedName("TotalConfirmed") val totalConfirmed: Long,
    @SerializedName("NewDeaths") val newDeaths: Long,
    @SerializedName("TotalDeaths") val totalDeaths: Long,
    @SerializedName("NewRecovered") val newRecovered: Long,
    @SerializedName("TotalRecovered") val totalRecovered: Long,
    @SerializedName("Date") val date: String
)

data class GlobalSummary(
    @SerializedName("NewConfirmed") val newConfirmed: Long,
    @SerializedName("TotalConfirmed") val totalConfirmed: Long,
    @SerializedName("NewDeaths") val newDeaths: Long,
    @SerializedName("TotalDeaths") val totalDeaths: Long,
    @SerializedName("NewRecovered") val newRecovered: Long,
    @SerializedName("TotalRecovered") val totalRecovered: Long,
    @SerializedName("Date") val date: String
)

data class SummaryResponse(
    @SerializedName("Global") val global: GlobalSummary,
    @SerializedName("Countries") val countries: List<Country>,
    @SerializedName("Date") val date: String
)