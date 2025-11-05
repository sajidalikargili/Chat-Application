package com.example.chatapplication.chat.presentation
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatapplication.R
import com.example.chatapplication.chat.core.ScreenRoute
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier ,navController: NavController) {
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(ScreenRoute.ChatScreen.route){
            popUpTo(ScreenRoute.SplashScreen.route){
                inclusive=true
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Text(text = "Chat Application", style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(10.dp))
        Image(painter = painterResource(R.drawable.ic_launcher_background),contentDescription = null)

    }
}