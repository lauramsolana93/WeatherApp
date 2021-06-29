package com.lms.wheatherapp.repository

import com.lms.weatherapp.common.repository.WeatherRepository
import com.lms.weatherapp.common.repository.WeatherRepositoryImpl
import com.lms.weatherapp.network.WeatherApiService
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryUnitTest {

    private lateinit var repository: WeatherRepository

    @Mock
    private lateinit var api: WeatherApiService

    @Before
    fun setup(){
        repository = WeatherRepositoryImpl(api)
    }


}