package com.example.agrosmart.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agrosmart.model.SMPost
import com.example.agrosmart.model.UserData
import java.util.Date

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
                SMPost(name = "John Doe", title = "My First Post", description = "Loving the new AgroSmart app!", timeStamp = Date(System.currentTimeMillis()), imageUrl = null, uploadType = "text", userID = "1"),
                SMPost(name = "John Doe", title = "Great Harvest", description = "Had a great harvest this season.", timeStamp = Date(System.currentTimeMillis()), imageUrl = "https://via.placeholder.com/400x200.png?text=Harvest", uploadType = "image", userID = "2")
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
