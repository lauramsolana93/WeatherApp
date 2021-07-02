package com.lms.weatherapp.location.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lms.weatherapp.location.repository.LocationRepository
import com.lms.weatherapp.location.factory.LocationFactory
import com.lms.weatherapp.location.model.Location

class LocationViewModel(
    private val repository: LocationRepository,
    private val factory: LocationFactory
): ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<String>()
    private val location = MutableLiveData<Location>()

    fun getLoading(): LiveData<Boolean> = loading
    fun getError(): LiveData<String> = error
    fun getLocation(): LiveData<Location> = location

    fun initLocation(loc: String){
        factory.buildLocation(object : LocationFactory.Callback {
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









}