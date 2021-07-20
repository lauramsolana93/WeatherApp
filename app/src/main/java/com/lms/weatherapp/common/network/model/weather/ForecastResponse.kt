package com.lms.weatherapp.common.network.model.weather

import com.google.gson.annotations.SerializedName
import com.lms.weatherapp.common.network.model.common.Metric

data class ForecastResponse(
    @SerializedName("Headline") val headline: Headline,
    @SerializedName("DailyForecasts") val forecasts :List<DailyForecastResponse>
)

data class Headline(
    @SerializedName("EffectiveDate") val effectiveDate: String,
    @SerializedName("EffectiveEpochDate") val effectiveEpochDate: Long,
    @SerializedName("Severity") val severity: Int,
    @SerializedName("Text") val text: String,
    @SerializedName("Category") val category: String,
    @SerializedName("EndDate") val endDate: String,
    @SerializedName("EndEpochDate") val endEpochDate: Long,
    @SerializedName("MobileLink") val mobileLink: String,
    @SerializedName("Link") val link: String
)
data class DailyForecastResponse(
    @SerializedName("Date") val date: String,
    @SerializedName("EpochDate") val epochDate: Long,
    @SerializedName("Temperature") val temperature: TemperatureForecastResponse,
    @SerializedName("Day") val day: DayResponse,
    @SerializedName("Night") val night: NightResponse,
    @SerializedName("Sources") val sources: List<String>,
    @SerializedName("MobileLink") val mobileLink: String,
    @SerializedName("Link") val link: String


)

data class TemperatureForecastResponse(
    @SerializedName("Minimum") val minimum: Metric,
    @SerializedName("Maximum") val maximum: Metric
)

data class DayResponse(
    @SerializedName("Icon") val icon: Int,
    @SerializedName("IconPhrase") val iconPhrase: String,
    @SerializedName("HasPrecipitation") val hasPrecipitation: Boolean
)

data class NightResponse(
    @SerializedName("Icon") val icon: Int,
    @SerializedName("IconPhrase") val iconPhrase: String,
    @SerializedName("HasPrecipitation") val hasPrecipitation: Boolean
)