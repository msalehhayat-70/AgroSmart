package com.example.agrosmart.view.user

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data?.data != null) {
                val filePath = data.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, filePath)
                    if (bitmap != null) {
                        if (uploadProfOrBack == 0) {
                            binding.userImageUserFrag.setImageBitmap(bitmap)
                        } else if (uploadProfOrBack == 1) {
                            binding.userBackgroundImage.setImageBitmap(bitmap)
                        }
                        // TODO: Here you would initiate the upload to your new database.
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

        // TODO: Replace with actual user ID from your authentication system
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
        setupMenu()
        setupUI()
        setupObservers()
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = true
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupUI() {
        binding.cityEditUserProfile.visibility = View.GONE
        binding.uploadProgressBarProfile.visibility = View.GONE
        binding.uploadBackProgressProfile.visibility = View.GONE

        binding.uploadUserBackgroundImage.setOnClickListener { selectImage(1) }
        binding.uploadProfilePictureImage.setOnClickListener { selectImage(0) }
        binding.imageEdit.setOnClickListener { toggleEditMode(true) }
        binding.imageChecked.setOnClickListener { toggleEditMode(false) }
    }

    private fun setupObservers() {
        viewModel.postsLiveData.observe(viewLifecycleOwner) { snapshots ->
            if (snapshots != null) {
                val posts = snapshots.map { doc ->
                    UserProfilePost(
                        id = doc.id,
                        title = doc.getString("title") ?: "",
                        timeStamp = (doc.get("timestamp") as? Timestamp)?.seconds ?: 0L,
                        imageUrl = doc.getString("imageUrl")
                    )
                }
                val adapter = PostListUserProfileAdapter(requireContext(), posts, this)
                binding.userProfilePostsRecycler.adapter = adapter
                binding.userProfilePostsRecycler.layoutManager = LinearLayoutManager(requireContext())
            }
        }

        userDataViewModel.userliveData.observe(viewLifecycleOwner) { doc ->
            if (doc != null && doc.exists()) {
                binding.userNameUserProfileFrag.text = doc.getString("name") ?: "User Name"
                binding.userCityUserProfileFrag.text = "City: ${doc.getString("city") ?: "Unknown"}"
                val posts = doc.get("posts") as? List<*>
                binding.userPostsCountUserProfileFrag.text = "Posts: ${posts?.size ?: 0}"
                binding.userEmailUserProfileFrag.text = doc.getString("email") ?: "user@example.com"
                binding.aboutValueUserProfileFrag.text = doc.getString("about") ?: "About me..."

                val profileImageUrl = doc.getString("profileImage")
                if (!profileImageUrl.isNullOrEmpty()) {
                    Glide.with(requireContext()).load(profileImageUrl).into(binding.userImageUserFrag)
                }

                val backgroundImageUrl = doc.getString("backImage")
                if (!backgroundImageUrl.isNullOrEmpty()) {
                    Glide.with(requireContext()).load(backgroundImageUrl).into(binding.userBackgroundImage)
                }
            } else {
                // Handle case where user data is not found or failed to load
                binding.userNameUserProfileFrag.text = "User not found"
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
        binding.uploadProfilePictureImage.visibility = visibility
        binding.uploadUserBackgroundImage.visibility = visibility
        binding.imageChecked.visibility = visibility
        binding.imageEdit.visibility = if (enable) View.GONE else View.VISIBLE
        binding.cityEditUserProfile.visibility = visibility
        binding.aboutValueEditUserProfileFrag.visibility = if (enable) View.VISIBLE else View.GONE

        if (enable) {
            binding.cityEditUserProfile.setText(binding.userCityUserProfileFrag.text.toString().removePrefix("City: "))
            binding.aboutValueEditUserProfileFrag.setText(binding.aboutValueUserProfileFrag.text.toString())
        } else {
            // When finishing edit, save data
            val newAbout = binding.aboutValueEditUserProfileFrag.text.toString()
            val newCity = binding.cityEditUserProfile.text.toString()
            // TODO: Replace with actual user ID
            userDataViewModel.updateUserField(requireContext(), "user@example.com", newAbout, newCity)

            binding.userCityUserProfileFrag.text = "City: ${newCity}"
            binding.aboutValueUserProfileFrag.text = newAbout
        }
    }

    override fun onCellClickListener(name: String) {
        AlertDialog.Builder(requireActivity())
            .setTitle("Your Post")
            .setMessage("Do you want to edit your post?")
            .setPositiveButton("View") { _, _ ->
                // TODO: Implement view post
            }
            .setNegativeButton("Delete") { _, _ ->
                // TODO: Implement delete post
                // TODO: Replace with actual user ID
                userDataViewModel.deleteUserPost("user@example.com", name)
                viewModel.getAllPosts("user@example.com")
            }
            .setNeutralButton("Cancel", null)
            .show()

        Toast.makeText(requireContext(), "You Clicked ${name}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
