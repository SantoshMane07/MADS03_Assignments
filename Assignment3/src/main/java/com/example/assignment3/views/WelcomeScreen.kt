package com.example.assignment3.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.assignment3.navigation.TopLevelDestination
import com.example.assignment3.utils.getRandomUsersList
import com.example.assignment3.viewmodels.DatabaseViewModel
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen (databaseViewModel: DatabaseViewModel,navController: NavHostController){

//    LaunchedEffect(Unit) {
//        if(databaseViewModel.authenticated.value){
//            navController.navigate(TopLevelDestination.UsersListScreen.route)
//            return@LaunchedEffect
//        }
//    }

    Scaffold(
        topBar = {
            TopAppBar( title = { Text(text = "User Directory", style = MaterialTheme.typography.titleLarge)
                               },
                colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) {innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { addUsersToDatabase(databaseViewModel,navController) }) {
                Text(text = "Add Users")
            }
        }
    }
}
// Add Random 5 users to Database
fun addUsersToDatabase(databaseViewModel: DatabaseViewModel,navController: NavController) {

    //After adding Setting Authenticated as true
    databaseViewModel.saveAuthenticated(true)
    //Adding Lists of 5 Users to Room_Database
    databaseViewModel.insertListOfUsers(getRandomUsersList())
    //After adding Navigating to Users List Screen
    navController.navigate(TopLevelDestination.UsersListScreen.route)
}
