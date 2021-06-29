package com.lms.weatherapp.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lms.weatherapp.WeatherApplication
import com.lms.weatherapp.location.viewmodel.LocationViewModel
import com.lms.weatherapp.location.viewmodel.LocationViewModelFactory
import com.lms.weatherapp.weather.viewmodel.WeatherViewModel
import com.lms.weatherapp.weather.viewmodel.WeatherViewModelFactory
import com.lms.wheatherapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initRepos()

    }

    private fun initRepos(){
        val repository = (application as WeatherApplication).weatherRepository
        val factory = (application as WeatherApplication).weatherFactory
        viewModel = ViewModelProvider(this, WeatherViewModelFactory(repository = repository, factory)).get(
            WeatherViewModel::class.java)
        viewModel.getCurrentWeatherByLocationKey()

    }

}