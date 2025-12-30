package com.example.agrosmart.view.articles

<<<<<<< HEAD
=======
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
>>>>>>> main
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity
=======
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
>>>>>>> main
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.agrosmart.databinding.FragmentFruitsBinding
import com.example.agrosmart.viewmodel.ArticleViewModel
<<<<<<< HEAD
=======
import java.io.File
>>>>>>> main

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
<<<<<<< HEAD
                    Glide.with(this).load(article.images[0]).into(binding.fruitFragmentImage)
=======
                    val imageResId = requireContext().resources.getIdentifier(article.images[0], "drawable", requireContext().packageName)
                    if (imageResId != 0) {
                        Glide.with(this).load(imageResId).into(binding.fruitFragmentImage)
                    } else {
                        // Load a default image if the resource is not found
                        Glide.with(this).load(com.example.agrosmart.R.drawable.scheme).into(binding.fruitFragmentImage)
                    }
                }

                binding.fruitFragmentImage.setOnClickListener {
                    openPdf(articleName.toPdfFileName())
>>>>>>> main
                }
            }
        }
    }

<<<<<<< HEAD
=======
    private fun openPdf(pdfFileName: String) {
        try {
            val file = File(requireContext().cacheDir, pdfFileName)
            if (!file.exists()) {
                requireContext().assets.open(pdfFileName).use { inputStream ->
                    file.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }

            val uri = FileProvider.getUriForFile(requireContext(), requireContext().packageName + ".provider", file)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No PDF viewer found", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error opening PDF", Toast.LENGTH_SHORT).show()
        }
    }

    private fun String.toPdfFileName(): String {
        return this.lowercase().replace(" ", "_") + ".pdf"
    }

>>>>>>> main
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
