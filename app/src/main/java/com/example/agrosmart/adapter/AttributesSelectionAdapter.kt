package com.project.farmingapp.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.farmingapp.R
import com.project.farmingapp.utilities.CellClickListener

class AttributesSelectionAdapter(
    private val allData: List<Map<String, Any>>,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<AttributesSelectionAdapter.AttributesSelectionViewHolder>() {

    // Stores the selected index for each attribute group (row in the RecyclerView)
    // The key is the adapter position, and the value is the selected card index (0, 1, or 2)
    private val selectionState = mutableMapOf<Int, Int>()

    // Holds the view references to avoid repeated and slow findViewById calls
    class AttributesSelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val attributeTitle: TextView = itemView.findViewById(R.id.attributeTitle)

        // Grouping views for easier management
        val attributeCards = listOf<CardView>(
            itemView.findViewById(R.id.cardSize1),
            itemView.findViewById(R.id.cardSize2),
            itemView.findViewById(R.id.cardSize3)
        )
        val attributeTexts = listOf<TextView>(
            itemView.findViewById(R.id.attribute1),
            itemView.findViewById(R.id.attribute2),
            itemView.findViewById(R.id.attribute3)
        )
        val attributePrices = listOf<TextView>(
            itemView.findViewById(R.id.attribute1Price),
            itemView.findViewById(R.id.attribute2Price),
            itemView.findViewById(R.id.attribute3Price)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributesSelectionViewHolder {
        // Use parent.context to inflate views - this is the safe way
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_selection_attributes_ecomm, parent, false)
        return AttributesSelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun onBindViewHolder(holder: AttributesSelectionViewHolder, position: Int) {
        val currentDataMap = allData[position]
        val entry = currentDataMap.entries.firstOrNull() ?: return // Exit if map is empty

        val key = entry.key
        holder.attributeTitle.text = key

        // Safely get the list of values
        val values = entry.value as? List<*> ?: return

        // Set the text for each attribute card
        for (i in 0..2) {
            val valueString = values.getOrNull(i)?.toString()
            val parts = valueString?.split(" ")

            holder.attributeTexts[i].text = parts?.getOrNull(0) ?: ""
            holder.attributePrices[i].text = parts?.getOrNull(1) ?: ""
        }

        // Restore the selection state for this item
        val selectedIndex = selectionState.getOrDefault(position, 0)
        updateCardStyles(holder, selectedIndex)

        // Set click listeners for each card
        for (i in 0..2) {
            holder.attributeCards[i].setOnClickListener {
                // Update state
                selectionState[position] = i
                // Update UI
                updateCardStyles(holder, i)
                // Notify the fragment/activity of the selection
                cellClickListener.onCellClickListener("${i + 1} $key")
            }
        }
    }

    /**
     * A helper function to update the visual style of all three cards based on the selected one.
     * This removes a massive amount of duplicate code.
     */
    private fun updateCardStyles(holder: AttributesSelectionViewHolder, selectedIndex: Int) {
        val context = holder.itemView.context

        // Define selected and unselected styles
        val selectedColor = ContextCompat.getColorStateList(context, R.color.colorPrimary)
        val unselectedColor = ContextCompat.getColorStateList(context, R.color.secondary)
        val selectedTextColor = Color.WHITE
        val unselectedTextColor = ContextCompat.getColor(context, R.color.fontColor) // FIXED: Use color resource

        for (i in 0..2) {
            val isSelected = (i == selectedIndex)
            val card = holder.attributeCards[i]
            val text = holder.attributeTexts[i]
            val price = holder.attributePrices[i]

            card.backgroundTintList = if (isSelected) selectedColor else unselectedColor
            text.setTextColor(if (isSelected) selectedTextColor else unselectedTextColor)
            price.setTextColor(if (isSelected) selectedTextColor else unselectedTextColor)
            text.setTypeface(null, if (isSelected) Typeface.BOLD else Typeface.NORMAL)
            price.setTypeface(null, if (isSelected) Typeface.BOLD else Typeface.NORMAL)
        }
    }
}
