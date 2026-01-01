package com.nikol.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class PersonUi(
    val id: Int,
    val name: String,
    val imageUrl: String?, // Полный URL
    val role: String       // "Director" или "Jack Sparrow"
)