package com.lms.weatherapp.ui.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lms.weatherapp.common.utils.dateFormater
import com.lms.weatherapp.common.utils.getDrawableWeather
import com.lms.weatherapp.ui.views.adapter.ForecastAdapter.*
import com.lms.weatherapp.weather.model.DailyForecast
import com.lms.wheatherapp.R

class ForecastAdapter(private val forecast: List<DailyForecast>) : RecyclerView.Adapter<ForecastViewHolder>() {

    class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val date: TextView
        val min: TextView
        val max: TextView
        val iconDay: ImageView
        val iconNight: ImageView

        init{
            date = view.findViewById(R.id.forecast_date)
            min = view.findViewById(R.id.forecast_min)
            max = view.findViewById(R.id.forecast_max)
            iconDay = view.findViewById(R.id.icon_forecast_day)
            iconNight = view.findViewById(R.id.icon_forecast_night)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast_row, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.date.text = forecast[position].date.dateFormater()
        holder.max.text = "Max: ${forecast[position].temperature.maximum.value}${forecast[position].temperature.maximum.unit}"
        holder.min.text = "Max: ${forecast[position].temperature.minimum.value}${forecast[position].temperature.minimum.unit}"
        holder.iconDay.setImageResource(getDrawableWeather(forecast[position].day.icon))
        holder.iconNight.setImageResource(getDrawableWeather(forecast[position].night.icon))
    }

    override fun getItemCount() = forecast.size
}