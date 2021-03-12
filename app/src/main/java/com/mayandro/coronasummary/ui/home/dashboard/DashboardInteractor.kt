package com.mayandro.coronasummary.ui.home.dashboard

import com.mayandro.coronasummary.ui.base.ViewInteractor

interface DashboardInteractor: ViewInteractor {
    fun showAlert(message: String)
}