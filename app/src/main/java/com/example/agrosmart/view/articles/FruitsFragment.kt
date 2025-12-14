package com.example.agrosmart.view.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.agrosmart.databinding.FragmentFruitsBinding
import com.example.agrosmart.viewmodel.ArticleViewModel

class FruitsFragment : Fragment() {

    private var _binding: FragmentFruitsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFruitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleName = arguments?.getString("name") ?: ""
        (activity as? AppCompatActivity)?.supportActionBar?.title = articleName

        viewModel.getSpecificFruitArticle(articleName)

        viewModel.article.observe(viewLifecycleOwner) { article ->
            if (article != null) {
                binding.fruitFragmentTitle.text = article.title
                if (article.images.isNotEmpty()) {
                    Glide.with(this).load(article.images[0]).into(binding.fruitFragmentImage)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
