package com.nikol.home_impl.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.di.scope.viewModelWithRouter
import com.nikol.home_impl.presentation.viewModel.MovieRouter
import com.nikol.home_impl.presentation.viewModel.MovieViewModel

@Composable
internal fun MovieScreen(text: String) {

    val viewModel = viewModelWithRouter<MovieViewModel, MovieRouter> {
        MovieRouter { }
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = text)
    }
}