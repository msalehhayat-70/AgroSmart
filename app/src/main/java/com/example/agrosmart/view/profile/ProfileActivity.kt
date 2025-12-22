package com.example.agrosmart.view.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.agrosmart.databinding.ActivityProfileBinding
import com.example.agrosmart.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.io.ByteArrayOutputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private var imageUri: Uri? = null
    private var imageString: String? = null
    private val TAG = "ProfileActivity"

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        if (imageUri != null) {
            binding.profileImage.setImageURI(imageUri)
            imageString = encodeImage(imageUri!!)
        }
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.updateProfileButton.isEnabled = !isLoading
        binding.nameEditText.isEnabled = !isLoading
        binding.cityEditText.isEnabled = !isLoading
        binding.passwordEditText.isEnabled = !isLoading
    }

    private fun loadUserInfo() {
        showLoading(true)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            binding.emailEditText.setText(user.email)
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(user.uid).get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val userProfile = document.toObject(User::class.java)
                    if (userProfile != null) {
                        binding.nameEditText.setText(userProfile.name)
                        binding.cityEditText.setText(userProfile.city)
                        binding.passwordEditText.setText(userProfile.password)
                        if (userProfile.profileImageString.isNotEmpty()) {
                            val decodedString = Base64.decode(userProfile.profileImageString, Base64.DEFAULT)
                            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                            binding.profileImage.setImageBitmap(decodedByte)
                        }
                    }
                }
                showLoading(false)
            }.addOnFailureListener { exception ->
                showLoading(false)
                Log.e(TAG, "Error getting user document from Firestore", exception)
            }
        } else {
            showLoading(false)
        }
    }

    private fun updateProfile() {
        showLoading(true)
        val user = FirebaseAuth.getInstance().currentUser ?: return

        val name = binding.nameEditText.text.toString()
        val city = binding.cityEditText.text.toString()
        val newPassword = binding.passwordEditText.text.toString()

        if (name.isBlank() || city.isBlank()) {
            Toast.makeText(this, "Name and City cannot be empty", Toast.LENGTH_SHORT).show()
            showLoading(false)
            return
        }

        // Update password if a new one is provided
        if (newPassword.isNotEmpty()) {
            if (newPassword.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                showLoading(false)
                return
            }
            user.updatePassword(newPassword).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                    // Continue to update Firestore data
                    updateFirestore(name, city, newPassword, imageString)
                } else {
                    Log.e(TAG, "Error updating password", task.exception)
                    handleUpdateError(task.exception ?: Exception("Unknown error updating password"))
                }
            }
        } else {
            // If no new password, just update Firestore
            updateFirestore(name, city, null, imageString)
        }
    }

    private fun updateFirestore(name: String, city: String, newPassword: String?, imageString: String?) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()
        val userMap = mutableMapOf<String, Any>()
        userMap["name"] = name
        userMap["city"] = city
        if (newPassword != null) {
            userMap["password"] = newPassword
        }
        if (imageString != null) {
            userMap["profileImageString"] = imageString
        }

        db.collection("users").document(user.uid).set(userMap, SetOptions.merge()).addOnSuccessListener {
            Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener { exception ->
            handleUpdateError(exception)
        }
    }

    private fun encodeImage(imageUri: Uri): String? {
        try {
            val inputStream = contentResolver.openInputStream(imageUri)
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream?.close()

            options.inSampleSize = calculateInSampleSize(options, 200, 200)
            options.inJustDecodeBounds = false

            val newInputStream = contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(newInputStream, null, options)
            newInputStream?.close()

            val outputStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
            val byteArray = outputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.outHeight to options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    private fun handleUpdateError(exception: Exception) {
        showLoading(false)
        Toast.makeText(this, "An error occurred: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
        Log.e(TAG, "Error updating profile", exception)
    }
}
