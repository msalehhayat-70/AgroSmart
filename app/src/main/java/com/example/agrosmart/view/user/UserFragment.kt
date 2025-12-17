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
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Timestamp
import java.io.IOException
import java.security.Timestamp

private val Timestamp?.seconds: Long

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
                val data: Intent? = result.data
                val filePath = data?.data
                if (filePath != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, filePath)
                        bitmap?.let {
                            if (uploadProfOrBack == 0) binding.userImageUserFrag.setImageBitmap(it)
                            else if (uploadProfOrBack == 1) binding.userBackgroundImage.setImageBitmap(it)

                            Toast.makeText(requireContext(), "Image selected. Ready to upload.", Toast.LENGTH_SHORT).show()
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

        // TODO: Replace with actual authenticated user ID
        val userId = "user@example.com"
        userDataViewModel.getUserData(userId)
        viewModel.getAllPosts(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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

        binding.uploadUserBackgroundImage.setOnClickListener { selectImage(1) }
        binding.uploadProfilePictureImage.setOnClickListener { selectImage(0) }
        binding.imageEdit.setOnClickListener { toggleEditMode(true) }
        binding.imageChecked.setOnClickListener { toggleEditMode(false) }

        // RecyclerView layout manager
        binding.userProfilePostsRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {

        // Posts LiveData
        viewModel.postsLiveData.observe(viewLifecycleOwner) { snapshots ->
            val posts = snapshots?.map { doc ->
                UserProfilePost(
                    id = doc.id,
                    title = doc.getString("title") ?: "",
                    timeStamp = (doc.get("timestamp") as? Timestamp)?.seconds ?: 0L,
                    imageUrl = doc.getString("imageUrl")
                )
            } ?: emptyList()

            val adapter = PostListUserProfileAdapter(requireContext(), posts, this)
            binding.userProfilePostsRecycler.adapter = adapter
        }

        // User data LiveData
        userDataViewModel.userliveData.observe(viewLifecycleOwner) { doc ->
            doc?.let {
                if (it.exists()) {
                    binding.userNameUserProfileFrag.text = it.getString("name") ?: "User Name"
                    binding.userCityUserProfileFrag.text = "City: ${it.getString("city") ?: "Unknown"}"
                    binding.userEmailUserProfileFrag.text = it.getString("email") ?: "user@example.com"
                    binding.aboutValueUserProfileFrag.text = it.getString("about") ?: "About me..."
                    binding.userPostsCountUserProfileFrag.text =
                        "Posts: ${(it.get("posts") as? List<*>)?.size ?: 0}"

                    it.getString("profileImage")?.let { url ->
                        Glide.with(requireContext()).load(url).into(binding.userImageUserFrag)
                    }

                    it.getString("backImage")?.let { url ->
                        Glide.with(requireContext()).load(url).into(binding.userBackgroundImage)
                    }
                } else {
                    binding.userNameUserProfileFrag.text = "User not found"
                }
            }
        }
    }

    private fun selectImage(uploadType: Int) {
        uploadProfOrBack = uploadType
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
        }
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

        if (enable) {
            binding.cityEditUserProfile.setText(binding.userCityUserProfileFrag.text.toString().removePrefix("City: "))
            binding.aboutValueEditUserProfileFrag.setText(binding.aboutValueUserProfileFrag.text.toString())
        } else {
            val newCity = binding.cityEditUserProfile.text.toString()
            val newAbout = binding.aboutValueEditUserProfileFrag.text.toString()
            // TODO: Replace with actual user ID
            userDataViewModel.updateUserField(requireContext(), "user@example.com", newAbout, newCity)

            binding.userCityUserProfileFrag.text = "City: $newCity"
            binding.aboutValueUserProfileFrag.text = newAbout
        }
    }

    override fun onCellClickListener(name: String) {
        AlertDialog.Builder(requireActivity())
            .setTitle("Your Post")
            .setMessage("Do you want to edit your post?")
            .setPositiveButton("View") { _, _ -> }
            .setNegativeButton("Delete") { _, _ ->
                // TODO: Replace with actual user ID
                userDataViewModel.deleteUserPost("user@example.com", name)
                viewModel.getAllPosts("user@example.com")
            }
            .setNeutralButton("Cancel", null)
            .show()

        Toast.makeText(requireContext(), "You clicked $name", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
