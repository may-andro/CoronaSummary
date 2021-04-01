package com.mayandro.coronasummary.di

import com.mayandro.coronasummary.ui.home.HomeViewModel
import com.mayandro.coronasummary.ui.home.dashboard.DashboardViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    factory { androidApplication().resources }

    viewModel { HomeViewModel() }

    viewModel { DashboardViewModel(resource = get(), getCoronaSummaryUseCase = get(), getGlobalSummaryListUseCase = get()) }
}