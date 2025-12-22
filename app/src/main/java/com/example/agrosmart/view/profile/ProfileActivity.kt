package com.example.agrosmart.view.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.databinding.ActivityProfileBinding
import com.example.agrosmart.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private var imageUri: Uri? = null

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        binding.profileImage.setImageURI(imageUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserInfo()

        binding.profileImage.setOnClickListener {
            selectImage.launch("image/*")
        }

        binding.updateProfileButton.setOnClickListener {
            updateProfile()
        }
    }

    private fun loadUserInfo() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            binding.emailEditText.setText(user.email)
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(user.uid).get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val userProfile = document.toObject(User::class.java)
                    if (userProfile != null) {
                        binding.nameEditText.setText(userProfile.name)
                        Glide.with(this).load(userProfile.profileImgUrl).placeholder(R.drawable.ic_launcher_foreground).into(binding.profileImage)
                    }
                }
            }.addOnFailureListener { exception ->
                Log.e("ProfileActivity", "Error getting user document", exception)
            }
        }
    }

    private fun updateProfile() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()

            if (imageUri != null) {
                val storageRef = FirebaseStorage.getInstance().reference.child("profile_images/${user.uid}")
                storageRef.putFile(imageUri!!).addOnSuccessListener { 
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        updateFirestore(name, email, imageUrl)
                    }.addOnFailureListener { exception ->
                        Log.e("ProfileActivity", "Error getting download URL", exception)
                    }
                }.addOnFailureListener { exception ->
                    Log.e("ProfileActivity", "Error uploading profile image", exception)
                }
            } else {
                updateFirestore(name, email, null)
            }
        }
    }

    private fun updateFirestore(name: String, email: String, imageUrl: String?) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val db = FirebaseFirestore.getInstance()
            val userMap = mutableMapOf<String, Any>()
            userMap["name"] = name
            userMap["email"] = email
            if (imageUrl != null) {
                userMap["profileImgUrl"] = imageUrl
            }

            db.collection("users").document(user.uid).set(userMap, SetOptions.merge()).addOnSuccessListener {
                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "Error updating profile", Toast.LENGTH_SHORT).show()
                Log.e("ProfileActivity", "Error updating profile", exception)
            }
        }
    }
}
