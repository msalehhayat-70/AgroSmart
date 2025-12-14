package com.example.agrosmart.view.ecommerce

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.agrosmart.databinding.FragmentEcommerceItemBinding
import com.example.agrosmart.viewmodel.EcommViewModel

class EcommerceItemFragment : Fragment() {

    private var _binding: FragmentEcommerceItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EcommViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEcommerceItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = arguments?.getString("productId")

        if (productId != null) {
            viewModel.getProductById(productId)
        }

        setupObservers()

        binding.addToCartButton.setOnClickListener {
            Toast.makeText(requireContext(), "Add to cart clicked", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        viewModel.product.observe(viewLifecycleOwner) { product ->
            if (product != null) {
                (activity as? AppCompatActivity)?.supportActionBar?.title = product.title
                binding.productTitle.text = product.title
                binding.productPrice.text = "\u20B9${product.price}"
                binding.productRetailer.text = product.retailer

                if (product.imageUrl.isNotEmpty()) {
                    Glide.with(this).load(product.imageUrl[0]).into(binding.productImage)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
