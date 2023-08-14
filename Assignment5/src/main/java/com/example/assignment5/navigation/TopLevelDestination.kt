package com.example.assignment5.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TopLevelDestination(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object HomeScreen : TopLevelDestination(
        title = "Home",
        route = "home_screen",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    object PostDetailsScreen : TopLevelDestination(
        title = "Post Details",
        route = "post_detail_screen",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    object ProfileScreen : TopLevelDestination(
        title = "Profile",
        route = "profile_screen",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )

    object LoginScreen : TopLevelDestination(
        title = "Login",
        route = "login_screen",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
}
//
val bottomNavItems = listOf(
    TopLevelDestination.HomeScreen,
    TopLevelDestination.ProfileScreen,
)