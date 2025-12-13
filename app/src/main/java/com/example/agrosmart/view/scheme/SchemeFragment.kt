package com.example.agrosmart.view.scheme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.databinding.FragmentSchemeBinding
import com.example.agrosmart.model.Scheme
import com.example.agrosmart.viewmodel.SchemeViewModel

class SchemeFragment : Fragment() {

    private var _binding: FragmentSchemeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SchemeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Krishi Scheme"

        viewModel = ViewModelProvider(requireActivity()).get(SchemeViewModel::class.java)

        binding.progressScheme.visibility = View.VISIBLE

        val schemeName = arguments?.getString("schemeName") ?: ""
        viewModel.getScheme(schemeName)

        viewModel.scheme.observe(viewLifecycleOwner) { scheme ->
            if (scheme != null) {
                binding.progressScheme.visibility = View.GONE
                binding.schemeTitle.text = scheme.title
                binding.schemeDesc.text = scheme.description
                binding.schemeDate.text = scheme.launch
                binding.schemeBudget.text = scheme.budget
                binding.schemeLaunchedBy.text = scheme.headedBy

                if (!scheme.image.isNullOrEmpty()) {
                    Glide.with(this).load(scheme.image).into(binding.schemeImage)
                }

                var eligibilityText = ""
                for ((index, item) in scheme.eligibility.withIndex()) {
                    eligibilityText += "${index + 1}. $item\n"
                }
                binding.schemeEligibility.text = eligibilityText

                var objectivesText = ""
                for ((index, item) in scheme.objectives.withIndex()) {
                    objectivesText += "${index + 1}. $item\n"
                }
                binding.schemeObjectives.text = objectivesText

                var documentsText = ""
                for ((index, item) in scheme.documents.withIndex()) {
                    documentsText += "${index + 1}. $item\n"
                }
                binding.schemeDocumentsRequired.text = documentsText
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
