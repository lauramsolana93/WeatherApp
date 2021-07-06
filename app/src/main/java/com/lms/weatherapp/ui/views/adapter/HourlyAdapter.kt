package com.lms.weatherapp.ui.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lms.weatherapp.common.utils.getDrawableWeather
import com.lms.weatherapp.common.utils.getHour
import com.lms.weatherapp.weather.model.HourlyWeather
import com.lms.wheatherapp.R

class HourlyAdapter(private val hourly: List<HourlyWeather>) : RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>(){

    class HourlyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image : ImageView
        val temperature: TextView
        val hour: TextView

        init {
            hour = view.findViewById(R.id.hour)
            temperature = view.findViewById(R.id.temperature)
            image = view.findViewById(R.id.imageHourly)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hourly_row, parent, false)
        return HourlyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.hour.text = hourly[position].dateTime.getHour()
        holder.temperature.text = "${hourly[position].temperature.value}º${hourly[position].temperature.unit}"
        holder.image.setImageResource(getDrawableWeather(hourly[position].weatherIcon))
    }

    override fun getItemCount() = hourly.size
}