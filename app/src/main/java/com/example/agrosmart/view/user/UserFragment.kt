package com.example.agrosmart.view.user

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.agrosmart.adapter.PostListUserProfileAdapter
import com.example.agrosmart.databinding.FragmentUserBinding
import com.example.agrosmart.model.UserProfilePost
import com.example.agrosmart.utilities.CellClickListener
import com.example.agrosmart.viewmodel.UserDataViewModel
import com.example.agrosmart.viewmodel.UserProfilePostsViewModel
import com.google.firebase.Timestamp
import java.io.IOException

class UserFragment : Fragment(), CellClickListener {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserProfilePostsViewModel
    private lateinit var userDataViewModel: UserDataViewModel

    private var bitmap: Bitmap? = null
    private var uploadProfOrBack: Int? = null

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val filePath = result.data?.data
                if (filePath != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(
                            requireActivity().contentResolver,
                            filePath
                        )

                        bitmap?.let {
                            if (uploadProfOrBack == 0)
                                binding.userImageUserFrag.setImageBitmap(it)
                            else
                                binding.userBackgroundImage.setImageBitmap(it)

                            Toast.makeText(
                                requireContext(),
                                "Image selected successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[UserProfilePostsViewModel::class.java]
        userDataViewModel = ViewModelProvider(requireActivity())[UserDataViewModel::class.java]

        val userId = "user@example.com"
        userDataViewModel.getUserData(userId)
        viewModel.getAllPosts(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Profile"

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.cityEditUserProfile.visibility = View.GONE
        binding.aboutValueEditUserProfileFrag.visibility = View.GONE
        binding.uploadProfilePictureImage.visibility = View.GONE
        binding.uploadUserBackgroundImage.visibility = View.GONE
        binding.imageChecked.visibility = View.GONE

        binding.uploadProfilePictureImage.setOnClickListener { selectImage(0) }
        binding.uploadUserBackgroundImage.setOnClickListener { selectImage(1) }

        binding.imageEdit.setOnClickListener { toggleEditMode(true) }
        binding.imageChecked.setOnClickListener { toggleEditMode(false) }

        binding.userProfilePostsRecycler.layoutManager =
            LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {

        viewModel.postsLiveData.observe(viewLifecycleOwner) { snapshots ->

            val posts = snapshots?.map { doc ->
                UserProfilePost(
                    id = doc.id,
                    title = doc.getString("title") ?: "",
                    timeStamp = (doc.get("timestamp") as? Timestamp)?.seconds ?: 0L,
                    imageUrl = doc.getString("imageUrl")
                )
            } ?: emptyList()

            binding.userProfilePostsRecycler.adapter =
                PostListUserProfileAdapter(requireContext(), posts, this)
        }

        userDataViewModel.userliveData.observe(viewLifecycleOwner) { doc ->
            doc?.let {
                if (it.exists()) {

                    binding.userNameUserProfileFrag.text =
                        it.getString("name") ?: "User Name"

                    binding.userCityUserProfileFrag.text =
                        "City: ${it.getString("city") ?: "Unknown"}"

                    binding.userEmailUserProfileFrag.text =
                        it.getString("email") ?: "user@example.com"

                    binding.aboutValueUserProfileFrag.text =
                        it.getString("about") ?: "About me..."

                    binding.userPostsCountUserProfileFrag.text =
                        "Posts: ${(it.get("posts") as? List<*>)?.size ?: 0}"

                    it.getString("profileImage")?.let { url ->
                        Glide.with(requireContext())
                            .load(url)
                            .into(binding.userImageUserFrag)
                    }

                    it.getString("backImage")?.let { url ->
                        Glide.with(requireContext())
                            .load(url)
                            .into(binding.userBackgroundImage)
                    }

                } else {
                    binding.userNameUserProfileFrag.text = "User not found"
                }
            }
        }
    }

    private fun selectImage(type: Int) {
        uploadProfOrBack = type
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickImageLauncher.launch(intent)
    }

    private fun toggleEditMode(enable: Boolean) {

        val visibility = if (enable) View.VISIBLE else View.GONE

        binding.cityEditUserProfile.visibility = visibility
        binding.aboutValueEditUserProfileFrag.visibility = visibility
        binding.uploadProfilePictureImage.visibility = visibility
        binding.uploadUserBackgroundImage.visibility = visibility
        binding.imageChecked.visibility = visibility
        binding.imageEdit.visibility = if (enable) View.GONE else View.VISIBLE

        if (!enable) {
            val city = binding.cityEditUserProfile.text.toString()
            val about = binding.aboutValueEditUserProfileFrag.text.toString()

            userDataViewModel.updateUserField(
                requireContext(),
                "user@example.com",
                about,
                city
            )

            binding.userCityUserProfileFrag.text = "City: $city"
            binding.aboutValueUserProfileFrag.text = about
        }
    }

    override fun onCellClickListener(name: String) {
        AlertDialog.Builder(requireActivity())
            .setTitle("Your Post")
            .setMessage("Do you want to delete this post?")
            .setPositiveButton("Delete") { _, _ ->
                userDataViewModel.deleteUserPost("user@example.com", name)
                viewModel.getAllPosts("user@example.com")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
