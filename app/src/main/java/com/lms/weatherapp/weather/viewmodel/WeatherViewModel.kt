package com.lms.weatherapp.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lms.weatherapp.weather.factory.WeatherFactory
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.model.HourlyWeather
import com.lms.weatherapp.weather.repository.WeatherRepository
import kotlinx.coroutines.*

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val factory: WeatherFactory
): ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<String>()
    private val _weather = MutableLiveData<CurrentWeather>()
    private val _forecast = MutableLiveData<ForecastWeather>()
    private val _locationName = MutableLiveData<String>()
    private val _hourly12hours = MutableLiveData<List<HourlyWeather>>()

    fun getLoading() : LiveData<Boolean> = loading
    val error : LiveData<String> = _error
    val currentWeather : LiveData<CurrentWeather> = _weather
    fun getForecastWeather(): LiveData<ForecastWeather> = _forecast
    val forecast: LiveData<ForecastWeather> = _forecast
    val locationName : LiveData<String> = _locationName
    val hourly12hours : LiveData<List<HourlyWeather>> = _hourly12hours

    private val weatherJob = Job()

    fun getCurrentWeatherByLocationKey(){
        val errorHandler = CoroutineExceptionHandler{ _, exception ->
            loading.value = false
            _error.value = exception.message
        }
        val coroutineScope = CoroutineScope(weatherJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            val response = factory.getWeatherByLocation()
            loading.value = false
            _weather.value = response
        }
    }

    fun get5DaysForecastByLocationKey(){
        val errorHandler = CoroutineExceptionHandler{ _, exception ->
            loading.value = false
            _error.value = exception.message
        }
        val coroutineScope = CoroutineScope(weatherJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            val response = factory.get5DaysForecast()
            loading.value = false
            _forecast.value = response
        }
    }

    fun getHourly12Hours(){
        val errorHandler = CoroutineExceptionHandler{ _, exception ->
            loading.value = false
            _error.value = exception.message
        }
        val coroutineScope = CoroutineScope(weatherJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            val response = factory.getHourly12Hours()
            loading.value = false
            _hourly12hours.value = response
        }
    }

    fun locationName(){
        _locationName.value = repository.getLocationName()
    }


}