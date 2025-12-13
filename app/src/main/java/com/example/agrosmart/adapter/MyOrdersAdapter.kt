package com.example.agrosmart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.model.MyOrder
import com.example.agrosmart.model.Product
import com.example.agrosmart.utilities.CartItemBuy
import com.example.agrosmart.utilities.CellClickListener

class MyOrdersAdapter(
    private val myOrders: List<Pair<MyOrder, Product>>,
    private val cellClickListener: CellClickListener,
    private val cartItemBuy: CartItemBuy
) : RecyclerView.Adapter<MyOrdersAdapter.MyOrdersViewHolder>() {

    class MyOrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val myOrderPinCode: TextView = itemView.findViewById(R.id.myOrderPinCode)
        val myOrderItemName: TextView = itemView.findViewById(R.id.myOrderItemName)
        val myOrderItemPrice: TextView = itemView.findViewById(R.id.myOrderItemPrice)
        val myOderDeliveryCharge: TextView = itemView.findViewById(R.id.myOderDeliveryCharge)
        val myOrderDeliveryStatus: TextView = itemView.findViewById(R.id.myOrderDeliveryStatus)
        val myOderItemImage: ImageView = itemView.findViewById(R.id.myOderItemImage)
        val myOrderTimeStamp: TextView = itemView.findViewById(R.id.myOrderTimeStamp)
        val myOrderPurchaseAgain: Button = itemView.findViewById(R.id.myOrderPurchaseAgain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrdersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_myorder_item, parent, false)
        return MyOrdersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myOrders.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyOrdersViewHolder, position: Int) {
        val (myOrder, product) = myOrders[position]

        holder.myOrderItemName.text = product.title

        if (product.imageUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context).load(product.imageUrl[0]).into(holder.myOderItemImage)
        }

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(product.id)
        }

        val totalCost = (myOrder.quantity * myOrder.itemCost) + myOrder.deliveryCost

        holder.myOrderPinCode.text = "Pin Code: ${myOrder.pincode}"
        holder.myOrderItemPrice.text = "\u20B9$totalCost"
        holder.myOderDeliveryCharge.text = myOrder.deliveryCost.toString()
        holder.myOrderDeliveryStatus.text = myOrder.deliveryStatus

        val time = myOrder.time.split(" ").getOrNull(0) ?: ""
        holder.myOrderTimeStamp.text = time

        holder.myOrderPurchaseAgain.setOnClickListener {
            cartItemBuy.addToOrders(myOrder.productId, myOrder.quantity, myOrder.itemCost, myOrder.deliveryCost)
        }
    }
}
