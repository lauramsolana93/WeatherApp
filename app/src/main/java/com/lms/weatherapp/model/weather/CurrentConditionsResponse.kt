package com.lms.weatherapp.model.weather

import com.lms.weatherapp.model.common.MetricImperialValues
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentConditionsResponse(
    @field:Json(name = "LocalObservationDateTime") val localObservationSateTime: String,
    @field:Json(name = "EpochTime") val epochTime: Long,
    @field:Json(name = "WeatherText") val weatherText: String,
    @field:Json(name = "WeatherIcon") val weatherIcon: Int,
    @field:Json(name = "HasPrecipitation") val hasPrecipitation: Boolean,
    @field:Json(name = "IsDayTime") val isDayTime: Boolean,
    @field:Json(name = "Temperature") val temperature: MetricImperialValues,
    @field:Json(name = "RealFeelTemperature") val realFeelTemperature: MetricImperialValues,
    @field:Json(name = "RealFeelTemperatureShade") val realFeelTemperatureShade: MetricImperialValues,
    @field:Json(name = "RelativeHumidity") val relativeHumidity: Int,
    @field:Json(name = "IndoorRelativeHumidity") val indoorRelativeHumidity: Int,
    @field:Json(name = "DewPoint") val dewPoint: MetricImperialValues,
    @field:Json(name = "Wind") val wind: Wind,
    @field:Json(name = "WindGust") val windGust: WindGust,
    @field:Json(name = "UVIndex") val uVIndex: Int,
    @field:Json(name = "UVIndexText") val uVIndexText: String,
    @field:Json(name = "Visibility") val visibility: MetricImperialValues,
    @field:Json(name = "ObstructionsToVisibility") val obstructionsToVisibility: String,
    @field:Json(name = "CloudCover") val cloudCover: Int,
    @field:Json(name = "Ceiling") val ceiling: MetricImperialValues,
    @field:Json(name = "Pressuere") val pressure: MetricImperialValues,
    @field:Json(name = "PressuereTendency") val pressureTendency: PressureTendency,
    @field:Json(name = "Past24HourTemperatureDeparture") val past24HourTemperatureDeparture: MetricImperialValues,
    @field:Json(name = "ApparentTemperature") val apparentTemperature: MetricImperialValues,
    @field:Json(name = "WindChillTemperature") val windChillTemperature: MetricImperialValues,
    @field:Json(name = "PressuereTendency") val wetBulbTemperature: MetricImperialValues,
    @field:Json(name = "Precip1hr") val precip1hr: MetricImperialValues,
    @field:Json(name = "PrecipitationSummary") val precipitationSummary: PrecipitationSummary,
    @field:Json(name = "TemperatureSummary") val temperatureSummary: TemperatureSummary,
    @field:Json(name = "MobileLink") val mobileLink: String,
    @field:Json(name = "Link") val link: String,
    )


@JsonClass(generateAdapter = true)
data class Wind(
    @field:Json(name = "Direction") val direction: Direction,
    @field:Json(name = "Speed") val speed: MetricImperialValues,
)

@JsonClass(generateAdapter = true)
data class Direction(
    @field:Json(name = "Degrees") val degrees: Int,
    @field:Json(name = "Localized") val localized: String,
    @field:Json(name = "English") val english: String
)

@JsonClass(generateAdapter = true)
data class WindGust(
    @field:Json(name = "Speed") val speed: MetricImperialValues
)

@JsonClass(generateAdapter = true)
data class PressureTendency(
    @field:Json(name = "LocalizedText") val localizedText: String,
    @field:Json(name = "Code") val code: String
)

@JsonClass(generateAdapter = true)
data class PrecipitationSummary(
    @field:Json(name = "Precipitation") val precipitation : MetricImperialValues,
    @field:Json(name = "PastHour") val pastHour: MetricImperialValues,
    @field:Json(name = "Past3Hours") val past3Hours: MetricImperialValues,
    @field:Json(name = "Past6Hours") val past6Hours: MetricImperialValues,
    @field:Json(name = "Past9Hours") val past9Hours: MetricImperialValues,
    @field:Json(name = "Past12Hours") val past12Hours: MetricImperialValues,
    @field:Json(name = "Past18Hours") val past18Hours: MetricImperialValues,
    @field:Json(name = "Past24Hours") val past24Hours: MetricImperialValues,
)

@JsonClass(generateAdapter = true)
data class TemperatureSummary(
    @field:Json(name = "Past6HourRange") val past6HourRange : PastHourRange,
    @field:Json(name = "Past12HourRange") val past12HourRange : PastHourRange,
    @field:Json(name = "Past24HourRange") val past24HourRange : PastHourRange,

)
@JsonClass(generateAdapter = true)
data class PastHourRange(
    @field:Json(name = "Minimum") val minimum : MetricImperialValues,
    @field:Json(name = "Maximum") val Maximum : MetricImperialValues,
)
