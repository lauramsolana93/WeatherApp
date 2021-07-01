package com.lms.weatherapp.model.common

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Metric(
    @SerializedName("Value")val value : Double,
    @SerializedName("Unit") val unit: String,
    @SerializedName("UnitType") val unitType: Int,
)

@JsonClass(generateAdapter = true)
data class Imperial(
    @SerializedName("Value")val value : Double,
    @SerializedName("Unit") val unit: String,
    @SerializedName("UnitType") val unitType: Int,
)

@JsonClass(generateAdapter = true)
data class MetricImperialValues(
    @SerializedName("Metric") val metric: Metric,
    @SerializedName("Imperial") val imperial: Imperial,
)