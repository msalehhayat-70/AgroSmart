package com.example.agrosmart.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.agrosmart.databinding.ActivitySignupBinding
import com.example.agrosmart.view.dashboard.DashboardActivity
import com.example.agrosmart.viewmodel.AuthViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: AuthViewModel by viewModels()

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
            viewModel.mobNo = binding.mobNoEditText.text.toString()
            viewModel.email = binding.emailEditText.text.toString()
            viewModel.city = binding.cityEditText.text.toString()
            viewModel.password = binding.passwordEditText.text.toString()
            viewModel.signup()
        }

        binding.googleSignupButton.setOnClickListener {
            Toast.makeText(this, "Google Sign-In is currently disabled.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers() {
        viewModel.signup().observe(this) { result ->
            binding.progressBar.visibility = View.GONE
            if (result == "Success") {
                Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Signup Failed: $result", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
