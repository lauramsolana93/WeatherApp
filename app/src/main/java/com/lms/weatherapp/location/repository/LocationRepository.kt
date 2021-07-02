package com.lms.weatherapp.location.repository


import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.location.model.Location

interface LocationRepository {
    fun getLocationKey(callback: RepositoryCallback<Location?, String>, location: String)
    fun saveLocationKey(locationKey: String)
    fun saveLocationName(locationName: String)
    fun getLocationKey() : String
    fun getLocationName() : String
}

