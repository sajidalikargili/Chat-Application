package com.example.chatapplication
import android.content.ClipData
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import com.example.chatapplication.chat.core.Constant
import com.example.chatapplication.chat.data.model.ChatMessage
import com.example.chatapplication.chat.presentation.ChatScreeen
import com.example.chatapplication.chat.presentation.GeminiViewModel
import com.example.chatapplication.chat.presentation.NavGraph
import com.example.chatapplication.chat.presentation.UiState
import com.example.chatapplication.ui.theme.ChatApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(geminiViewModel: GeminiViewModel = hiltViewModel(), name: String, modifier:Modifier) {
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

@Composable
fun ChatBubble(message: ChatMessage) {
    val backgroundColor =
        if (message.isUser) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.secondaryContainer

    val alignment = if (message.isUser) Alignment.End else Alignment.Start

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            modifier = Modifier
                .widthIn(max = 280.dp)
                .padding(4.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ChatApplicationTheme {
//        Greeting("Android")
//    }
//}