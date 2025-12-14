package com.example.agrosmart.adapter

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
import com.example.agrosmart.model.Product
import com.example.agrosmart.utilities.CellClickListener

class EcommerceAdapter(
    private val context: Context,
    private val productList: List<Product>,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<EcommerceAdapter.EcommerceViewHolder>() {

    class EcommerceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ecommTitle: TextView = itemView.findViewById(R.id.ecommtitle)
        val ecommPrice: TextView = itemView.findViewById(R.id.ecommPrice)
        val ecommRetailer: TextView = itemView.findViewById(R.id.ecommretailer)
        val ecommItemAvailability: TextView = itemView.findViewById(R.id.ecommItemAvailability)
        val ecommImage: ImageView = itemView.findViewById(R.id.ecommImage)
        val ecommRating: RatingBar = itemView.findViewById(R.id.ecommRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EcommerceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_ecomm_item, parent, false)
        return EcommerceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: EcommerceViewHolder, position: Int) {
        val currentProduct = productList[position]

        holder.ecommTitle.text = currentProduct.title
        holder.ecommPrice.text = "\u20B9${currentProduct.price}"
        holder.ecommRetailer.text = currentProduct.retailer
        holder.ecommItemAvailability.text = currentProduct.availability

        if (currentProduct.imageUrl.isNotEmpty()) {
            Glide.with(context).load(currentProduct.imageUrl[0]).into(holder.ecommImage)
        }

        holder.ecommRating.rating = currentProduct.rating

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(currentProduct.id)
        }
    }
}
