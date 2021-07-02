package com.lms.weatherapp.location.factory

import com.lms.weatherapp.location.repository.LocationRepository
import com.lms.weatherapp.location.model.Location
import com.lms.weatherapp.network.RepositoryCallback

class LocationFactoryImpl(
    private val repository: LocationRepository
): LocationFactory {

    override fun buildLocation(callback: LocationFactory.Callback, location: String) {
        repository.getLocationKey(
            object : RepositoryCallback<Location?, String> {
                override fun onSuccess(t: Location?) {
                    callback.onSuccess(t as Location)
                }

                override fun onError(e: String) {
                    callback.onError(e)
                }

                override fun onLoading() {
                    callback.onLoading(true)
                }
            }
            , location)
    }
}