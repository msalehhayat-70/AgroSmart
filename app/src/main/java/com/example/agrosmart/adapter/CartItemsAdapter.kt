package com.example.agrosmart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.model.CartItem
import com.example.agrosmart.model.Product
import com.example.agrosmart.utilities.CartItemBuy

class CartItemsAdapter(
    private val cartItems: List<Pair<CartItem, Product>>,
    private val cartItemBuy: CartItemBuy
) : RecyclerView.Adapter<CartItemsAdapter.CartItemsViewHolder>() {

    class CartItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameCart: TextView = itemView.findViewById(R.id.itemNameCart)
        val itemPriceCart: TextView = itemView.findViewById(R.id.itemPriceCart)
        val quantityCountEcomm: TextView = itemView.findViewById(R.id.quantityCountEcomm)
        val deliveryChargeCart: TextView = itemView.findViewById(R.id.deliveryChargeCart)
        val cartItemFirm: TextView = itemView.findViewById(R.id.cartItemFirm)
        val cartItemAvailability: TextView = itemView.findViewById(R.id.cartItemAvailability)
        val cartItemImage: ImageView = itemView.findViewById(R.id.cartItemImage)
        val cartItemBuyBtn: Button = itemView.findViewById(R.id.cartItemBuyBtn)
        val removeCartBtn: ImageView = itemView.findViewById(R.id.removeCartBtn)
        val increaseQtyBtn: ImageButton = itemView.findViewById(R.id.increaseQtyBtn)
        val decreaseQtyBtn: ImageButton = itemView.findViewById(R.id.decreaseQtyBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_cart_item, parent, false)
        return CartItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartItemsViewHolder, position: Int) {
        val (cartItem, product) = cartItems[position]

        holder.itemNameCart.text = product.title
        holder.itemPriceCart.text = "\u20B9${product.price}"
        holder.quantityCountEcomm.text = cartItem.quantity.toString()
        holder.deliveryChargeCart.text = product.delCharge.toString()
        holder.cartItemFirm.text = product.retailer
        holder.cartItemAvailability.text = product.availability

        if (product.imageUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context).load(product.imageUrl[0]).into(holder.cartItemImage)
        }

        val totalCost = (product.price * cartItem.quantity) + product.delCharge
        holder.cartItemBuyBtn.text = "Buy Now: \u20B9$totalCost"

        holder.cartItemBuyBtn.setOnClickListener {
            cartItemBuy.addToOrders(
                product.id,
                cartItem.quantity,
                product.price,
                product.delCharge
            )
        }

        holder.removeCartBtn.setOnClickListener {
            cartItemBuy.removeItem(product.id)
        }

        holder.increaseQtyBtn.setOnClickListener {
            val newQuantity = cartItem.quantity + 1
            holder.quantityCountEcomm.text = newQuantity.toString()
            cartItemBuy.updateQuantity(product.id, newQuantity)
        }

        holder.decreaseQtyBtn.setOnClickListener {
            if (cartItem.quantity > 1) {
                val newQuantity = cartItem.quantity - 1
                holder.quantityCountEcomm.text = newQuantity.toString()
                cartItemBuy.updateQuantity(product.id, newQuantity)
            }
        }
    }
}
