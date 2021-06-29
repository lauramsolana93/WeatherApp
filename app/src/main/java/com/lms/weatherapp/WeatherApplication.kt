package com.lms.weatherapp

import android.app.Application
import android.content.Context
import com.lms.weatherapp.network.WeatherApiService
import com.lms.weatherapp.common.repository.location.LocationRepository
import com.lms.weatherapp.common.repository.location.LocationRepositoryImpl
import com.lms.weatherapp.common.repository.weather.WeatherRepository
import com.lms.weatherapp.common.repository.weather.WeatherRepositoryImpl
import com.lms.weatherapp.location.factory.LocationFactory
import com.lms.weatherapp.location.factory.LocationFactoryImpl
import com.lms.weatherapp.weather.factory.WeatherFactory
import com.lms.weatherapp.weather.factory.WeatherFactoryImpl

class WeatherApplication: Application() {

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepositoryImpl(
            WeatherApiService.create(),
            getSharedPreferences("Weather", Context.MODE_PRIVATE)
        )
    }

    val weatherFactory: WeatherFactory by lazy {
        WeatherFactoryImpl(weatherRepository)
    }

    val locationRepository: LocationRepository by lazy {
        LocationRepositoryImpl(
            WeatherApiService.create(),
            getSharedPreferences("Weather", Context.MODE_PRIVATE)
        )
    }

    val locationFactory: LocationFactory by lazy {
        LocationFactoryImpl(locationRepository)
    }
}