package com.lms.weatherapp.common.utils

import com.lms.wheatherapp.R
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

fun getJsonWeather(weatherIcon: Int): Int {
    var icon: Int? = null
    when (weatherIcon) {
        1, 2 -> {
            icon = Icons.SUNNY.json
        }
        3, 4, 5 -> {
            icon = Icons.PARTLY_CLOUDY.json
        }
        6, 7, 19, 20 -> {
            icon = Icons.CLOUDY.json
        }
        8, 11 -> {
            icon = Icons.FOG.json
        }
        12, 18 -> {
            icon = Icons.RAIN.json
        }
        13, 14, 16, 17 -> {
            icon = Icons.SUNNY_SHOWERS.json
        }
        15 -> {
            icon = Icons.SHOWERS.json
        }
        22, 23 -> {
            icon = Icons.SNOW.json
        }
        25, 26 -> {
            icon = Icons.SLEET.json
        }
        29 -> {
            icon = Icons.SNOW_RAIN.json
        }
        33, 34, 35, 36 -> {
            icon = Icons.NIGHT_CLEAR.json
        }
        37, 38, 43, 44 -> {
            icon = Icons.NIGHT_CLOUDY.json
        }
        39, 40 -> {
            icon = Icons.NIGHT_RAIN.json
        }
        41, 42 -> {
            icon = Icons.NIGHT_STORM.json
        }
        43,44 -> {
            icon = Icons.NIGHT_SNOW.json
        }
        else -> {
            icon = Icons.SUNNY.json
        }

    }
    return icon
}

fun getDrawableWeather(weatherIcon: Int): Int {
    var icon: Int? = null
    when (weatherIcon) {
        1, 2 -> {
            icon = Icons.SUNNY.drawable
        }
        3, 4, 5 -> {
            icon = Icons.PARTLY_CLOUDY.drawable
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
        }
        43,44 -> {
            icon = Icons.NIGHT_SNOW.drawable
        }
        else -> {
            icon = Icons.SUNNY.drawable
        }

    }
    return icon
}

fun String.dateFormater(): String {
    var format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ")
    val newDate = format.parse(this)
    format = SimpleDateFormat("dd/M/yyyy")
    val date = format.format(newDate)
    return date.toString()
}

fun String.faranheidToCelsius(): String {
    val data = this.replace("F", "").toDouble()
    var celsius : Double = data
    if(this.contains("F")){
        this.replace("F", "")
        celsius =( (data  -  32  ) *  5)/9
    }
    celsius = (celsius * 10.0).roundToInt() / 10.0
    return "${celsius}ºC"
}

enum class Icons(val json: Int, val drawable: Int){
    SUNNY(R.raw.weather_sunny, R.drawable.ic_sunny),
    CLOUDY(R.raw.weather_windy, R.drawable.ic_cloudy),
    PARTLY_CLOUDY(R.raw.weather_partly_cloudy, R.drawable.ic_cloudy),
    SUNNY_SHOWERS(R.raw.weather_partly_shower, R.drawable.ic_sunny_storm),
    SHOWERS(R.raw.weather_storm, R.drawable.ic_storm),
    RAIN(R.raw.weather_stormshowersday, R.drawable.ic_rain),
    SNOW(R.raw.weather_snow, R.drawable.ic_snowy),
    SNOW_RAIN(R.raw.weather_snow_sunny, R.drawable.ic_rain_snowy),
    FOG(R.raw.foggy, R.drawable.ic_foggy),
    NIGHT_CLEAR(R.raw.weather_night, R.drawable.ic_night),
    NIGHT_CLOUDY(R.raw.weather_cloudynight, R.drawable.ic_night_cloudy),
    NIGHT_SNOW(R.raw.weather_snownight, R.drawable.ic_night_snowy),
    NIGHT_RAIN(R.raw.weather_rainynight, R.drawable.ic_night_rain),
    NIGHT_STORM(R.raw.weather_storm, R.drawable.ic_night_storm),
    SLEET(R.raw.weather_thunder, R.drawable.ic_hail)
}


