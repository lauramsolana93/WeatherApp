package com.lms.wheatherapp.repository

import android.content.SharedPreferences
import com.lms.weatherapp.weather.repository.WeatherRepository
import com.lms.weatherapp.weather.repository.WeatherRepositoryImpl
import com.lms.weatherapp.network.WeatherApiService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryUnitTest {

    private lateinit var repository: WeatherRepository

    @Mock
    private lateinit var api: WeatherApiService
    @Mock
    private lateinit var sharedPreferences: SharedPreferences
    @Mock
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setup(){
        whenever(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
        repository = WeatherRepositoryImpl(api, sharedPreferences)
    }

    @Test
    fun getLocationKey_shouldGetLocationFromSharedPreferences(){
        repository.getLocationKey()
        verify(sharedPreferences).getString(any(), any())
    }
}