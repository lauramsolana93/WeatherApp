package com.lms.weatherapp.common.repository

import com.lms.weatherapp.model.location.GeopositionResponse
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.network.WeatherApiService
import com.lms.weatherapp.weather.model.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepositoryImpl(
    private val api: WeatherApiService
): WeatherRepository {

    private var getLocationCall: Call<GeopositionResponse>? = null

    override fun getLocationKey(
        callback: RepositoryCallback<Location?, String>,location: String
    ) {
        getLocationCall?.cancel()
        getLocationCall = api.getLocationKeyBygeoposition(location)
        getLocationCall?.enqueue(wrapCallback(callback))
    }

    private fun wrapCallback(callback: RepositoryCallback<Location?, String>) =
        object : Callback<GeopositionResponse>{
            override fun onResponse(
                call: Call<GeopositionResponse>?,
                response: Response<GeopositionResponse>?
            ) {
               if(response != null && response.isSuccessful){
                   val loc = response.body()
                   if(loc != null){
                       callback.onSuccess(Location(loc.key))
                       return
                   }
               }
                callback.onError("Couldn't find location")
            }

            override fun onFailure(call: Call<GeopositionResponse>, t: Throwable) {
                callback.onError("Couldn't find location")
            }
        }
}