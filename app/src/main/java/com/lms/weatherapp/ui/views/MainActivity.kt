package com.lms.weatherapp.ui.views

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lms.weatherapp.WeatherApplication
import com.lms.weatherapp.weather.viewmodel.LocationViewModel
import com.lms.weatherapp.weather.viewmodel.LocationViewModelFactory
import com.lms.wheatherapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initRepos()

    }

    private fun initRepos(){
        val repository = (application as WeatherApplication).repository
        val factory = (application as WeatherApplication).weatherFactory
        viewModel = ViewModelProvider(this, LocationViewModelFactory(repository = repository, factory)).get(LocationViewModel::class.java)
        viewModel.getWetherByLocation()
    }

}