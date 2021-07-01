package com.lms.wheatherapp.utils

import com.lms.weatherapp.model.common.Metric
import com.lms.weatherapp.weather.model.*

val temperatureForecastMock = TemperatureForecast(
    minimum = Metric(20.0, "", 1),
    maximum = Metric(20.0, "", 1)
)
val dayMock = Day(1, "", false)
val nightMock = Night(1, "", false)

val dailyForecastMock = DailyForecast(
    "23/01/2020",
    798789789L,
    temperatureForecastMock,
    dayMock,
    nightMock,
    listOf(),
    "",
    "")

val forecastWeatherMock = ForecastWeather(listOf(dailyForecastMock))


