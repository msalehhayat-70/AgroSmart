package com.example.agrosmart.network

data class GoogleChatRequest(
    val contents: List<Content>
)

data class Content(
    val parts: List<Part>
)

data class Part(
    val text: String
)

data class GoogleChatResponse(
    val candidates: List<Candidate>
)

data class Candidate(
    val content: Content
)
