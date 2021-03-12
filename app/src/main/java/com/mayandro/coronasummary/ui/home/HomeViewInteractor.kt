package com.mayandro.coronasummary.ui.home

import com.mayandro.coronasummary.ui.base.ViewInteractor

interface HomeViewInteractor: ViewInteractor {
    fun showAlert(message: String)
}