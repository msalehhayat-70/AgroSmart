package com.example.agrosmart.view.ecommerce

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrosmart.adapter.CartItemsAdapter
import com.example.agrosmart.databinding.FragmentCartBinding
import com.example.agrosmart.model.CartItem
import com.example.agrosmart.model.Product
import com.example.agrosmart.utilities.CartItemBuy
import com.example.agrosmart.viewmodel.EcommViewModel

class CartFragment : Fragment(), CartItemBuy {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EcommViewModel by viewModels()
    private lateinit var cartAdapter: CartItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Cart"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        viewModel.getCartItems() // Ask the ViewModel to fetch the data
    }

    private fun setupObservers() {
        binding.progressCart.visibility = View.VISIBLE
        binding.loadingTitleText.visibility = View.VISIBLE

        viewModel.cartItemsWithProducts.observe(viewLifecycleOwner) { cartItems ->
            binding.progressCart.visibility = View.GONE
            binding.loadingTitleText.visibility = View.GONE

            if (cartItems.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show()
                binding.recyclerCart.visibility = View.GONE
            } else {
                binding.recyclerCart.visibility = View.VISIBLE
                cartAdapter = CartItemsAdapter(cartItems, this)
                binding.recyclerCart.adapter = cartAdapter
                updateCartTotals(cartItems)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updateCartTotals(cartItems: List<Pair<CartItem, Product>>) {
        val totalItems = cartItems.sumOf { it.first.quantity }
        val totalPrice = cartItems.sumOf { (it.first.quantity * it.second.price) + it.second.delCharge }
        binding.totalItemsValue.text = totalItems.toString()
        binding.totalCostValue.text = "\u20B9$totalPrice"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Implementation for CartItemBuy interface
    override fun addToOrders(productId: String, quantity: Int, itemCost: Int, deliveryCost: Int) {
        // The original logic was to navigate to RazorPayActivity. This is preserved.
        Intent(requireActivity(), RazorPayActivity::class.java).also {
            it.putExtra("productId", productId)
            it.putExtra("itemCost", itemCost.toString())
            it.putExtra("quantity", quantity.toString())
            it.putExtra("deliveryCost", deliveryCost.toString())
            startActivity(it)
        }
    }

    override fun removeItem(productId: String) {
        // Placeholder for removing item logic
        Toast.makeText(requireContext(), "$productId removed", Toast.LENGTH_SHORT).show()
        viewModel.getCartItems() // Re-fetch to update UI
    }

    override fun updateQuantity(productId: String, newQuantity: Int) {
        // Placeholder for updating quantity logic
        Toast.makeText(requireContext(), "Quantity for $productId updated to $newQuantity", Toast.LENGTH_SHORT).show()
        viewModel.getCartItems() // Re-fetch to update UI
    }
}
