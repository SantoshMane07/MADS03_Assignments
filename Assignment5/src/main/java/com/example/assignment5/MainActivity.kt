package com.example.assignment5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.assignment5.navigation.AppNavigation
import com.example.assignment5.navigation.TopLevelDestination
import com.example.assignment5.navigation.bottomNavItems
import com.example.assignment5.ui.theme.Assignment5Theme
import com.example.assignment5.viewmodels.DatabaseViewModel
import com.google.androidgamesdk.gametextinput.Settings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            setContent {
                Assignment5Theme {
                    //
                    val navHostController: NavHostController = rememberNavController()
                    val databaseViewModel: DatabaseViewModel = hiltViewModel<DatabaseViewModel>()
                    val id = databaseViewModel.getUserId()
                    //debug("$id")
                    var topLevelDestinationType: TopLevelDestination
                    //
                    //val currentBackStackEntry by navHostController.currentBackStackEntryAsState()
                    var bottomBarState = remember { mutableStateOf(true) }
//                    LaunchedEffect(currentBackStackEntry) {
//                        bottomBarState.value =
//                            currentBackStackEntry?.destination?.route != TopLevelDestination.LoginScreen.route
//                    }
                    //
                    Scaffold(
                        bottomBar = {
                            AnimatedVisibility(
                                visible = bottomBarState.value,
                                enter = fadeIn() + expandVertically(),
                                exit = fadeOut() + shrinkVertically()
                            ) {
                                BottomBar(
                                    destinations = bottomNavItems,
                                    onNavigateToDestination = { destination ->
                                        navHostController.navigate(destination.route) {
                                            popUpTo(navHostController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    currentDestination = navHostController.currentBackStackEntryAsState().value?.destination
                                )
                            }
                        }
                    ) {
                        if (id != 0) {
                            topLevelDestinationType = TopLevelDestination.HomeScreen
                        } else {
                            topLevelDestinationType = TopLevelDestination.LoginScreen
                        }
                        AppNavigation(
                            modifier = Modifier.padding(it),
                            navHostController = navHostController,
                            topLevelDestinationType = topLevelDestinationType,
                            bottomBarState = bottomBarState
                        )
                    }
                }
            }
        }
    }
}

//
@Composable
private fun BottomBar(
    modifier: Modifier = Modifier,
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any {
                it.route?.contains(destination.route, true) ?: false
            } ?: false

            val icon = if (selected) destination.selectedIcon else destination.unselectedIcon

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = { Icon(imageVector = icon, contentDescription = destination.route) },
                label = { Text(text = destination.title) },
                alwaysShowLabel = false
            )
        }
    }
}