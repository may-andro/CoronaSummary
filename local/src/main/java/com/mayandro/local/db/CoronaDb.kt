package com.mayandro.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mayandro.local.dao.CountrySummaryDao
import com.mayandro.local.dao.GlobalSummaryDao
import com.mayandro.local.entity.CountrySummaryEntity
import com.mayandro.local.entity.GlobalSummaryEntity

@Database(entities = [GlobalSummaryEntity::class, CountrySummaryEntity::class], version = 3, exportSchema = false)
abstract class CoronaDb: RoomDatabase(){
    abstract fun globalSummaryDao(): GlobalSummaryDao
    abstract fun countrySummaryDao(): CountrySummaryDao
}