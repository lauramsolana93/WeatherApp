package com.lms.weatherapp.ui.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lms.weatherapp.WeatherApplication
import com.lms.weatherapp.location.viewmodel.LocationViewModel
import com.lms.weatherapp.location.viewmodel.LocationViewModelFactory
import com.lms.wheatherapp.R

class SplashActivity : ComponentActivity() {

    private lateinit var viewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        initRepos()
    }

    private fun initRepos() {
        val repository = (application as WeatherApplication).locationRepository
        val factory = (application as WeatherApplication).locationFactory
        viewModel = ViewModelProvider(this, LocationViewModelFactory(repository, factory)).get(
            LocationViewModel::class.java
        )

        viewModel.initLocation("41.54329,2.10942")

        viewModel.getLocation().observe(this, Observer {
            it.let {
                startActivity(Intent(this, MainActivity::class.java))
            }
        })

        viewModel.getError().observe(this, Observer {
            it.let { }
        })


    }
}

