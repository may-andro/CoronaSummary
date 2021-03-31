package com.mayandro.datasource.utils

import com.mayandro.local.entity.CountrySummaryEntity
import com.mayandro.local.entity.GlobalSummaryEntity
import com.mayandro.remote.model.CountrySummary
import com.mayandro.remote.model.GlobalSummary

fun GlobalSummaryEntity.toGlobalSummary(): GlobalSummary {
    return GlobalSummary(
        newConfirmed = this.newConfirmed,
        totalConfirmed = this.totalConfirmed,
        newDeaths = this.newDeaths,
        totalDeaths = this.totalDeaths,
        newRecovered = this.newRecovered,
        totalRecovered = this.totalRecovered,
        date = this.date
    )
}

fun GlobalSummary.toGlobalSummary(): GlobalSummaryEntity {
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

fun List<CountrySummaryEntity>.toCountryList(): List<CountrySummary> {
    val list = mutableListOf<CountrySummary>()
    this.forEach {
        list.add(it.toCountry())
    }
    return list
}

fun CountrySummaryEntity.toCountry(): CountrySummary {
    return CountrySummary(
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

fun List<CountrySummary>.toCountrySummaryEntityList(): List<CountrySummaryEntity> {
    val list = mutableListOf<CountrySummaryEntity>()
    this.forEach {
        list.add(it.toCountrySummaryEntity())
    }
    return list
}

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