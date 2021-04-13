package com.mayandro.coronasummary.ui.home.dashboard.utils

import android.content.res.Resources
import com.mayandro.coronasummary.R
import com.mayandro.coronasummary.ui.home.dashboard.adapter.DashboardCountryModel
import com.mayandro.local.entity.CountrySummaryEntity
import com.mayandro.utility.extensions.getRoughNumber
import com.mayandro.utility.mapper.ObjectMapper
import java.util.*

class CountryListDataMapper(
    private val resources: Resources
): ObjectMapper<List<CountrySummaryEntity>, List<DashboardCountryModel>>() {
    override fun mapFromOriginalObject(originalObject: List<CountrySummaryEntity>): List<DashboardCountryModel> {
        return originalObject.toUiList(resources.getIntArray(R.array.color_list).toList())
    }

    private fun List<CountrySummaryEntity>.toUiList(colorsList: List<Int>): List<DashboardCountryModel> {
        val random = Random()
        val sortedList = this.sortedByDescending { it.totalConfirmed }
        return sortedList.map {
            DashboardCountryModel(
                id = it.id,
                country = it.country,
                countryCode = it.countryCode,
                slug = it.slug,
                totalCase = it.totalConfirmed.getRoughNumber(),
                backgroundColor = colorsList[random.nextInt(colorsList.size)]
            )
        }
    }
}