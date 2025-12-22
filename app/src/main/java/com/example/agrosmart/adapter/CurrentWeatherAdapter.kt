package com.example.agrosmart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.model.CurrentWeather
import java.util.Locale

class CurrentWeatherAdapter(private val context: Context, private val weatherrootdatas: List<CurrentWeather>) :
    RecyclerView.Adapter<CurrentWeatherAdapter.CurrentWeatherViewHolder>() {

    class CurrentWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cTemp: TextView = itemView.findViewById(R.id.temp)
        val cWeDesc: TextView = itemView.findViewById(R.id.desc)
        val cWeLogo: ImageView = itemView.findViewById(R.id.icon)
        val cMinTemp: TextView = itemView.findViewById(R.id.minTemp)
        val cMaxTemp: TextView = itemView.findViewById(R.id.maxTemp)
        val cHumidity: TextView = itemView.findViewById(R.id.humidity)
        val cTodayTitle: TextView = itemView.findViewById(R.id.todayTitle)
        val container: ConstraintLayout = itemView.findViewById(R.id.currentWeatherContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentWeatherViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_currentweather, parent, false)
        return CurrentWeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherrootdatas.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CurrentWeatherViewHolder, position: Int) {
        val weatherNew = weatherrootdatas[position]
        holder.cTemp.text = "${(weatherNew.main.temp - 273.15).toInt()}\u2103"

        weatherNew.weather.firstOrNull()?.let { weather ->
            holder.cWeDesc.text = weather.description.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            val iconCode = weather.icon
            val iconUrl = "https://openweathermap.org/img/w/$iconCode.png"

            Glide.with(holder.itemView.context)
                .load(iconUrl)
                .into(holder.cWeLogo)
        }

        weatherNew.dtTxt?.let {
            holder.cTodayTitle.text = "Today, ${it.slice(10..15)}"
        }

        holder.cMinTemp.text = "${(weatherNew.main.tempMin - 273.1).toInt()}\u2103"
        holder.container.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale)

        holder.cMaxTemp.text = "${(weatherNew.main.tempMax - 273.1).toInt()}\u2103"
        holder.cHumidity.text = "${weatherNew.main.humidity}%"
    }
}
