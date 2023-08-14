package com.example.assignment5

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems

inline fun <T : Any> LazyListScope.items(
    items: LazyPagingItems<T>,
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit
) = items(
    count = items.itemCount,
) {
    items[it]?.let { it1 -> itemContent(it1) }
}
//
inline fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    crossinline itemContent: @Composable LazyGridItemScope.(item: T) -> Unit
) = items(
    count = items.itemCount,
) {
    items[it]?.let { it1 -> itemContent(it1) }
}
//

// Loading ui
@Composable
fun LoadingUi(
    modifier: Modifier,
    size: Dp = 40.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 4.dp
) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size)
                .align(Alignment.Center),
            color = color,
            strokeWidth = strokeWidth
        )
    }
}

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 4.dp
) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size)
                .align(Alignment.Center),
            color = color,
            strokeWidth = strokeWidth
        )
    }
}

//Error ui
@Composable
fun ErrorUi(
    modifier: Modifier = Modifier,
    errorMsg: String,
    r_code:Int
) {
    Box(modifier = modifier) {
        if(r_code == 403){
            Text(text = "You cannot delete this Post, this Post does not belong to you",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleLarge
            )
        }
        else{
            Text(text = errorMsg, color = MaterialTheme.colorScheme.error)
        }
    }
}

//Spacers
@Composable
fun VerticalSpacer(size: Int) {
    Spacer(modifier = Modifier.height(size.dp))
}

@Composable
fun HorizontalSpacer(size: Int) {
    Spacer(modifier = Modifier.width(size.dp))
}

//Debug
fun debug(msg: String) {
    Log.d("Nwk", "debug: $msg")
}
