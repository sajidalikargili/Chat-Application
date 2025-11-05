package com.example.chatapplication.chat.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed class ScreenRoute(val route:String){
    data object SplashScreen: ScreenRoute(ScreenName.SPLASH_SCREEEN.name)
    data object ChatScreen: ScreenRoute(ScreenName.CHAT_SCREEN.name)
}