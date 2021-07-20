package com.lms.weatherapp.location.factory

import com.lms.weatherapp.location.model.Location
import com.lms.weatherapp.location.repository.LocationRepository

class LocationFactoryImpl(
    private val repository: LocationRepository
): LocationFactory {


    override suspend fun buildLocation(location: String): Location {
        val geopositionResponse =  repository.getLocationKey(location = location)
        return Location(geopositionResponse.key, geopositionResponse.localizedName)
    }
}