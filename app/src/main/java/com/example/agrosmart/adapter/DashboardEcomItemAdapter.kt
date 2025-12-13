package com.example.agrosmart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.model.DashboardEcomItem
import com.example.agrosmart.utilities.CellClickListener

class DashboardEcomItemAdapter(
    private val context: Context,
    private val allData: List<DashboardEcomItem>,
    private val itemsToShow: List<Int>,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<DashboardEcomItemAdapter.DashboardEcomItemViewHolder>() {

    class DashboardEcomItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardEcomItemViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.single_dashboard_ecomm_item, parent, false)
        return DashboardEcomItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsToShow.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DashboardEcomItemViewHolder, position: Int) {
        val currentData = allData[itemsToShow[position]]

        holder.itemTitle.text = currentData.title
        holder.itemPrice.text = "\u20B9${currentData.price}"
        Glide.with(context).load(currentData.imageUrl[0]).into(holder.itemImage)
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(currentData.id)
        }
    }
}
