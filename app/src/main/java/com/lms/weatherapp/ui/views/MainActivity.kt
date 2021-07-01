package com.lms.weatherapp.ui.views

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lms.weatherapp.WeatherApplication
import com.lms.weatherapp.common.utils.getJsonWeather
import com.lms.weatherapp.ui.views.adapter.ForecastAdapter
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.viewmodel.WeatherViewModel
import com.lms.weatherapp.weather.viewmodel.WeatherViewModelFactory
import com.lms.wheatherapp.R
import com.lms.wheatherapp.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: MainActivityBinding
    private lateinit var adapter: ForecastAdapter

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
            bindCurrendWeatherViews(it)
            viewModel.get5DaysForecastByLocationKey()
        })

        viewModel.getError().observe(this, {
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.weather_not_found))
                .setMessage(getString(R.string.weather_try_again))
                .setPositiveButton(getString(R.string.accept_permission)){ _, _ ->
                    viewModel.getCurrentWeather()
                }
                .setOnDismissListener {  viewModel.getCurrentWeather() }
                .show()
        })

        viewModel.getLoading().observe(this, {
            if(it){
                binding.loading.loadingLayout.visibility = VISIBLE
            } else {
                binding.loading.loadingLayout.visibility = GONE
            }
        })

        viewModel.getForecatWeather().observe(this, {
            bindForecastViews(it)
        })

    }

    private fun bindCurrendWeatherViews(weather : CurrentWeather){
        binding.weatherIcon.setAnimation(getJsonWeather(weather.weatherIcon))
        binding.weatherIcon.playAnimation()
        binding.weatherText.text = weather.weatherText
        binding.weatherTemperature.text = weather.temperature
    }

    private fun bindForecastViews(forecast: ForecastWeather){
        adapter = ForecastAdapter(forecast = forecast.forecast)
        binding.rvForecasat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvForecasat.adapter = adapter
    }
}

