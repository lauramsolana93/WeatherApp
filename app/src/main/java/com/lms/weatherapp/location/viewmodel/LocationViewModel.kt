package com.lms.weatherapp.location.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lms.weatherapp.common.network.model.location.GeopositionResponse
import com.lms.weatherapp.common.utils.Resource
import com.lms.weatherapp.common.utils.mapToLocation
import com.lms.weatherapp.location.factory.LocationFactory
import com.lms.weatherapp.location.model.Location
import com.lms.weatherapp.location.repository.LocationRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Response


class LocationViewModel(
    private val repository: LocationRepository,
    private val factory: LocationFactory
): ViewModel() {

    val location = MutableLiveData<Resource<Location>>()

    fun initLocation(loc: String){
        location.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response = factory.buildLocation(loc)
                location.postValue(handleResponse(response))
            } catch (t: Throwable){
                location.postValue(Resource.Error("Couldn't find the location"))
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    private fun handleResponse(response: Response<GeopositionResponse>) : Resource<Location>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                val result = resultResponse.mapToLocation()
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }









}