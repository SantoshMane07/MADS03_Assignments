package com.example.assignment3.views

import android.service.autofill.UserData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.assignment3.model.room_db.entites.User
import com.example.assignment3.utils.getRandomUser
import com.example.assignment3.viewmodels.DatabaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(databaseViewModel: DatabaseViewModel,navController: NavHostController,userId:Long){
    val userid:Long = userId
    val user = remember {databaseViewModel.getSingleUser(userId)}
    Scaffold(
        topBar = {
            TopAppBar( title = { Text(text = "User Directory", style = MaterialTheme.typography.titleLarge)
            }, colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primaryContainer),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {innerPadding->
        DisplayUserDetail(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            user,
            navController = navController
        ) {
            databaseViewModel.deleteSingleUser(user)
            navController.popBackStack()
        }
    }
}

@Composable
fun DisplayUserDetail(
    modifier: Modifier,
    user:User,
    navController: NavController,
    deleteUser:()->Unit
) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Welcome to User Detail Screen", style =  MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        }
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),

            ) {
                Text(text = "UserId: ${user.userId}")
                Text(text = "Username: ${user.userName}")
                Text(text = "Full name: ${user.fullName}")
                Text(text = "Email: ${user.email}")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { deleteUser() }) {
                Text(text = "Delete User")
            }
        }
    }
}

//Preview Here
@Preview(showSystemUi = true)
@Composable
fun PreviewUserDetailScreen(){
    //UserDetailScreen()
    //DisplayUserDetail(modifier = Modifier.padding(0.dp), getRandomUser())
}