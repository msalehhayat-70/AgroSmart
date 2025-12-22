package com.example.agrosmart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.model.ForecastItem

class WeatherAdapter(private val forecastItems: List<ForecastItem>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.date)
        val icon: ImageView = itemView.findViewById(R.id.weatherIcon)
        val temp: TextView = itemView.findViewById(R.id.temperature)
        val description: TextView = itemView.findViewById(R.id.weatherDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = forecastItems[position]
        holder.date.text = item.dtTxt.substring(0, 10)
        holder.temp.text = "${item.main.temp.toInt()}°C"
        holder.description.text = item.weather.firstOrNull()?.description ?: ""

        val iconUrl = "https://openweathermap.org/img/w/${item.weather.firstOrNull()?.icon}.png"
        Glide.with(holder.itemView.context).load(iconUrl).into(holder.icon)
    }

    override fun getItemCount() = forecastItems.size
}
