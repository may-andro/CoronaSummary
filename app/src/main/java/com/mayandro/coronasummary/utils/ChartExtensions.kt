package com.mayandro.coronasummary.utils

import com.github.mikephil.charting.data.Entry
import com.mayandro.local.entity.GlobalSummaryEntity
import com.mayandro.utility.CHART_FORMAT
import com.mayandro.utility.dataandtime.getDate

fun List<GlobalSummaryEntity>.getActiveCaseChartEntries(): Pair<ChartDataModel, ChartDataModel> {
    val entries1: MutableList<Entry> = mutableListOf()
    val xAxisList: MutableList<String> = mutableListOf()
    var yAxisMax1 = 0L
    var yAxisMin1 = -1L

    val entries2: MutableList<Entry> = mutableListOf()
    var yAxisMax2 = 0L
    var yAxisMin2 = -1L

    this.forEach {
        xAxisList.add(it.date.getDate(CHART_FORMAT) ?: "")
        //Find Max
        if (yAxisMax1 < it.newConfirmed) yAxisMax1 = it.newConfirmed
        if (yAxisMax2 < it.totalConfirmed) yAxisMax2 = it.totalConfirmed
        //Find Min
        if (yAxisMin1 == -1L || yAxisMin1 > it.newConfirmed) yAxisMin1 = it.newConfirmed
        if (yAxisMin2 == -1L || yAxisMin2 > it.totalConfirmed) yAxisMin2 = it.totalConfirmed

        val entry1 = Entry((xAxisList.size - 1).toFloat(), it.newConfirmed.toFloat())
        entries1.add(entry1)

        val entry2 = Entry((xAxisList.size - 1).toFloat(), it.totalConfirmed.toFloat())
        entries2.add(entry2)
    }

    val chart1 = ChartDataModel(
        yAxisMax = yAxisMax1.toFloat(),
        yAxisMin = yAxisMin1.toFloat(),
        xAxisList = xAxisList,
        entries = entries1
    )

    val chart2 = ChartDataModel(
        yAxisMax = yAxisMax2.toFloat(),
        yAxisMin = yAxisMin2.toFloat(),
        xAxisList = xAxisList,
        entries = entries2
    )

    return Pair(chart1, chart2)
}

fun List<GlobalSummaryEntity>.getDeathCaseChartEntries(): Pair<ChartDataModel, ChartDataModel> {
    val entries: MutableList<Entry> = mutableListOf()
    val xAxisList: MutableList<String> = mutableListOf()
    var yAxisMax = 0L
    var yAxisMin = -1L

    val entries2: MutableList<Entry> = mutableListOf()
    var yAxisMax2 = 0L
    var yAxisMin2 = -1L

    this.forEach {
        xAxisList.add(it.date.getDate(CHART_FORMAT) ?: "")
        //Find Max
        if (yAxisMax < it.newDeaths) yAxisMax = it.newDeaths
        if (yAxisMax2 < it.totalDeaths) yAxisMax2 = it.totalDeaths
        //Find Min
        if (yAxisMin == -1L || yAxisMin > it.newDeaths) yAxisMin = it.newDeaths
        if (yAxisMin2 == -1L || yAxisMin2 > it.totalDeaths) yAxisMin2 = it.totalDeaths

        val entry = Entry((xAxisList.size - 1).toFloat(), it.newDeaths.toFloat())
        entries.add(entry)

        val entry2 = Entry((xAxisList.size - 1).toFloat(), it.totalDeaths.toFloat())
        entries2.add(entry2)
    }

    val chart1 = ChartDataModel(
        yAxisMax = yAxisMax.toFloat(),
        yAxisMin = yAxisMin.toFloat(),
        xAxisList = xAxisList,
        entries = entries
    )

    val chart2 = ChartDataModel(
        yAxisMax = yAxisMax2.toFloat(),
        yAxisMin = yAxisMin2.toFloat(),
        xAxisList = xAxisList,
        entries = entries2
    )

    return Pair(chart1, chart2)
}

fun List<GlobalSummaryEntity>.getRecoveredCaseChartEntries(): Pair<ChartDataModel, ChartDataModel> {
    val entries: MutableList<Entry> = mutableListOf()
    val xAxisList: MutableList<String> = mutableListOf()
    var yAxisMax = 0L
    var yAxisMin = -1L

    val entries2: MutableList<Entry> = mutableListOf()
    var yAxisMax2 = 0L
    var yAxisMin2 = -1L

    this.forEach {
        println("<top>.getRecoveredCaseChartEntries date ${it.date} ${it.newRecovered}")

        xAxisList.add(it.date.getDate(CHART_FORMAT) ?: "")
        //Find Max
        if (yAxisMax < it.newRecovered) yAxisMax = it.newRecovered
        if (yAxisMax2 < it.totalRecovered) yAxisMax2 = it.totalRecovered
        //Find Min
        if (yAxisMin == -1L || yAxisMin > it.newRecovered) yAxisMin = it.newRecovered
        if (yAxisMin2 == -1L || yAxisMin2 > it.totalRecovered) yAxisMin2 = it.totalRecovered

        val entry = Entry((xAxisList.size - 1).toFloat(), it.newRecovered.toFloat())
        entries.add(entry)

        val entry2 = Entry((xAxisList.size - 1).toFloat(), it.totalRecovered.toFloat())
        entries2.add(entry2)
    }



    val chart1 = ChartDataModel(
        yAxisMax = yAxisMax.toFloat(),
        yAxisMin = yAxisMin.toFloat(),
        xAxisList = xAxisList,
        entries = entries
    )

    val chart2 = ChartDataModel(
        yAxisMax = yAxisMax2.toFloat(),
        yAxisMin = yAxisMin2.toFloat(),
        xAxisList = xAxisList,
        entries = entries2
    )

    return Pair(chart1, chart2)
}