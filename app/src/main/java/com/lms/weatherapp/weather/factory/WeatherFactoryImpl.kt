package com.lms.weatherapp.weather.factory

import com.lms.weatherapp.common.utils.mapToCurrentWeather
import com.lms.weatherapp.weather.repository.WeatherRepository
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.model.HourlyWeather

class WeatherFactoryImpl(
    private val repository: WeatherRepository
) : WeatherFactory {
    override suspend fun getWeatherByLocation() : CurrentWeather {
        val currentWeatherResponse = repository.getWeatherByLocationKey()
        return currentWeatherResponse[0].mapToCurrentWeather()
    }

    override fun get5DaysForecast(callback: WeatherFactory.Callback) {
        repository.get5DaysForecast(
            object : RepositoryCallback<ForecastWeather, String>{
                override fun onSuccess(t: ForecastWeather) {
                    callback.onSuccess(t)
                }

                override fun onError(e: String) {
                    callback.onError(e)
                }

                override fun onLoading() {
                    callback.onLoading(true)
                }
            }
        )
    }

    override fun getCurrentLocationName(): String {
        return repository.getLocationName()
    }

    override fun getHourly12Hours(callback: WeatherFactory.Callback) {
        repository.getHourly12Hours(
            object : RepositoryCallback<List<HourlyWeather>, String>{
                override fun onSuccess(t: List<HourlyWeather>) {
                    callback.onSuccess(t)
                }

                override fun onLoading() {
                    callback.onLoading(true)
                }

                override fun onError(e: String) {
                    callback.onError(e)
                }
            }
        )
    }




}