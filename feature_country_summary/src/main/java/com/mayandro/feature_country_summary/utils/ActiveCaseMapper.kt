package com.mayandro.feature_country_summary.utils

import com.github.mikephil.charting.data.Entry
import com.mayandro.feature_country_summary.ui.model.StateSuccessModel
import com.mayandro.remote.model.CountryStatsResponse
import com.mayandro.uicommon.utils.chart.ChartDataModel
import com.mayandro.utility.CHART_FORMAT
import com.mayandro.utility.dataandtime.getDate
import com.mayandro.utility.extensions.getFormattedString
import com.mayandro.utility.mapper.ObjectMapper

class ActiveCaseMapper: ObjectMapper<List<CountryStatsResponse>, StateSuccessModel>() {
    override fun mapFromOriginalObject(originalObject: List<CountryStatsResponse>): StateSuccessModel {
        return originalObject.tabActiveCases()
    }

    private fun List<CountryStatsResponse>.tabActiveCases(): StateSuccessModel {
        val latestCountrySummary = this.last()

        return StateSuccessModel(
            detailLabel = "Active Cases",
            detailValue = latestCountrySummary.confirmed.getFormattedString(),
            chartDataModel = this.getActiveCasesLineChartData(),
            summaryList = this
        )
    }

    private fun List<CountryStatsResponse>.getActiveCasesLineChartData(): ChartDataModel {
        val xAxisList: MutableList<String> = mutableListOf()

        val entriesActiveCase: MutableList<Entry> = mutableListOf()
        var yAxisActiveCaseMax = 0L
        var yAxisActiveCaseMin = -1L

        this.forEach {
            xAxisList.add(it.date.getDate(CHART_FORMAT) ?: "")

            //Find Max
            if (yAxisActiveCaseMax < it.confirmed) yAxisActiveCaseMax = it.confirmed

            //Find Min
            if (yAxisActiveCaseMin == -1L || yAxisActiveCaseMin > it.confirmed) yAxisActiveCaseMin = it.confirmed

            val entryActiveCase = Entry((xAxisList.size - 1).toFloat(), it.confirmed.toFloat())
            entriesActiveCase.add(entryActiveCase)
        }

        return ChartDataModel(
            yAxisMax = yAxisActiveCaseMax.toFloat(),
            yAxisMin = yAxisActiveCaseMin.toFloat(),
            xAxisList = xAxisList,
            entries = entriesActiveCase
        )
    }

}