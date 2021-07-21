package com.lms.wheatherapp.location.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lms.weatherapp.location.repository.LocationRepository
import com.lms.weatherapp.location.factory.LocationFactory
import com.lms.weatherapp.location.model.Location
import com.lms.weatherapp.location.viewmodel.LocationViewModel
import com.lms.wheatherapp.TestCoroutineRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mock


class LocationViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var repository: LocationRepository
    private lateinit var factory: LocationFactory
    private lateinit var viewModel: LocationViewModel
    private lateinit var observer : Observer<Location>


    private val loc = "41.54329,2.10942"



    @ExperimentalCoroutinesApi
    @Before
    fun setup(){
        repository = mock()
        factory = mock()
        observer = mock()
        viewModel = LocationViewModel(repository, factory)
    }



    @ExperimentalCoroutinesApi
    @Test
    fun buildLocation_shouldBuildLocation(){
        testCoroutineRule.runBlockingTest {
            doReturn(Location("301307", "Sabadell"))
                .`when`(factory)
                .buildLocation(loc)
                viewModel.getLocation().observeForever(observer)
        }
    }
    
    @ExperimentalCoroutinesApi
    @After
    fun tareDown(){
        Dispatchers.resetMain()
    }

}