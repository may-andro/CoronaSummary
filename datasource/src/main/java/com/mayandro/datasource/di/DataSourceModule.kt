package com.mayandro.datasource.di

import com.mayandro.datasource.factory.DataSourceFactory
import com.mayandro.datasource.factory.DataSourceFactoryImpl
import com.mayandro.local.LocalDataSource
import com.mayandro.remote.RemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module() {
    single {
        provideDataSourceFactory(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
}

private fun provideDataSourceFactory(
    localDataSource: LocalDataSource,
    remoteDataSource: RemoteDataSource,
): DataSourceFactory {
    return DataSourceFactoryImpl(localDataSource = localDataSource, remoteDataSource = remoteDataSource)
}