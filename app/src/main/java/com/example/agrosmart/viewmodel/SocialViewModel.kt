package com.example.agrosmart.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agrosmart.model.SMPost

class SocialViewModel : ViewModel() {

    enum class PostStatus { POSTING, SUCCESS, FAILED }

    private val _postStatus = MutableLiveData<PostStatus>()
    val postStatus: LiveData<PostStatus> = _postStatus

    private val _posts = MutableLiveData<List<SMPost>>()
    val posts: LiveData<List<SMPost>> = _posts

    // In a real app, this would interact with a repository to upload the post.
    fun createPost(title: String, description: String, imageUri: Uri?) {
        _postStatus.value = PostStatus.POSTING

        // Simulate a network delay
        android.os.Handler().postDelayed({
            // Simulate a successful post creation
            _postStatus.value = PostStatus.SUCCESS
        }, 2000)
    }

    fun loadPosts() {
        // Placeholder data
        val placeholderPosts = listOf(
            SMPost("User 1", "First Post", "This is the description for the first post.", System.currentTimeMillis(), null, "text", "1"),
            SMPost("User 2", "Second Post", "This is the description for the second post.", System.currentTimeMillis(), "https://via.placeholder.com/400x200.png?text=Post+Image", "image", "2")
        )
        _posts.postValue(placeholderPosts)
    }
}
