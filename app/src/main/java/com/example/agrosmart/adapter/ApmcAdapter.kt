package com.project.farmingapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.farmingapp
import com.project.farmingapp.model.data.APMCCustomRecords

private val model: Any

class ApmcAdapter(val context: Context, private val data: List<APMCCustomRecords>) :
    RecyclerView.Adapter<ApmcAdapter.ApmcViewHolder>() {

    // A ViewHolder describes an item view and metadata about its place within the RecyclerView.
    class ApmcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // It's good practice to make these private if they are only used within the adapter
        val market: TextView = itemView.findViewById(R.id.apmcNameValue)
        val location: TextView = itemView.findViewById(R.id.apmcLocationValue)
        val commodity: TextView = itemView.findViewById(R.id.comodityname)
        val min: TextView = itemView.findViewById(R.id.minvalue)
        val max: TextView = itemView.findViewById(R.id.maxvalue)
        // var modal=itemView.findViewById<TextView>(R.id.modalValueTextApmc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApmcViewHolder {
        val view = LayoutInflater.from(parent.context) // Use parent.context for better practice
            .inflate(R.layout.apmc_single_list, parent, false)
        return ApmcViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ApmcViewHolder, position: Int) {
        val currentItem = data[position]
        holder.market.text = currentItem.market
        holder.location.text = "${currentItem.district}, ${currentItem.state}"

        // Use StringBuilder for efficient string concatenation in a loop
        val commodityBuilder = StringBuilder()
        for (item in currentItem.commodity) {
            commodityBuilder.append(item).append("\n")
        }
        // Remove the last newline character before setting the text
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

        // holder.modal.text = currentItem.modal_price
    }
}
