package com.mayandro.local

import com.mayandro.local.entity.CountrySummaryEntity
import com.mayandro.local.entity.GlobalSummaryEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertGlobalSummary(list: List<GlobalSummaryEntity>)

    suspend fun deleteAllGlobalSummary()

    suspend fun getAllGlobalSummary(): List<GlobalSummaryEntity>

    suspend fun getLastGlobalSummary(): GlobalSummaryEntity?

    suspend fun getGlobalSummaryByDate(date: String): GlobalSummaryEntity?

    suspend fun insertCountrySummary(countrySummaryEntity: List<CountrySummaryEntity>)

    suspend fun deleteAllCountrySummary()

    suspend fun getAllCountrySummary(): List<CountrySummaryEntity>

    suspend fun getCountrySummaryById(id: String): CountrySummaryEntity?

    suspend fun getCountrySummaryByCountry(country: String): CountrySummaryEntity?

    suspend fun getCountrySummaryByCountryCode(countryCode: String): CountrySummaryEntity?
}