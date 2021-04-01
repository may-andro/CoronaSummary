package com.mayandro.coronasummary.ui.home.dashboard

import android.content.res.Resources
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import com.mayandro.coronasummary.R
import com.mayandro.coronasummary.ui.home.dashboard.adapter.DashboardCountryModel
import com.mayandro.coronasummary.ui.home.dashboard.adapter.DashboardSummaryModel
import com.mayandro.coronasummary.ui.home.dashboard.utils.toUiList
import com.mayandro.domain.usecase.GetCoronaSummaryUseCase
import com.mayandro.domain.usecase.GetGlobalSummaryListUseCase
import com.mayandro.local.entity.CountrySummaryEntity
import com.mayandro.local.entity.GlobalSummaryEntity
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val resource: Resources,
    private val getCoronaSummaryUseCase: GetCoronaSummaryUseCase,
    private val getGlobalSummaryListUseCase: GetGlobalSummaryListUseCase
) : ViewModel() {

    private var countriesListLiveData: MutableLiveData<NetworkStatus<List<CountrySummaryEntity>>> =
        MutableLiveData()

    private var globalSummaryListLiveData: MutableLiveData<NetworkStatus<List<GlobalSummaryEntity>>> =
        MutableLiveData()

    var globalUiListLiveData: LiveData<NetworkStatus<List<DashboardSummaryModel>>> =
        map(globalSummaryListLiveData) { networkStatus ->
            when (networkStatus) {
                is NetworkStatus.Success -> {
                    NetworkStatus.Success(networkStatus.data?.sortedBy { it.date }?.toUiList(resource))
                }
                is NetworkStatus.Error -> {
                    NetworkStatus.Error(
                        errorMessage = networkStatus.errorMessage,
                        networkStatus.data?.sortedBy { it.date }?.toUiList(resource)
                    )
                }
                is NetworkStatus.Loading -> {
                    NetworkStatus.Loading(networkStatus.data?.sortedBy { it.date }?.toUiList(resource))
                }
            }
        }


    var countriesUiListLiveData: LiveData<NetworkStatus<List<DashboardCountryModel>>> =
        map(countriesListLiveData) {
            val colorList = resource.getIntArray(R.array.color_list).toList()
            when (it) {
                is NetworkStatus.Success -> {
                    NetworkStatus.Success(it.data?.toUiList(colorList))
                }
                is NetworkStatus.Error -> {
                    NetworkStatus.Error(
                        errorMessage = it.errorMessage,
                        it.data?.toUiList(colorList)
                    )
                }
                is NetworkStatus.Loading -> {
                    NetworkStatus.Loading(it.data?.toUiList(colorList))
                }
            }
        }

    init {
        getCountries()
        getGlobalSummary()
    }

    private fun getCountries() {
        viewModelScope.launch {
            getCoronaSummaryUseCase()
                .flowOn(Dispatchers.IO)
                .collect {
                    countriesListLiveData.postValue(it)
                }
        }
    }

    private fun getGlobalSummary() {
        viewModelScope.launch {
            getGlobalSummaryListUseCaseParam()
                .flatMapLatest { getGlobalSummaryListUseCase(it) }
                .flowOn(Dispatchers.IO)
                .collect {
                    globalSummaryListLiveData.postValue(it)
                }
        }
    }

    private fun getGlobalSummaryListUseCaseParam(): Flow<GetGlobalSummaryListUseCase.Param> {
        return flow {
            emit(GetGlobalSummaryListUseCase.Param(
                from = "2021-03-20T00:00:00Z",
                to= "2021-03-31T00:00:00Z"
            ))
        }
    }
}

