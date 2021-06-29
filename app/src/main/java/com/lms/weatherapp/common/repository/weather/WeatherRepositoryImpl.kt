package com.lms.weatherapp.common.repository.weather

import android.content.SharedPreferences
import com.lms.weatherapp.common.utils.mapToCurrentWeather
import com.lms.weatherapp.model.weather.CurrentConditionsResponse
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.network.WeatherApiService
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.location.model.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
const val CURRENT_KEY = "CURRENT_KEY"
class WeatherRepositoryImpl(
    private val api: WeatherApiService,
    private val sharedPreferences: SharedPreferences
) : WeatherRepository {

    private var getCurrentWeather: Call<List<CurrentConditionsResponse>>? = null

    override fun getWeatherByLocationKey(
        callback: RepositoryCallback<List<CurrentWeather>, String>
    ) {
        getCurrentWeather?.cancel()
        getCurrentWeather = api.getCurrentConditionsByLocationKey(getCurrentLocationKey())
        val wrapCallback =
            object : Callback<List<CurrentConditionsResponse>> {
                override fun onResponse(
                    call: Call<List<CurrentConditionsResponse>>?,
                    response: Response<List<CurrentConditionsResponse>>?
                ) {
                    if (response != null && response.isSuccessful) {
                        val weatherListResponse = response.body()
                        val currentWeatherList = ArrayList<CurrentWeather>()
                        if (weatherListResponse != null) {
                            weatherListResponse.forEach {
                                currentWeatherList.add(it.mapToCurrentWeather())
                            }
                            callback.onSuccess(currentWeatherList.toList())
                            return
                        }
                    }
                    callback.onError("Couldn't find location")
                }

                override fun onFailure(call: Call<List<CurrentConditionsResponse>>, t: Throwable) {
                    callback.onError("Couldn't find location")
                }
            }
        getCurrentWeather?.enqueue(wrapCallback)
    }

    private fun getCurrentLocationKey(): String {
        return sharedPreferences.getString(CURRENT_KEY, "") ?: ""
    }

}