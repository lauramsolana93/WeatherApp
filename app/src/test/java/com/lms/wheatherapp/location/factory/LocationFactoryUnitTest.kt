package com.lms.wheatherapp.location.factory

import com.lms.weatherapp.location.repository.LocationRepository
import com.lms.weatherapp.location.factory.LocationFactory
import com.lms.weatherapp.location.factory.LocationFactoryImpl
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.location.model.Location
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LocationFactoryUnitTest {


    private val location = "41.54329,2.10942"
    private lateinit var repository: LocationRepository
    private lateinit var factory: LocationFactory

    @Before
    fun setup(){
        repository = mock()
        factory = LocationFactoryImpl(repository)
    }

    @Test
    fun buildWeather_shouldGetGeopositionFromRepo(){
        factory.buildLocation(mock(), location)
        verify(repository).getLocationKeyFromSharedPreferences(any(), any())
    }

    @Test
    fun buildWeather_shouldCallOnSuccess(){
        val callback = mock<LocationFactory.Callback>()
        setUpRepositoryWithGeolocation(repository)
        factory.buildLocation(callback, location)
        verify(callback).onSuccess(Location("301307", "Sabadell"))
    }

    @Test
    fun buildWeather_shouldCallOnError(){
        val callback = mock<LocationFactory.Callback>()
        setUpRepositoryWithError(repository = repository)
        factory.buildLocation(callback, location)
        verify(callback).onError("Error")
    }

    @Test
    fun buildWeather_shouldCallOnLoading(){
        val callback = mock<LocationFactory.Callback>()
        setUpRepositoryWithLoading(repository)
        factory.buildLocation(callback, location)
        verify(callback).onLoading(true)
    }

    @Test
    fun buildWeather_shouldBuildWeatherWithLocation(){
        setUpRepositoryWithGeolocation(repository)
        val locat = Location("301307", "Sabadell")

        factory.buildLocation(object : LocationFactory.Callback {
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
        factory.buildLocation(object : LocationFactory.Callback {
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



    private fun setUpRepositoryWithGeolocation(repository: LocationRepository){
        doAnswer {
            val callback: RepositoryCallback<Location?, String> = it.getArgument(0)
            callback.onSuccess(Location("301307", "Sabadell"))
        }.whenever(repository).getLocationKeyFromSharedPreferences(any(), any())
    }

    private fun setUpRepositoryWithError(repository: LocationRepository){
        doAnswer {
            val callback: RepositoryCallback<Location?, String> = it.getArgument(0)
            callback.onError("Error")
        }.whenever(repository).getLocationKeyFromSharedPreferences(any(), any())
    }

    private fun setUpRepositoryWithLoading(repository: LocationRepository){
        doAnswer {
            val callback: RepositoryCallback<Location?, String> = it.getArgument(0)
            callback.onLoading()
        }.whenever(repository).getLocationKeyFromSharedPreferences(any(), any())
    }
}