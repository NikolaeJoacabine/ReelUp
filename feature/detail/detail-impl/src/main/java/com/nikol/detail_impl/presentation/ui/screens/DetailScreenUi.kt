package com.nikol.detail_impl.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.detail_api.DetailScreen
import com.nikol.detail_impl.R
import com.nikol.detail_impl.presentation.mvi.intent.DetailIntent
import com.nikol.detail_impl.presentation.ui.components.DetailActionsSheet
import com.nikol.detail_impl.presentation.ui.components.DetailContentBody
import com.nikol.detail_impl.presentation.ui.components.FloatingBackButton
import com.nikol.detail_impl.presentation.viewModel.DetailRouter
import com.nikol.detail_impl.presentation.viewModel.DetailViewModel
import com.nikol.di.scope.viewModelWithRouter
import com.nikol.ui.state.SingleState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenUi(
    onBack: () -> Unit,
    onDetail: (detailScreen: DetailScreen) -> Unit
) {
    val viewModel = viewModelWithRouter<DetailViewModel, DetailRouter> {
        object : DetailRouter {
            override fun onBack() = onBack()
            override fun toContent(detailScreen: DetailScreen) = onDetail(detailScreen)
        }
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when (val contentState = state.state) {
                is SingleState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is SingleState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(stringResource(R.string.detail_error_loading))
                            Button(onClick = { viewModel.setIntent(DetailIntent.Update) }) {
                                Text(stringResource(R.string.detail_retry))
                            }
                        }
                    }
                }

                is SingleState.Success -> {
                    DetailContentBody(
                        content = contentState.content,
                        isLoading = state.isLoading,
                        onRefresh = { viewModel.setIntent(DetailIntent.Update) },
                        onMore = { viewModel.setIntent(DetailIntent.ToggleAction) },
                        clickDetail = { viewModel.setIntent(DetailIntent.NavigateOtherDetail(it)) },
                        toggleInfo = { viewModel.setIntent(DetailIntent.ToggleDescription) }
                    )

                    if (state.showBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { viewModel.setIntent(DetailIntent.ToggleAction) },
                            containerColor = Color.Transparent,
                            dragHandle = null
                        ) {
                            DetailActionsSheet(
                                onRate = { },
                                onWriteReview = { },
                                onShare = { },
                                onToggleWatchlist = { },
                            )
                        }
                    }
                }
            }
            FloatingBackButton(
                onClick = { viewModel.setIntent(DetailIntent.NavigateBack) },
                modifier = Modifier.padding(
                    top = padding.calculateTopPadding() + 8.dp,
                    start = 16.dp
                )
            )
        }
    }
}