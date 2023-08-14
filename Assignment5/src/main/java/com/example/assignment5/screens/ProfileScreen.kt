package com.example.assignment5.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.example.assignment5.ErrorUi
import com.example.assignment5.HorizontalSpacer
import com.example.assignment5.LoadingIndicator
import com.example.assignment5.LoadingUi
import com.example.assignment5.VerticalSpacer
import com.example.assignment5.debug
import com.example.assignment5.items
import com.example.assignment5.models.PostModel
import com.example.assignment5.navigation.TopLevelDestination
import com.example.assignment5.room_db.entities.User
import com.example.assignment5.viewmodels.DatabaseViewModel
import com.example.assignment5.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    databaseViewModel: DatabaseViewModel,
    profileViewModel: ProfileViewModel
) {
    //Getting the User and Profile Detail from Local DB
    val users by remember { databaseViewModel.users }.collectAsState(emptyList())
    val username by remember { databaseViewModel.username }.collectAsState("")
    //Getting Posts
    val posts = remember { profileViewModel.posts }.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        databaseViewModel.getAllUsersFlow()
        debug("user id ${databaseViewModel.getUserId()}")
        profileViewModel.refreshUserPosts()
        debug("After refresh is called")
    }
    //

    //
    if (users.isNotEmpty()) {
        Scaffold(topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = username,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primaryContainer)
            )
        }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                LazyVerticalGridProfileAndPosts(
                    modifier = Modifier,
                    userProfile = users[0],
                    posts = posts,
                    navController = navHostController,
                )
            }
        }
    }
    //
}

//Displaying Lists and Posts
//LazyVerticalGridPosts
@Composable
fun LazyVerticalGridProfileAndPosts(
    modifier: Modifier,
    userProfile: User,
    posts: LazyPagingItems<PostModel>,
    navController: NavHostController,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        //User Details here
        item(span = { GridItemSpan(maxLineSpan) }) {
            //Parent Column
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                //Child Column 1
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                ) {
                    ProfileDetail(
                        modifier = Modifier.fillMaxWidth(), userProfile = userProfile
                    )
                    //
                    Spacer(modifier = Modifier.height(10.dp))
                    //
                    Divider(
                        color = Color.Black, modifier = Modifier
                            .height(5.dp)
                            .fillMaxWidth()
                    )

                }
            }
        }
        // Posts here
        items(posts) { post ->
            GridPostItem(modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(180.dp),
                post = post,
                { postId: Int -> navController.navigate(TopLevelDestination.PostDetailsScreen.route + "/${postId}") })
        }
        //
        // Handling Append and loading of Posts page by page
        posts.apply {
            when (posts.loadState.refresh) {
                is LoadState.Loading -> {
                    item { LoadingIndicator(modifier = Modifier.fillMaxSize()) }
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
                    //item { LoadingIndicator(modifier = Modifier.fillMaxWidth()) }
                    item {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .size(180.dp), contentAlignment = Alignment.Center
                        ) {
                            LoadingIndicator(modifier = Modifier.fillMaxWidth())
                        }
                    }
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
///////


//ProfileDetail
@Composable
fun ProfileDetail(modifier: Modifier, userProfile: User) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ProfilePic(userProfile = userProfile)
            HorizontalSpacer(size = 10)
            UserStats(user = userProfile)
        }
        VerticalSpacer(size = 10)
        Text(text = userProfile.userName, style = MaterialTheme.typography.titleMedium)
        VerticalSpacer(size = 5)
        Text(text = userProfile.biography, style = MaterialTheme.typography.titleMedium)
    }
}

//ProfilePic
@Composable
private fun ProfilePic(userProfile: User) {

    SubcomposeAsyncImage(
        model = userProfile.profilePicUrl,
        contentDescription = "Profile Pic",
        loading = {
            LoadingUi(modifier = Modifier.size(10.dp))
        },
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .border(2.dp, Color.LightGray, CircleShape)
    )
}

//
//Grid
@Composable
fun GridPostItem(
    modifier: Modifier = Modifier,
    post: PostModel,
    navigateToPostDetailScreen: (Int) -> Unit
) {
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = post.url,
            contentDescription = "Post Image",
            loading = {
                LoadingUi(modifier = Modifier.size(10.dp))
            },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { navigateToPostDetailScreen(post.postId) }
        )
    }
}

//
@Composable
fun UserStats(modifier: Modifier = Modifier, user: User) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatsView(name = "Posts", value = user.postsCount)

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        StatsView(name = "Followers", value = user.followers)
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        StatsView(name = "Following", value = user.following)
    }
}

//Stats View
@Composable
private fun StatsView(modifier: Modifier = Modifier, name: String, value: Int) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )
        VerticalSpacer(size = 8)
        Text(text = name, style = MaterialTheme.typography.titleMedium)
    }
}
