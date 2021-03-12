package com.mayandro.local

import com.mayandro.local.dao.CountrySummaryDao
import com.mayandro.local.dao.GlobalSummaryDao
import com.mayandro.local.entity.CountrySummaryEntity
import com.mayandro.local.entity.GlobalSummaryEntity


class LocalDataSourceImpl(
    private val countrySummaryDao: CountrySummaryDao,
    private val globalSummaryDao: GlobalSummaryDao,
): LocalDataSource {
    override suspend fun insertGlobalSummary(globalSummaryEntity: GlobalSummaryEntity) {
        globalSummaryDao.insertGlobalSummary(globalSummaryEntity)
    }

    override suspend fun deleteAllGlobalSummary() {
        globalSummaryDao.clearGlobalSummary()
    }

    override suspend fun getAllGlobalSummary(): List<GlobalSummaryEntity> {
        return globalSummaryDao.getAllGlobalSummary()
    }

    override suspend fun getLastGlobalSummary(): GlobalSummaryEntity? {
        return globalSummaryDao.getLastGlobalSummary()
    }

    override suspend fun getGlobalSummaryByDate(date: String): GlobalSummaryEntity? {
        return globalSummaryDao.getGlobalSummaryByDate(date)
    }

    override suspend fun insertCountrySummary(countrySummaryEntity: CountrySummaryEntity){
        countrySummaryDao.insertCountrySummary(countrySummaryEntity)
    }

    override suspend fun deleteAllCountrySummary() {
        countrySummaryDao.clearCountrySummary()
    }

    override suspend fun getAllCountrySummary(): List<CountrySummaryEntity> {
        return countrySummaryDao.getAllCountrySummary()
    }

    override suspend fun getCountrySummaryById(id: String): CountrySummaryEntity? {
        return countrySummaryDao.getCountrySummaryById(id)
    }

    override suspend fun getCountrySummaryByCountry(country: String): CountrySummaryEntity? {
        return countrySummaryDao.getCountrySummaryByCountry(country)
    }

    override suspend fun getCountrySummaryByCountryCode(countryCode: String): CountrySummaryEntity? {
        return countrySummaryDao.getCountrySummaryByCountryCode(countryCode)
    }
}