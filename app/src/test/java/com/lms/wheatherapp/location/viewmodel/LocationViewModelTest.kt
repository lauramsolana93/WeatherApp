package com.lms.wheatherapp.location.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lms.weatherapp.common.utils.Resource
import com.lms.weatherapp.location.factory.LocationFactory
import com.lms.weatherapp.location.model.Location
import com.lms.weatherapp.location.repository.LocationRepository
import com.lms.weatherapp.location.viewmodel.LocationViewModel
import com.lms.wheatherapp.TestCoroutineRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LocationViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var repository: LocationRepository
    private lateinit var factory: LocationFactory
    private lateinit var viewModel: LocationViewModel
    private lateinit var observer : Observer<Resource<Location>>

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
            doReturn(Resource.Success(Location("301307", "Sabadell")))
                .`when`(factory)
                .buildLocation(loc)
                viewModel.location.observeForever(observer)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun buildLocation_shouldReturnError(){
        testCoroutineRule.runBlockingTest {
            doReturn(Resource.Error("", null))
                .`when`(factory)
                .buildLocation(loc)
            viewModel.location.observeForever(observer)
        }
    }

    
    @ExperimentalCoroutinesApi
    @After
    fun tareDown(){
        Dispatchers.resetMain()
    }

}