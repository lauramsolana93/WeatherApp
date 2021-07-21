package com.lms.wheatherapp.weather.factory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lms.weatherapp.common.network.model.common.Imperial
import com.lms.weatherapp.common.network.model.common.Metric
import com.lms.weatherapp.common.network.model.common.MetricImperialValues
import com.lms.weatherapp.common.network.model.weather.*
import com.lms.weatherapp.weather.repository.WeatherRepository
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.factory.WeatherFactory
import com.lms.weatherapp.weather.factory.WeatherFactoryImpl
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.model.HourlyWeather
import com.lms.wheatherapp.TestCoroutineRule
import com.lms.wheatherapp.utils.forecastWeatherMock
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherFactoryUnitTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var repository: WeatherRepository
    private lateinit var factory: WeatherFactory

    private val listCurrentConditionsWeather = listOf(
        CurrentConditionsResponse("30º", 0L, "",7, false, false, MetricImperialValues(Metric(20.0,"", 1), Imperial(20.0,"", 1)),"", ""),
        CurrentConditionsResponse("30º", 0L, "",7, false, false, MetricImperialValues(Metric(20.0,"", 1), Imperial(20.0,"", 1)),"", ""),
        CurrentConditionsResponse("30º", 0L,"", 7, false, false, MetricImperialValues(Metric(20.0,"", 1), Imperial(20.0,"", 1)),"", ""),
        CurrentConditionsResponse("30º", 0L, "",7, false, false, MetricImperialValues(Metric(20.0,"", 1), Imperial(20.0,"", 1)),"", "")
    )
    private val listHourlyResponse = listOf(
        HourlyResponse("", 0L, 1, "", false, false, TemperatureHourly(20, "", 1), 1, "", ""),
        HourlyResponse("", 0L, 1, "", false, false, TemperatureHourly(20, "", 1), 1, "", ""),
        HourlyResponse("", 0L, 1, "", false, false, TemperatureHourly(20, "", 1), 1, "", "")
    )

    private lateinit var currentWeather: CurrentWeather
    private lateinit var forecastWeather: ForecastWeather

    @Before
    fun setup(){
        repository = mock()
        factory = WeatherFactoryImpl(repository)
        currentWeather = mock()
        forecastWeather = mock()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getWeatherConditions_shouldGetConditionsFromRepo(){
        testCoroutineRule.runBlockingTest {
            doReturn(listCurrentConditionsWeather)
                .`when`(repository)
                .getWeatherByLocationKey()
            factory.getWeatherByLocation()
            verify(repository).getWeatherByLocationKey()
        }

    }


    @ExperimentalCoroutinesApi
    @Test
    fun get5DaysForecast_shouldGet5DaysForecastFromRepo(){
        testCoroutineRule.runBlockingTest {
            doReturn(ForecastResponse(Headline("", 0L,1, "", "", "", 0L, "", "" ), listOf()))
                .`when`(repository)
                .get5DaysForecast()
            factory.get5DaysForecast()
            verify(repository).get5DaysForecast()
        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun getHourly12Hours_shouldGetHorulyFromRepo(){
        testCoroutineRule.runBlockingTest {
            doReturn(listHourlyResponse)
                .`when`(repository)
                .getHourly12Hours()
            factory.getHourly12Hours()
            verify(repository).getHourly12Hours()
        }

    }

}