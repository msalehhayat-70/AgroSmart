package com.example.agrosmart.view.socialmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrosmart.R
import com.example.agrosmart.adapter.SMPostListAdapter
import com.example.agrosmart.databinding.FragmentSocialMediaPostsBinding
import com.example.agrosmart.viewmodel.SocialViewModel

class SocialMediaPostsFragment : Fragment() {

    private var _binding: FragmentSocialMediaPostsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SocialViewModel by viewModels()
    private lateinit var postAdapter: SMPostListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSocialMediaPostsBinding.inflate(inflater, container, false)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Social Media"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClickListeners()
        setupObservers()

        viewModel.loadPosts()
    }

    private fun setupObservers() {
        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            if (posts != null) {
                postAdapter = SMPostListAdapter(requireActivity(), viewLifecycleOwner, posts, viewModel)
                binding.postsRecycler.adapter = postAdapter
            }
        }
    }

    private fun setupRecyclerView() {
        binding.postsRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupClickListeners() {
        binding.createPostFloating.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, SMCreatePostFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
