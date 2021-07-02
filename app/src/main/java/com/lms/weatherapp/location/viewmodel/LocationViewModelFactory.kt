package com.lms.weatherapp.location.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lms.weatherapp.location.repository.LocationRepository
import com.lms.weatherapp.location.factory.LocationFactory

class LocationViewModelFactory(
    private val repository: LocationRepository,
    private val locationFactory: LocationFactory
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LocationViewModel(repository, locationFactory) as T
    }
}