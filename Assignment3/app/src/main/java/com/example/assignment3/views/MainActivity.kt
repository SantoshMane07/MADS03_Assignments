package com.example.assignment3.views

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.assignment3.navigation.AppNavigation
import com.example.assignment3.navigation.TopLevelDestination
import com.example.assignment3.ui.theme.Assignment3Theme
import com.example.assignment3.viewmodels.DatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val databaseViewModel by viewModels<DatabaseViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            databaseViewModel.authenticated.collect{ authenticateStatus->
                setContent {
                    Assignment3Theme {
                        Scaffold {
                            val navController:NavHostController = rememberNavController()
                            AppNavigation(modifier = Modifier.padding(it), navController =navController, isAuthenticated = authenticateStatus)
                        }
                    }
                }
            }
        }
    }
}