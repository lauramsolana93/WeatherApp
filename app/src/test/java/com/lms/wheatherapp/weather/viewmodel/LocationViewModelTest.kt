package com.lms.wheatherapp.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lms.weatherapp.common.repository.location.LocationRepository
import com.lms.weatherapp.location.factory.LocationFactory
import com.lms.weatherapp.location.model.Location
import com.lms.weatherapp.location.viewmodel.LocationViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocationViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: LocationRepository
    private lateinit var factory: LocationFactory
    private lateinit var viewModel: LocationViewModel

    private val loc = "41.54329,2.10942"

    @Before
    fun setup(){
        repository = mock()
        factory = mock()
        viewModel = LocationViewModel(repository, factory)
    }

    @Test
    fun buildLocation_shouldBuildLocation(){
        viewModel.initLocation(loc)
        verify(factory).buildLocation(any(), any())
    }

}