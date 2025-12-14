package com.example.agrosmart.view.pamra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrosmart.R
import com.example.agrosmart.adapter.PamraAdapter
import com.example.agrosmart.databinding.FragmentPamraBinding
import com.example.agrosmart.model.PamraCustomRecords
import com.example.agrosmart.viewmodel.PamraViewModel
import java.text.SimpleDateFormat
import java.util.*

class PamraFragment : Fragment() {

    private var _binding: FragmentPamraBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PamraViewModel by viewModels()
    private lateinit var adapter: PamraAdapter

    private var states: Array<String>? = null
    private var districts: Map<String, Array<String>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPamraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "PAMRA"

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.dateValueTextPamra.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

        states = resources.getStringArray(R.array.states)
        districts = mapOf(
            "Gujarat" to resources.getStringArray(R.array.gujarat_districts),
            "Maharashtra" to resources.getStringArray(R.array.maharashtra_districts)
            // Add other states and districts here
        )

        val statesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, states!!)
        binding.spinner1.adapter = statesAdapter

        binding.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    val selectedState = states!![position]
                    val districtAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, districts!![selectedState]!!)
                    binding.spinner2.adapter = districtAdapter
                } else {
                    binding.textPamraWarning.text = getString(R.string.please_select_state)
                    binding.textPamraWarning.visibility = View.VISIBLE
                    binding.recyclePamra.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    val selectedDistrict = parent?.getItemAtPosition(position).toString()
                    viewModel.fetchPamraData(selectedDistrict)
                    binding.progressPamra.visibility = View.VISIBLE
                    binding.loadingTextPamra.visibility = View.VISIBLE
                } else {
                    binding.textPamraWarning.text = getString(R.string.please_select_district)
                    binding.textPamraWarning.visibility = View.VISIBLE
                    binding.recyclePamra.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupObservers() {
        viewModel.pamraData.observe(viewLifecycleOwner) { data ->
            binding.progressPamra.visibility = View.GONE
            binding.loadingTextPamra.visibility = View.GONE

            if (data.isNullOrEmpty()) {
                binding.textPamraWarning.text = getString(R.string.no_records_found)
                binding.textPamraWarning.visibility = View.VISIBLE
                binding.recyclePamra.visibility = View.GONE
            } else {
                binding.textPamraWarning.visibility = View.GONE
                binding.recyclePamra.visibility = View.VISIBLE
                setupRecyclerView(data)
            }
        }
    }

    private fun setupRecyclerView(data: List<PamraCustomRecords>) {
        adapter = PamraAdapter(data)
        binding.recyclePamra.adapter = adapter
        binding.recyclePamra.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
