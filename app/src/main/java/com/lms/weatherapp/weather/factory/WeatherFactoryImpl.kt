package com.lms.weatherapp.weather.factory

import com.lms.weatherapp.common.repository.weather.WeatherRepository
import com.lms.weatherapp.location.model.Location
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather

class WeatherFactoryImpl(
    private val repository: WeatherRepository
) : WeatherFactory {
    override fun getWeatherByLocation(callback: WeatherFactory.Callback) {
        repository.getWeatherByLocationKey(
            object : RepositoryCallback<List<CurrentWeather>, String> {
                override fun onSuccess(t: List<CurrentWeather>) {
                    callback.onSuccess(t)
                }

                override fun onError(e: String) {
                    callback.onError(e)
                }

                override fun onLoading() {
                    callback.onLoading(true)
                }
            }
        ,)
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


}