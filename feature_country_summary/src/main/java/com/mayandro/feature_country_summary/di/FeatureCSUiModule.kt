package com.mayandro.feature_country_summary.di

import com.mayandro.feature_country_summary.ui.CountryDetailViewModel
import com.mayandro.feature_country_summary.utils.ActiveCaseMapper
import com.mayandro.feature_country_summary.utils.DeathCaseMapper
import com.mayandro.feature_country_summary.utils.RecoveredCaseMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureCountrySummaryUIModule = module {

    factory { DeathCaseMapper() }

    factory { ActiveCaseMapper() }

    factory { RecoveredCaseMapper() }

    viewModel { CountryDetailViewModel(
        activeCaseMapper = get(),
        recoveredCaseMapper = get(),
        deathCaseMapper = get(),
        getCountrySummaryUseCase = get()
    ) }
}