package com.lms.weatherapp.common.utils

import com.lms.weatherapp.model.weather.CurrentConditionsResponse
import com.lms.weatherapp.weather.model.CurrentWeather

fun CurrentConditionsResponse.mapToCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        temperature = "${this.temperature.metric.value} ${this.temperature.metric.unitType}",
        realFeelTemperature = "${this.realFeelTemperature.metric.value} ${this.realFeelTemperature.metric.unitType}",
        visibility = "${this.visibility.metric.unit} ${this.visibility.metric.unit}",
        uVIIndex = this.uVIndexText,
        wind = "${this.wind.direction} ${this.wind.speed}",
        weatherIcon = this.weatherIcon
    )
}