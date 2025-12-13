package com.example.agrosmart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.model.Scheme
import com.example.agrosmart.utilities.CellClickListener

class SchemeAdapter(
    private val schemeData: List<Scheme>,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<SchemeAdapter.SchemeViewHolder>() {

    class SchemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val schemeName: TextView = itemView.findViewById(R.id.schemeTitle)
        val schemeImage: ImageView = itemView.findViewById(R.id.schemeImage)
        val schemeDate: TextView = itemView.findViewById(R.id.schemeDate)
        val schemeCard: CardView = itemView.findViewById(R.id.schemeCard)
        val schemeStatus: TextView = itemView.findViewById(R.id.schemeStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchemeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_scheme_list, parent, false)
        return SchemeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return schemeData.size
    }

    override fun onBindViewHolder(holder: SchemeViewHolder, position: Int) {
        val singleScheme = schemeData[position]

        holder.schemeName.text = singleScheme.title
        holder.schemeStatus.text = singleScheme.status
        holder.schemeDate.text = singleScheme.launch

        if (!singleScheme.image.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(singleScheme.image)
                .into(holder.schemeImage)
        }

        holder.schemeCard.setOnClickListener {
            cellClickListener.onCellClickListener(singleScheme.title)
        }
    }
}
