package com.example.agrosmart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrosmart.R

class AttributesNormalAdapter(private val allData: List<Map<String, Any>>) :
    RecyclerView.Adapter<AttributesNormalAdapter.AttributesNormalViewHolder>() {

    class AttributesNormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.normalAttributeTitle)
        val valueTextView: TextView = itemView.findViewById(R.id.normalAttributeValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributesNormalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_normal_attributes_ecomm, parent, false)
        return AttributesNormalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AttributesNormalViewHolder, position: Int) {
        val currentDataMap = allData[position]
        val firstEntry = currentDataMap.entries.firstOrNull()

        if (firstEntry != null) {
            holder.titleTextView.text = "${firstEntry.key} - "
            holder.valueTextView.text = firstEntry.value.toString()
        } else {
            holder.titleTextView.text = ""
            holder.valueTextView.text = ""
        }
    }
}
