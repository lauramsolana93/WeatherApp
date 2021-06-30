package com.lms.wheatherapp.weather.factory

import com.lms.weatherapp.common.repository.weather.WeatherRepository
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.factory.WeatherFactory
import com.lms.weatherapp.weather.factory.WeatherFactoryImpl
import com.lms.weatherapp.weather.model.CurrentWeather
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherFactoryUnitTest {

    private lateinit var repository: WeatherRepository
    private lateinit var factory: WeatherFactory
    private val listCurrentWeather = listOf(
        CurrentWeather("30º", "32º", 7, "nublado"),
        CurrentWeather("30º", "32º", 7, "nublado"),
        CurrentWeather("30º", "32º", 7, "nublado"),
        CurrentWeather("30º", "32º", 7, "nublado")
    )

    @Before
    fun setup(){
        repository = mock()
        factory = WeatherFactoryImpl(repository)
    }

    @Test
    fun getWeatherConditions_shouldGetConditionsFromRepo(){
        factory.getWeatherByLocation(mock())
    }

    @Test
    fun getWeatherConditions_shouldCallOnSuccess(){
        val callback = mock<WeatherFactory.Callback>()
        setUpRepositoryGetConditionsOnSuccess(repository)
        factory.getWeatherByLocation(callback)
        verify(callback).onSuccess(listCurrentWeather)
    }

    @Test
    fun getWeatherConditions_shouldCallOnError(){
        val callback = mock<WeatherFactory.Callback>()
        setUpRepositoryWithError(repository)
        factory.getWeatherByLocation(callback)
        verify(callback).onError("Error")
    }

    @Test
    fun getWeatherConditions_shouldCallOnLoading(){
        val callback = mock<WeatherFactory.Callback>()
        setUpRepositoryWithLoading(repository)
        factory.getWeatherByLocation(callback)
        verify(callback).onLoading(true)

    }

    @Test
    fun getWeatherConditions_shouldGetWeatherConditions(){
        setUpRepositoryGetConditionsOnSuccess(repository)
        factory.getWeatherByLocation(object : WeatherFactory.Callback {
            override fun onSuccess(any: Any) {
                val currentWeatherList = any as List<CurrentWeather>
                Assert.assertEquals(listCurrentWeather, currentWeatherList)
            }

            override fun onError(e: String) {
                Assert.fail()
            }

            override fun onLoading(isLoading: Boolean) {
                Assert.assertEquals(false, false)
            }
        })
    }

    @Test
    fun getWeatherConditions_shouldGetWeatherConditionsLoading(){
        setUpRepositoryWithLoading(repository)
        factory.getWeatherByLocation(object : WeatherFactory.Callback {
            override fun onSuccess(any: Any) {
                Assert.assertNotNull(any)
            }

            override fun onError(e: String) {
                Assert.fail()
            }

            override fun onLoading(isLoading: Boolean) {
                Assert.assertTrue(isLoading)
            }
        })
    }


   private fun setUpRepositoryGetConditionsOnSuccess(repository: WeatherRepository){
       doAnswer {
           val callback: RepositoryCallback<List<CurrentWeather>, String> = it.getArgument(0)
           callback.onSuccess(listCurrentWeather)
       }.whenever(repository).getWeatherByLocationKey(any())
   }

    private fun setUpRepositoryWithError(repository: WeatherRepository){
        doAnswer {
            val callback : RepositoryCallback<List<CurrentWeather>, String> = it.getArgument(0)
            callback.onError("Error")
        }.whenever(repository).getWeatherByLocationKey(any())
    }

    private fun setUpRepositoryWithLoading(repository: WeatherRepository){
        doAnswer {
            val callback: RepositoryCallback<List<CurrentWeather>, String> = it.getArgument(0)
            callback.onLoading()
        }.whenever(repository).getWeatherByLocationKey(any())
    }

}