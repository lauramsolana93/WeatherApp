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
        val errorHandler: CoroutineExceptionHandler = CoroutineExceptionHandler{ _, exception ->
            _error.value = exception.message
        }
        val coroutineScope = CoroutineScope(weatherJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            val response = factory.getWeatherByLocation()
            _weather.value = response
        }
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
                _error.value = e
            }
        })
    }

    fun getHourly12Hours(){
        factory.getHourly12Hours(object : WeatherFactory.Callback {
            override fun onSuccess(any: Any) {
                loading.value = false
                val hourlyWeatherList = any as List<HourlyWeather>
                _hourly12hours.value = hourlyWeatherList

            }

            override fun onError(e: String) {
                loading.value = false
                _error.value = e
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