package com.example.assignment3.navigation

sealed class TopLevelDestination (
    val title:String,
    val route:String
    ){
    object WelcomeScreen : TopLevelDestination(
        title = "Welcome Screen",
        route = "welcome_screen"
    )
    object  UsersListScreen : TopLevelDestination(
        title = "Users List Screen",
        route = "users_list_screen"
    )
    object  UserDetailScreen : TopLevelDestination(
        title = "User Detail Screen",
        route = "user_detail_screen"
    )
}