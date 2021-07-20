package com.lms.weatherapp.common.network.model.weather

import com.google.gson.annotations.SerializedName
import com.lms.weatherapp.common.network.model.common.MetricImperialValues


data class CurrentConditionsResponse(
    @SerializedName("LocalObservationDateTime") val localObservationSateTime: String,
    @SerializedName("EpochTime") val epochTime: Long,
    @SerializedName("WeatherText") val weatherText: String,
    @SerializedName("WeatherIcon") val weatherIcon: Int,
    @SerializedName("HasPrecipitation") val hasPrecipitation: Boolean,
    @SerializedName("IsDayTime") val isDayTime: Boolean,
    @SerializedName("Temperature") val temperature: MetricImperialValues,
    @SerializedName("MobileLink") val mobileLink: String,
    @SerializedName("Link") val link: String,
    )
