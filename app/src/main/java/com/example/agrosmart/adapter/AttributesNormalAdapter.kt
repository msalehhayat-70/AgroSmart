package com.project.farmingapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.farmingapp.R

// The data type is a List of Maps, where each map represents one item in the RecyclerView.
class AttributesNormalAdapter(private val allData: List<Map<String, Any>>) :
    RecyclerView.Adapter<AttributesNormalAdapter.AttributesNormalViewHolder>() {

    // ViewHolder now holds direct references to the TextViews, which is more efficient.
    class AttributesNormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.normalAttributeTitle)
        val valueTextView: TextView = itemView.findViewById(R.id.normalAttributeValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributesNormalViewHolder {
        // Use parent.context to avoid potential memory leaks.
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_normal_attributes_ecomm, parent, false)
        return AttributesNormalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AttributesNormalViewHolder, position: Int) {
        // Get the map for the current position.
        val currentDataMap = allData[position]

        // Since each map is expected to have only one entry for this adapter to make sense,
        // we take the first (and likely only) key-value pair.
        val firstEntry = currentDataMap.entries.firstOrNull()

        if (firstEntry != null) {
            // Display the key and value of that single entry.
            holder.titleTextView.text = "${firstEntry.key} - "
            holder.valueTextView.text = firstEntry.value.toString()
        } else {
            // Handle cases where the map might be empty.
            holder.titleTextView.text = ""
            holder.valueTextView.text = ""
        }
    }
}
