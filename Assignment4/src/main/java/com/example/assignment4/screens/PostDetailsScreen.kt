package com.example.assignment4.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.assignment4.ErrorUi
import com.example.assignment4.LoadingUi
import com.example.assignment4.R
import com.example.assignment4.models.PostModel
import com.example.assignment4.networkingUiStateForProfile.NetworkingUiState
import com.example.assignment4.networkingUiStateForProfile.NetworkingUiStateForSinglePost
import com.example.assignment4.viewmodels.NetworkingViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailsScreen(
    navController: NavHostController,
    networkingViewModel: NetworkingViewModel,
    postId: Long,
) {
    val uriHandler = LocalUriHandler.current
    //
    val networkingUiStateForSinglePost by remember { networkingViewModel.networkingUiStateForSinglePost }.collectAsState()
    LaunchedEffect(Unit) {
        networkingViewModel.getSinglePost(postId)
    }
    //
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Post Details", style = MaterialTheme.typography.titleLarge)
        },
            colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primaryContainer),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            })
    }) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            when (networkingUiStateForSinglePost) {
                is NetworkingUiStateForSinglePost.Loading -> {
                    LoadingUi(modifier = Modifier.fillMaxSize())
                }

                is NetworkingUiStateForSinglePost.Success -> {
                    PostDetail(
                        uriHandler = uriHandler,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        postDetails = (networkingUiStateForSinglePost as NetworkingUiStateForSinglePost.Success).postDetails
                    )
                }

                is NetworkingUiStateForSinglePost.Error -> {
                    ErrorUi(
                        modifier = Modifier.fillMaxSize(),
                        errorMsg = (networkingUiStateForSinglePost as NetworkingUiStateForSinglePost.Error).msg
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
fun PostDetail(uriHandler: UriHandler, modifier: Modifier, postDetails: PostModel) {
    Column(
        modifier = modifier
    ) {
        //Post Pic
        SubcomposeAsyncImage(
            model = postDetails.download_url,
            contentDescription = "Post Image",
            loading = {
                LoadingUi(modifier = Modifier.size(10.dp))
            },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(450.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(20.dp))
        //Post Detail
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Column {
                //Row 1
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Author: ",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = postDetails.author,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                //Row 2
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Width: ",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = postDetails.width.toString(),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Height: ",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = postDetails.height.toString(),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                //Row 3
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "URL : ",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = postDetails.download_url,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.clickable { uriHandler.openUri(postDetails.download_url) },
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewPostDetailScreen() {
    //PostDetailsScreen(1)
}
