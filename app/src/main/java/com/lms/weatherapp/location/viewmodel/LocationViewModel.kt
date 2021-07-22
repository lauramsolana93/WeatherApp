package com.lms.weatherapp.location.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lms.weatherapp.location.repository.LocationRepository
import com.lms.weatherapp.location.factory.LocationFactory
import com.lms.weatherapp.location.model.Location
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi

class LocationViewModel(
    private val repository: LocationRepository,
    private val factory: LocationFactory
): ViewModel() {

    private val error = MutableLiveData<String>()
    private val location = MutableLiveData<Location>()

    fun getError(): LiveData<String> = error
    fun getLocation(): LiveData<Location> = location


    fun initLocation(loc: String){
        val errorHandler = CoroutineExceptionHandler{ _, exception ->
            error.value = exception.message
        }

        viewModelScope.launch(errorHandler) {
            val response = factory.buildLocation(loc)
            location.value = response
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }









}