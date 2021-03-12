package com.mayandro.local.dao

import androidx.room.*
import com.mayandro.local.entity.CountrySummaryEntity
import com.mayandro.local.utils.DbConstants

@Dao
interface CountrySummaryDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountrySummary(countrySummaryEntity: CountrySummaryEntity)

    @Update
    suspend fun updateCountrySummary(countrySummaryEntity: CountrySummaryEntity)

    @Query(DbConstants.QUERY_GET_ALL_COUNTRIES_SUMMARY)
    suspend fun getAllCountrySummary(): List<CountrySummaryEntity>

    @Query(DbConstants.GET_COUNTRY_SUMMARY_BY_ID)
    suspend fun getCountrySummaryById(id: String): CountrySummaryEntity?

    @Query(DbConstants.GET_COUNTRY_SUMMARY_BY_COUNTRY)
    suspend fun getCountrySummaryByCountry(country: String): CountrySummaryEntity?

    @Query(DbConstants.GET_COUNTRY_SUMMARY_BY_COUNTRY_CODE)
    suspend fun getCountrySummaryByCountryCode(countryCode: String): CountrySummaryEntity?

    @Query(DbConstants.DELETE_GLOBAL_SUMMARY)
    suspend fun clearCountrySummary()
}