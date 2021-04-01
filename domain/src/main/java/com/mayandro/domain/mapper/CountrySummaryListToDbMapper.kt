package com.mayandro.domain.mapper

import com.mayandro.domain.utils.toCountrySummaryEntity
import com.mayandro.local.entity.CountrySummaryEntity
import com.mayandro.remote.model.CountrySummary
import com.mayandro.utility.mapper.ObjectMapper

class CountrySummaryListToDbMapper: ObjectMapper<List<CountrySummary>, List<CountrySummaryEntity>>() {
    override fun mapFromOriginalObject(originalObject: List<CountrySummary>): List<CountrySummaryEntity> {
        return originalObject.map {
            it.toCountrySummaryEntity()
        }
    }
}