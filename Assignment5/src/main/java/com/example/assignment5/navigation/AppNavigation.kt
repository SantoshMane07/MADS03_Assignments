package com.example.assignment5.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.assignment5.models.DeletePostRequest
import com.example.assignment5.screens.HomeScreen
import com.example.assignment5.screens.LoginScreen
import com.example.assignment5.screens.PostDetailScreen
import com.example.assignment5.screens.ProfileScreen
import com.example.assignment5.viewmodels.DatabaseViewModel
import com.example.assignment5.viewmodels.HomeViewModel
import com.example.assignment5.viewmodels.LoginViewModel
import com.example.assignment5.viewmodels.PostDetailViewModel
import com.example.assignment5.viewmodels.ProfileViewModel


@Composable
fun AppNavigation(
    modifier: Modifier,
    navHostController: NavHostController,
    topLevelDestinationType: TopLevelDestination,
    bottomBarState:MutableState<Boolean>
) {
    NavHost(
        navController = navHostController,
        modifier = modifier,
        startDestination = topLevelDestinationType.route
    ) {
        //Home Screen
        composable(route = TopLevelDestination.HomeScreen.route) {
            bottomBarState.value = true
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navHostController,
                homeViewModel,
                { postId: Int -> navHostController.navigate(TopLevelDestination.PostDetailsScreen.route + "/${postId}") }
            )
        }

        //Post Detail Screen
        composable(
            route = TopLevelDestination.PostDetailsScreen.route + "/{postId}",
            arguments = listOf(navArgument("postId") {
                type = NavType.IntType
            })
        ) {
            bottomBarState.value = false
            val postDetailViewModel = hiltViewModel<PostDetailViewModel>()
            val databaseViewModel = hiltViewModel<DatabaseViewModel>()
            val postId = it.arguments?.getInt("postId") ?: return@composable
            PostDetailScreen(
                navHostController,
                postDetailViewModel,
                postId
            ) { postId: Int ->
                postDetailViewModel.deletePostById(
                    postId,
                    DeletePostRequest(databaseViewModel.getUserId())
                )
            }
        }

        //Profile Screen
        composable(route = TopLevelDestination.ProfileScreen.route) {
            bottomBarState.value = true
            val databaseViewModel = hiltViewModel<DatabaseViewModel>()
            val profileViewModel= hiltViewModel<ProfileViewModel>()
            ProfileScreen(navHostController, databaseViewModel,profileViewModel)
        }

        //Login Screen
        composable(route = TopLevelDestination.LoginScreen.route) {
            bottomBarState.value = false
            val loginViewModel = hiltViewModel<LoginViewModel>()
            val databaseViewModel = hiltViewModel<DatabaseViewModel>()
            LoginScreen(navHostController, loginViewModel,databaseViewModel)
        }
    }
}


