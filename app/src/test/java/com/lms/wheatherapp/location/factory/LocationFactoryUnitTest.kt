package com.lms.wheatherapp.location.factory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lms.weatherapp.common.network.model.common.Imperial
import com.lms.weatherapp.common.network.model.common.Metric
import com.lms.weatherapp.common.network.model.common.MetricImperialValues
import com.lms.weatherapp.common.network.model.location.*
import com.lms.weatherapp.location.repository.LocationRepository
import com.lms.weatherapp.location.factory.LocationFactory
import com.lms.weatherapp.location.factory.LocationFactoryImpl
import com.lms.weatherapp.network.RepositoryCallback
import com.lms.weatherapp.location.model.Location
import com.lms.wheatherapp.TestCoroutineRule
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocationFactoryUnitTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    private val location = "41.54329,2.10942"
    private lateinit var repository: LocationRepository
    private lateinit var factory: LocationFactory
    private val geolocationResponse = GeopositionResponse(1, "", "", 1, "", "",
    "", Region("", "", ""), Country("", "", ""),
        AdministrativeArea("", "", "", 1, "", "", ""),
        TimeZone("", "", 1, false, ""),
        GeoPosition(0.0, 0.0, MetricImperialValues(Metric(20.0,"", 2), Imperial(20.0, "", 2))),
        false, listOf(), listOf()
    )

    @Before
    fun setup(){
        repository = mock()
        factory = LocationFactoryImpl(repository)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun buildWeather_shouldGetGeopositionFromRepo(){
        testCoroutineRule.runBlockingTest {
            doReturn(geolocationResponse)
                .`when`(repository)
                .getLocationKey(location)
            factory.buildLocation(location)
            verify(repository).getLocationKey(location)
        }

    }
}