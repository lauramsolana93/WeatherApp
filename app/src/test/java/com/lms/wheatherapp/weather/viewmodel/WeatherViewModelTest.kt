package com.lms.wheatherapp.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lms.weatherapp.common.repository.weather.WeatherRepository
import com.lms.weatherapp.weather.factory.WeatherFactory
import com.lms.weatherapp.weather.viewmodel.WeatherViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: WeatherRepository
    private lateinit var factory: WeatherFactory
    private lateinit var viewModel : WeatherViewModel

    @Before
    fun setup(){
        repository = mock()
        factory = mock()
        viewModel = WeatherViewModel(repository, factory)
    }

    @Test
    fun getCurrentWeather_shouldGetCurrentLocation(){
        viewModel.getCurrentWeatherByLocationKey()
        verify(factory).getWeatherByLocation(any())
    }

}
