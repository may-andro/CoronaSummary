package com.mayandro.feature_country_summary.utils

import com.github.mikephil.charting.data.Entry
import com.mayandro.feature_country_summary.ui.model.StateSuccessModel
import com.mayandro.remote.model.CountryStatsResponse
import com.mayandro.uicommon.utils.chart.ChartDataModel
import com.mayandro.utility.CHART_FORMAT
import com.mayandro.utility.dataandtime.getDate
import com.mayandro.utility.extensions.getFormattedString
import com.mayandro.utility.mapper.ObjectMapper

class RecoveredCaseMapper : ObjectMapper<List<CountryStatsResponse>, StateSuccessModel>() {
    override fun mapFromOriginalObject(originalObject: List<CountryStatsResponse>): StateSuccessModel {
        return originalObject.tabRecoveredCases()
    }

    private fun List<CountryStatsResponse>.tabRecoveredCases(): StateSuccessModel {
        val latestCountrySummary = this.last()

        return StateSuccessModel(
            detailLabel = "New recoveries",
            detailValue = latestCountrySummary.recovered.getFormattedString(),
            chartDataModel = this.getRecoveredLineChartData(),
            summaryList = this
        )
    }

    private fun List<CountryStatsResponse>.getRecoveredLineChartData(): ChartDataModel {
        val xAxisList: MutableList<String> = mutableListOf()

        val entriesRecovered: MutableList<Entry> = mutableListOf()
        var yAxisRecoveredMax = 0L
        var yAxisRecoveredMin = -1L

        this.forEach {
            xAxisList.add(it.date.getDate(CHART_FORMAT) ?: "")

            //Find Max
            if (yAxisRecoveredMax < it.recovered) yAxisRecoveredMax = it.recovered

            //Find Min
            if (yAxisRecoveredMin == -1L || yAxisRecoveredMin > it.recovered) yAxisRecoveredMin = it.recovered

            val entryRecovered = Entry((xAxisList.size - 1).toFloat(), it.recovered.toFloat())
            entriesRecovered.add(entryRecovered)
        }

        return ChartDataModel(
            yAxisMax = yAxisRecoveredMax.toFloat(),
            yAxisMin = yAxisRecoveredMin.toFloat(),
            xAxisList = xAxisList,
            entries = entriesRecovered
        )
    }

}