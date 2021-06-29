package com.lms.weatherapp.common.repository.location


import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.location.model.Location

interface LocationRepository {
    fun getLocationKey(callback: RepositoryCallback<Location?, String>, location: String)
}

