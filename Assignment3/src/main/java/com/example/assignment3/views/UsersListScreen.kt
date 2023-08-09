package com.example.assignment3.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.assignment3.model.room_db.entites.User
import com.example.assignment3.navigation.TopLevelDestination
import com.example.assignment3.utils.getRandomUser
import com.example.assignment3.utils.getRandomUsersList
import com.example.assignment3.viewmodels.DatabaseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreen(databaseViewModel: DatabaseViewModel,navController: NavHostController){
    // Collecting Users State Flow
    val users by remember { databaseViewModel.users }.collectAsState(emptyList())
    val usersList = databaseViewModel.getAllUsers()
    if(usersList.isEmpty()){
        databaseViewModel.saveAuthenticated(false)
    }
    LaunchedEffect(Unit) {
        databaseViewModel.getAllUsersFlow()
    }
    //
    Scaffold(
        topBar = {
            TopAppBar( title = { Text(text = "User Directory", style = MaterialTheme.typography.titleLarge)
            }, colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) {innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            //Calling UsersList LazyColumn composable function here
            DisplayUsersList(
                modifier = Modifier
                    .weight(1f),
                users = users,
                navController = navController,
                databaseViewModel = databaseViewModel
            )
        }
    }
}
//USersList Lazycolumn Composable function here
@Composable
fun DisplayUsersList(
    modifier: Modifier,
    users: List<User>,
    navController: NavController,
    databaseViewModel:DatabaseViewModel
){
    LazyColumn(modifier = modifier){
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Welcome to User List Screen", style =  MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
            }

        }
        items(users){user->
            // Creating Ui for each user item
            UserItem(
                modifier = Modifier
                    .padding(10.dp),
                user,
                users,
            ) { navController.navigate(TopLevelDestination.UserDetailScreen.route + "/${user.userId}") }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { databaseViewModel.inserSingletUser(getRandomUser())}) {
            Text(text = "Add User")
        }
    }
}
//Ui for each user item here
@Composable
fun UserItem(modifier: Modifier , user:User,users: List<User>,onUserCardClicked:(Long)->Unit){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(8.dp)
    ){
        //Parent
        Column(
            modifier = Modifier.fillMaxWidth().clickable { onUserCardClicked(user.userId) },
            verticalArrangement = Arrangement.Center
        ) {
            //Childs
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround

            ) {
                //Child 1
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = "UserId: ${user.userId}", style = MaterialTheme.typography.titleMedium)
                    Column(
                        modifier =Modifier,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Full name:", style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = user.fullName
                        )
                    }
                }
                //Child 2
                Column(
                    modifier = Modifier
                ) {
                    Text(text = "Username: ${user.userName}", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Email: ${user.email}", style = MaterialTheme.typography.titleSmall)
                }
            }
            //Circular Box To represent row number
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 10.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Box (
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center,
                ){
                    Text(text = "${users.indexOf(user)+1}")
                }
            }

        }
    }
}
