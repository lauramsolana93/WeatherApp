package com.lms.weatherapp.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lms.weatherapp.ui.model.Hourly
import com.lms.weatherapp.weather.repository.WeatherRepository
import com.lms.weatherapp.weather.factory.WeatherFactory
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.model.HourlyWeather

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val factory: WeatherFactory
): ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<String>()
    private val weather = MutableLiveData<CurrentWeather>()
    private val _forecast = MutableLiveData<ForecastWeather>()
    private val _locationName = MutableLiveData<String>()
    private val hourly12hours = MutableLiveData<List<HourlyWeather>>()

    fun getLoading() : LiveData<Boolean> = loading
    fun getError() : LiveData<String> = error
    fun getCurrentWeather() : LiveData<CurrentWeather> = weather
    fun getForecastWeather(): LiveData<ForecastWeather> = _forecast
    val forecast: LiveData<ForecastWeather> = _forecast
    //fun getLocationName(): LiveData<String> = _locationName
    val locationName : LiveData<String> = _locationName
    fun getHourly() : LiveData<List<HourlyWeather>> = hourly12hours

    fun getCurrentWeatherByLocationKey(){
        factory.getWeatherByLocation(object : WeatherFactory.Callback{
            override fun onSuccess(any: Any) {
                loading.value = false
                val weatherList = any as List<CurrentWeather>
                weather.value = weatherList[0]
            }

            override fun onLoading(isLoading: Boolean) {
                loading.value = isLoading
            }

            override fun onError(e: String) {
                loading.value = false
                error.value = e
            }
        })
    }

    fun get5DaysForecastByLocationKey(){
        factory.get5DaysForecast(object : WeatherFactory.Callback {
            override fun onSuccess(any: Any) {
                locationName()
                loading.value = false
                _forecast.value = any as ForecastWeather
            }

            override fun onLoading(isLoading: Boolean) {
                loading.value = isLoading
            }

            override fun onError(e: String) {
                loading.value = false
                error.value = e
            }
        })
    }

    fun getHourly12Hours(){
        factory.getHourly12Hours(object : WeatherFactory.Callback {
            override fun onSuccess(any: Any) {
                loading.value = false
                val hourlyWeatherList = any as List<HourlyWeather>
                hourly12hours.value = hourlyWeatherList

            }

            override fun onError(e: String) {
                loading.value = false
                error.value = e
            }

            override fun onLoading(isLoading: Boolean) {
                loading.value = true
            }
        }
        )
    }

    fun locationName(){
        _locationName.value = repository.getLocationName()
    }


}