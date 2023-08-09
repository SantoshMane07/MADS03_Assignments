package com.example.assignment4.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.assignment4.ErrorUi
import com.example.assignment4.LoadingIndicator
import com.example.assignment4.LoadingUi
import com.example.assignment4.R
import com.example.assignment4.debug
import com.example.assignment4.items
import com.example.assignment4.models.PostModel
import com.example.assignment4.models.ProfileModel
import com.example.assignment4.navigation.TopLevelDestination
import com.example.assignment4.networkingUiStateForProfile.NetworkingUiState
import com.example.assignment4.viewmodels.NetworkingViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController, networkingViewModel: NetworkingViewModel
) {
    //getting the User Profile Detail
    val networkingUiState by remember { networkingViewModel.networkingUiState }.collectAsState()
    val username by remember { networkingViewModel.username }.collectAsState("")
    val posts = remember { networkingViewModel.posts }.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit, block = {
        networkingViewModel.getUserDetail()
    })
    //
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
            when (networkingUiState) {
                is NetworkingUiState.Loading -> {
                    LoadingUi(modifier = Modifier.fillMaxSize())
                }

                is NetworkingUiState.Success -> {
                    LazyVerticalGridProfileAndPosts(
                        modifier = Modifier,
                        userProfile = (networkingUiState as NetworkingUiState.Success).profileDetail,
                        posts = posts,
                        navController = navController
                    )
                }

                is NetworkingUiState.Error -> {
                    ErrorUi(
                        modifier = Modifier.fillMaxSize(),
                        errorMsg = (networkingUiState as NetworkingUiState.Error).msg
                    )
                }

                else -> {}
            }
        }

//        LazyVerticalGridProfileAndPosts(
//            modifier = Modifier.fillMaxSize()
//        )
    }
}

//LazyVerticalGridPosts
@Composable
fun LazyVerticalGridProfileAndPosts(
    modifier: Modifier,
    userProfile: ProfileModel,
    posts: LazyPagingItems<PostModel>,
    navController: NavHostController
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
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
                    AddressCard(
                        modifier = Modifier.fillMaxWidth(), userProfile = userProfile
                    )
                }
            }
        }
        // Subscription and Status here
        item(span = { GridItemSpan(maxLineSpan) }) {
            GridItemSubscriptionAndStatus(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .padding(5.dp)
                    .fillMaxWidth(),
                userProfile = userProfile
            )
        }
        // Posts here
        items(posts) { post ->
            GridPostItem(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(180.dp),
                post = post,
                { postId: Long -> navController.navigate(TopLevelDestination.PostDetailsScreen.route + "/${postId}") }
            )
        }
        // Handling Append and loading of Posts page by page
        posts.apply {
            when (posts.loadState.refresh) {
                is LoadState.Loading -> {
                    item { LoadingIndicator(modifier = Modifier.fillMaxSize()) }
                }

                is LoadState.Error -> {
                    item {
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            ErrorUi(
                                modifier = Modifier.fillMaxWidth(),
                                errorMsg = "Failed to load data",
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
                        )
                    }
                }

                else -> {}
            }
        }
    }
}

//ProfileDetail
@Composable
fun ProfileDetail(modifier: Modifier, userProfile: ProfileModel) {
    Row(
        //horizontalArrangement = Arrangement,
        verticalAlignment = Alignment.CenterVertically, modifier = modifier
    ) {
        ProfilePic(userProfile = userProfile)
        //
        Spacer(modifier = Modifier.width(20.dp))
        //
        Column(
            modifier = Modifier.weight(1f)
//                .background(Color.Red),

        ) {
            Text(
                text = "${userProfile.first_name}  ${userProfile.last_name}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = userProfile.employment.jobTitle, style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = userProfile.email, style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

//ProfilePic
@Composable
private fun ProfilePic(userProfile: ProfileModel) {

    SubcomposeAsyncImage(
        model = userProfile.profilePic,
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

//AddressCard
@Composable
fun AddressCard(modifier: Modifier, userProfile: ProfileModel) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(20f),
        border = BorderStroke(2.dp, Color.Black),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
//                    .background(Color.Red)
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = userProfile.address.street_name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = userProfile.address.city, style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = userProfile.address.country, style = MaterialTheme.typography.titleMedium
                )
            }
            Column(
                modifier = Modifier,
//                    .background(Color.Green)
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = userProfile.address.street_address,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = userProfile.address.state, style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = userProfile.address.zip_code,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

//
@Composable
fun GridItemSubscriptionAndStatus(modifier: Modifier = Modifier, userProfile: ProfileModel) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = userProfile.subscription.plan, style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = userProfile.subscription.status, style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            color = Color.Black, modifier = Modifier
                .height(5.dp)
                .fillMaxWidth()
        )
    }
}

//
@Composable
fun GridPostItem(
    modifier: Modifier = Modifier,
    post: PostModel,
    navigateToPostDetailScreen: (Long) -> Unit
) {
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = post.download_url,
            contentDescription = "Post Image",
            loading = {
                LoadingUi(modifier = Modifier.size(10.dp))
            },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { navigateToPostDetailScreen(post.id) }
        )
    }
}

//Preview Here
@Preview(showSystemUi = true)
@Composable
fun Preview() {
    //ProfileScreen()
    //ProfilePic()
}

