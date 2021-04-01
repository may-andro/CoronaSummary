package com.mayandro.domain.di

import com.mayandro.datasource.factory.DataSourceFactory
import com.mayandro.domain.mapper.CountrySummaryListToDbMapper
import com.mayandro.domain.mapper.GlobalSummaryListToDbMapper
import com.mayandro.domain.repository.CoronaSummaryRepository
import com.mayandro.domain.repository.CoronaSummaryRepositoryImpl
import com.mayandro.domain.usecase.GetCoronaSummaryUseCase
import com.mayandro.domain.usecase.GetCountrySummaryUseCase
import com.mayandro.domain.usecase.GetGlobalSummaryListUseCase
import org.koin.dsl.module

val domainModule = module() {
    single {
        provideCoronaSummaryRepository(dataSourceFactory = get())
    }

    factory {
        CountrySummaryListToDbMapper()
    }

    factory {
        GlobalSummaryListToDbMapper()
    }

    factory {
        GetCoronaSummaryUseCase(coronaSummaryRepository = get(), countrySummaryListToDbMapper = get())
    }

    factory {
        GetCountrySummaryUseCase(coronaSummaryRepository = get())
    }

    factory {
        GetGlobalSummaryListUseCase(coronaSummaryRepository = get(), globalSummaryListToDbMapper = get())
    }
}

private fun provideCoronaSummaryRepository(
    dataSourceFactory: DataSourceFactory
): CoronaSummaryRepository {
    return CoronaSummaryRepositoryImpl(dataSourceFactory = dataSourceFactory)
}