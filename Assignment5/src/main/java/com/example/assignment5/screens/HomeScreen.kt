package com.example.assignment5.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.example.assignment5.ErrorUi
import com.example.assignment5.viewmodels.HomeViewModel
import com.example.assignment5.HorizontalSpacer
import com.example.assignment5.LoadingIndicator
import com.example.assignment5.LoadingUi
import com.example.assignment5.R
import com.example.assignment5.VerticalSpacer
import com.example.assignment5.debug
import com.example.assignment5.items
import com.example.assignment5.models.PostModel
import com.example.assignment5.viewmodels.DatabaseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onPostClick: (Int) -> Unit
) {
    //Getting all posts from network
    val posts: LazyPagingItems<PostModel> =
        remember { homeViewModel.posts }.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        homeViewModel.refreshPosts()
    }
    //
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Home", style = MaterialTheme.typography.titleLarge) },
            colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primaryContainer)
        )
    }) {
        //Displaying Lists of Posts
        DisplayPostsList(
            posts,
            Modifier
                .padding(it)
                .fillMaxSize(),
            onPostClick
        )
        //..
    }
}

//Display Post Compose function
@Composable
fun DisplayPostsList(
    posts: LazyPagingItems<PostModel>,
    modifier: Modifier,
    onPostClick: (Int) -> Unit
) {
    //
    var isOverflow by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    //
    LazyColumn(
        modifier = modifier, contentPadding = PaddingValues(8.dp)
    ) {
        items(posts) { post ->
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 0.dp, 0.dp, 20.dp)
                    .clickable { onPostClick(post.postId) },
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                ) {
                    PostHeading(post)
                    VerticalSpacer(size = 5)
                    PostPic(post)
                    VerticalSpacer(size = 15)
                    LikesAndComments(post = post)
                    VerticalSpacer(size = 5)
                    //PostCaption(post = post)
                    //Post Caption
                    Text(
                        text = post.caption,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = if (expanded) Int.MAX_VALUE else 2,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = {
                            isOverflow = it.hasVisualOverflow
                        }
                    )
                    AnimatedVisibility(visible = isOverflow) {
                        Text(
                            modifier = Modifier.clickable { expanded = true },
                            text = "See more",
                            color = Color.Gray
                        )
                    }
                }
            }
        }
        // Handling Append and loading of Posts page by page
        posts.apply {
            when (posts.loadState.refresh) {
                is LoadState.Loading -> {
                    item { LoadingUi(modifier = Modifier.fillMaxSize()) }
                }

                is LoadState.Error -> {
                    item {
                        Column(
                            Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
                        ) {
                            ErrorUi(
                                modifier = Modifier.fillMaxWidth(),
                                errorMsg = "Failed to load data",
                                r_code = 0
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(onClick = { posts.retry() }) {
                                Text(text = "Retry")
                            }
                        }
                    }
                }

                else -> {}
            }
            when (posts.loadState.append) {
                is LoadState.Loading -> {
                    item { LoadingUi(modifier = Modifier.fillMaxWidth()) }
                }

                is LoadState.Error -> {
                    item {
                        ErrorUi(
                            modifier = Modifier.fillMaxWidth(),
                            errorMsg = "Failed to load data",
                            r_code = 0
                        )
                    }
                }

                else -> {}
            }
        }
    }
}

//Post Heading
@Composable
fun PostHeading(post: PostModel) {
    Row(
        modifier = Modifier
            .padding(10.dp, 0.dp, 0.dp, 0.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfilePic(post)
        HorizontalSpacer(size = 10)
        // User name
        Text(text = post.user.userName, style = MaterialTheme.typography.titleMedium)
    }
}

//Profile Pic
@Composable
private fun ProfilePic(post: PostModel) {
    SubcomposeAsyncImage(
        model = post.user.profilePicUrl, contentDescription = "Profile Pic", loading = {
            LoadingUi(modifier = Modifier.size(10.dp))
        }, contentScale = ContentScale.Crop, modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
    )
}

//PostPic
@Composable
fun PostPic(post: PostModel) {
    SubcomposeAsyncImage(
        model = post.url,
        contentDescription = "Post Image",
        loading = {
            LoadingUi(modifier = Modifier.size(10.dp))
        },
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}

//LikesAndComments
@Composable
fun LikesAndComments(post: PostModel) {
    Row {
        Likes(likesCount = post.likesCount.toString())
        HorizontalSpacer(size = 6)
        Comments(commentsCount = post.commentsCount.toString())
    }
}

//PostCaption
//@Composable
//fun PostCaption(post: PostModel) {
//    Text(
//        text = post.caption,
//        style = MaterialTheme.typography.titleMedium,
//        maxLines = if (expanded) Int.MAX_VALUE else 2,
//    )
//}

//Likes and Comments
@Composable
fun Likes(likesCount: String) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Likes")
        HorizontalSpacer(size = 6)
        Text(text = likesCount, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun Comments(commentsCount: String) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = R.drawable.comment), contentDescription = "Comment icon"
        )
        HorizontalSpacer(size = 6)
        Text(text = commentsCount, style = MaterialTheme.typography.titleMedium)
    }
}
