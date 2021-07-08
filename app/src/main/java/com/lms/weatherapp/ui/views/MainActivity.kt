package com.lms.weatherapp.ui.views

import android.app.AlertDialog
import android.app.Dialog
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
import com.lms.weatherapp.ui.views.adapter.HourlyAdapter
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.model.HourlyWeather
import com.lms.weatherapp.weather.viewmodel.WeatherViewModel
import com.lms.weatherapp.weather.viewmodel.WeatherViewModelFactory
import com.lms.wheatherapp.databinding.MainActivityBinding


import androidx.recyclerview.widget.RecyclerView

import android.widget.LinearLayout
import android.widget.TextView
import com.lms.wheatherapp.R


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: MainActivityBinding
    private lateinit var adapter: ForecastAdapter
    private lateinit var hourlyAdapter : HourlyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initRepos()
        setUpListeners()
    }


    private fun initRepos(){
        val repository = (application as WeatherApplication).weatherRepository
        val factory = (application as WeatherApplication).weatherFactory
        viewModel = ViewModelProvider(this, WeatherViewModelFactory(repository = repository, factory)).get(
            WeatherViewModel::class.java)
        viewModel.getCurrentWeatherByLocationKey()

        viewModel.getCurrentWeather().observe(this, {
            bindCurrentWeatherViews(it)
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

        viewModel.getForecastWeather().observe(this, {
            bindForecastViews(it)
        })

        viewModel.getLocationName().observe(this, {
            binding.weatherLocation.text = it
        })

        viewModel.getHourly().observe(this, {
            it.let {
                createDialog(it)
            }
        })

    }

    private fun bindCurrentWeatherViews(weather : CurrentWeather){
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

    private fun setUpListeners(){
        binding.hourlyIcon.setOnClickListener {
            viewModel.getHourly12Hours()
        }
    }



    private fun createDialog(hourlyList: List<HourlyWeather>) {

        val dialog = Dialog(this)

        dialog.setContentView(R.layout.dialog_hourly)
        dialog.setCancelable(true)

        if (dialog.window != null) {
            dialog.window!!.setLayout(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        hourlyAdapter = HourlyAdapter(hourlyList)
        val list: RecyclerView = dialog.findViewById(R.id.hourly_rv)
        val title : TextView = dialog.findViewById(R.id.hourlyTitle)
        title.text = getString(R.string.hourly)
        list.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        list.adapter = hourlyAdapter

        dialog.show()


    }
}

