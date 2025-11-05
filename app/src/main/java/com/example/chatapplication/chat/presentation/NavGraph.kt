package com.example.chatapplication.chat.presentation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapplication.chat.core.ScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
@Composable
fun NavGraph(modifier: Modifier = Modifier) {
val navController= rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.SplashScreen.route){
     composable(ScreenRoute.SplashScreen.route){
         SplashScreen(modifier = modifier, navController = navController)
     }
        composable(ScreenRoute.ChatScreen.route){
            ChatScreeen(modifier = modifier)
        }
    }
}