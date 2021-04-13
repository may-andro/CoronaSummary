package com.mayandro.coronasummary.di

import com.mayandro.coronasummary.ui.home.HomeViewModel
import com.mayandro.coronasummary.ui.home.dashboard.DashboardViewModel
import com.mayandro.coronasummary.ui.home.dashboard.utils.CountryListDataMapper
import com.mayandro.coronasummary.ui.home.dashboard.utils.UiPagerDataMapper
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    factory { androidApplication().resources }

    factory { UiPagerDataMapper(resource = get()) }

    factory { CountryListDataMapper(resources = get()) }

    viewModel { HomeViewModel() }

    viewModel {
        DashboardViewModel(
            countryListDataMapper = get(),
            getCoronaSummaryUseCase = get(),
            getGlobalSummaryListUseCase = get(),
            uiPagerDataMapper = get()
        )
    }
}