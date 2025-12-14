package com.example.agrosmart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrosmart.R
import com.example.agrosmart.model.PamraCustomRecords

class PamraAdapter(private val data: List<PamraCustomRecords>) :
    RecyclerView.Adapter<PamraAdapter.PamraViewHolder>() {

    class PamraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val market: TextView = itemView.findViewById(R.id.pamraNameValue)
        val location: TextView = itemView.findViewById(R.id.pamraLocationValue)
        val commodity: TextView = itemView.findViewById(R.id.comodityname)
        val min: TextView = itemView.findViewById(R.id.minvalue)
        val max: TextView = itemView.findViewById(R.id.maxvalue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PamraViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pamra_single_list, parent, false)
        return PamraViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PamraViewHolder, position: Int) {
        val currentItem = data[position]
        holder.market.text = currentItem.market
        holder.location.text = "${currentItem.district}, ${currentItem.state}"

        val commodityBuilder = StringBuilder()
        for (item in currentItem.commodity) {
            commodityBuilder.append(item).append("\n")
        }
        holder.commodity.text = commodityBuilder.toString().trimEnd()

        val minPriceBuilder = StringBuilder()
        for (price in currentItem.min_price) {
            minPriceBuilder.append(price).append("\n")
        }
        holder.min.text = minPriceBuilder.toString().trimEnd()

        val maxPriceBuilder = StringBuilder()
        for (price in currentItem.max_price) {
            maxPriceBuilder.append(price).append("\n")
        }
        holder.max.text = maxPriceBuilder.toString().trimEnd()
    }
}
