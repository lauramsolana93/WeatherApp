package com.lms.weatherapp.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lms.weatherapp.WeatherApplication
import com.lms.weatherapp.common.utils.dateFormater
import com.lms.weatherapp.common.utils.faranheidToCelsius
import com.lms.weatherapp.common.utils.getDrawableWeather
import com.lms.weatherapp.ui.theme.WeatherComposeTheme
import com.lms.weatherapp.weather.model.*
import com.lms.weatherapp.weather.viewmodel.WeatherViewModel
import com.lms.weatherapp.weather.viewmodel.WeatherViewModelFactory
import com.lms.wheatherapp.R
import kotlinx.coroutines.launch

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


    }
}

@Composable
fun ForecastWeather5days(viewModel: WeatherViewModel) {
    val forecast by viewModel.forecast.observeAsState(initial = ForecastWeather(listOf()))
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
                Text(
                    fontSize = 20.sp,
                    text = dailyForecast.date.dateFormater()
                )
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
fun CurrentWeatherView(viewModel: WeatherViewModel) {
    val currentWeather: CurrentWeather by viewModel.currentWeather.observeAsState(
        initial = CurrentWeather(
            "",
            "",
            1,
            ""
        )
    )
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp), Arrangement.SpaceBetween
    ) {
        Image(
            ImageVector.vectorResource(id = getDrawableWeather(currentWeather.weatherIcon)),
            contentDescription = "",
            Modifier.size(80.dp)
        )
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = currentWeather.temperature,
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

@ExperimentalMaterialApi
@Composable
fun HourlyModalDrawer(viewModel: WeatherViewModel){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

        ModalDrawer(
            drawerContent = {
                    Column(Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Top) {
                        Column(horizontalAlignment = Alignment.End){
                            Surface(onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                            }) {
                                Image(
                                    ImageVector.vectorResource(id = R.drawable.ic_cancel),
                                    contentDescription = "",
                                    Modifier.size(20.dp),
                                )
                            }
                        }
                        ForecastHourly12hours(viewModel = viewModel)
                    }
            },
            drawerState = drawerState,
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    Button(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }){
                        Text("Hourly")
                    }
                    LocationName(viewModel = viewModel)
                    CurrentWeatherView(viewModel = viewModel)
                    ForecastWeather5days(viewModel = viewModel)
                }
            }
        )
}

@Composable
fun ForecastHourly12hours(viewModel: WeatherViewModel){
    viewModel.getHourly12Hours()
    val hourly : List<HourlyWeather> by viewModel.hourly12hours.observeAsState(listOf())
    LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)) {
        items(
            items = hourly,
            itemContent = {
                HourlyListItem(hourlyWeather = it)
            }
        )
    }
}

@Composable
fun HourlyListItem(hourlyWeather : HourlyWeather){
    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
        Text(text = hourlyWeather.dateTime.dateFormater())
        val temperature = "${hourlyWeather.temperature.value}${hourlyWeather.temperature.unit}"
        Text(text = temperature.faranheidToCelsius())
        Image(
            ImageVector.vectorResource(id = getDrawableWeather(hourlyWeather.weatherIcon)),
            contentDescription = "",
            Modifier.size(20.dp)
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
