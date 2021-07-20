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

    override suspend fun get5DaysForecast(): ForecastResponse {
        return api.get5DaysForecast(getLocationKey())
    }

    override suspend fun getHourly12Hours() : List<HourlyResponse> {
        return api.getHourly12hours(getLocationKey())
    }

}