package com.nikol.home_impl.presentation.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun TVScreen(
    text: String,
    onBackPressed: () -> Unit
) {
    BackHandler(onBack = onBackPressed)
    Text(text = text)
}