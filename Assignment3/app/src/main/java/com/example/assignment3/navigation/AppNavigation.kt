package com.example.assignment3.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assignment3.ui.theme.Assignment3Theme
import com.example.assignment3.viewmodels.DatabaseViewModel
import com.example.assignment3.views.UserDetailScreen
import com.example.assignment3.views.UsersListScreen
import com.example.assignment3.views.WelcomeScreen

@Composable
fun AppNavigation(
    modifier:Modifier,
    navController: NavHostController,
    isAuthenticated:Boolean
){
    val databaseViewModel:DatabaseViewModel = hiltViewModel<DatabaseViewModel>()
    // Selecting Start Destination
    val startDestination = remember(isAuthenticated) {
        if (isAuthenticated) {
            TopLevelDestination.UsersListScreen.route
        } else {
            TopLevelDestination.WelcomeScreen.route
        }
    }
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ){
        composable(route = TopLevelDestination.WelcomeScreen.route){
            WelcomeScreen(databaseViewModel=databaseViewModel,navController = navController)
        }
        composable(route = TopLevelDestination.UsersListScreen.route){
            UsersListScreen(databaseViewModel=databaseViewModel,navController = navController)
        }
        composable(route = TopLevelDestination.UserDetailScreen.route + "/{userId}",
                arguments = listOf(
                    navArgument("userId"){
                        type = NavType.LongType
                    }
                )
            ){
            val userId = it.arguments?.getLong("userId") ?: return@composable
            UserDetailScreen(databaseViewModel=databaseViewModel,navController = navController,userId)
        }
    }
}