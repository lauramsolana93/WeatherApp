package com.lms.weatherapp.common.utils

import com.lms.weatherapp.model.weather.CurrentConditionsResponse
import com.lms.weatherapp.weather.model.CurrentWeather

fun CurrentConditionsResponse.mapToCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        localObservationDateTime = this.localObservationSateTime,
        temperature = "${this.temperature.metric.value} ${this.temperature.metric.unitType}",
        weatherIcon = this.weatherIcon,
        weatherText = this.weatherText
    )
}