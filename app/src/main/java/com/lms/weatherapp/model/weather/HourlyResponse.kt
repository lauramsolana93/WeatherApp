package com.lms.weatherapp.model.weather

import com.google.gson.annotations.SerializedName

data class HourlyResponse(
    @SerializedName("DateTime") val dateTime: String,
    @SerializedName("EpochDateTime") val epochDateTime: Long,
    @SerializedName("WeatherIcon") val weatherIcon: Int,
    @SerializedName("IconPhrase") val iconPhrase: String,
    @SerializedName("HasPrecipitation") val hasPrecipitation: Boolean,
    @SerializedName("IsDaylight") val isDaylight: Boolean,
    @SerializedName("Temperature") val temperatureHourly: TemperatureHourly,
    @SerializedName("PrecipitationProbability") val precipitationProbability: Int,
    @SerializedName("MobileLink") val mobileLink: String,
    @SerializedName("Link") val link: String
)

data class TemperatureHourly(
    @SerializedName("Value") val value: Int,
    @SerializedName("Unit") val unit: String,
    @SerializedName("UnitType") val unitType: Int
)