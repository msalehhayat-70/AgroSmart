package com.example.agrosmart.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.agrosmart.R
import com.example.agrosmart.utilities.CellClickListener

class AttributesSelectionAdapter(
    private val allData: List<Map<String, Any>>,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<AttributesSelectionAdapter.AttributesSelectionViewHolder>() {

    private val selectionState = mutableMapOf<Int, Int>()

    class AttributesSelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val attributeTitle: TextView = itemView.findViewById(R.id.attributeTitle)

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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_selection_attributes_ecomm, parent, false)
        return AttributesSelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun onBindViewHolder(holder: AttributesSelectionViewHolder, position: Int) {
        val currentDataMap = allData[position]
        val entry = currentDataMap.entries.firstOrNull() ?: return

        val key = entry.key
        holder.attributeTitle.text = key

        val values = entry.value as? List<*> ?: return

        for (i in 0..2) {
            val valueString = values.getOrNull(i)?.toString()
            val parts = valueString?.split(" ")

            holder.attributeTexts[i].text = parts?.getOrNull(0) ?: ""
            holder.attributePrices[i].text = parts?.getOrNull(1) ?: ""
        }

        val selectedIndex = selectionState.getOrDefault(position, 0)
        updateCardStyles(holder, selectedIndex)

        for (i in 0..2) {
            holder.attributeCards[i].setOnClickListener {
                selectionState[position] = i
                updateCardStyles(holder, i)
                cellClickListener.onCellClickListener("${i + 1} $key")
            }
        }
    }

    private fun updateCardStyles(holder: AttributesSelectionViewHolder, selectedIndex: Int) {
        val context = holder.itemView.context

        val selectedColor = ContextCompat.getColorStateList(context, R.color.colorPrimary)
        val unselectedColor = ContextCompat.getColorStateList(context, R.color.secondary)
        val selectedTextColor = Color.WHITE
        val unselectedTextColor = ContextCompat.getColor(context, R.color.fontColor)

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
