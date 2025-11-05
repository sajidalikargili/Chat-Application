package com.example.chatapplication.chat.domain

interface GeminiRepository {
    suspend fun  generateContent(prompt: String,apiKey: String): String
}