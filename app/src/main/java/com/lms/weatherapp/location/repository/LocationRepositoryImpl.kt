package com.lms.weatherapp.location.repository

import android.content.SharedPreferences
import com.lms.weatherapp.common.network.model.location.GeopositionResponse
import com.lms.weatherapp.network.WeatherApiService
import retrofit2.Response

const val CURRENT_KEY = "CURRENT_KEY"
const val CURRENT_NAME = "CURRENT_NAME"
class LocationRepositoryImpl(
    private val api: WeatherApiService,
    private val sharedPreferences: SharedPreferences
) : LocationRepository {


    override suspend fun getLocationKey(location: String): Response<GeopositionResponse> {
        return api.getLocationKeyBygeoposition(location)
    }


    override fun saveLocationKey(locationKey: String){
        val currentLocationKey = getLocationKeyFromSharedPreferences()
        if(locationKey != currentLocationKey){
            val editor = sharedPreferences.edit()
            editor.putString(CURRENT_KEY, locationKey)
            editor.apply()
        }
    }

    override fun saveLocationName(locationName: String) {
        val currentLocationName = getLocationNameFromSharedPreferences()
        if(locationName != currentLocationName){
            val editor = sharedPreferences.edit()
            editor.putString(CURRENT_NAME, locationName)
            editor.apply()
        }
    }

    override fun getLocationKeyFromSharedPreferences(): String {
        return sharedPreferences.getString(CURRENT_KEY, "") ?: ""
    }

    override fun getLocationNameFromSharedPreferences(): String {
        return sharedPreferences.getString(CURRENT_NAME, "") ?: ""
    }



}