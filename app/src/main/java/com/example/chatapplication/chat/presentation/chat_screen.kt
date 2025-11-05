package com.example.chatapplication.chat.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chatapplication.ChatBubble
import com.example.chatapplication.chat.core.Constant

@Composable
fun ChatScreeen( modifier:Modifier) {
    val geminiViewModel: GeminiViewModel = hiltViewModel()
    val uiState by geminiViewModel.uiState.collectAsState()
    val messages by geminiViewModel.message.collectAsState()
    var prompt by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth(), reverseLayout = true){
            items(messages.reversed()){message->
                ChatBubble(message)
            }
        }
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
            OutlinedTextField(
                value = prompt,
                onValueChange = { prompt = it },
                label = { Text("Enter your prompt") },
                placeholder = { Text("Ask me anything...") },
                modifier = Modifier.weight(1f),
                enabled = uiState !is UiState.Loading)
            Spacer(modifier = Modifier.width(10.dp))
            // Send button
            Button(
                onClick = {
                    geminiViewModel.generatedContent(prompt = prompt, apiKey = Constant.apiKey)
                    prompt=" "
                },
                enabled = uiState !is UiState.Loading && prompt.isNotBlank()
            ) {
                if (uiState is UiState.Loading) CircularProgressIndicator(modifier = Modifier.width(20.dp), strokeWidth = 2.dp) else Text(text = "Send")

            }
        }


    }

}