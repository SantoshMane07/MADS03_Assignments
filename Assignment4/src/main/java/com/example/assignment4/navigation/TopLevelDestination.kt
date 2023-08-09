package com.example.assignment4.navigation


sealed class TopLevelDestination(
    val title: String,
    val route: String
) {
    object ProfileScreen : TopLevelDestination(
        title = "Profile_Screen",
        route = "profile_screen"
    )

    object PostDetailsScreen : TopLevelDestination(
        title = "PostDetailsScreen",
        route = "post_detail_screen"
    )
}