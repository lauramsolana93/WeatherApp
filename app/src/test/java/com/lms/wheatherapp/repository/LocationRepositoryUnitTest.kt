package com.lms.wheatherapp.repository

import android.content.SharedPreferences
import com.lms.weatherapp.location.repository.LocationRepository
import com.lms.weatherapp.location.repository.LocationRepositoryImpl
import com.lms.weatherapp.network.WeatherApiService
import com.nhaarman.mockitokotlin2.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocationRepositoryUnitTest {

    private lateinit var repository: LocationRepository

    @Mock
    private lateinit var api: WeatherApiService
    @Mock
    private lateinit var sharedPreferences: SharedPreferences
    @Mock
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setup(){
        whenever(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
        repository = LocationRepositoryImpl(api, sharedPreferences)
    }

    @Test
    fun saveLocationKey_shouldSaveLocationKeyToSharedPreferences(){
        val loc = "41.54329,2.10942"
        repository.saveLocationKey(loc)

        inOrder(sharedPreferencesEditor){
            verify(sharedPreferencesEditor).putString(any(), eq(loc))
            verify(sharedPreferencesEditor).apply()
        }
    }

    @Test
    fun getLocationKey_shouldGetLocationFromSharedPreferences(){
        repository.getLocationKey()
        verify(sharedPreferences).getString(any(), any())
    }

}


