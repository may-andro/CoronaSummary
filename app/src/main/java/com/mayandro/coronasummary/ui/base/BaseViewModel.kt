package com.mayandro.coronasummary.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancelChildren
import org.koin.core.component.KoinComponent

abstract class BaseViewModel<VI: ViewInteractor>: ViewModel(), KoinComponent {

    var viewInteractor: VI? = null
        set

    override fun onCleared() {
        viewModelScope.coroutineContext.cancelChildren()
        viewInteractor = null
        super.onCleared()
    }
}