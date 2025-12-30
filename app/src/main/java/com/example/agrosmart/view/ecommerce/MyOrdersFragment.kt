package com.example.agrosmart.view.ecommerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrosmart.R
import com.example.agrosmart.adapter.MyOrdersAdapter
import com.example.agrosmart.databinding.FragmentMyOrdersBinding
import com.example.agrosmart.utilities.CartItemBuy
import com.example.agrosmart.utilities.CellClickListener
import com.example.agrosmart.viewmodel.MyOrderViewModel

class MyOrdersFragment : Fragment(), CellClickListener, CartItemBuy {

    private var _binding: FragmentMyOrdersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyOrderViewModel by viewModels()
    private lateinit var myOrdersAdapter: MyOrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "My Orders"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        viewModel.loadMyOrders()
    }

    private fun setupObservers() {
        viewModel.myOrdersWithProducts.observe(viewLifecycleOwner) { orders ->
            if (!orders.isNullOrEmpty()) {
                myOrdersAdapter = MyOrdersAdapter(orders, this, this)
                binding.recyclerMyOrders.adapter = myOrdersAdapter
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerMyOrders.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCellClickListener(data: String) {
        val ecommerceItemFragment = EcommerceItemFragment()
        val bundle = Bundle()
        bundle.putString("productId", data)
        ecommerceItemFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, ecommerceItemFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun addToOrders(productId: String, quantity: Int, itemCost: Int, deliveryCost: Int) {}
    override fun removeItem(productId: String) {}
    override fun updateQuantity(productId: String, newQuantity: Int) {}
}
