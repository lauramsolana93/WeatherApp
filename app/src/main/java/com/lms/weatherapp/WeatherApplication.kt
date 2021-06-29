package com.lms.weatherapp

import android.app.Application
import com.lms.weatherapp.network.WeatherApiService
import com.lms.weatherapp.common.repository.WeatherRepository
import com.lms.weatherapp.common.repository.WeatherRepositoryImpl
import com.lms.weatherapp.weather.factory.WeatherFactory
import com.lms.weatherapp.weather.factory.WeatherFactoryImpl

class WeatherApplication: Application() {

    val repository: WeatherRepository by lazy {
        WeatherRepositoryImpl(
            WeatherApiService.create()
        )
    }

    val weatherFactory: WeatherFactory by lazy {
        WeatherFactoryImpl(repository)
    }
}