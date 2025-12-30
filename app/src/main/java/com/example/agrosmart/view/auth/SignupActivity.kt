package com.example.agrosmart.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.agrosmart.databinding.ActivitySignupBinding
import com.example.agrosmart.model.User
import com.example.agrosmart.view.dashboard.DashboardActivity
import com.example.agrosmart.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: AuthViewModel by viewModels()
    private val TAG = "SignupActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners() {
        binding.loginRedirectText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signupButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.name = binding.nameEditText.text.toString()
            viewModel.email = binding.emailEditText.text.toString()
            viewModel.password = binding.passwordEditText.text.toString()
            viewModel.city = binding.cityEditText.text.toString()
            viewModel.signup()
        }
    }

    private fun setupObservers() {
        viewModel.user.observe(this) { firebaseUser ->
            if (firebaseUser != null) {
                Log.d(TAG, "Signup successful, creating user document in Firestore.")
                createUserDocumentInFirestore(firebaseUser)
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            if (error.isNotEmpty()) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createUserDocumentInFirestore(firebaseUser: FirebaseUser) {
        val db = FirebaseFirestore.getInstance()
        val user = User(
            name = viewModel.name,
            email = viewModel.email,
            city = viewModel.city,
            password = viewModel.password, // Storing password as requested
            profileImageString = "" // Initially empty
        )

        db.collection("users").document(firebaseUser.uid).set(user)
            .addOnSuccessListener {
                Log.d(TAG, "User document created successfully in Firestore.")
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error creating user document in Firestore", e)
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Error creating profile: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
