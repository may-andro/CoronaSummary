package com.mayandro.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountrySummary(
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
): Parcelable

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
    @SerializedName("Countries") val countrySummaries: List<CountrySummary>,
    @SerializedName("Date") val date: String
)

data class CountryStatsResponse(
    @SerializedName("Country") val country: String,
    @SerializedName("CountryCode") val countryCode: String,
    @SerializedName("Confirmed") val confirmed: Long,
    @SerializedName("Deaths") val deaths: Long,
    @SerializedName("Recovered") val recovered: Long,
    @SerializedName("Active") val active: Long,
    @SerializedName("Date") val date: String
)