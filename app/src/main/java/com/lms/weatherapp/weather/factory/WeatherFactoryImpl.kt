package com.lms.weatherapp.weather.factory

import com.lms.weatherapp.common.utils.mapToCurrentWeather
import com.lms.weatherapp.common.utils.mapToForecastWeather
import com.lms.weatherapp.common.utils.mapToHourlyWeather
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.model.HourlyWeather
import com.lms.weatherapp.weather.repository.WeatherRepository

class WeatherFactoryImpl(
    private val repository: WeatherRepository
) : WeatherFactory {

    override suspend fun getWeatherByLocation() : CurrentWeather {
        val currentWeatherResponse = repository.getWeatherByLocationKey()
        return currentWeatherResponse[0].mapToCurrentWeather()
    }

    override suspend fun get5DaysForecast(): ForecastWeather {
        val forecastResponse = repository.get5DaysForecast()
        return forecastResponse.mapToForecastWeather()
    }

    override fun getCurrentLocationName(): String {
        return repository.getLocationName()
    }

    override suspend fun getHourly12Hours() : List<HourlyWeather> {
        val hourlyResponse = repository.getHourly12Hours()
        val hourlyList = ArrayList<HourlyWeather>()
        hourlyResponse.forEach {
            hourlyList.add(it.mapToHourlyWeather())
        }
        return hourlyList
    }




}