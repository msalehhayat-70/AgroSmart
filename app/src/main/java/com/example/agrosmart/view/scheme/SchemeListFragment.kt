package com.example.agrosmart.view.scheme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrosmart.R
import com.example.agrosmart.adapter.SchemeAdapter
import com.example.agrosmart.databinding.FragmentSchemeListBinding
import com.example.agrosmart.model.Scheme
import com.example.agrosmart.utilities.CellClickListener
import com.example.agrosmart.viewmodel.SchemeViewModel

class SchemeListFragment : Fragment(), CellClickListener {

    private var _binding: FragmentSchemeListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SchemeViewModel
    private lateinit var schemeAdapter: SchemeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchemeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Krishi Scheme"

        viewModel = ViewModelProvider(requireActivity()).get(SchemeViewModel::class.java)
        viewModel.getAllSchemes()

        viewModel.schemes.observe(viewLifecycleOwner) { schemes ->
            if (schemes != null) {
                setupRecyclerView(schemes)
            }
        }
    }

    private fun setupRecyclerView(schemes: List<Scheme>) {
        schemeAdapter = SchemeAdapter(schemes, this)
        binding.rcyclrSchemeList.adapter = schemeAdapter
        binding.rcyclrSchemeList.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCellClickListener(data: String) {
        val bundle = Bundle()
        bundle.putString("schemeName", data)

        val schemeFragment = SchemeFragment()
        schemeFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, schemeFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}