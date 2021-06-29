package com.lms.weatherapp.model.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Metric(
    @field:Json(name = "Value")val value : Int,
    @field:Json(name = "Unit") val unit: String,
    @field:Json(name = "UnitType") val unitType: Int,
)

@JsonClass(generateAdapter = true)
data class Imperial(
    @field:Json(name = "Value")val value : Int,
    @field:Json(name = "Unit") val unit: String,
    @field:Json(name = "UnitType") val unitType: Int,
)

@JsonClass(generateAdapter = true)
data class MetricImperialValues(
    @field:Json(name = "Metric") val metric: Metric,
    @field:Json(name = "Imperial") val imperial: Imperial,
)