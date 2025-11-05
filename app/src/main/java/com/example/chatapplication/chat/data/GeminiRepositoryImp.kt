package com.example.chatapplication.chat.data

import com.example.chatapplication.chat.domain.GeminiRepository
import javax.inject.Inject

class  GeminiRepositoryImp @Inject constructor(private val geminiApiService: GeminiApiService): GeminiRepository {
    override suspend fun generateContent(
        prompt: String,
        apiKey: String
    ): String {
        return try {
            val request= GeminiRequest(
                contents = listOf(
                    GeminiRequest.Content(
                        parts = listOf(
                            GeminiRequest.Content.Part(text =prompt)
                        )
                    )
                )
            )
            val response=geminiApiService.generateContentSuspend(apiKey,request);
            response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "No response from AI"
        } catch (e: Exception) {
            "Error: ${e.message ?: "Failed to generate content. Please check your internet connection and try again."}"
        }
    }
}