package com.mayandro.datasource.factory

import com.mayandro.local.LocalDataSource
import com.mayandro.remote.RemoteDataSource

class DataSourceFactoryImpl (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): DataSourceFactory {
    override fun retrieveLocalDataStore(): LocalDataSource {
        return localDataSource
    }

    override fun retrieveRemoteDataStore(): RemoteDataSource {
        return remoteDataSource
    }
}