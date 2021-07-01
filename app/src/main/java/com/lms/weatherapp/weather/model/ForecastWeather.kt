package com.lms.weatherapp.weather.model

import com.google.gson.annotations.SerializedName
import com.lms.weatherapp.model.common.Metric

data class ForecastWeather(
    val forecast: List<DailyForecast>
)

data class DailyForecast(
    @SerializedName("Date") val date: String,
    @SerializedName("EpochDate") val epochDate: Long,
    @SerializedName("Temperature") val temperature: TemperatureForecast,
    @SerializedName("Day") val day: Day,
    @SerializedName("Night") val night: Night,
    @SerializedName("Sources") val sources: List<String>,
    @SerializedName("MobileLink") val mobileLink: String,
    @SerializedName("Link") val link: String


)

data class TemperatureForecast(
    @SerializedName("Minimum") val minimum: Metric,
    @SerializedName("Maximum") val maximum: Metric
)

data class Day(
    @SerializedName("Icon") val icon: Int,
    @SerializedName("IconPhrase") val iconPhrase: String,
    @SerializedName("HasPrecipitation") val hasPrecipitation: Boolean
)

data class Night(
    @SerializedName("Icon") val icon: Int,
    @SerializedName("IconPhrase") val iconPhrase: String,
    @SerializedName("HasPrecipitation") val hasPrecipitation: Boolean
)