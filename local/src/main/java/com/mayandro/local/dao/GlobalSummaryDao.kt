package com.mayandro.local.dao

import androidx.room.*
import com.mayandro.local.entity.GlobalSummaryEntity
import com.mayandro.local.utils.DbConstants

@Dao
interface GlobalSummaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobalSummary(list: List<GlobalSummaryEntity>)

    @Update
    suspend fun updateGlobalSummary(globalSummaryEntity: GlobalSummaryEntity)

    @Query(DbConstants.QUERY_GET_ALL_GLOBAL_SUMMARY)
    suspend fun getAllGlobalSummary(): List<GlobalSummaryEntity>

    @Query(DbConstants.QUERY_LAST_GLOBAL_SUMMARY)
    suspend fun getLastGlobalSummary(): GlobalSummaryEntity?

    @Query(DbConstants.QUERY_GET_ALL_GLOBAL_SUMMARY_BY_DATE)
    suspend fun getGlobalSummaryByDate(date: String): GlobalSummaryEntity?

    @Query(DbConstants.DELETE_GLOBAL_SUMMARY)
    suspend fun clearGlobalSummary()
}