package com.nikol.home_impl.domain.parameters

import com.nikol.ui.model.MediaType

data class ContentParameter(
    val mediaType: MediaType,
    val period: Period = Period.Day
)

enum class Period() {
    Week,
    Day
}

