package com.nikol.reelup.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nikol.auth_api.Auth
import com.nikol.nav_impl.navApi.MainFeatureApi
import com.nikol.nav_impl.navApi.RootFeatureApi
import com.nikol.reelup.navigation.mainGraph
import com.nikol.reelup.ui.theme.ReelUpTheme
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listFeatures = getKoin().getAll<RootFeatureApi>()
        val mainListFeature = getKoin().getAll<MainFeatureApi>()
        enableEdgeToEdge()
        setContent {
            ReelUpTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = Auth,
                        modifier = Modifier.fillMaxSize(),
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec = tween(500)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec = tween(500)
                            )
                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec = tween(500)
                            )
                        },
                        popExitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec = tween(500)
                            )
                        }
                    ) {
                        mainGraph(navController, mainListFeature)

                        listFeatures.forEach {
                            it.registerFeature(navController, this)
                        }
                    }
                }
            }
        }
    }
}
