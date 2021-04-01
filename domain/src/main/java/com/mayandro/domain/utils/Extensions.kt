package com.mayandro.domain.utils

import com.mayandro.local.entity.CountrySummaryEntity
import com.mayandro.local.entity.GlobalSummaryEntity
import com.mayandro.remote.model.CountrySummary
import com.mayandro.remote.model.GlobalSummary

fun CountrySummary.toCountrySummaryEntity(): CountrySummaryEntity {
    return CountrySummaryEntity(
        id = this.id,
        country = this.country,
        countryCode = this.countryCode,
        slug = this.slug,
        newConfirmed = this.newConfirmed,
        totalConfirmed = this.totalConfirmed,
        newDeaths = this.newDeaths,
        totalDeaths = this.totalDeaths,
        newRecovered = this.newRecovered,
        totalRecovered = this.totalRecovered,
        date = this.date
    )
}

fun GlobalSummary.toGlobalSummaryEntity(): GlobalSummaryEntity {
    return GlobalSummaryEntity(
        newConfirmed = this.newConfirmed,
        totalConfirmed = this.totalConfirmed,
        newDeaths = this.newDeaths,
        totalDeaths = this.totalDeaths,
        newRecovered = this.newRecovered,
        totalRecovered = this.totalRecovered,
        date = this.date
    )
}