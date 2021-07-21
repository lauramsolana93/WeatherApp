package com.lms.wheatherapp.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lms.weatherapp.weather.factory.WeatherFactory
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.model.HourlyWeather
import com.lms.weatherapp.weather.repository.WeatherRepository
import com.lms.weatherapp.weather.viewmodel.WeatherViewModel
import com.lms.wheatherapp.TestCoroutineRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var repository: WeatherRepository
    private lateinit var factory: WeatherFactory
    private lateinit var viewModel : WeatherViewModel
    private lateinit var currentWeather: CurrentWeather
    private lateinit var forecastWeather: ForecastWeather

    @Before
    fun setup(){
        repository = mock()
        factory = mock()
        viewModel = WeatherViewModel(repository, factory)

        currentWeather = mock()
        forecastWeather = mock()

    }

    @ExperimentalCoroutinesApi
    @Test
    fun getCurrentWeather_shouldGetCurrentLocation(){
        testCoroutineRule.runBlockingTest {
            doReturn(currentWeather)
                .`when`(factory)
                .getWeatherByLocation()
            viewModel.getCurrentWeatherByLocationKey()
            verify(factory).getWeatherByLocation()
        }



    }

    @ExperimentalCoroutinesApi
    @Test
    fun get5DaysForecast_shouldGetForecast(){
        testCoroutineRule.runBlockingTest {
            doReturn(forecastWeather)
                .`when`(factory)
                .get5DaysForecast()
            viewModel.get5DaysForecastByLocationKey()
            verify(factory).get5DaysForecast()
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun getHourly12Hours_shouldGetHourly(){
        testCoroutineRule.runBlockingTest {
            doReturn(listOf<HourlyWeather>())
                .`when`(factory)
                .getHourly12Hours()
            viewModel.getHourly12Hours()
            verify(factory).getHourly12Hours()
        }

    }

}
