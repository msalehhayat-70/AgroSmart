package com.example.agrosmart.viewmodel

<<<<<<< HEAD
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agrosmart.model.SMPost
=======
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrosmart.model.SMPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
>>>>>>> main

class SocialViewModel : ViewModel() {

    enum class PostStatus { POSTING, SUCCESS, FAILED }

    private val _postStatus = MutableLiveData<PostStatus>()
    val postStatus: LiveData<PostStatus> = _postStatus

    private val _posts = MutableLiveData<List<SMPost>>()
    val posts: LiveData<List<SMPost>> = _posts

<<<<<<< HEAD
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
=======
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val postsCollection = firestore.collection("posts")

    init {
        loadPosts()
    }

    private fun addPostToFirestore(post: SMPost) {
        postsCollection.add(post)
            .addOnSuccessListener {
                _postStatus.value = PostStatus.SUCCESS
            }
            .addOnFailureListener {
                _postStatus.value = PostStatus.FAILED
            }
    }

    private fun getResizedBitmap(imageStream: InputStream, maxWidth: Int, maxHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(imageStream, null, options)

        var inSampleSize = 1
        val height = options.outHeight
        val width = options.outWidth

        if (height > maxHeight || width > maxWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while ((halfHeight / inSampleSize) >= maxHeight && (halfWidth / inSampleSize) >= maxWidth) {
                inSampleSize *= 2
            }
        }

        options.inJustDecodeBounds = false
        options.inSampleSize = inSampleSize

        imageStream.reset() // Reset the stream to be decoded again
        return BitmapFactory.decodeStream(imageStream, null, options)!!
    }

    private fun encodeImage(context: Context, imageUri: Uri): String? {
        return try {
            context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
                // Use a resettable stream
                val resettableStream = inputStream.buffered()
                resettableStream.mark(resettableStream.available())

                val bitmap = getResizedBitmap(resettableStream, 500, 500) // Resize bitmap to a max of 500x500
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos) // Compress to 50% quality
                val b = baos.toByteArray()
                Base64.encodeToString(b, Base64.DEFAULT)
            }
        } catch (e: Exception) {
            Log.e("SocialViewModel", "Error encoding image", e)
            null
        }
    }


    fun createPost(context: Context, title: String, description: String, imageUri: Uri?) {
        _postStatus.value = PostStatus.POSTING
        val currentUser = auth.currentUser

        val userId = currentUser?.uid ?: "test_user"
        val userName = currentUser?.displayName ?: "Test User"

        viewModelScope.launch(Dispatchers.IO) {
            val encodedImage = if (imageUri != null) encodeImage(context, imageUri) else null

            withContext(Dispatchers.Main) {
                val post = SMPost(
                    name = userName,
                    title = title,
                    description = description,
                    imageUrl = encodedImage,
                    uploadType = if (encodedImage != null) "image" else "text",
                    userID = userId
                )
                addPostToFirestore(post)
            }
        }
    }

    fun deletePost(post: SMPost) {
        postsCollection.document(post.id).delete()
            .addOnFailureListener {
                // Optionally notify the user that deletion failed
            }
    }

    private fun loadPosts() {
        postsCollection.orderBy("timeStamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("SocialViewModel", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    val postList = snapshots.toObjects(SMPost::class.java)
                    _posts.value = postList
                }
            }
>>>>>>> main
    }
}
