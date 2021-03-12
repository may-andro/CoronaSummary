package com.mayandro.domain.di

import com.mayandro.datasource.factory.DataSourceFactory
import com.mayandro.domain.repository.CoronaSummaryRepository
import com.mayandro.domain.repository.CoronaSummaryRepositoryImpl
import com.mayandro.domain.usecase.GetCoronaSummaryUseCase
import org.koin.dsl.module

val domainModule = module() {
    single {
        provideCoronaSummaryRepository(dataSourceFactory = get())
    }

    factory {
        GetCoronaSummaryUseCase(coronaSummaryRepository = get())
    }
}

private fun provideCoronaSummaryRepository(
    dataSourceFactory: DataSourceFactory
): CoronaSummaryRepository {
    return CoronaSummaryRepositoryImpl(dataSourceFactory = dataSourceFactory)
}