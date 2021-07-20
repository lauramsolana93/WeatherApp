package com.lms.weatherapp.location.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lms.weatherapp.location.repository.LocationRepository
import com.lms.weatherapp.location.factory.LocationFactory
import com.lms.weatherapp.location.model.Location
import kotlinx.coroutines.*

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

    private val locationJob : CompletableJob = Job()

    fun initLocation(loc: String){
        val errorHandler: CoroutineExceptionHandler = CoroutineExceptionHandler{ _, exception ->
            error.value = exception.message
        }

        val coroutineScope : CoroutineScope = CoroutineScope(locationJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            val response = factory.buildLocation(loc)
            location.value = response
            loading.value = false
        }
    }


    override fun onCleared() {
        super.onCleared()
        locationJob.cancel()
    }









}