package com.example.chatapplication.chat.core

import com.example.chatapplication.BuildConfig

object Constant {
    const val baseUrl="https://generativelanguage.googleapis.com/"
    val apiKey: String get() = BuildConfig.GEMINI_API_KEY
}