package com.lms.weatherapp.weather.repository

import android.content.SharedPreferences
import com.lms.weatherapp.common.utils.mapToCurrentWeather
import com.lms.weatherapp.common.utils.mapToForecastWeather
import com.lms.weatherapp.common.utils.mapToHourlyWeather
import com.lms.weatherapp.common.network.model.weather.CurrentConditionsResponse
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.network.WeatherApiService
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.common.network.model.weather.ForecastResponse
import com.lms.weatherapp.common.network.model.weather.HourlyResponse
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.model.HourlyWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val CURRENT_KEY = "CURRENT_KEY"
const val CURRENT_NAME = "CURRENT_NAME"

class WeatherRepositoryImpl(
    private val api: WeatherApiService,
    private val sharedPreferences: SharedPreferences
) : WeatherRepository {

    private var getCurrentWeather: Call<List<CurrentConditionsResponse>>? = null
    private var get5DaysForecast: Call<ForecastResponse>? = null
    private var getHourly12hours: Call<List<HourlyResponse>>? = null

    override suspend fun getWeatherByLocationKey(): List<CurrentConditionsResponse> {
        return api.getCurrentConditionsByLocationKey(getLocationKey())
    }

    override fun getLocationKey(): String {
        return sharedPreferences.getString(CURRENT_KEY, "") ?: ""
    }

    override fun getLocationName(): String {
        return sharedPreferences.getString(CURRENT_NAME, "") ?: ""
    }

    override fun get5DaysForecast(callback: RepositoryCallback<ForecastWeather, String>) {
        get5DaysForecast?.cancel()
        get5DaysForecast = api.get5DaysForecast(getLocationKey())
        val wrapCallback =
            object : Callback<ForecastResponse> {
                override fun onResponse(
                    call: Call<ForecastResponse>,
                    response: Response<ForecastResponse>
                ) {
                    if (response != null && response.isSuccessful) {
                        val forecastResponse = response.body()
                        if (forecastResponse != null) {
                            callback.onSuccess(forecastResponse.mapToForecastWeather())
                            return
                        }
                    }
                    callback.onError("Couldn't find forecast")
                }

                override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                    callback.onError("Couldn't fin forecast")
                }
            }
        get5DaysForecast?.enqueue(wrapCallback)
    }

    override fun getHourly12Hours(callback: RepositoryCallback<List<HourlyWeather>, String>) {
        getHourly12hours?.cancel()
        getHourly12hours = api.getHourly12hours(getLocationKey())
        val wrapCallback =
            object : Callback<List<HourlyResponse>>{
                override fun onResponse(
                    call: Call<List<HourlyResponse>>,
                    response: Response<List<HourlyResponse>>
                ) {
                    if(response != null && response.isSuccessful){
                        val hourlyResponseList = response.body()
                        val hourlyList = ArrayList<HourlyWeather>()
                        if(hourlyResponseList != null){
                            hourlyResponseList.forEach {
                                hourlyList.add(it.mapToHourlyWeather())
                            }
                            callback.onSuccess(hourlyList)
                            return
                        }
                    }
                    callback.onError("Couldn't find hourly weather")
                }

                override fun onFailure(call: Call<List<HourlyResponse>>, t: Throwable) {
                    callback.onError("Couldn't find hourly weather")
                }
            }
        getHourly12hours?.enqueue(wrapCallback)
    }

}