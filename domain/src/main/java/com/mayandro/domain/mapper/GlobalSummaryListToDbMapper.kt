package com.mayandro.domain.mapper

import com.mayandro.domain.utils.toGlobalSummaryEntity
import com.mayandro.local.entity.GlobalSummaryEntity
import com.mayandro.remote.model.GlobalSummary
import com.mayandro.utility.mapper.ObjectMapper

class GlobalSummaryListToDbMapper: ObjectMapper<List<GlobalSummary>, List<GlobalSummaryEntity>>() {
    override fun mapFromOriginalObject(originalObject: List<GlobalSummary>): List<GlobalSummaryEntity> {
        return originalObject.map {
            it.toGlobalSummaryEntity()
        }
    }
}