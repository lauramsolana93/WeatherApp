package com.lms.weatherapp.location.repository

import android.content.SharedPreferences
import com.lms.weatherapp.model.location.GeopositionResponse
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.network.WeatherApiService
import com.lms.weatherapp.location.model.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
const val CURRENT_KEY = "CURRENT_KEY"
const val CURRENT_NAME = "CURRENT_NAME"
class LocationRepositoryImpl(
    private val api: WeatherApiService,
    private val sharedPreferences: SharedPreferences
) : LocationRepository {

    private var getLocationCall: Call<GeopositionResponse>? = null

    override fun getLocationKey(
        callback: RepositoryCallback<Location?, String>, location: String
    ) {
        getLocationCall?.cancel()
        getLocationCall = api.getLocationKeyBygeoposition(location)
        val wrapCallback =
            object : Callback<GeopositionResponse> {
                override fun onResponse(
                    call: Call<GeopositionResponse>?,
                    response: Response<GeopositionResponse>?
                ) {
                    if (response != null && response.isSuccessful) {
                        val loc = response.body()
                        if (loc != null) {
                            saveLocationKey(loc.key)
                            saveLocationName(loc.localizedName)
                            callback.onSuccess(Location(loc.key, loc.localizedName))
                            return
                        }
                    }
                    callback.onError("Couldn't find location")
                }

                override fun onFailure(call: Call<GeopositionResponse>, t: Throwable) {
                    callback.onError("Couldn't find location")
                }
            }
        getLocationCall?.enqueue(wrapCallback)
    }

    override fun saveLocationKey(locationKey: String){
        val currentLocationKey = getLocationKey()
        if(locationKey != currentLocationKey){
            val editor = sharedPreferences.edit()
            editor.putString(CURRENT_KEY, locationKey)
            editor.apply()
        }
    }

    override fun saveLocationName(locationName: String) {
        val currentLocationName = getLocationName()
        if(locationName != currentLocationName){
            val editor = sharedPreferences.edit()
            editor.putString(CURRENT_NAME, locationName)
            editor.apply()
        }
    }

    override fun getLocationKey(): String {
        return sharedPreferences.getString(CURRENT_KEY, "") ?: ""
    }

    override fun getLocationName(): String {
        return sharedPreferences.getString(CURRENT_NAME, "") ?: ""
    }



}