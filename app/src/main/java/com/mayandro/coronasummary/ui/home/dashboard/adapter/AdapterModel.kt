package com.mayandro.coronasummary.ui.home.dashboard.adapter

import com.mayandro.coronasummary.utils.ChartDataModel

data class DashboardCountryModel(
    val backgroundColor: Int,
    val id: String,
    val country: String,
    val countryCode: String,
    val slug: String,
    val totalCase: String
)

data class DashboardSummaryModel(
    val label1: String,
    val value1: String,
    val label2: String,
    val value2: String,
    val percentage: Float,
    val chartData1: ChartDataModel,
    val chartData2: ChartDataModel
)