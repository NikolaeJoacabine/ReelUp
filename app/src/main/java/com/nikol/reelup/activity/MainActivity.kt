package com.nikol.reelup.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nikol.auth_api.Auth
import com.nikol.auth_impl.presentation.nav.authFeature
import com.nikol.reelup.navigation.mainGraph
import com.nikol.reelup.ui.theme.ReelUpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReelUpTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Auth,
                    modifier = Modifier.fillMaxSize()
                ){
                    authFeature(navController)
                    mainGraph(navController)
                }
            }
        }
    }
}
