package com.example.agrosmart.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.databinding.ActivityDashboardBinding
import com.example.agrosmart.model.User
import com.example.agrosmart.view.ChatbotActivity
import com.example.agrosmart.view.articles.ArticleListFragment
import com.example.agrosmart.view.auth.LoginActivity
import com.example.agrosmart.view.ecommerce.EcommerceFragment
import com.example.agrosmart.view.ecommerce.MyOrdersFragment
import com.example.agrosmart.view.pamra.PamraFragment
import com.example.agrosmart.view.profile.ProfileActivity
import com.example.agrosmart.view.socialmedia.SMCreatePostFragment
import com.example.agrosmart.view.socialmedia.SocialMediaPostsFragment
import com.example.agrosmart.view.weather.WeatherFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private val TAG = "DashboardActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbarAndDrawer()
        setupBottomNavigation()
        setupFab()

        if (savedInstanceState == null) {
            setCurrentFragment(DashboardFragment(), "dashFrag")
            binding.appBarMain.bottomNav.selectedItemId = R.id.bottomNavHome
        }
    }

    override fun onResume() {
        super.onResume()
        updateNavHeader()
    }

    private fun setupToolbarAndDrawer() {
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.appBarMain.toolbar, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun setupBottomNavigation() {
        binding.appBarMain.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottomNavAPMC -> setCurrentFragment(PamraFragment(), "pamraFrag")
                R.id.bottomNavHome -> setCurrentFragment(DashboardFragment(), "dashFrag")
                R.id.bottomNavEcomm -> setCurrentFragment(EcommerceFragment(), "ecommItemFrag")
                R.id.bottomNavPost -> setCurrentFragment(SocialMediaPostsFragment(), "socialFrag")
            }
            true
        }
    }

    private fun setupFab() {
        binding.fabAiChat.setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java))
        }
    }

    private fun updateNavHeader() {
        val headerView = binding.navView.getHeaderView(0)
        val navUsername = headerView.findViewById<TextView>(R.id.navbarUserName)
        val navUserEmail = headerView.findViewById<TextView>(R.id.navbarUserEmail)
        val navUserImage = headerView.findViewById<ImageView>(R.id.navbarUserImage)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            Log.d(TAG, "User is logged in: ${user.email}")
            navUserEmail.text = user.email
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(user.uid).get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val userProfile = document.toObject(User::class.java)
                    if (userProfile != null) {
                        Log.d(TAG, "User data: Name=${userProfile.name}, Image=${userProfile.profileImageString}")
                        navUsername.text = userProfile.name
                        if (userProfile.profileImageString.isNotEmpty()) {
                            val decodedString = android.util.Base64.decode(userProfile.profileImageString, android.util.Base64.DEFAULT)
                            val decodedByte = android.graphics.BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                            navUserImage.setImageBitmap(decodedByte)
                        }
                    }
                }
            }.addOnFailureListener { exception ->
                Log.e(TAG, "Error getting user document from Firestore", exception)
            }
        } else {
            Log.d(TAG, "User is not logged in.")
        }

        headerView.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miItem1 -> setCurrentFragment(EcommerceFragment(), "ecommListFrag")
            R.id.miItem2 -> setCurrentFragment(PamraFragment(), "pamraFrag")
            R.id.miItem3 -> setCurrentFragment(SMCreatePostFragment(), "createPostFrag")
            R.id.miItem4 -> setCurrentFragment(SocialMediaPostsFragment(), "socialFrag")
            R.id.miItem5 -> setCurrentFragment(WeatherFragment(), "weatherFrag")
            R.id.miItem6 -> setCurrentFragment(ArticleListFragment(), "articleListFrag")
            R.id.miItem7 -> setCurrentFragment(MyOrdersFragment(), "myOrdersFrag")
            R.id.miItem8 -> showLogoutDialog()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setCurrentFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.appBarMain.frameLayout.id, fragment, tag)
            commit()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Log Out")
            .setMessage("Do you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
