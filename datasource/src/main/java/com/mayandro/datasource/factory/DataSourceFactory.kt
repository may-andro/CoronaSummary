package com.mayandro.datasource.factory

import com.mayandro.local.LocalDataSource
import com.mayandro.remote.RemoteDataSource

interface DataSourceFactory  {
    fun retrieveLocalDataStore(): LocalDataSource
    fun retrieveRemoteDataStore(): RemoteDataSource
}