package com.mayandro.coronasummary.ui.home.dashboard

import android.content.res.Resources
import androidx.lifecycle.*
import com.mayandro.coronasummary.R
import com.mayandro.coronasummary.ui.base.BaseViewModel
import com.mayandro.coronasummary.ui.home.dashboard.adapter.DashboardCountryModel
import com.mayandro.coronasummary.ui.home.dashboard.adapter.DashboardSummaryModel
import com.mayandro.domain.usecase.GetCoronaSummaryUseCase
import com.mayandro.remote.model.Country
import com.mayandro.remote.model.GlobalSummary
import com.mayandro.remote.model.SummaryResponse
import com.mayandro.utility.extensions.getRoughNumber
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class DashboardViewModel(
    private val resource: Resources,
    private val getCoronaSummaryUseCase: GetCoronaSummaryUseCase
): BaseViewModel<DashboardInteractor>() {

    var summaryResponseLiveData: MutableLiveData<NetworkStatus<SummaryResponse>> = MutableLiveData()

    fun getCoronaSummary() {
        getCoronaSummaryUseCase(
            scope = viewModelScope,
            param = Any(),
        ) { result ->
            summaryResponseLiveData.postValue(result)
        }
    }

    fun getUiData(summaryResponse: SummaryResponse, onResult: (List<DashboardSummaryModel>, List<DashboardCountryModel>) -> Unit) {
        viewModelScope.launch {
            val globalSummary = getPagerUiDataList(summaryResponse.global)
            val counties = getRecyclerViewListFlow(summaryResponse.countries)

            globalSummary.zip(counties) { globalSUmmary, countryList ->
                Pair(globalSUmmary, countryList)
            }.collect {
                onResult.invoke(it.first, it.second)
            }
        }
    }

    private fun getPagerUiDataList(globalSummaryDataModel: GlobalSummary): Flow<List<DashboardSummaryModel>> {
        return flow{
            val activeCaseSummary = DashboardSummaryModel(
                label1 = resource.getString(R.string.active_cases),
                value1 = globalSummaryDataModel.newConfirmed.getRoughNumber(),
                label2 = resource.getString(R.string.total_cases),
                value2 = globalSummaryDataModel.totalConfirmed.getRoughNumber(),
                percentage = ((globalSummaryDataModel.newConfirmed * 100) / globalSummaryDataModel.totalConfirmed).toFloat()
            )

            val deadCaseSummary = DashboardSummaryModel(
                label1 = resource.getString(R.string.new_death),
                value1 = globalSummaryDataModel.newDeaths.getRoughNumber(),
                label2 = resource.getString(R.string.total_death),
                value2 = globalSummaryDataModel.totalDeaths.getRoughNumber(),
                percentage = ((globalSummaryDataModel.totalDeaths * 100) / globalSummaryDataModel.totalConfirmed).toFloat()
            )

            val recoveredCaseSummary = DashboardSummaryModel(
                label1 = resource.getString(R.string.new_recovered),
                value1 = globalSummaryDataModel.newRecovered.getRoughNumber(),
                label2 = resource.getString(R.string.total_recovered),
                value2 = globalSummaryDataModel.totalRecovered.getRoughNumber(),
                percentage = ((globalSummaryDataModel.totalRecovered * 100) / globalSummaryDataModel.totalConfirmed).toFloat()
            )
            emit(
                listOf(recoveredCaseSummary, deadCaseSummary, activeCaseSummary)
            )
        }.flowOn(Dispatchers.Default)
    }

    private fun getRecyclerViewListFlow(countryList: List<Country>): Flow<List<DashboardCountryModel>> {
        return flow{
            val random = Random()
            val sortedList = countryList.sortedByDescending { it.totalConfirmed }
            val list = sortedList.map {
                val colorsList = resource.getIntArray(R.array.color_list)
                DashboardCountryModel(
                    id = it.id,
                    country = it.country,
                    countryCode = it.countryCode,
                    slug = it.slug,
                    totalCase = it.totalConfirmed.getRoughNumber(),
                    backgroundColor = colorsList[random.nextInt(colorsList.size)]
                )
            }
            emit(list)
        }.flowOn(Dispatchers.Default)
    }
}