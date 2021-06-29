package com.lms.weatherapp.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lms.weatherapp.common.repository.WeatherRepository
import com.lms.weatherapp.weather.factory.WeatherFactory
import com.lms.weatherapp.weather.model.Location

class LocationViewModel(
    private val repository: WeatherRepository,
    private val factory: WeatherFactory
): ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<String>()
    private val location = MutableLiveData<Location>()

    fun getLoading(): LiveData<Boolean> = loading
    fun getError(): LiveData<String> = error
    fun getLocation(): LiveData<Location> = location

    fun initLocation(loc: String){
        factory.buildWeather(object : WeatherFactory.Callback {
            override fun onSuccess(any: Any) {
               location.value = any as Location
            }

            override fun onError(e: String) {
                error.value = e
            }

            override fun onLoading(isLoading: Boolean) {
                loading.value = isLoading
            }
        }, loc)
    }

    fun getWetherByLocation(){
        factory.getWeatherByLocation(object : WeatherFactory.Callback {
            override fun onSuccess(any: Any) {

            }

            override fun onError(e: String) {

            }

            override fun onLoading(isLoading: Boolean) {

            }
        }, getLocation().value ?: Location(""))
    }

}