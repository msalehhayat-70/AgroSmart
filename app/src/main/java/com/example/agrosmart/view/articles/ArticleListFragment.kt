package com.example.agrosmart.view.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.agrosmart.R
import com.example.agrosmart.adapter.ArticleListAdapter
import com.example.agrosmart.databinding.FragmentArticleListBinding
import com.example.agrosmart.model.Article
import com.example.agrosmart.utilities.CellClickListener
import com.example.agrosmart.viewmodel.ArticleViewModel

class ArticleListFragment : Fragment(), CellClickListener {

    private var _binding: FragmentArticleListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleViewModel by viewModels()
    private lateinit var articleAdapter: ArticleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Articles"

        setupRecyclerView()
        setupObservers()

        viewModel.getAllArticles()
    }

    private fun setupObservers() {
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            if (articles != null) {
                articleAdapter = ArticleListAdapter(articles, this)
                binding.recyclerArticleListFrag.adapter = articleAdapter
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerArticleListFrag.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    override fun onCellClickListener(data: String) {
        val fruitsFragment = FruitsFragment()
        val bundle = Bundle()
        bundle.putString("name", data)
        fruitsFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, fruitsFragment, data)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
