package com.mayandro.local.di

import android.content.Context
import androidx.room.Room
import com.mayandro.local.LocalDataSource
import com.mayandro.local.LocalDataSourceImpl
import com.mayandro.local.dao.CountrySummaryDao
import com.mayandro.local.dao.GlobalSummaryDao
import com.mayandro.local.db.CoronaDb
import com.mayandro.local.utils.DbConstants
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { provideAppDatabase(androidApplication()) }
    single { provideCountrySummaryDao(get()) }
    single { provideGlobalSummaryDao(get()) }
    single { provideLocalDataSource(get(),get()) }
}

fun provideAppDatabase(context: Context): CoronaDb {
    return Room.databaseBuilder(
        context,
        CoronaDb::class.java,
        DbConstants.DATABASE_NAME
    ).fallbackToDestructiveMigration().build()
}


fun provideCountrySummaryDao(db: CoronaDb): CountrySummaryDao = db.countrySummaryDao()

fun provideGlobalSummaryDao(db: CoronaDb): GlobalSummaryDao = db.globalSummaryDao()

fun provideLocalDataSource(
    countrySummaryDao: CountrySummaryDao,
    globalSummaryDao: GlobalSummaryDao
): LocalDataSource {
    return LocalDataSourceImpl(
        countrySummaryDao = countrySummaryDao,
        globalSummaryDao = globalSummaryDao
    )
}