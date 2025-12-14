package com.example.agrosmart.view.ecommerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.agrosmart.R
import com.example.agrosmart.adapter.EcommerceAdapter
import com.example.agrosmart.databinding.FragmentEcommerceBinding
import com.example.agrosmart.utilities.CellClickListener
import com.example.agrosmart.viewmodel.EcommViewModel

class EcommerceFragment : Fragment(), CellClickListener {

    private var _binding: FragmentEcommerceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EcommViewModel by viewModels()
    private lateinit var ecommerceAdapter: EcommerceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEcommerceBinding.inflate(inflater, container, false)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Shop"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        viewModel.loadAllEcommItems()
    }

    private fun setupObservers() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            if (!products.isNullOrEmpty()) {
                ecommerceAdapter = EcommerceAdapter(requireContext(), products, this)
                binding.recyclerEcomm.adapter = ecommerceAdapter
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerEcomm.layoutManager = GridLayoutManager(requireContext(), 2)
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
}
