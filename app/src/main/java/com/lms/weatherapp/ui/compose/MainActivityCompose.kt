package com.lms.weatherapp.ui.compose

import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lms.weatherapp.WeatherApplication
import com.lms.weatherapp.common.utils.dateFormater
import com.lms.weatherapp.common.utils.getDrawableWeather
import com.lms.weatherapp.common.utils.getJsonWeather
import com.lms.weatherapp.ui.views.adapter.ForecastAdapter
import com.lms.weatherapp.ui.views.adapter.HourlyAdapter
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.DailyForecast
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.model.HourlyWeather
import com.lms.weatherapp.weather.viewmodel.WeatherViewModel
import com.lms.weatherapp.weather.viewmodel.WeatherViewModelFactory
import com.lms.wheatherapp.R

class MainActivityCompose : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var hourlyAdapter : HourlyAdapter

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {

        }
        initRepos()
    }

    private fun initRepos(){
        val repository = (application as WeatherApplication).weatherRepository
        val factory = (application as WeatherApplication).weatherFactory
        viewModel = ViewModelProvider(this, WeatherViewModelFactory(repository = repository, factory)).get(
            WeatherViewModel::class.java)
        viewModel.getCurrentWeatherByLocationKey()

        viewModel.getCurrentWeather().observe(this, {
            //bindCurrentWeatherViews(it)
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
        })

        viewModel.getForecastWeather().observe(this, {
            setContent {
                ForecastWeather12hours(forecast = it)
            }

        })

        viewModel.getLocationName().observe(this, {
            setContent {
                LocationName(location = it)
            }
        })

        viewModel.getHourly().observe(this, {
            it.let {
                createDialog(it)
            }
        })

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

@Composable
fun ForecastWeather12hours(forecast: ForecastWeather){
    val dailyForecast = remember { forecast.forecast }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = dailyForecast,
            itemContent = {
                WeatherListItem(dailyForecast = it)
            }
        )
    }
}

@Composable
fun WeatherListItem(dailyForecast: DailyForecast) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = dailyForecast.date.dateFormater())
                Text(text = "Min: ${dailyForecast.temperature.minimum.value}${dailyForecast.temperature.minimum.unit}")
                Text(text = "Max: ${dailyForecast.temperature.maximum.value}${dailyForecast.temperature.maximum.unit}")
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
                horizontalAlignment = Alignment.End) {
                Icon(painter = painterResource(id = getDrawableWeather(dailyForecast.day.icon)), contentDescription = "")
                Icon(painter = painterResource(id = getDrawableWeather(dailyForecast.night.icon)), contentDescription = "")
            }
        }
    }

}

@Composable
fun LocationName(location: String){
    Box(modifier = Modifier.padding(12.dp), contentAlignment = Alignment.TopCenter){
        Text(text = location)
    }

}