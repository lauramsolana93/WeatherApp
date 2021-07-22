package com.lms.weatherapp.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _error = MutableLiveData<String>()
    private val _weather = MutableLiveData<CurrentWeather>()
    private val _forecast = MutableLiveData<ForecastWeather>()
    private val _locationName = MutableLiveData<String>()
    private val _hourly12hours = MutableLiveData<List<HourlyWeather>>()

    val error : LiveData<String> = _error
    val currentWeather : LiveData<CurrentWeather> = _weather
    val forecast: LiveData<ForecastWeather> = _forecast
    val locationName : LiveData<String> = _locationName
    val hourly12hours : LiveData<List<HourlyWeather>> = _hourly12hours


    fun getCurrentWeatherByLocationKey(){
        val errorHandler = CoroutineExceptionHandler{ _, exception ->
            _error.value = exception.message
        }

        viewModelScope.launch(errorHandler) {
            val response = factory.getWeatherByLocation()
            _weather.value = response
        }
    }

    fun get5DaysForecastByLocationKey(){
        val errorHandler = CoroutineExceptionHandler{ _, exception ->
            _error.value = exception.message
        }

        viewModelScope.launch(errorHandler) {
            val response = factory.get5DaysForecast()
            _forecast.value = response
        }
    }

    fun getHourly12Hours(){
        val errorHandler = CoroutineExceptionHandler{ _, exception ->
            _error.value = exception.message
        }

        viewModelScope.launch(errorHandler) {
            val response = factory.getHourly12Hours()
            _hourly12hours.value = response
        }
    }

    fun locationName(){
        _locationName.value = repository.getLocationName()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}