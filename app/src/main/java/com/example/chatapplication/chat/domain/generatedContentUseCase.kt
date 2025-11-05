package com.example.chatapplication.chat.domain

import javax.inject.Inject

class GeneratedContentUseCase @Inject constructor(private val geminiRepository: GeminiRepository) {
    suspend operator fun invoke(prompt: String,apiKey: String): String {
        return geminiRepository.generateContent(prompt = prompt, apiKey = apiKey)
    }
}