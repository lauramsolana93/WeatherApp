package com.lms.weatherapp.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lms.weatherapp.WeatherApplication
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.viewmodel.WeatherViewModel
import com.lms.weatherapp.weather.viewmodel.WeatherViewModelFactory
import com.lms.wheatherapp.R
import com.lms.wheatherapp.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initRepos()
    }

    private fun initRepos(){
        val repository = (application as WeatherApplication).weatherRepository
        val factory = (application as WeatherApplication).weatherFactory
        viewModel = ViewModelProvider(this, WeatherViewModelFactory(repository = repository, factory)).get(
            WeatherViewModel::class.java)
        viewModel.getCurrentWeatherByLocationKey()

        viewModel.getCurrentWeather().observe(this, {
            bindViews(it)

        })

        viewModel.getError().observe(this, {

        })

        viewModel.getLoading().observe(this, {

        })

    }

    private fun bindViews(weather : CurrentWeather){
        var icon : Int? = null
        when(weather.weatherIcon){
            1,2,3,4,5 -> {
                icon = Icons.SUNNY.drawable
            }
            6, 7, 19, 20 -> {
                icon = Icons.CLOUDY.drawable
            }
            8, 11 -> {
                icon = Icons.FOG.drawable
            }
            12, 18 -> {
                icon = Icons.RAIN.drawable
            }
            13, 14, 16, 17 -> {
                icon = Icons.SUNNY_SHOWERS.drawable
            }
            15 -> {
                icon = Icons.SHOWERS.drawable
            }
            22, 23 -> {
                icon = Icons.SNOW.drawable
            }
            25, 26 -> {
                icon = Icons.SLEET.drawable
            }
            29 -> {
                icon = Icons.SNOW_RAIN.drawable
            }
            33, 34, 35, 36 -> {
                icon = Icons.NIGHT_CLEAR.drawable
            }
            37, 38, 43, 44 -> {
                icon = Icons.NIGHT_CLOUDY.drawable
            }
            39, 40 -> {
                icon = Icons.NIGHT_RAIN.drawable
            }
            41, 42 -> {
                icon = Icons.NIGHT_STORM.drawable
            } else -> {
                icon = Icons.SUNNY.drawable
            }

        }
        binding.weatherIcon.setImageResource(icon)
        binding.weatherText.text = weather.weatherText
    }

}

enum class Icons(val drawable: Int){
    SUNNY(R.drawable.ic_sunny),
    CLOUDY(R.drawable.ic_cloudy),
    SUNNY_SHOWERS(R.drawable.ic_sunny_storm),
    SHOWERS(R.drawable.ic_storm),
    RAIN(R.drawable.ic_rain),
    SNOW(R.drawable.ic_snowy),
    SNOW_RAIN(R.drawable.ic_rain_snowy),
    FOG(R.drawable.ic_foggy),
    NIGHT_CLEAR(R.drawable.ic_night),
    NIGHT_CLOUDY(R.drawable.ic_night_cloudy),
    NIGHT_RAIN(R.drawable.ic_night_rain),
    NIGHT_STORM(R.drawable.ic_night_storm),
    SLEET(R.drawable.ic_hail)
}