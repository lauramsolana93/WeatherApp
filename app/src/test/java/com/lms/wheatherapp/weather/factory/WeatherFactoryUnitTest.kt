package com.lms.wheatherapp.weather.factory

import com.lms.weatherapp.common.network.model.weather.TemperatureHourly
import com.lms.weatherapp.weather.repository.WeatherRepository
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.factory.WeatherFactory
import com.lms.weatherapp.weather.factory.WeatherFactoryImpl
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.model.HourlyWeather
import com.lms.wheatherapp.utils.forecastWeatherMock
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
    private val listHourlyWeather = listOf(
        HourlyWeather("",1, "", TemperatureHourly(12, "", 1), 2),
        HourlyWeather("",1, "", TemperatureHourly(12, "", 1), 2),
        HourlyWeather("",1, "", TemperatureHourly(12, "", 1), 2),
        HourlyWeather("",1, "", TemperatureHourly(12, "", 1), 2),
    )

    @Before
    fun setup(){
        repository = mock()
        factory = WeatherFactoryImpl(repository)
    }

   /* @Test
    fun getWeatherConditions_shouldGetConditionsFromRepo(){
        factory.getWeatherByLocation(mock())
        verify(repository).getWeatherByLocationKey(any())
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
        setUpRepositoryGetConditionsWithError(repository)
        factory.getWeatherByLocation(callback)
        verify(callback).onError("Error")
    }

    @Test
    fun getWeatherConditions_shouldCallOnLoading(){
        val callback = mock<WeatherFactory.Callback>()
        setUpRepositoryGetConditionsWithLoading(repository)
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
                Assert.assertEquals(false, isLoading)
            }
        })
    }


    @Test
    fun getWeatherConditions_shouldGetWeatherConditionsLoading(){
        setUpRepositoryGetConditionsWithLoading(repository)
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



    @Test
    fun get5DaysForecast_shouldGet5DaysForecastFromRepo(){
        factory.get5DaysForecast(mock())
        verify(repository).get5DaysForecast(any())
    }

    @Test
    fun get5DaysForecast_shouldGet5DaysForecastCallOnSuccess(){
        val callback = mock<WeatherFactory.Callback>()
        setUpRepositoryGetForecastOnSuccess(repository)
        factory.get5DaysForecast(callback)
        verify(callback).onSuccess(forecastWeatherMock)
    }

    @Test
    fun get5DaysForecast_shouldGet5DaysForecastCallOnError(){
        val callback = mock<WeatherFactory.Callback>()
        setUpRepositoryGetForecastOnError(repository)
        factory.get5DaysForecast(callback)
        verify(callback).onError("Error")
    }

    @Test
    fun get5DaysForecast_shouldGet5DaysForecastCallOnLoading(){
        val callback = mock<WeatherFactory.Callback>()
        setUpRepositoryGetForecastOnLoading(repository)
        factory.get5DaysForecast(callback)
        verify(callback).onLoading(true)
    }

    @Test
    fun get5DaysForecast_shouldGet5DaysForecast(){
        setUpRepositoryGetForecastOnSuccess(repository)
        factory.get5DaysForecast(object : WeatherFactory.Callback {
            override fun onSuccess(any: Any) {
                Assert.assertEquals(forecastWeatherMock, any as ForecastWeather)
            }

            override fun onError(e: String) {
                Assert.fail()
            }

            override fun onLoading(isLoading: Boolean) {
                Assert.assertEquals(false, isLoading)
            }
        })

    }

    @Test
    fun get5DaysForecast_shouldGet5DaysForecastLoading(){
        setUpRepositoryGetForecastOnLoading(repository)
        factory.get5DaysForecast(object : WeatherFactory.Callback {
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

    @Test
    fun getHourly12Hours_shouldGetHorulyFromRepo(){
        factory.getHourly12Hours(mock())
        verify(repository).getHourly12Hours(any())
    }

    @Test
    fun getHourly12Hours_shouldCallOnSuccess(){
        val callback = mock<WeatherFactory.Callback>()
        setUpRepositoryGetHourlyOnSuccess(repository)
        factory.getHourly12Hours(callback)
        verify(callback).onSuccess(listHourlyWeather)
    }

    @Test
    fun getHourly12Hours_shouldCallOnError(){
        val callback = mock<WeatherFactory.Callback>()
        setUpRepositoryGetHourlyOnError(repository)
        factory.getHourly12Hours(callback)
        verify(callback).onError("Error")
    }

    @Test
    fun getHourly12Hours_shouldCallOnLoading(){
        val callback = mock<WeatherFactory.Callback>()
        setUpRepostitoryGetHourlyOnLoading(repository)
        factory.getHourly12Hours(callback)
        verify(callback).onLoading(true)
    }

    @Test
    fun getHourly12Hours_shouldGetHourly12Hours(){
        setUpRepositoryGetHourlyOnSuccess(repository)
        factory.getHourly12Hours(object : WeatherFactory.Callback {
            override fun onSuccess(any: Any) {
                val hourlyWeather = any as List<HourlyWeather>
                Assert.assertEquals(listHourlyWeather, hourlyWeather)
            }

            override fun onError(e: String) {
                Assert.fail()
            }

            override fun onLoading(isLoading: Boolean) {
                Assert.assertEquals(false, isLoading)
            }
        })
    }

    @Test
    fun getHourly12Hours_shouldGetHourlyLoading(){
        setUpRepostitoryGetHourlyOnLoading(repository)
        factory.getHourly12Hours(object : WeatherFactory.Callback {
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

    private fun setUpRepositoryGetConditionsWithError(repository: WeatherRepository){
        doAnswer {
            val callback : RepositoryCallback<List<CurrentWeather>, String> = it.getArgument(0)
            callback.onError("Error")
        }.whenever(repository).getWeatherByLocationKey(any())
    }

    private fun setUpRepositoryGetConditionsWithLoading(repository: WeatherRepository){
        doAnswer {
            val callback: RepositoryCallback<List<CurrentWeather>, String> = it.getArgument(0)
            callback.onLoading()
        }.whenever(repository).getWeatherByLocationKey(any())
    }

    private fun setUpRepositoryGetForecastOnSuccess(repository: WeatherRepository){
        doAnswer {
            val callback: RepositoryCallback<ForecastWeather, String> = it.getArgument(0)
            callback.onSuccess(forecastWeatherMock)
        }.whenever(repository).get5DaysForecast(any())
    }

    private fun setUpRepositoryGetForecastOnLoading(repository: WeatherRepository){
        doAnswer {
            val callback: RepositoryCallback<ForecastWeather, String> = it.getArgument(0)
            callback.onLoading()
        }.whenever(repository).get5DaysForecast(any())
    }

    private fun setUpRepositoryGetForecastOnError(repository: WeatherRepository){
        doAnswer {
            val callback: RepositoryCallback<ForecastWeather, String> = it.getArgument(0)
            callback.onError("Error")
        }.whenever(repository).get5DaysForecast(any())
    }

    private fun setUpRepositoryGetHourlyOnSuccess(repository: WeatherRepository){
        doAnswer {
            val callback: RepositoryCallback<List<HourlyWeather>, String> = it.getArgument(0)
            callback.onSuccess(listHourlyWeather)
        }.whenever(repository).getHourly12Hours(any())
    }

    private fun setUpRepostitoryGetHourlyOnLoading(repository: WeatherRepository){
        doAnswer {
            val callback : RepositoryCallback<List<HourlyWeather>, String> = it.getArgument(0)
            callback.onLoading()
        }.whenever(repository).getHourly12Hours(any())
    }

    private fun setUpRepositoryGetHourlyOnError(repository: WeatherRepository){
        doAnswer {
            val callback: RepositoryCallback<List<HourlyWeather>, String> = it.getArgument(0)
            callback.onError("Error")
        }.whenever(repository).getHourly12Hours(any())
    }*/

}