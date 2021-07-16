package com.lms.weatherapp.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lms.weatherapp.WeatherApplication
import com.lms.weatherapp.ui.compose.HourlyModalDrawer
import com.lms.weatherapp.ui.theme.WeatherComposeTheme
import com.lms.weatherapp.weather.viewmodel.WeatherViewModel
import com.lms.weatherapp.weather.viewmodel.WeatherViewModelFactory
import com.lms.wheatherapp.R

class MainActivityCompose : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel

    val repository by lazy {
        (application as WeatherApplication).weatherRepository
    }
    val factory by lazy {
        (application as WeatherApplication).weatherFactory
    }

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, WeatherViewModelFactory(repository = repository, factory)).get(
                WeatherViewModel::class.java
            )
        setContent {
            WeatherComposeTheme {
                Surface {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        HourlyModalDrawer(viewModel = viewModel)
                    }
                }
            }
        }

        initRepos()
    }

    private fun initRepos() {

        viewModel.getCurrentWeatherByLocationKey()
        viewModel.get5DaysForecastByLocationKey()
        viewModel.locationName()


        viewModel.error.observe(this, {
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.weather_not_found))
                .setMessage(getString(R.string.weather_try_again))
                .setPositiveButton(getString(R.string.accept_permission)) { _, _ ->
                    viewModel.getCurrentWeatherByLocationKey()
                }
                .setOnDismissListener { viewModel.getCurrentWeatherByLocationKey() }
                .show()
        })
    }
}

