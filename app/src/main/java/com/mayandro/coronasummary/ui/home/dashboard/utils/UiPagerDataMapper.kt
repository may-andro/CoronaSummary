package com.mayandro.coronasummary.ui.home.dashboard.utils

import android.content.res.Resources
import com.mayandro.coronasummary.R
import com.mayandro.coronasummary.ui.home.dashboard.adapter.DashboardSummaryModel
import com.mayandro.coronasummary.utils.getActiveCaseChartEntries
import com.mayandro.coronasummary.utils.getDeathCaseChartEntries
import com.mayandro.coronasummary.utils.getRecoveredCaseChartEntries
import com.mayandro.local.entity.GlobalSummaryEntity
import com.mayandro.utility.extensions.getRoughNumber
import com.mayandro.utility.mapper.ObjectMapper

class UiPagerDataMapper(private val resource: Resources): ObjectMapper<List<GlobalSummaryEntity>, List<DashboardSummaryModel>>() {
    override fun mapFromOriginalObject(originalObject: List<GlobalSummaryEntity>): List<DashboardSummaryModel> {
        return originalObject.toUiList(resource)
    }

    private fun List<GlobalSummaryEntity>.toUiList(resource: Resources): List<DashboardSummaryModel> {
        val globalSummaryDataModel = this.last()

        val pairActiveCase = this.getActiveCaseChartEntries()
        val activeCaseSummary = DashboardSummaryModel(
            label1 = resource.getString(R.string.active_cases),
            value1 = globalSummaryDataModel.newConfirmed.getRoughNumber(),
            label2 = resource.getString(R.string.total_cases),
            value2 = globalSummaryDataModel.totalConfirmed.getRoughNumber(),
            percentage = ((globalSummaryDataModel.newConfirmed * 100) / globalSummaryDataModel.totalConfirmed).toFloat(),
            chartData1 = pairActiveCase.first,
            chartData2 = pairActiveCase.second
        )

        val pairDeathCase = this.getDeathCaseChartEntries()
        val deadCaseSummary = DashboardSummaryModel(
            label1 = resource.getString(R.string.new_death),
            value1 = globalSummaryDataModel.newDeaths.getRoughNumber(),
            label2 = resource.getString(R.string.total_death),
            value2 = globalSummaryDataModel.totalDeaths.getRoughNumber(),
            percentage = ((globalSummaryDataModel.totalDeaths * 100) / globalSummaryDataModel.totalConfirmed).toFloat(),
            chartData1 = pairDeathCase.first,
            chartData2 = pairDeathCase.second
        )

        val pairRecoveredCase = this.getRecoveredCaseChartEntries()
        val recoveredCaseSummary = DashboardSummaryModel(
            label1 = resource.getString(R.string.new_recovered),
            value1 = globalSummaryDataModel.newRecovered.getRoughNumber(),
            label2 = resource.getString(R.string.total_recovered),
            value2 = globalSummaryDataModel.totalRecovered.getRoughNumber(),
            percentage = ((globalSummaryDataModel.totalRecovered * 100) / globalSummaryDataModel.totalConfirmed).toFloat(),
            chartData1 = pairRecoveredCase.first,
            chartData2 = pairRecoveredCase.second
        )

        return mutableListOf(
            recoveredCaseSummary, deadCaseSummary, activeCaseSummary
        )
    }
}