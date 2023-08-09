package com.example.assignment4.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.assignment4.screens.PostDetailsScreen
import com.example.assignment4.screens.ProfileScreen
import com.example.assignment4.viewmodels.NetworkingViewModel

@Composable
fun AppNavigation(
    modifier: Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = TopLevelDestination.ProfileScreen.route
    ) {
        composable(route = TopLevelDestination.ProfileScreen.route) {
            val networkingViewModel = hiltViewModel<NetworkingViewModel>()
            ProfileScreen(navController, networkingViewModel)
        }

        composable(
            route = TopLevelDestination.PostDetailsScreen.route + "/{postId}",
            arguments = listOf(navArgument("postId") {
                type = NavType.LongType
            })
        ) {
            val networkingViewModel = hiltViewModel<NetworkingViewModel>()
            val postId = it.arguments?.getLong("postId") ?: return@composable
            PostDetailsScreen(navController, networkingViewModel, postId)
        }
    }
}




