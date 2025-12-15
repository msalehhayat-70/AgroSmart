package com.example.agrosmart.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SocialViewModel : ViewModel() {

    enum class PostStatus { POSTING, SUCCESS, FAILED }

    private val _postStatus = MutableLiveData<PostStatus>()
    val postStatus: LiveData<PostStatus> = _postStatus

    fun createPost(title: String, description: String, imageUri: Uri?) {
        _postStatus.value = PostStatus.POSTING

        // Simulate a network delay
        android.os.Handler().postDelayed({
            // In a real app, this would interact with a repository to upload the post.
            // For now, we just simulate success.
            _postStatus.value = PostStatus.SUCCESS
        }, 2000)
    }
}
