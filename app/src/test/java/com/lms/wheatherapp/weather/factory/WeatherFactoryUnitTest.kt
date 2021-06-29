package com.lms.wheatherapp.weather.factory

import com.lms.weatherapp.common.repository.WeatherRepository
import com.lms.weatherapp.model.location.GeopositionResponse
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.weather.factory.WeatherFactory
import com.lms.weatherapp.weather.factory.WeatherFactoryImpl
import com.lms.weatherapp.weather.model.Location
import com.lms.wheatherapp.utils.getGeoPositionMock
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherFactoryUnitTest {


    private val location = "41.54329,2.10942"
    private val geopositionMock = getGeoPositionMock()

    private lateinit var repository: WeatherRepository
    private lateinit var factory: WeatherFactory

    @Before
    fun setup(){
        repository = mock()
        factory = WeatherFactoryImpl(repository)
    }

    @Test
    fun buildWeather_shouldGetGeopositionFromRepo(){
        factory.buildWeather(mock(), location)
        verify(repository).getLocationKey(any(), any())
    }

    @Test
    fun buildWeather_shouldCallOnSuccess(){
        val callback = mock<WeatherFactory.Callback>()
        setUpRepositoryWithGeolocation(repository)
        factory.buildWeather(callback, location)
        verify(callback).onSuccess(Location("301307"))
    }

    @Test
    fun buildWeather_shouldCallOnError(){
        val callback = mock<WeatherFactory.Callback>()
        setUpRepositoryWithError(repository = repository)
        factory.buildWeather(callback, location)
        verify(callback).onError("Error")
    }

    @Test
    fun buildWeather_shouldBuildWeatherWithLocation(){
        setUpRepositoryWithGeolocation(repository)
        val locat = Location("301307")

        factory.buildWeather(object : WeatherFactory.Callback {
            override fun onSuccess(any: Any) {
                val loc = any as Location
                Assert.assertEquals(locat.key, loc.key)
            }

            override fun onError(e: String) {
                Assert.fail()
            }

            override fun onLoading(isLoading: Boolean) {
                Assert.assertEquals(false, false)
            }
        }, location)
    }

    @Test
    fun buildWeather_shouldBuildWeatherLoading(){
        setUpRepositoryWithLoading(repository)
        factory.buildWeather(object : WeatherFactory.Callback {
            override fun onSuccess(any: Any) {
                Assert.assertNotNull(any)
            }

            override fun onError(e: String) {
                Assert.fail()
            }

            override fun onLoading(isLoading: Boolean) {
                Assert.assertTrue(isLoading)
            }
        }, location)
    }



    private fun setUpRepositoryWithGeolocation(repository: WeatherRepository){
        doAnswer {
            val callback: RepositoryCallback<Location?, String> = it.getArgument(0)
            callback.onSuccess(Location("301307"))
        }.whenever(repository).getLocationKey(any(), any())
    }

    private fun setUpRepositoryWithError(repository: WeatherRepository){
        doAnswer {
            val callback: RepositoryCallback<Location?, String> = it.getArgument(0)
            callback.onError("Error")
        }.whenever(repository).getLocationKey(any(), any())
    }

    private fun setUpRepositoryWithLoading(repository: WeatherRepository){
        doAnswer {
            val callback: RepositoryCallback<Location?, String> = it.getArgument(0)
            callback.onLoading()
        }.whenever(repository).getLocationKey(any(), any())
    }
}