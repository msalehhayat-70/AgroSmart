package com.example.agrosmart.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.agrosmart.databinding.ActivityLoginBinding
import com.example.agrosmart.view.dashboard.DashboardActivity
import com.example.agrosmart.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // The new splash screen API is handled by the theme.
        // The system will automatically switch to your app's theme
        // once the app is ready. No manual splash screen logic is needed.

        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners() {
        binding.signupRedirectText.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.loginEmail = binding.emailEditText.text.toString()
            viewModel.loginPassword = binding.passwordEditText.text.toString()
            viewModel.login()
        }
    }

    private fun setupObservers() {
        viewModel.user.observe(this) { user ->
            if (user != null) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            if (error.isNotEmpty()) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        }
    }
}
