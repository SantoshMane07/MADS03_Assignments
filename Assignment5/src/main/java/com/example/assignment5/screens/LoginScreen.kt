package com.example.assignment5.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.assignment5.ErrorUi
import com.example.assignment5.LoadingUi
import com.example.assignment5.debug
import com.example.assignment5.models.UserModel
import com.example.assignment5.navigation.TopLevelDestination
import com.example.assignment5.networking_ui_states.LoginScreenUiState
import com.example.assignment5.room_db.entities.User
import com.example.assignment5.viewmodels.DatabaseViewModel
import com.example.assignment5.viewmodels.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel,
    databaseViewModel: DatabaseViewModel
) {
    val loginScreenUiState by remember { loginViewModel.loginScreenUiState }.collectAsState()

    Column {
        when (loginScreenUiState) {
            is LoginScreenUiState.Loading -> {
                LoadingUi(modifier = Modifier.fillMaxSize())
            }

            is LoginScreenUiState.Success -> {
                //(loginScreenUiState as LoginScreenUiState.Success).userModel,
                val randomUser: UserModel =
                    (loginScreenUiState as LoginScreenUiState.Success).userModel
                val user = User(
                    userId = randomUser.userId,
                    userName = randomUser.userName,
                    fullName = randomUser.fullName,
                    email = randomUser.email,
                    biography = randomUser.biography,
                    postsCount = randomUser.postsCount,
                    followers = randomUser.followers,
                    following = randomUser.following,
                    profilePicUrl = randomUser.profilePicUrl
                )
                databaseViewModel.insertUser(user = user)
                navHostController.navigate(TopLevelDestination.HomeScreen.route)
                //navHostController.popBackStack()
            }

            is LoginScreenUiState.Error -> {
                ErrorUi(
                    modifier = Modifier.fillMaxSize(),
                    errorMsg = (loginScreenUiState as LoginScreenUiState.Error).msg,
                    r_code = (loginScreenUiState as LoginScreenUiState.Error).r_code
                )
            }

            is LoginScreenUiState.Initial -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Hey there,",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Button(onClick = {
                        loginViewModel.getRandomUser()
                    }) {
                        Text(text = "Login")
                    }
                }
            }

            else -> {}
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    //LoginScreen()
}