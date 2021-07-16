package com.lms.weatherapp.ui.compose

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lms.weatherapp.common.utils.dateFormater
import com.lms.weatherapp.common.utils.faranheidToCelsius
import com.lms.weatherapp.common.utils.getDrawableWeather
import com.lms.weatherapp.common.utils.getHour
import com.lms.weatherapp.weather.model.CurrentWeather
import com.lms.weatherapp.weather.model.DailyForecast
import com.lms.weatherapp.weather.model.ForecastWeather
import com.lms.weatherapp.weather.model.HourlyWeather
import com.lms.weatherapp.weather.viewmodel.WeatherViewModel
import com.lms.wheatherapp.R
import kotlinx.coroutines.launch

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
fun HourlyModalDrawer(viewModel: WeatherViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerContent = {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),horizontalAlignment = Alignment.End) {
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
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                Surface(onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }, Modifier.padding(8.dp)) {
                    Image(
                        ImageVector.vectorResource(id = R.drawable.ic_hourly),
                        contentDescription = "",
                        Modifier.size(24.dp)
                    )
                }
                LocationName(viewModel = viewModel)
                CurrentWeatherView(viewModel = viewModel)
                ForecastWeather5days(viewModel = viewModel)
            }
        }
    )
}

@Composable
fun ForecastHourly12hours(viewModel: WeatherViewModel) {
    viewModel.getHourly12Hours()
    val hourly: List<HourlyWeather> by viewModel.hourly12hours.observeAsState(listOf())
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
fun HourlyListItem(hourlyWeather: HourlyWeather) {
    Card(modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = colorResource(id = R.color.colorPrimary),
        shape = RoundedCornerShape(corner = CornerSize(8.dp))
    )
    {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp), Arrangement.SpaceBetween) {
            Text(text = hourlyWeather.dateTime.getHour())
            val temperature = "${hourlyWeather.temperature.value}${hourlyWeather.temperature.unit}"
            Text(text = temperature.faranheidToCelsius())
            Image(
                ImageVector.vectorResource(id = getDrawableWeather(hourlyWeather.weatherIcon)),
                contentDescription = "",
                Modifier.size(20.dp)
            )
        }
    }

}
