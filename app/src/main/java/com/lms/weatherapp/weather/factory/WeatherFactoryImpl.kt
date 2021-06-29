package com.lms.weatherapp.weather.factory

import com.lms.weatherapp.common.repository.WeatherRepository
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.model.Location

class WeatherFactoryImpl(
    private val repository: WeatherRepository
) : WeatherFactory {

    override fun buildWeather(callback: WeatherFactory.Callback, location: String) {
        repository.getLocationKey(
            object : RepositoryCallback<Location?, String> {
                override fun onSuccess(t: Location?) {
                    callback.onSuccess(t as Location)
                }

                override fun onError(e: String) {
                    callback.onError(e)
                }

                override fun onLoading() {
                    callback.onLoading(true)
                }
            }
        , location)
    }

    override fun getWeatherByLocation(callback: WeatherFactory.Callback, location: Location) {
        //
    }


}