package com.mayandro.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mayandro.local.utils.DbConstants

@Entity(tableName = DbConstants.CORONA_GLOBAL_SUMMARY_TABLE)
data class GlobalSummaryEntity(
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date : String,

    @ColumnInfo(name = "newConfirmed")
    val newConfirmed : Long,

    @ColumnInfo(name = "totalConfirmed")
    val totalConfirmed : Long,

    @ColumnInfo(name = "newDeaths")
    val newDeaths: Long,

    @ColumnInfo(name = "totalDeaths")
    val totalDeaths : Long,

    @ColumnInfo(name = "newRecovered")
    val newRecovered : Long,

    @ColumnInfo(name = "totalRecovered")
    val totalRecovered : Long
)

@Entity(tableName = DbConstants.CORONA_COUNTRY_SUMMARY_TABLE)
data class CountrySummaryEntity(
    @ColumnInfo(name = "id")
    val id : String,

    @PrimaryKey
    @ColumnInfo(name = "country")
    val country : String,

    @ColumnInfo(name = "countryCode")
    val countryCode : String,

    @ColumnInfo(name = "slug")
    val slug : String,

    @ColumnInfo(name = "date")
    val date : String,

    @ColumnInfo(name = "newConfirmed")
    val newConfirmed : Long,

    @ColumnInfo(name = "totalConfirmed")
    val totalConfirmed : Long,

    @ColumnInfo(name = "newDeaths")
    val newDeaths: Long,

    @ColumnInfo(name = "totalDeaths")
    val totalDeaths : Long,

    @ColumnInfo(name = "newRecovered")
    val newRecovered : Long,

    @ColumnInfo(name = "totalRecovered")
    val totalRecovered : Long
)