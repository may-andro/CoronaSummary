package com.mayandro.feature_country_summary.ui.model

import com.mayandro.remote.model.CountryStatsResponse
import com.mayandro.uicommon.utils.chart.ChartDataModel

data class StateSuccessModel(
    val detailLabel: String,
    val detailValue: String,
    val chartDataModel: ChartDataModel,
    val summaryList: List<CountryStatsResponse>
)

data class TimeRangeOption(
    val title: String,
    val from: String?,
    val to: String?
)