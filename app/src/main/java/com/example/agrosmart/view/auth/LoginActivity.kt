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
        viewModel.authResult.observe(this) { result ->
            binding.progressBar.visibility = View.GONE
            if (result == "Success") {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
