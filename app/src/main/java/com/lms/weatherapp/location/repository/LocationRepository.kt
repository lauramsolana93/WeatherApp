package com.lms.weatherapp.location.repository



import com.lms.weatherapp.common.network.model.location.GeopositionResponse
import retrofit2.Response


interface LocationRepository {
    //fun getLocationKey(callback: RepositoryCallback<Location?, String>, location: String)
    suspend fun getLocationKey(location: String): Response<GeopositionResponse>
    fun saveLocationKey(locationKey: String)
    fun saveLocationName(locationName: String)
    fun getLocationKeyFromSharedPreferences() : String
    fun getLocationNameFromSharedPreferences() : String
}

