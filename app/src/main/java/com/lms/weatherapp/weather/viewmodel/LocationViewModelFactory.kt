package com.lms.weatherapp.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lms.weatherapp.common.repository.WeatherRepository
import com.lms.weatherapp.weather.factory.WeatherFactory

class LocationViewModelFactory(
    private val repository: WeatherRepository,
    private val weatherFactory: WeatherFactory
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LocationViewModel(repository, weatherFactory) as T
    }
}