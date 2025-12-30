package com.example.agrosmart.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class SMPost(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val title: String = "",
    val description: String = "",
    @ServerTimestamp
    val timeStamp: Date? = null,
    val imageUrl: String? = null,
    val uploadType: String = "",
    val userID: String = ""
)
