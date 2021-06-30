package com.lms.weatherapp.model.weather

import com.google.gson.annotations.SerializedName
import com.lms.weatherapp.model.common.MetricImperialValues


data class CurrentConditionsResponse(
    @SerializedName("LocalObservationDateTime") val localObservationSateTime: String,
    @SerializedName("EpochTime") val epochTime: Long,
    @SerializedName("WeatherText") val weatherText: String,
    @SerializedName("WeatherIcon") val weatherIcon: Int,
    @SerializedName("HasPrecipitation") val hasPrecipitation: Boolean,
    @SerializedName("IsDayTime") val isDayTime: Boolean,
    @SerializedName("Temperature") val temperature: MetricImperialValues,
    /*@SerializedName("RealFeelTemperature") val realFeelTemperature: MetricImperialValues,
    @SerializedName("RealFeelTemperatureShade") val realFeelTemperatureShade: MetricImperialValues,
    @SerializedName("RelativeHumidity") val relativeHumidity: Int,
    @SerializedName("IndoorRelativeHumidity") val indoorRelativeHumidity: Int,
    @SerializedName("DewPoint") val dewPoint: MetricImperialValues,
    @SerializedName("Wind") val wind: Wind,
    @SerializedName("WindGust") val windGust: WindGust,
    @SerializedName("UVIndex") val uVIndex: Int,
    @SerializedName("UVIndexText") val uVIndexText: String,
    @SerializedName("Visibility") val visibility: MetricImperialValues,
    @SerializedName("ObstructionsToVisibility") val obstructionsToVisibility: String,
    @SerializedName("CloudCover") val cloudCover: Int,
    @SerializedName("Ceiling") val ceiling: MetricImperialValues,
    @SerializedName("Pressuere") val pressure: MetricImperialValues,
    @SerializedName("PressuereTendency") val pressureTendency: PressureTendency,
    @SerializedName("Past24HourTemperatureDeparture") val past24HourTemperatureDeparture: MetricImperialValues,
    @SerializedName("ApparentTemperature") val apparentTemperature: MetricImperialValues,
    @SerializedName("WindChillTemperature") val windChillTemperature: MetricImperialValues,
    @SerializedName("PressuereTendency") val wetBulbTemperature: MetricImperialValues,
    @SerializedName("Precip1hr") val precip1hr: MetricImperialValues,
    @SerializedName("PrecipitationSummary") val precipitationSummary: PrecipitationSummary,
    @SerializedName("TemperatureSummary") val temperatureSummary: TemperatureSummary,*/
    @SerializedName("MobileLink") val mobileLink: String,
    @SerializedName("Link") val link: String,
    )



data class Wind(
    @SerializedName("Direction") val direction: Direction,
    @SerializedName("Speed") val speed: MetricImperialValues,
)


data class Direction(
    @SerializedName("Degrees") val degrees: Int,
    @SerializedName("Localized") val localized: String,
    @SerializedName("English") val english: String
)


data class WindGust(
    @SerializedName("Speed") val speed: MetricImperialValues
)


data class PressureTendency(
    @SerializedName("LocalizedText") val localizedText: String,
    @SerializedName("Code") val code: String
)


data class PrecipitationSummary(
    @SerializedName("Precipitation") val precipitation : MetricImperialValues,
    @SerializedName("PastHour") val pastHour: MetricImperialValues,
    @SerializedName("Past3Hours") val past3Hours: MetricImperialValues,
    @SerializedName("Past6Hours") val past6Hours: MetricImperialValues,
    @SerializedName("Past9Hours") val past9Hours: MetricImperialValues,
    @SerializedName("Past12Hours") val past12Hours: MetricImperialValues,
    @SerializedName("Past18Hours") val past18Hours: MetricImperialValues,
    @SerializedName("Past24Hours") val past24Hours: MetricImperialValues,
)


data class TemperatureSummary(
    @SerializedName("Past6HourRange") val past6HourRange : PastHourRange,
    @SerializedName("Past12HourRange") val past12HourRange : PastHourRange,
    @SerializedName("Past24HourRange") val past24HourRange : PastHourRange,

    )

data class PastHourRange(
    @SerializedName("Minimum") val minimum : MetricImperialValues,
    @SerializedName("Maximum") val Maximum : MetricImperialValues,
)
