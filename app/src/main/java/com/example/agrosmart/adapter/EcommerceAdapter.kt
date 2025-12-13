package com.example.agrosmart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.model.EcommItem
import com.example.agrosmart.utilities.CellClickListener

class EcommerceAdapter(
    private val context: Context,
    private val ecommtListData: List<EcommItem>,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<EcommerceAdapter.EcommercceViewModel>() {

    class EcommercceViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ecommtitle: TextView = itemView.findViewById(R.id.ecommtitle)
        val ecommPrice: TextView = itemView.findViewById(R.id.ecommPrice)
        val ecommretailer: TextView = itemView.findViewById(R.id.ecommretailer)
        val ecommItemAvailability: TextView = itemView.findViewById(R.id.ecommItemAvailability)
        val ecommImage: ImageView = itemView.findViewById(R.id.ecommImage)
        val ecommRating: RatingBar = itemView.findViewById(R.id.ecommRating)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EcommercceViewModel {
        val view = LayoutInflater.from(context).inflate(R.layout.single_ecomm_item, parent, false)
        return EcommercceViewModel(view)
    }

    override fun getItemCount(): Int {
        return ecommtListData.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EcommercceViewModel, position: Int) {
        val currentList = ecommtListData[position]
        holder.ecommtitle.text = currentList.title
        holder.ecommPrice.text = "\u20B9 ${currentList.price}"
        holder.ecommretailer.text = currentList.retailer
        holder.ecommItemAvailability.text = currentList.availability
        Glide.with(context).load(currentList.imageUrl[0]).into(holder.ecommImage)
        holder.ecommRating.rating = currentList.rating

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(currentList.id)
        }
    }
}