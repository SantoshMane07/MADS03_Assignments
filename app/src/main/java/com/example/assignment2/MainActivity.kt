package com.example.assignment2

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment2.ui.theme.Assignment2Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment2Theme {

                Scaffold(topBar = { TopAppBar(title = { Text(
                    text = "Users List", style = MaterialTheme.typography.titleLarge,
                )
                },colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primaryContainer)
                )

                }) {
                    //A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize().padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val context = LocalContext.current
                        // calling compose functions here
                        ItemContainer(
                            Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            onCardClick = {
                                Toast.makeText(context, "You have Clicked on $it Username",Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ItemContainer(modifier:Modifier, onCardClick:(String)->Unit){
    val usersList : MutableList<UserData> = remember{getRandomUsersList()}
    LazyColumn(modifier = Modifier.fillMaxSize(),content = {

        items(usersList){ user->
            Card(
                modifier = modifier,
                CircleShape,
                elevation = CardDefaults.cardElevation(8.dp)
            ){
                DisplayUserCard(user.userId, user.username,usersList.indexOf(user),
                    Modifier
                        .clickable { onCardClick(user.username) }
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
        }
    })
}

@Composable
private fun DisplayUserCard(id: Long, name: String, index: Int,modifier:Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        UserDetails(id, name,Modifier.wrapContentSize())
        UserItemIndex(index,Modifier)
    }
}

@Composable
private fun UserDetails(id:Long,name:String,modifier:Modifier) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ){
        Text(text = "UserId : $id", style = MaterialTheme.typography.titleMedium)
        Text(text = "Username : $name",style = MaterialTheme.typography.titleSmall)
    }
}
@Composable
private fun UserItemIndex(index:Int,modifier:Modifier) {
        Text(
            text = "$index",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 20.sp,
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .padding(0.dp, 10.dp, 0.dp, 0.dp)
        )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
//    ItemContainer()
    DisplayUserCard(12345678,"Santosh",100,Modifier)
}
//Data Class of User
data class UserData(
    val userId :Long,
    val username : String,
    val fullName : String,
    val email : String
)
//Random Functions
fun generateRandomAlphanumeric(length: Int, random: Random): String {
    val alphanumericChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { alphanumericChars[random.nextInt(alphanumericChars.size)] }
        .joinToString("")
}

fun generateRandomAlphabetical(minLength: Int, maxLength: Int, random: Random): String {
    val alphabetChars = ('a'..'z') + ('A'..'Z')
    val length = random.nextInt(minLength, maxLength + 1)
    return (1..length)
        .map { alphabetChars[random.nextInt(alphabetChars.size)] }
        .joinToString("")
}

fun generateRandomEmail(random: Random): String {
    val username = generateRandomAlphanumeric(8, random)
    val domain = generateRandomAlphabetical(5, 10, random)
    return "$username@$domain.com"
}
// Get Random 100 Users
private fun getRandomUsersList(): MutableList<UserData> {
    val userList = mutableListOf<UserData>()
    val random = Random.Default
    repeat(100) {
        val userId = random.nextLong(1_000_000_00L, 9_999_999_99L)
        val username = generateRandomAlphanumeric(6, random)
        val fullName = generateRandomAlphabetical(1, 20, random)
        val email = generateRandomEmail(random)

        val user = UserData(
            userId = userId,
            username = username,
            fullName = fullName,
            email = email
        )

        userList.add(user)
    }
    return userList
}

