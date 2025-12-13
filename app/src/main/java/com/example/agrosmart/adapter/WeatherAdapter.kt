package com.example.agrosmart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.model.WeatherList
import java.text.SimpleDateFormat
import java.util.*

class WeatherAdapter(private val weatherrootdatas: List<WeatherList>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wedate: TextView = itemView.findViewById(R.id.weatherDate)
        val wedesc: TextView = itemView.findViewById(R.id.weatherDescription)
        val wemain: TextView = itemView.findViewById(R.id.weatherTemperature)
        val welogo: ImageView = itemView.findViewById(R.id.weatherIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherrootdatas.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weathernew = weatherrootdatas[position]

        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val date: Date? = inputFormat.parse(weathernew.dt_txt.slice(0..9))
        val outputDate = date?.let { outputFormat.format(it) } ?: ""

        val we = weathernew.weather[0]
        val we2 = weathernew.main
        holder.wedate.text = outputDate
        holder.wedesc.text = we.description.replaceFirstChar {
            if (it.isLowerCase()) it.uppercase() else it.toString()
        }
        val temp = we2.temp - 273.15
        holder.wemain.text = "${temp.toInt()}\u2103"

        val iconcode = weathernew.weather[0].icon

        val iconurl = "https://openweathermap.org/img/w/$iconcode.png"
        Glide.with(holder.itemView.context)
            .load(iconurl)
            .into(holder.welogo)
    }
}
