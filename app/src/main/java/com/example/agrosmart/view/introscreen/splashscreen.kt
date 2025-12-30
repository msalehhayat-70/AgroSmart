package com.example.agrosmart.view.introscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.agrosmart.R
import com.example.agrosmart.view.auth.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        // Use lifecycleScope instead of Handler for modern approach
        lifecycleScope.launch {
            delay(2000) // 2 seconds delay
            startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
            finish()
        }
    }
}
