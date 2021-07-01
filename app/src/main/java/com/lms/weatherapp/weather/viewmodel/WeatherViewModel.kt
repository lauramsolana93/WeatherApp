package com.lms.weatherapp.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lms.weatherapp.common.repository.weather.WeatherRepository
import com.lms.weatherapp.weather.factory.WeatherFactory
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val factory: WeatherFactory
): ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<String>()
    private val weather = MutableLiveData<CurrentWeather>()
    private val forecast = MutableLiveData<ForecastWeather>()

    fun getLoading() : LiveData<Boolean> = loading
    fun getError() : LiveData<String> = error
    fun getCurrentWeather() : LiveData<CurrentWeather> = weather
    fun getForecatWeather(): LiveData<ForecastWeather> = forecast

    fun getCurrentWeatherByLocationKey(){
        factory.getWeatherByLocation(object : WeatherFactory.Callback{
            override fun onSuccess(any: Any) {
                val weatherList = any as List<CurrentWeather>
                weather.value = weatherList[0]
            }

            override fun onLoading(isLoading: Boolean) {
                loading.value = isLoading
            }

            override fun onError(e: String) {
                error.value = e
            }
        })
    }

    fun get5DaysForecastByLocationKey(){
        factory.get5DaysForecast(object : WeatherFactory.Callback {
            override fun onSuccess(any: Any) {
                forecast.value = any as ForecastWeather
            }

            override fun onLoading(isLoading: Boolean) {
                loading.value = isLoading
            }

            override fun onError(e: String) {
                error.value = e
            }
        })
    }


}