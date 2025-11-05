package com.example.chatapplication.chat.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapplication.chat.data.model.ChatMessage
import com.example.chatapplication.chat.domain.GeneratedContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val text: String) : UiState()
    data class Error(val message: String) : UiState()
}

@HiltViewModel
class GeminiViewModel @Inject constructor(private val generatedContentUseCase: GeneratedContentUseCase):
    ViewModel(){
        private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
       private  val _message=MutableStateFlow<List<ChatMessage>>(emptyList())
        val uiState = _uiState.asStateFlow()
        val message=_message.asStateFlow()

        private val _generatedText = MutableStateFlow<String>("")
        val generatedText = _generatedText.asStateFlow()

    fun  generatedContent(prompt:String,apiKey:String){
        if (prompt.isBlank()) {
            _uiState.value = UiState.Error("Please enter a prompt")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _message.value += ChatMessage(prompt, isUser = true)
            try {
                val result = generatedContentUseCase.invoke(prompt = prompt, apiKey = apiKey)
                _generatedText.value = result
                _uiState.value = UiState.Success(result)
                _message.value += ChatMessage(result,isUser = false)
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown error occurred"
                _uiState.value = UiState.Error(errorMessage)
                _generatedText.value = "Error: $errorMessage"
            }
        }
    }


}