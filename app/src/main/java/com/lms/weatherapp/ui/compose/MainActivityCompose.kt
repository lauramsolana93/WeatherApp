package com.lms.weatherapp.ui.compose

import android.app.Dialog
import android.os.Bundle
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lms.weatherapp.WeatherApplication
import com.lms.weatherapp.common.utils.faranheidToCelsius
import com.lms.weatherapp.common.utils.getDrawableWeather
import com.lms.weatherapp.model.common.Metric
import com.lms.weatherapp.ui.theme.WeatherComposeTheme
import com.lms.weatherapp.ui.views.adapter.HourlyAdapter
import com.lms.weatherapp.weather.model.*
import com.lms.weatherapp.weather.viewmodel.WeatherViewModel
import com.lms.weatherapp.weather.viewmodel.WeatherViewModelFactory
import com.lms.wheatherapp.R

class MainActivityCompose : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var hourlyAdapter: HourlyAdapter
    val repository by lazy {
        (application as WeatherApplication).weatherRepository
    }
    val factory by lazy {
        (application as WeatherApplication).weatherFactory
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, WeatherViewModelFactory(repository = repository, factory)).get(
                WeatherViewModel::class.java
            )
        setContent {
            WeatherComposeTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top) {
                        LocationName(viewModel = viewModel)
                        CurrentWeatherView(viewModel = viewModel)
                        ForecastWeather12hours(viewModel = viewModel)
                    }
                }
            }
        }
        viewModel.getCurrentWeatherByLocationKey()
        initRepos()
    }

    private fun initRepos() {

        viewModel.getError().observe(this, {
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.weather_not_found))
                .setMessage(getString(R.string.weather_try_again))
                .setPositiveButton(getString(R.string.accept_permission)) { _, _ ->
                    viewModel.getCurrentWeatherByLocationKey()
                }
                .setOnDismissListener { viewModel.getCurrentWeatherByLocationKey() }
                .show()
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
        val title: TextView = dialog.findViewById(R.id.hourlyTitle)
        title.text = getString(R.string.hourly)
        list.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        list.adapter = hourlyAdapter

        dialog.show()
    }
}

@Composable
fun ForecastWeather12hours(viewModel: WeatherViewModel?) {
    val forecast by viewModel?.forecast!!.observeAsState(initial = ForecastWeather(listOf()))
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        items(
            items = forecast.forecast,
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
        Row(Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(fontSize = 20.sp,text = dailyForecast.date)//dailyForecast.date.dateFormater())
                val minTemp =
                    "${dailyForecast.temperature.minimum.value}${dailyForecast.temperature.minimum.unit}".faranheidToCelsius()
                val maxTemp =
                    "${dailyForecast.temperature.maximum.value}${dailyForecast.temperature.maximum.unit}".faranheidToCelsius()
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text(text = "Min: $minTemp")
                    Image(
                        ImageVector.vectorResource(
                            id = getDrawableWeather(dailyForecast.day.icon)
                        ), contentDescription = "", Modifier.size(20.dp)
                    )
                }
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text(text = "Max: $maxTemp", fontSize = 16.sp)
                    Image(
                        ImageVector.vectorResource(id = getDrawableWeather(dailyForecast.night.icon)),
                        contentDescription = "",
                        Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CurrentWeatherView(viewModel: WeatherViewModel){
    val currentWeather : CurrentWeather by viewModel.currentWeather.observeAsState(initial = CurrentWeather("", "", 1, ""))
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp), Arrangement.SpaceBetween){
        Image(
            ImageVector.vectorResource(id = getDrawableWeather(currentWeather.weatherIcon)),
            contentDescription = "",
            Modifier.size(80.dp)
        )
        Box(contentAlignment = Alignment.Center){
            Text(text = currentWeather.temperature,
                fontSize = 60.sp
            )
        }



    }

}

@Composable
fun LocationName(viewModel: WeatherViewModel) { 
    val locationName: String by viewModel.locationName.observeAsState(initial = "")
    Box(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(), contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = locationName,
            fontSize = 20.sp
        )
    }

}

/*@Preview
@Composable
fun weatherForecast(){
    val metric = Metric(100.0, "F", 1)
    val temperature = TemperatureForecast(metric, metric)
    val day = Day(1, "", false)
    val night = Night(1, "", false)
    val dailyForecast = DailyForecast(
        "23/08/2021",
        0L,
        temperature = temperature,
        day,
        night,
        listOf(),
        "",
        ""
    )
    val currentWeather = CurrentWeather("", "20ºC", 1, "Soleado")

    WeatherComposeTheme {
        Surface{
            Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top) {
                LocationName("Sabadell")
                CurrentWeatherView(currentWeather)
                WeatherListItem(dailyForecast = dailyForecast)
            }


        }


    }
}*/
