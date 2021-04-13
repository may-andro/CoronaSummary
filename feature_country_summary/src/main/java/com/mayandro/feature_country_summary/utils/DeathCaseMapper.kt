package com.mayandro.feature_country_summary.utils

import com.github.mikephil.charting.data.Entry
import com.mayandro.feature_country_summary.ui.model.StateSuccessModel
import com.mayandro.remote.model.CountryStatsResponse
import com.mayandro.uicommon.utils.chart.ChartDataModel
import com.mayandro.utility.CHART_FORMAT
import com.mayandro.utility.dataandtime.getDate
import com.mayandro.utility.extensions.getFormattedString
import com.mayandro.utility.mapper.ObjectMapper

class DeathCaseMapper: ObjectMapper<List<CountryStatsResponse>, StateSuccessModel>() {
    override fun mapFromOriginalObject(originalObject: List<CountryStatsResponse>): StateSuccessModel {
        return originalObject.tabDeathCases()
    }

    private fun List<CountryStatsResponse>.tabDeathCases(): StateSuccessModel {
        val latestCountrySummary = this.last()

        return StateSuccessModel(
            detailLabel = "New Deaths",
            detailValue = latestCountrySummary.deaths.getFormattedString(),
            chartDataModel = this.getDeathLineChartData(),
            summaryList = this
        )
    }

    private fun List<CountryStatsResponse>.getDeathLineChartData(): ChartDataModel {
        val xAxisList: MutableList<String> = mutableListOf()

        val entriesDeath: MutableList<Entry> = mutableListOf()
        var yAxisDeathMax = 0L
        var yAxisDeathMin = -1L

        this.forEach {
            xAxisList.add(it.date.getDate(CHART_FORMAT) ?: "")

            //Find Max
            if (yAxisDeathMax < it.deaths) yAxisDeathMax = it.deaths

            //Find Min
            if (yAxisDeathMin == -1L || yAxisDeathMin > it.deaths) yAxisDeathMin = it.deaths

            val entryDeath = Entry((xAxisList.size - 1).toFloat(), it.deaths.toFloat())
            entriesDeath.add(entryDeath)
        }

        return ChartDataModel(
            yAxisMax = yAxisDeathMax.toFloat(),
            yAxisMin = yAxisDeathMin.toFloat(),
            xAxisList = xAxisList,
            entries = entriesDeath
        )
    }

}