package com.nikol.detail_impl.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.detail_api.ContentType
import com.nikol.detail_impl.presentation.mvi.intent.DetailIntent
import com.nikol.detail_impl.presentation.viewModel.DetailRouter
import com.nikol.detail_impl.presentation.viewModel.DetailViewModel
import com.nikol.di.scope.viewModelWithRouter
import com.nikol.viewmodel.BindLifecycle

@Composable
fun DetailScreenUi(
    onBack: () -> Unit
) {
    val viewModel = viewModelWithRouter<DetailViewModel, DetailRouter> {
        object : DetailRouter {
            override fun onBack() = onBack()

            override fun toContent(contentType: ContentType, id: Int) {
                TODO("Not yet implemented")
            }
        }
    }
    BindLifecycle(viewModel)
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Button(
                onClick = { viewModel.setIntent(DetailIntent.NavigateBack) }
            ) {
                Text("Back")
            }
            Text("${state.contentType}")
            Button({viewModel.setIntent(DetailIntent.Update)}) {
                Text("Check")
            }
        }
    }
}