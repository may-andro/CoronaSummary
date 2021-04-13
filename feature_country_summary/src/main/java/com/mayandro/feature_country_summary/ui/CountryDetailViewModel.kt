package com.mayandro.feature_country_summary.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayandro.domain.usecase.GetCountrySummaryUseCase
import com.mayandro.feature_country_summary.ui.model.StateSuccessModel
import com.mayandro.feature_country_summary.ui.model.TimeRangeOption
import com.mayandro.feature_country_summary.utils.*
import com.mayandro.remote.model.CountryStatsResponse
import com.mayandro.uicommon.utils.livedata.SingleLiveEvent
import com.mayandro.utility.dataandtime.getServerRequestDate
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class CountryDetailViewModel (
    private val getCountrySummaryUseCase: GetCountrySummaryUseCase,
    private val activeCaseMapper: ActiveCaseMapper,
    private val recoveredCaseMapper: RecoveredCaseMapper,
    private val deathCaseMapper: DeathCaseMapper
): ViewModel() {

    private val errorLiveData = SingleLiveEvent<String>()
    val errorState : LiveData<String>
        get() = errorLiveData

    private val successLiveData = MutableLiveData<List<CountryStatsResponse>>()
    val successState : LiveData<List<CountryStatsResponse>>
        get() = successLiveData

    private var successStateLiveData: MutableLiveData<StateSuccessModel> = MutableLiveData()
    val successUiData : LiveData<StateSuccessModel>
        get() = successStateLiveData

    private val timeRangeOptionList = listOf(
        TimeRangeOption(
            title = "Last 7 days",
            from = getServerRequestDate(-7),
            to= getServerRequestDate(0)
        ),
        TimeRangeOption(
            title = "Last 30 days",
            from = getServerRequestDate(-30),
            to= getServerRequestDate(0)
        ),
        TimeRangeOption(
            title = "Last 180 days",
            from = getServerRequestDate(-180),
            to= getServerRequestDate(0)
        ),
        TimeRangeOption(
            title = "Last 365 days",
            from = getServerRequestDate(-365),
            to= getServerRequestDate(0)
        ),
        TimeRangeOption(
            title = "From start",
            from = null,
            to= null
        )
    )

    var selectedToggleState : Int = TOGGLE_STATE_GRAPH
    var selectedTab : Int = TAB_RECOVERED
    var selectedTimeRange : TimeRangeOption = timeRangeOptionList[0]

    //API CALL
    fun getCountrySummary(
        country: String
    ) {
        viewModelScope.launch {
            getCountrySummaryUseCase.invoke(
                param = GetCountrySummaryUseCase.Param(
                    country,
                    selectedTimeRange.from,
                    selectedTimeRange.to
                )
            )
                .flowOn(Dispatchers.IO)
                .collect { networkStatus ->
                    when(networkStatus) {
                        is NetworkStatus.Error -> {
                            errorLiveData.value = networkStatus.errorMessage?: "Error happened"
                        }
                        is NetworkStatus.Loading -> {

                        }
                        is NetworkStatus.Success -> {
                            networkStatus.data?.let {
                                successLiveData.value = it
                                setStateData()
                            } ?: kotlin.run {
                                errorLiveData.value = "Null value saved"
                            }
                        }
                    }
                }
        }
    }

    //HELPER METHOD
    private fun setStateData(){
        viewModelScope.launch {
            getScreenStateFlow()
                .catch {

                }
                .collect {
                    successStateLiveData.value = it
                }
        }
    }

    private fun getScreenStateFlow(): Flow<StateSuccessModel>  {
        return flow {
            successLiveData.value?.let { list ->
                val sortedList = list.sortedBy { it.date }
                val screenState = when(selectedTab) {
                    TAB_RECOVERED -> recoveredCaseMapper.mapFromOriginalObject(sortedList)
                    TAB_DEAD -> deathCaseMapper.mapFromOriginalObject(sortedList)
                    TAB_ACTIVE_CASE -> activeCaseMapper.mapFromOriginalObject(sortedList)
                    else -> recoveredCaseMapper.mapFromOriginalObject(sortedList)
                }

                emit(screenState)
            } ?: kotlin.run {
                throw NullPointerException()
            }
        }.flowOn(Dispatchers.Default)
    }

    fun onTabSelection(position: Int) {
        selectedTab = position
        setStateData()
    }

    fun onTimeRangeSelection(timeRangeOption: TimeRangeOption) {
        selectedTimeRange = timeRangeOption
    }

    fun getTimeRangeOptionList(): Flow<Triple<List<TimeRangeOption>, List<String>, Int>> {
        return flow{
            val scheduleList = timeRangeOptionList.map {
                it.title
            }
            val selectedPosition = timeRangeOptionList.indexOf(selectedTimeRange)
            emit(Triple(timeRangeOptionList, scheduleList, selectedPosition))
        }.flowOn(Dispatchers.Default)
    }
}