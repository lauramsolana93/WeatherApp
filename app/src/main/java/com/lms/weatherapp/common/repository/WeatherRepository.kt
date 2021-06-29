package com.lms.weatherapp.common.repository


import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.model.Location

interface WeatherRepository {
    fun getLocationKey(callback: RepositoryCallback<Location?, String>, location: String)
}

