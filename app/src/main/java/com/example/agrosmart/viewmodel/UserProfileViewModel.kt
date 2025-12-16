package com.example.agrosmart.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agrosmart.model.SMPost
import com.example.agrosmart.model.UserData

class UserProfileViewModel : ViewModel() {

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    private val _userPosts = MutableLiveData<List<SMPost>>()
    val userPosts: LiveData<List<SMPost>> = _userPosts

    fun loadUserProfile() {
        // Placeholder data. In a real app, this would come from a repository.
        _userData.postValue(
            UserData(
                name = "John Doe",
                email = "john.doe@example.com",
                city = "New York",
                profileImage = "https://via.placeholder.com/150",
                backImage = "https://via.placeholder.com/600x200",
                about = "A passionate farmer and tech enthusiast.",
                posts = listOf("post1", "post2")
            )
        )

        _userPosts.postValue(
            listOf(
                SMPost("John Doe", "My First Post", "Loving the new AgroSmart app!", System.currentTimeMillis(), null, "text", "1"),
                SMPost("John Doe", "Great Harvest", "Had a great harvest this season.", System.currentTimeMillis(), "https://via.placeholder.com/400x200.png?text=Harvest", "image", "2")
            )
        )
    }

    fun updateProfileImage(imageUri: Uri) {
        // Placeholder logic for updating profile image
    }

    fun updateBackgroundImage(imageUri: Uri) {
        // Placeholder logic for updating background image
    }

    fun updateUserField(about: String, city: String) {
        // Placeholder logic for updating user fields
    }

    fun deleteUserPost(postId: String) {
        // Placeholder logic for deleting a post
    }
}
