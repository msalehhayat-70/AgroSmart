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
import com.example.agrosmart.adapter.PostListUserProfileAdapter
import com.example.agrosmart.databinding.FragmentUserBinding
import com.example.agrosmart.utilities.CellClickListener
import com.example.agrosmart.viewmodel.UserDataViewModel
import com.example.agrosmart.viewmodel.UserProfilePostsViewModel
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
        viewModel = ViewModelProvider(requireActivity()).get(UserProfilePostsViewModel::class.java)
        userDataViewModel = ViewModelProvider(requireActivity()).get(UserDataViewModel::class.java)
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
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // No menu to inflate, but you could add items here programmatically
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle menu item selection
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupUI() {
        binding.cityEditUserProfile.visibility = View.GONE
        binding.uploadProgressBarProfile.visibility = View.GONE
        binding.uploadBackProgressProfile.visibility = View.GONE

        // TODO: Observe user data from your new database and update the UI.
        // For now, setting some dummy data.
        binding.userNameUserProfileFrag.text = "User Name"
        binding.userCityUserProfileFrag.text = "City: Unknown"
        binding.userPostsCountUserProfileFrag.text = "Posts: 0"
        binding.userEmailUserProfileFrag.text = "user@example.com"
        binding.aboutValueUserProfileFrag.text = "About me..."

        binding.uploadUserBackgroundImage.setOnClickListener {
            selectImage(1)
        }

        binding.uploadProfilePictureImage.setOnClickListener {
            selectImage(0)
        }

        binding.imageEdit.setOnClickListener {
            toggleEditMode(true)
        }

        binding.imageChecked.setOnClickListener {
            toggleEditMode(false)
            // TODO: Update user data in new database
        }
    }

    private fun setupObservers() {
        // TODO: Observe LiveData from ViewModels when they are implemented
         viewModel.liveData3.observe(viewLifecycleOwner) {
            val adapter = PostListUserProfileAdapter(requireContext(), it, this)
            binding.userProfilePostsRecycler.adapter = adapter
            binding.userProfilePostsRecycler.layoutManager = LinearLayoutManager(requireContext())
        }

        // userDataViewModel.userliveData.observe(viewLifecycleOwner) {
            // Update UI with user data from new database
        // }
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

        binding.aboutValueUserProfileFrag.visibility = if (enable) View.GONE else View.VISIBLE
        binding.aboutValueEditUserProfileFrag.visibility = if (enable) View.VISIBLE else View.GONE

        if (enable) {
            binding.cityEditUserProfile.setText(binding.userCityUserProfileFrag.text.toString().removePrefix("City: "))
            binding.aboutValueEditUserProfileFrag.setText(binding.aboutValueUserProfileFrag.text.toString())
        } else {
            // When finishing edit, you might want to save data
            binding.userCityUserProfileFrag.text = "City: ${binding.cityEditUserProfile.text}"
            binding.aboutValueUserProfileFrag.text = binding.aboutValueEditUserProfileFrag.text.toString()
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
            }
            .setNeutralButton("Cancel", null)
            .show()

        Toast.makeText(requireContext(), "You Clicked $name", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserFragment()
    }
}
