package com.example.agrosmart.view.socialmedia

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.agrosmart.R
import com.example.agrosmart.databinding.FragmentSmCreatePostBinding
import com.example.agrosmart.viewmodel.SocialViewModel

class SMCreatePostFragment : Fragment() {

    private var _binding: FragmentSmCreatePostBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SocialViewModel by viewModels()
    private var selectedImageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            binding.uploadImagePreview.setImageURI(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSmCreatePostBinding.inflate(inflater, container, false)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Create Post"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners() {
        binding.uploadImagePreview.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.createPostBtnSM.setOnClickListener {
            val title = binding.postTitleSM.text.toString()
            val description = binding.descPostSM.text.toString()

            if (title.isBlank()) {
                Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.createPost(title, description, selectedImageUri)
        }
    }

    private fun setupObservers() {
        viewModel.postStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                SocialViewModel.PostStatus.POSTING -> {
                    binding.progressCreatePost.visibility = View.VISIBLE
                    binding.progressTitle.visibility = View.VISIBLE
                }
                SocialViewModel.PostStatus.SUCCESS -> {
                    binding.progressCreatePost.visibility = View.GONE
                    binding.progressTitle.visibility = View.GONE
                    Toast.makeText(requireContext(), "Post created successfully!", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
                SocialViewModel.PostStatus.FAILED -> {
                    binding.progressCreatePost.visibility = View.GONE
                    binding.progressTitle.visibility = View.GONE
                    Toast.makeText(requireContext(), "Failed to create post", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    binding.progressCreatePost.visibility = View.GONE
                    binding.progressTitle.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
