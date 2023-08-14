package com.example.assignment5.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.assignment5.ErrorUi
import com.example.assignment5.HorizontalSpacer
import com.example.assignment5.LoadingUi
import com.example.assignment5.VerticalSpacer
import com.example.assignment5.models.PostModel
import com.example.assignment5.networking_ui_states.PostDetailScreenUiState
import com.example.assignment5.viewmodels.PostDetailViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    navHostController: NavHostController,
    postDetailViewModel: PostDetailViewModel,
    postId: Int,
    onDeleteClick: (Int) -> Unit
) {
    // Get user ID from local DataBase and perform delete operation
    //
    val uriHandler = LocalUriHandler.current
    val postDetailScreenUiState by remember { postDetailViewModel.postDetailScreenUiState }.collectAsState()
    LaunchedEffect(Unit) {
        postDetailViewModel.getPostById(postId)
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Post Details", style = MaterialTheme.typography.titleLarge)
        },
            colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primaryContainer),
            navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            actions = {
                IconButton(onClick = { onDeleteClick(postId) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            })
    }) {
        //..
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(10.dp)
        ) {
            when (postDetailScreenUiState) {
                is PostDetailScreenUiState.Loading -> {
                    LoadingUi(modifier = Modifier.fillMaxSize())
                }

                is PostDetailScreenUiState.Success -> {
                    DisplayPost(
                        post = (postDetailScreenUiState as PostDetailScreenUiState.Success).post,
                        modifier = Modifier,
                        onLinkClicked = {
                            uriHandler.openUri(it)
                        }
                    )
                    //..
                }

                is PostDetailScreenUiState.Error -> {
                    ErrorUi(
                        modifier = Modifier.fillMaxSize(),
                        errorMsg = (postDetailScreenUiState as PostDetailScreenUiState.Error).msg,
                        r_code = (postDetailScreenUiState as PostDetailScreenUiState.Error).r_code
                    )
                }

                is PostDetailScreenUiState.AfterDelete -> {
                    Text(
                        text = "Post Deleted Successfully",
                        style = MaterialTheme.typography.titleLarge
                    )
                    //navHostController.popBackStack() //(Not Working - TopAppBar popBackStack is Working but not this) error:kotlinx.coroutines.JobCancellationException: Job was cancelled; job=SupervisorJobImpl{Cancelling}@61245a8 in UserAllPostsPagingSource
                }

                else -> {}
            }
        }
        //..
    }
}

//Display Post
@Composable
fun DisplayPost(modifier: Modifier, post: PostModel, onLinkClicked: (String) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            PostPic(post = post)
            VerticalSpacer(size = 5)
            LikesAndCommentsCount(post = post, modifier = Modifier)
            VerticalSpacer(size = 8)
            PostDetail(post = post, modifier = Modifier, onLinkClicked = onLinkClicked)
        }
    }
}

//LikesAndCommentsCount
@Composable
fun LikesAndCommentsCount(
    post: PostModel,
    modifier: Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        VerticalCountBox(count = post.likesCount, text = "Likes", Modifier)
        VerticalCountBox(count = post.commentsCount, text = "Comments", Modifier)
    }
}

// Likes and Comments Count Box
@Composable
fun VerticalCountBox(count: Int, text: String, modifier: Modifier) {
    Box(
        modifier = Modifier
            .size(110.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = count.toString(), style = MaterialTheme.typography.titleMedium)
            VerticalSpacer(size = 8)
            Text(text = text, style = MaterialTheme.typography.titleMedium)
        }
    }
}

// Post Details
@Composable
fun PostDetail(modifier: Modifier, post: PostModel, onLinkClicked: (String) -> Unit) {
    Column {
        ShowTitleAndDescription(title = "Created by: ", desc = post.user.fullName)
        ShowTitleAndDescription(title = "Created at: ", desc = post.createdAt)
        ShowTitleAndDescriptionforUrl(title = "URL: ", desc = post.url, onLinkClicked)
    }
}

//
@Composable
fun ShowTitleAndDescription(title: String, desc: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        HorizontalSpacer(size = 3)
        Text(text = desc, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ShowTitleAndDescriptionforUrl(title: String, desc: String, onLinkClicked: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        HorizontalSpacer(size = 3)
        Text(
            text = desc,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable { onLinkClicked(desc) },
        )
    }
}
//