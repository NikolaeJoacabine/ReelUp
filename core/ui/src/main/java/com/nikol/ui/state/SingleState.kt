package com.nikol.ui.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed interface SingleState<out T> {
    data object Loading : SingleState<Nothing>
    data object Error : SingleState<Nothing>
    data class Success<T>(val content: T) : SingleState<T>
}