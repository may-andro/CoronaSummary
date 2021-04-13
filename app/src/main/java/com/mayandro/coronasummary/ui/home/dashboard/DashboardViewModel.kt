package com.mayandro.coronasummary.ui.home.dashboard

import androidx.lifecycle.*
import com.mayandro.coronasummary.ui.home.dashboard.adapter.DashboardCountryModel
import com.mayandro.coronasummary.ui.home.dashboard.adapter.DashboardSummaryModel
import com.mayandro.coronasummary.ui.home.dashboard.utils.CountryListDataMapper
import com.mayandro.coronasummary.ui.home.dashboard.utils.UiPagerDataMapper
import com.mayandro.domain.usecase.GetCoronaSummaryUseCase
import com.mayandro.domain.usecase.GetGlobalSummaryListUseCase
import com.mayandro.utility.dataandtime.getServerRequestDate
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getCoronaSummaryUseCase: GetCoronaSummaryUseCase,
    private val getGlobalSummaryListUseCase: GetGlobalSummaryListUseCase,
    private val uiPagerDataMapper: UiPagerDataMapper,
    private val countryListDataMapper: CountryListDataMapper
) : ViewModel() {

    var globalUiListLiveData: MutableLiveData<NetworkStatus<List<DashboardSummaryModel>>> = MutableLiveData()

    var countriesUiListLiveData: MutableLiveData<NetworkStatus<List<DashboardCountryModel>>> =  MutableLiveData()

    init {
        getCountries()
        getGlobalSummary()
    }

    private fun getCountries() {
        viewModelScope.launch {
            getCoronaSummaryUseCase()
                .map {
                    when (it) {
                        is NetworkStatus.Success -> {
                            it.data?.let { list ->
                                NetworkStatus.Success(countryListDataMapper.mapFromOriginalObject(list))
                            } ?: kotlin.run {
                                NetworkStatus.Error(
                                    errorMessage = "Null Response error"
                                )
                            }

                        }
                        is NetworkStatus.Error -> {
                            NetworkStatus.Error(
                                errorMessage = it.errorMessage,
                                it.data?.let { list ->
                                    countryListDataMapper.mapFromOriginalObject(list)
                                }
                            )
                        }
                        is NetworkStatus.Loading -> {
                            NetworkStatus.Loading(
                                it.data?.let { list ->
                                    countryListDataMapper.mapFromOriginalObject(list)
                                }
                            )
                        }
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    countriesUiListLiveData.postValue(it)
                }
        }
    }

    private fun getGlobalSummary() {
        viewModelScope.launch {
            getGlobalSummaryListUseCaseParam()
                .flatMapLatest { getGlobalSummaryListUseCase(it) }
                .map {
                    when (it) {
                        is NetworkStatus.Success -> {
                            it.data?.let { list ->
                                NetworkStatus.Success(uiPagerDataMapper.mapFromOriginalObject(list))
                            } ?: kotlin.run {
                                NetworkStatus.Error(
                                    errorMessage = "Null Response error"
                                )
                            }
                        }
                        is NetworkStatus.Error -> {
                            NetworkStatus.Error(
                                errorMessage = it.errorMessage,
                                it.data?.let { list ->
                                    uiPagerDataMapper.mapFromOriginalObject(list)
                                }
                            )
                        }
                        is NetworkStatus.Loading -> {
                            NetworkStatus.Loading(
                                it.data?.let { list ->
                                    uiPagerDataMapper.mapFromOriginalObject(list)
                                }
                            )
                        }
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    globalUiListLiveData.postValue(it)
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

