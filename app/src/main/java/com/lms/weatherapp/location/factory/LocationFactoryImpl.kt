package com.lms.weatherapp.location.factory

import com.lms.weatherapp.common.network.model.location.GeopositionResponse
import com.lms.weatherapp.location.model.Location
import com.lms.weatherapp.location.repository.LocationRepository
import retrofit2.Response

class LocationFactoryImpl(
    private val repository: LocationRepository
): LocationFactory {


    override suspend fun buildLocation(location: String): Response<GeopositionResponse> {
        return repository.getLocationKey(location = location)
    }
}