package com.lms.wheatherapp.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.repository.WeatherRepository
import com.lms.weatherapp.weather.factory.WeatherFactory
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.viewmodel.WeatherViewModel
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: WeatherRepository
    private lateinit var factory: WeatherFactory
    private lateinit var viewModel : WeatherViewModel
    private lateinit var loadingObserver: Observer<Boolean>
    private lateinit var errorObserver: Observer<String>
    private lateinit var currentWeatherObserver: Observer<CurrentWeather>
    private lateinit var locationNameObserver : Observer<String>
    private lateinit var forecastObserver: Observer<ForecastWeather>
    private lateinit var currentWeather: CurrentWeather
    private lateinit var forecastWeather: ForecastWeather

    @Before
    fun setup(){
        repository = mock()
        factory = mock()
        viewModel = WeatherViewModel(repository, factory)

        loadingObserver = mock()
        errorObserver = mock()
        currentWeatherObserver = mock()
        locationNameObserver = mock()
        forecastObserver = mock()

        viewModel.getLoading().observeForever(loadingObserver)
        viewModel.getCurrentWeather().observeForever(currentWeatherObserver)
        viewModel.getError().observeForever(errorObserver)
        viewModel.getLocationName().observeForever(locationNameObserver)
        viewModel.getForecastWeather().observeForever(forecastObserver)

        currentWeather = mock()
        forecastWeather = mock()

    }

    @Test
    fun getCurrentWeather_shouldGetCurrentLocation(){
        viewModel.getCurrentWeatherByLocationKey()
        verify(factory).getWeatherByLocation(any())
    }

    @Test
    fun get5DaysForecast_shouldGetForecast(){
        viewModel.get5DaysForecastByLocationKey()
        verify(factory).get5DaysForecast(any())
    }

    @Test
    fun getCurrentWeather_shouldHideLoading_whenFactoryReturnsError(){
        setUpFactoryWithErrorCurrentWeather()
        viewModel.getCurrentWeatherByLocationKey()
        verify(loadingObserver).onChanged(eq(false))
    }

    @Test
    fun get5DaysForecast_shouldHideLoading_whenFactoryReturnsError(){
        setUpFactoryWithErrorForecastWeather()
        viewModel.get5DaysForecastByLocationKey()
        verify(loadingObserver).onChanged(eq(false))
    }


    private fun setUpFactoryWithErrorCurrentWeather(){
        doAnswer {
            val callback: WeatherFactory.Callback = it.getArgument(0)
            callback.onError("Error")
        }.whenever(factory).getWeatherByLocation(any())
    }

    private fun setUpFactoryWithErrorForecastWeather(){
        doAnswer {
            val callback: WeatherFactory.Callback = it.getArgument(0)
            callback.onError("Error")
        }.whenever(factory).get5DaysForecast(any())
    }

}
