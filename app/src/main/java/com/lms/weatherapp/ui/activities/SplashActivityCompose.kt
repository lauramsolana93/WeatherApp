package com.lms.weatherapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lms.weatherapp.WeatherApplication
import com.lms.weatherapp.location.viewmodel.LocationViewModel
import com.lms.weatherapp.location.viewmodel.LocationViewModelFactory
import com.lms.weatherapp.ui.commons.BaseActivity
import com.lms.weatherapp.ui.compose.Logo
import com.lms.weatherapp.ui.theme.WeatherComposeTheme
import com.lms.wheatherapp.R

class SplashActivityCompose : BaseActivity() {

    private lateinit var viewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherComposeTheme {
                Surface(color = MaterialTheme.colors.primary){
                    Logo()
                }
            }
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        checkLocationPermission()
        initRepos()
    }
    private fun initRepos() {
        val repository = (application as WeatherApplication).locationRepository
        val factory = (application as WeatherApplication).locationFactory
        viewModel = ViewModelProvider(this, LocationViewModelFactory(repository, factory)).get(
            LocationViewModel::class.java
        )

        viewModel.getLocation().observe(this, {
            startActivity(Intent(this, MainActivityCompose::class.java))
            finish()
        })

        viewModel.getError().observe(this, {
            it.let {
                MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.location_not_found))
                    .setMessage(getString(R.string.location_try_again))
                    .setPositiveButton(getString(R.string.accept_permission)){ _, _ ->
                        checkLocationPermission()
                    }
                    .setOnDismissListener { checkLocationPermission() }
                    .show()
            }
        })

    }

    override fun permissionGranted() {
        super.permissionGranted()
        if(location != null){
            Log.e("SPLASH", "HELLO: $location")
            viewModel.initLocation(location ?: "")
        }
    }
}