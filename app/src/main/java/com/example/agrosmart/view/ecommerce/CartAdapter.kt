package com.example.agrosmart.view.ecommerce

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrosmart.databinding.SingleCartItemBinding
import com.example.agrosmart.model.CartItem

class CartAdapter(private val items: List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = SingleCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class CartViewHolder(private val binding: SingleCartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.itemNameCart.text = item.title
            binding.itemPriceCart.text = "Rs ${item.price}"
            binding.quantityCountEcomm.text = item.quantity.toString()
            Glide.with(binding.root.context).load(item.imageUrl).into(binding.cartItemImage)
        }
    }
}