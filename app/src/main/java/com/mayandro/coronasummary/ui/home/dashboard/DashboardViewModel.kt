package com.mayandro.coronasummary.ui.home.dashboard

import androidx.lifecycle.*
import com.mayandro.coronasummary.ui.home.dashboard.adapter.DashboardCountryModel
import com.mayandro.coronasummary.ui.home.dashboard.adapter.DashboardSummaryModel
import com.mayandro.coronasummary.ui.home.dashboard.utils.CountryListDataMapper
import com.mayandro.coronasummary.ui.home.dashboard.utils.UiPagerDataMapper
import com.mayandro.domain.usecase.GetCoronaSummaryUseCase
import com.mayandro.domain.usecase.GetGlobalSummaryListUseCase
import com.mayandro.uicommon.utils.livedata.SingleLiveEvent
import com.mayandro.utility.dataandtime.getServerRequestDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getCoronaSummaryUseCase: GetCoronaSummaryUseCase,
    private val getGlobalSummaryListUseCase: GetGlobalSummaryListUseCase,
    private val uiPagerDataMapper: UiPagerDataMapper,
    private val countryListDataMapper: CountryListDataMapper
) : ViewModel() {

    private val errorLiveData = SingleLiveEvent<String>()
    val errorState : LiveData<String>
        get() = errorLiveData

    private val successDashboardSummaryStateLiveData = MutableLiveData<List<DashboardSummaryModel>>()
    val successDashboardSummaryState : LiveData<List<DashboardSummaryModel>>
        get() = successDashboardSummaryStateLiveData

    private val successCountryListStateLiveData = MutableLiveData<List<DashboardCountryModel>>()
    val successCountryListState : LiveData<List<DashboardCountryModel>>
        get() = successCountryListStateLiveData

    init {
        getCountries()
        getGlobalSummary()
    }

    private fun getCountries() {
        viewModelScope.launch {
            getCoronaSummaryUseCase()
                .map {
                    val list = it.data?.let { list ->
                        countryListDataMapper.mapFromOriginalObject(list)
                    }
                    val error = it.errorMessage

                    Pair(list, error)
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    it.first?.let { list ->
                        successCountryListStateLiveData.value = list
                    }

                    it.second?.let { error ->
                        errorLiveData.postValue(error)
                    }
                }
        }
    }

    private fun getGlobalSummary() {
        viewModelScope.launch {
            getGlobalSummaryListUseCaseParam()
                .flatMapLatest { getGlobalSummaryListUseCase(it) }
                .map {
                    val list = it.data?.let { list ->
                        uiPagerDataMapper.mapFromOriginalObject(list)
                    }
                    val error = it.errorMessage

                    Pair(list, error)
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    it.first?.let { list ->
                        successDashboardSummaryStateLiveData.value = list
                    }

                    it.second?.let { error ->
                        errorLiveData.postValue(error)
                    }
                }
        }
    }

    private fun getGlobalSummaryListUseCaseParam(): Flow<GetGlobalSummaryListUseCase.Param> {
        return flow {
            emit(GetGlobalSummaryListUseCase.Param(
                from = getServerRequestDate(-7),
                to= getServerRequestDate(0)
            ))
        }
    }
}

