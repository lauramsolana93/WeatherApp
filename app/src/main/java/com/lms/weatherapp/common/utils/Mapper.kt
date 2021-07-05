package com.lms.weatherapp.common.utils

import com.lms.weatherapp.model.weather.*
import com.lms.weatherapp.weather.model.*

fun CurrentConditionsResponse.mapToCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        localObservationDateTime = this.localObservationSateTime,
        temperature = "${this.temperature.metric.value}º${this.temperature.metric.unit}",
        weatherIcon = this.weatherIcon,
        weatherText = this.weatherText
    )
}

fun ForecastResponse.mapToForecastWeather(): ForecastWeather{
    var forecast = ArrayList<DailyForecast>()
    this.forecasts.forEach {
        forecast.add(it.mapToDailyForecast())
    }
    return ForecastWeather(
        forecast = forecast
    )
}

fun DailyForecastResponse.mapToDailyForecast(): DailyForecast{
    return DailyForecast(
        date = this.date,
        epochDate = this.epochDate,
        temperature = this.temperature.mapToTemperatureForecast(),
        day = this.day.mapToDay(),
        night = this.night.mapToNight(),
        sources = this.sources,
        mobileLink = this.mobileLink,
        link = this.link

    )
}

fun TemperatureForecastResponse.mapToTemperatureForecast(): TemperatureForecast{
    return TemperatureForecast(
        minimum = this.minimum,
        maximum = this.maximum
    )
}

fun DayResponse.mapToDay(): Day{
    return Day(
        icon = this.icon,
        iconPhrase = this.iconPhrase,
        hasPrecipitation = this.hasPrecipitation
    )
}

fun NightResponse.mapToNight(): Night{
    return Night(
        icon = this.icon,
        iconPhrase = this.iconPhrase,
        hasPrecipitation = this.hasPrecipitation
    )
}

fun HourlyResponse.mapToHourlyWeather(): HourlyWeather {
    return HourlyWeather(
        dateTime = this.dateTime,
        weatherIcon = this.weatherIcon,
        iconPhrase = this.iconPhrase,
        temperature = this.temperatureHourly.mapToTemperatureHourly(),
        precipitationProb = this.precipitationProbability
    )
}

fun TemperatureHourly.mapToTemperatureHourly(): TemperatureHourly {
    return TemperatureHourly(
        value = this.value,
        unit = this.unit,
        unitType = this.unitType
    )
}